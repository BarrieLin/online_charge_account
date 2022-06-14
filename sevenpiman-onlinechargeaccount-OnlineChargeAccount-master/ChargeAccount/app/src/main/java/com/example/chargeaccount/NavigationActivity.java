package com.example.chargeaccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.chargeaccount.entity.OcaUser;
import com.example.chargeaccount.utils.DisplayUtils;
import com.example.chargeaccount.viewmodel.MainViewModel;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.chargeaccount.temp.TempData.ip;
import static com.example.chargeaccount.temp.TempData.ocaUser;

public class NavigationActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radiogroup;
    private RadioButton rb_flow;
    private RadioButton rb_center;

    private MainViewModel viewModel;

    //Fragment Object
    private Fragment fg_flow,fg_bill,fg_form,fg_center;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initViewModel();



        fManager = getSupportFragmentManager();
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        //RadioGroup的点击事件监听
        radiogroup.setOnCheckedChangeListener(this);

        //获取首页单选按钮，并设置为选中状态
        rb_flow = (RadioButton) findViewById(R.id.rb_flow);
        rb_flow.setChecked(true);
        //获取个人中心单选按钮
        rb_center = (RadioButton) findViewById(R.id.rb_center);

        //监听
        viewModel.isPush.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer fragmentId) {
                goFragment(fragmentId);
            }
        });
    }


    private void initViewModel() {
        viewModel=new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        okhttpGetUserMessage();
    }

    //Radio 点击事件设置
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);

        switch (checkedId){
            case R.id.rb_flow:
                if (fg_flow == null){
                    //第一次需要加载Fragment
                    fg_flow = new FlowFragment();
                    fTransaction.add(R.id.content,fg_flow);
                } else{
                    fTransaction.show(fg_flow);
                }
                break;
            case R.id.rb_bill:
                if (fg_bill == null){
                    fg_bill = new BillFragment();
                    fTransaction.add(R.id.content,fg_bill);
                }else{
                    fTransaction.show(fg_bill);
                }
                break;
            case R.id.rb_form:
                if (fg_form == null){
                    fg_form = new FormFragment();
                    fTransaction.add(R.id.content,fg_form);
                }else{
                    fTransaction.show(fg_form);
                }
                break;
            case R.id.rb_center:
                if (fg_center == null){
                    fg_center = new CenterFragment();
                    fTransaction.add(R.id.content,fg_center);
                }else{
                    fTransaction.show(fg_center);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有的Fragment
    private void hideAllFragment(FragmentTransaction fTransaction) {
        if(fg_flow != null)fTransaction.hide(fg_flow);
        if(fg_bill != null)fTransaction.hide(fg_bill);
        if(fg_form != null)fTransaction.hide(fg_form);
        if(fg_center != null)fTransaction.hide(fg_center);
    }

    //跳转函数
    public void goFragment(int fragmentId){
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);

        switch (fragmentId){
            case R.id.rb_flow:
                if (fg_flow == null){
                    //第一次需要加载Fragment
                    fg_flow = new FlowFragment();
                    fTransaction.add(R.id.content,fg_flow);
                } else{
                    fTransaction.show(fg_flow);
                }
                rb_flow.setChecked(true);
                break;
            case R.id.rb_center:
                if (fg_center == null){
                    //第一次需要加载Fragment
                    fg_center = new FlowFragment();
                    fTransaction.add(R.id.content,fg_center);
                } else{
                    fTransaction.show(fg_center);
                }
                rb_center.setChecked(true);
                break;
            default:
                break;
        }
        fTransaction.commit();
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("FirstActivity", String.valueOf(requestCode));
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    String returnedData = data.getStringExtra("data_return");
//                    Log.d("FirstActivity", returnedData);
//                }
//                break;
//            default:
//                ;
//        }
//    }

    //post方式查询用户信息
    private void okhttpGetUserMessage() {
        try {
            FormBody.Builder params = new FormBody.Builder();
            params.add("userId",ocaUser.getUserId());
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ip + "/show_user_msg")
                    .post(params.build())
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    Intent intent=new Intent();  //构建一个Intent
//                    intent.putExtra("data_return",R.id.rb_flow);  //数据内容
//                    setResult(RESULT_OK,intent);  //向上一个活动返回数据
//                    finish();  //结束这个活动
                    String backJson = response.body().string();
                    ocaUser = new Gson().fromJson(backJson, OcaUser.class);
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
