package com.example.chargeaccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chargeaccount.entity.MonSumOIncome;
import com.example.chargeaccount.entity.OcaChart;
import com.example.chargeaccount.utils.DailyPayChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DailyPayFormFragment extends Fragment {
    public static List<MonSumOIncome> listCurrentMonthOutcome;
    private View view;
    //x轴数据集合
    List<String> xValues;
    //y轴数据集合
    List<Float> yValues;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.form_dailypay, null);
        initViewID();
        return view;
    }
//插入统计图动态数据
    private void initViewID() {
//        RadioButton rbMoneySevenDay = view.findViewById(R.id.rb_dailypay);
//        //默认选中7天
//        rbMoneySevenDay.setChecked(true);
        DailyPayChart dailyPayChart = view.findViewById(R.id.dailypaychart);
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            xValues.add(i + "月");
            yValues.add((float) 0);
            if (listCurrentMonthOutcome != null){
                for (MonSumOIncome Outcome : listCurrentMonthOutcome) {
                    String[] time = Outcome.getDate().split("-");
                    String month = time[1];
                    if(Integer.parseInt(month) == i) {
                        Float outcome = Outcome.getOutOrIncome();
                        yValues.remove(i-1);
                        yValues.add(outcome);
                    }
                }
            }
            else {
            }
//            Random random = new Random();
//            xValues.add(i + "月");
//            yValues.add(random.nextInt(1000));
    }
        // xy轴集合自己添加数据
        dailyPayChart.setXValues(xValues);
        dailyPayChart.setYValues(yValues);
    }

    public static Fragment newInstance() {
        return new DailyPayFormFragment();
    }

}
