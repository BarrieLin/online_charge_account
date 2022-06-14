package com.example.chargeaccount;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.chargeaccount.entity.EnterStatus;
import com.example.chargeaccount.entity.OcaChart;
import com.example.chargeaccount.entity.OcaUser;
import com.example.chargeaccount.utils.PieChart;
import com.example.chargeaccount.utils.PieData;
import com.example.chargeaccount.viewmodel.MainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.chargeaccount.DailyIncomeFormFragment.listCurrentMonthIncome;
import static com.example.chargeaccount.DailyPayFormFragment.listCurrentMonthOutcome;
import static com.example.chargeaccount.temp.TempData.ip;
import static com.example.chargeaccount.temp.TempData.ocaUser;

public class FormFragment extends Fragment {
    protected View rootView;
    private TabLayout tabLayout;
    private PieChart pieChart;
    private List<PieData> pieData;
    private String[] type = {"餐饮","交通","购物","医疗","娱乐","学习","金融","转账"};

    private MainViewModel viewModel;

    private MutableLiveData<OcaChart> ocaChartMutableLiveData;
    public static final int COUNT=1;//连续绘制

    //总支出
    private TextView currOutcome;
    //总收入
    private TextView currIncome;
    //结余
    private TextView currremain;
    //平均支出
    private TextView avgOutcome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViewModel();
        initOcaChartData();
        rootView=inflater.inflate(R.layout.fragment_form,container,false);
        initView();

//        drawPiwChart(new OcaChart());
        return rootView;
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(requireActivity(),new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
    }

    private void initOcaChartData() {
        new Thread(){
            @Override
            public void run() {
                okhttpGetChartData();
            }
        }.start();
    }

    private void initView(){
        currOutcome = rootView.findViewById(R.id.curr_outcome);
        currIncome = rootView.findViewById(R.id.curr_income);
        currremain = rootView.findViewById(R.id.curr_remain);
        avgOutcome = rootView.findViewById(R.id.avg_outcome);
        pieChart=rootView.findViewById(R.id.piechart);

        tabLayout=rootView.findViewById(R.id.tablayout_form);
        tabLayout.addTab(tabLayout.newTab().setText("支出"));
        tabLayout.addTab(tabLayout.newTab().setText("收入"));
        ocaChartMutableLiveData=new MediatorLiveData<>();

        viewModel.isPush.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer.equals(R.id.rb_flow)){
                    initOcaChartData();
                }
            }
        });

        //观察者
        ocaChartMutableLiveData.observe(this, new Observer<OcaChart>() {
            @Override
            public void onChanged(OcaChart s) {
                //图表模块上面那部分
                currIncome.setText(s.getCurrentIncome().toString());
                currOutcome.setText(s.getCurrentOutcome().toString());
                Float remain = s.getCurrentIncome() - s.getCurrentOutcome();
                currremain.setText(remain.toString());
                avgOutcome.setText((s.getCurrentOutcome()/365) + "");
                //图表模块中间那部分
                listCurrentMonthIncome = s.getListCurrentMonthIncome();
                listCurrentMonthOutcome = s.getListCurrentMonthOutcome();
                initLineChart();
                //图表模块下面那个饼状图
                drawPiwChart(s);
            }
        });
    }

    private void initLineChart() {
        //默认月支出
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_form, DailyPayFormFragment.newInstance()).commit();
        initListener();
    }

    private void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                if (tab.getPosition() == 0) {
                    //支出
                    transaction.replace(R.id.framelayout_form, DailyPayFormFragment.newInstance()).commit();
                } else if (tab.getPosition() == 1) {
                    //收入
                    transaction.replace(R.id.framelayout_form, DailyIncomeFormFragment.newInstance()).commit();
            }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    //画图表
    private void drawPiwChart(OcaChart ocaChart){
        pieData=new ArrayList<>();
        for (int i=0;i<8;i++){
            if (ocaChart.getListCurrMonthTypeOutcome().get(i) != 0)
                pieData.add(new PieData(type[i], ocaChart.getListCurrMonthTypeOutcome().get(i)));
        }
        pieChart.setData(pieData,COUNT);
    }

    //获取图表所需数据，用于显示到图标模块上面的数据
    private void okhttpGetChartData() {
        try {
            FormBody.Builder params = new FormBody.Builder();
            params.add("userId",ocaUser.getUserId());
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ip + "/oca_chart")
                    .post(params.build())
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("FormFragment", "onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    OcaChart ocaChart = new Gson().fromJson(response.body().string(), OcaChart.class);
                    ocaChartMutableLiveData.postValue(ocaChart);
                }
            });

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
