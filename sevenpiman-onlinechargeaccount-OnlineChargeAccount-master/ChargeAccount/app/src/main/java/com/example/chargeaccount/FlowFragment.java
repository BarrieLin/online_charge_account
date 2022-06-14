package com.example.chargeaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chargeaccount.adapter.RecycleViewAdapter;
import com.example.chargeaccount.entity.OcaBill;
import com.example.chargeaccount.entity.OcaFlow;
import com.example.chargeaccount.utils.BasisTimesUtils;
import com.example.chargeaccount.viewmodel.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.chargeaccount.temp.TempData.ip;
import static com.example.chargeaccount.temp.TempData.ocaUser;


public class FlowFragment extends Fragment {
    private View view;
    private LinearLayout top_date;
    private TextView top_tv;
    private Calendar calendar =  Calendar.getInstance();
    private RecyclerView recyclerView;
    private RecycleViewAdapter mAdapter;
    private LinearLayoutManager manager;
    private ArrayList<OcaBill> mList=new ArrayList<>();//账单数据
    private MutableLiveData<OcaFlow> ocaBillsMutableLiveData;

    //设置月预算
    private TextView month_income;
    //月支出
    private TextView month_outcome;
    //结余
    private TextView month_remain;
    private MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_flow, container, false);
        initViewModel();
        initview();
        onClick();
        initData();


        return view;
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(requireActivity(),new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
    }

    //初始化
    private void initview() {
        viewModel.isPush.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer.equals(R.id.rb_flow)){
                    okhttpGetBillsYearMonData();
                }
            }
        });
//        viewModel.isPush.observe(this, new Observer<OcaBill>() {
//            @Override
//            public void onChanged(OcaBill ocaBill) {
//
//            }
//        });

        top_date = view.findViewById(R.id.top_date);
        top_tv = view.findViewById(R.id.top_tv);
        recyclerView=view.findViewById(R.id.rv_list);
        month_income = view.findViewById(R.id.month_income);
        month_outcome = view.findViewById(R.id.month_outcome);
        month_remain = view.findViewById(R.id.month_remain);
        //TODO
        manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);


        Date currtime = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] yearMonDay = dateFormat.format(currtime).split("-");
        top_tv.setText(yearMonDay[0] + "." + yearMonDay[1]);

        ocaBillsMutableLiveData = new MutableLiveData<>();
    }

    //点击事件
    private void onClick() {
        top_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYearMonthPicker();
            }
        });
    }


    private void showYearMonthPicker() {
        Date currtime = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] yearMonDay = dateFormat.format(currtime).split("-");
        int year = Integer.parseInt(yearMonDay[0]);
        int month = Integer.parseInt(yearMonDay[1]);
        int day = Integer.parseInt(yearMonDay[2]);
        BasisTimesUtils.showDatePickerDialog(getContext(), true, "选择日期", year, month, day,
                new BasisTimesUtils.OnDatePickerListener() {
                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {
//                        toast(year + "-" + month + "-" + dayOfMonth);
                        top_tv.setText(year+"."+month);
                        initData();
                    }

                    @Override
                    public void onCancel() {
//                        toast("cancle");
                    }
                }).setDayGone();
    }
    private void initData(){
        ocaBillsMutableLiveData.observe(this, new Observer<OcaFlow>() {
            @Override
            public void onChanged(OcaFlow ocaFlow) {
                //如果月收入非空月支出为空
                if (ocaFlow.getMonthIncome() != null && ocaFlow.getMonthOutcome() == null){
                    month_income.setText(String.format("%.2f",ocaFlow.getMonthIncome()));
                    month_outcome.setText("0.00");
                    month_remain.setText(String.format("%.2f",ocaFlow.getMonthIncome()));
                }
                //如果月收入为空月支出非空
                else if (ocaFlow.getMonthIncome() == null && ocaFlow.getMonthOutcome() != null){
                    month_income.setText("0.00");
                    month_outcome.setText(String.format("%.2f",ocaFlow.getMonthOutcome()) + "");
                    month_remain.setText("-" + String.format("%.2f",ocaFlow.getMonthOutcome()));
                }
                //如果月支出月收入都为空
                else if (ocaFlow.getMonthIncome() == null && ocaFlow.getMonthOutcome() == null){
                    month_income.setText("0.00");
                    month_outcome.setText("0.00");
                    month_remain.setText("0.00");
                }
                else {
                    month_income.setText(String.format("%.2f",ocaFlow.getMonthIncome()));
                    month_outcome.setText(String.format("%.2f",ocaFlow.getMonthOutcome()));
                    Float remain = ocaFlow.getMonthIncome() - ocaFlow.getMonthOutcome();
                    month_remain.setText(String.format("%.2f",remain));
                }
                mList = ocaFlow.getBillList();
                if (mAdapter==null){
                    mAdapter = new RecycleViewAdapter(getContext(),mList);
                    mAdapter.setOnItemClickListener(new RecycleViewAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, OcaBill bill) {
                            if(bill.getCheckStatus() == 1){
                                Intent intent = new Intent(view.getContext(), ChangeBillPayActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("billId",bill.getBillId());
                                bundle.putString("userId",bill.getUserId());
                                bundle.putFloat("payMoney",bill.getPayMoney());
                                bundle.putString("time",bill.getTime());
                                bundle.putInt("checkType",bill.getCheckType());
                                bundle.putString("remark",bill.getRemark());
                                bundle.putInt("checkStatus",bill.getCheckStatus());
                                intent.putExtras(bundle);
                                startActivityForResult(intent,1);
//                                view.getContext().startActivity(intent);
                                //                    startActivityForResult(intent,1);
                            }
                            else {
                                Intent intent = new Intent(view.getContext(), ChangeBillIncomeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("billId",bill.getBillId());
                                bundle.putString("userId",bill.getUserId());
                                bundle.putFloat("payMoney",bill.getPayMoney());
                                bundle.putString("time",bill.getTime());
                                bundle.putInt("checkType",bill.getCheckType());
                                bundle.putString("remark",bill.getRemark());
                                bundle.putInt("checkStatus",bill.getCheckStatus());
                                intent.putExtras(bundle);
                                //TODO
                                startActivityForResult(intent,1);
                            }

                        }
                    });
                    recyclerView.setAdapter(mAdapter);
                }else {
                    mAdapter.refresh(mList);
                }
            }
        });
        new Thread(){
            @Override
            public void run() {
                okhttpGetBillsYearMonData();
            }
        }.start();
    }

    //获取流水
    private void okhttpGetBillsYearMonData() {
        try {
            FormBody.Builder params = new FormBody.Builder();
            String[] yearAndMon = top_tv.getText().toString().trim().split("\\.");
//            params.add("userId","1");
            params.add("userId",ocaUser.getUserId());
            params.add("year",yearAndMon[0]);
            params.add("month",yearAndMon[1]);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ip + "/oca_select_flowData_byYearMon")
                    .post(params.build())
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("FlowFragment", "onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String backJson = response.body().string();

                    Type type = new TypeToken<OcaFlow>() {}.getType();
                    OcaFlow bills = new Gson().fromJson(backJson, type);
                    ocaBillsMutableLiveData.postValue(bills);
                }
            });

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public ArrayList<OcaBill> refresh(OcaBill bill){
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    int data_return = data.getIntExtra("data_return",0);
                    viewModel.isPush.postValue(data_return);
                }
                break;
            default:
                ;
        }
    }



}

