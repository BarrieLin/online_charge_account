package com.example.chargeaccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chargeaccount.entity.MonSumOIncome;
import com.example.chargeaccount.entity.OcaChart;
import com.example.chargeaccount.utils.DailyPayChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DailyIncomeFormFragment extends Fragment {
    public static List<MonSumOIncome> listCurrentMonthIncome;
    private View view;
    //x轴数据集合
    List<String> xValues;
    //y轴数据集合
    List<Float> yValues;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.form_dailyincom, null);
        initViewID();
        return view;
    }
    //插入统计图动态数据
    private void initViewID() {
//        RadioButton rbMoneySevenDay = view.findViewById(R.id.rb_dailypay);
//        //默认选中7天
//        rbMoneySevenDay.setChecked(true);
        DailyPayChart dailyPayChart = view.findViewById(R.id.dailyincomechart);
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
//            xValues.add(i + "月");
//            yValues.add((float) 0);
            xValues.add(i + "月");
            yValues.add((float) 0);
            if (listCurrentMonthIncome != null){
                for (MonSumOIncome income : listCurrentMonthIncome) {
                    String[] time = income.getDate().split("-");
                    String month = time[1];
                    if(Integer.parseInt(month) == i) {
                        Float outcome = income.getOutOrIncome();
                        yValues.remove(i-1);
                        yValues.add(outcome);
                    }
                }
            }
        }
        // xy轴集合自己添加数据
        dailyPayChart.setXValues(xValues);
        dailyPayChart.setYValues(yValues);
    }

    public static Fragment newInstance() {
        return new DailyIncomeFormFragment();
    }
}
