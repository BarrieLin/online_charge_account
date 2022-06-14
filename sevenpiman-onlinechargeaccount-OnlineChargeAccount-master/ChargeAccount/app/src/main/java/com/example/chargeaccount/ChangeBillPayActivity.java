package com.example.chargeaccount;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.OptionsPickerView;
import com.example.chargeaccount.entity.OcaBill;
import com.example.chargeaccount.viewmodel.MainViewModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.chargeaccount.temp.TempData.ip;

public class ChangeBillPayActivity extends AppCompatActivity {
    private EditText moneyChange;
    private TextView dateChange;
    private Calendar calendar= Calendar.getInstance(Locale.CHINA);
    private TextView typeChange;
    private EditText remarksChange;
    private Button btnChange;
    private LinearLayout btnCancel;
    private Button btnDelete;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_bill_pay);

        init();
        onClick();
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
    }

    //点击事件
    public void onClick(){
        moneyChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //删除按钮点击事件
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogMsg("确认删除？");
            }
        });

        //时间选择按钮点击事件
        dateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(ChangeBillPayActivity.this,  4,dateChange , calendar);
            }
        });

        //选择账单类型按钮
        typeChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView<String> mOptionsPickerView = new OptionsPickerView<>(ChangeBillPayActivity.this);
                final ArrayList<String> list = new ArrayList<>();
                list.add("餐饮");
                list.add("交通");
                list.add("购物");
                list.add("医疗");
                list.add("娱乐");
                list.add("学习");
                list.add("金融");
                list.add("转账");

                // 设置数据
                mOptionsPickerView.setPicker(list);

                // 设置选项单位
                mOptionsPickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int option1, int option2, int option3) {
                        String type = list.get(option1);
                        typeChange.setText(type);
                    }
                });
                mOptionsPickerView.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //保存点击事件
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OcaBill tempOcaBill = new OcaBill();
                OcaBill bill = getBill();
                tempOcaBill.setBillId(bill.getBillId());
                tempOcaBill.setUserId(bill.getUserId());
                tempOcaBill.setCheckStatus(bill.getCheckStatus());
                String check_Type = typeChange.getText().toString().trim();
                Integer checkType;
                if (check_Type.equals("餐饮"))
                    checkType=0;
                else if (check_Type.equals("交通"))
                    checkType=1;
                else if (check_Type.equals("购物"))
                    checkType=2;
                else if (check_Type.equals("医疗"))
                    checkType=3;
                else if (check_Type.equals("娱乐"))
                    checkType=4;
                else if (check_Type.equals("学习"))
                    checkType=5;
                else if (check_Type.equals("金融"))
                    checkType=6;
                else
                    checkType=7;
                tempOcaBill.setCheckType(checkType);
                tempOcaBill.setPayMoney(Float.parseFloat(moneyChange.getText().toString().trim()));
                tempOcaBill.setTime(dateChange.getText().toString().trim());
                tempOcaBill.setRemark(remarksChange.getText().toString().trim());

                //将获取到的数据打包成对象
                new Thread(){
                    @Override
                    public void run() {
                        okhttpChangeBill(tempOcaBill);
                    }
                }.start();

            }
        });

    }

    //获取上一个跳转传来的值
    private OcaBill getBill() {
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        OcaBill bill = new OcaBill();
        bill.setBillId(bundle.getInt("billId"));
        bill.setUserId(bundle.getString("userId"));
        bill.setPayMoney(bundle.getFloat("payMoney"));
        bill.setTime(bundle.getString("time"));
        bill.setCheckType(bundle.getInt("checkType"));
        bill.setRemark(bundle.getString("remark"));
        bill.setCheckStatus(bundle.getInt("checkStatus"));
        //测试
//        bill.setBillId(1);
//        bill.setUserId("2");
//        bill.setPayMoney((float)200);
//        bill.setTime("2020-01-08");
//        bill.setCheckType(0);
//        bill.setRemark("qwe");
//        bill.setCheckStatus(0);
        return bill;
    }


    private void init() {
        moneyChange = findViewById(R.id.money_change);
        dateChange = findViewById(R.id.date_change);
        typeChange = findViewById(R.id.type_change);
        remarksChange = findViewById(R.id.remarks_change);
        btnChange = findViewById(R.id.bt_change);
        btnCancel = findViewById(R.id.bt_cancel);
        btnDelete = findViewById(R.id.bt_delete);
        initViewModel();
        OcaBill bill = getBill();
        moneyChange.setText(bill.getPayMoney() + "");
        dateChange.setText(bill.getTime().split("T")[0]);
        if (bill.getCheckType() == OcaBill.catering){
            typeChange.setText("餐饮");
        }
        //交通
        else if (bill.getCheckType() == OcaBill.transportation){
            typeChange.setText("交通");
        }
        //购物
        else if (bill.getCheckType() == OcaBill.shop){typeChange.setText("购物");}
        //医疗
        else if (bill.getCheckType() == OcaBill.medical){typeChange.setText("医疗");}
        //娱乐
        else if (bill.getCheckType() == OcaBill.entertainment){typeChange.setText("娱乐");}
        //学习
        else if (bill.getCheckType() == OcaBill.learning){typeChange.setText("学习");}
        //金融
        else if (bill.getCheckType() == OcaBill.finance){typeChange.setText("金融");}
        //转账
        else if (bill.getCheckType() == OcaBill.transfer){typeChange.setText("转账");}
        //-------------------收入支出分割线--------------------
        //生活费（收入）
        else if (bill.getCheckType() == OcaBill.living_cost){typeChange.setText("生活费");}
        //工资
        else if (bill.getCheckType() == OcaBill.salary){typeChange.setText("工资");}
        //红包
        else if (bill.getCheckType() == OcaBill.red_packet){typeChange.setText("红包");}
        //基金股票
        else if (bill.getCheckType() == OcaBill.equity_funds){typeChange.setText("基金股票");}
        remarksChange.setText(bill.getRemark());
    }

    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                if (monthOfYear + 1 < 10 && dayOfMonth < 10)
                    tv.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                else if (monthOfYear + 1 < 10)
                    tv.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                else if (dayOfMonth < 10)
                    tv.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                else
                    tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //post方式修改账单
    private void okhttpChangeBill(OcaBill ocaBill) {
        try {
            //将获取到的对象变为json字符串
            String json = new Gson().toJson(ocaBill);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ip + "/oca_change_bill")
                    .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json))
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("BillPayFragment", "onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    viewModel.isPush.postValue(R.id.rb_flow);
                    Intent intent=new Intent();  //构建一个Intent
                    intent.putExtra("data_return",R.id.rb_flow);  //数据内容
                    setResult(RESULT_OK,intent);  //向上一个活动返回数据
                    finish();  //结束这个活动
//                    finish();
                }
            });
//            Thread.sleep(85);
//            //添加成功跳转到主界面
//            Intent intent = new Intent(ChangeBillPayActivity.this,NavigationActivity.class);
//            startActivity(intent);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    //post方式删除账单
    private void okhttpDeleteBill(OcaBill ocaBill) {
        try {
            FormBody.Builder params = new FormBody.Builder();
            params.add("billId",ocaBill.getBillId()+"");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ip + "/oca_delete_bill")
                    .post(params.build())
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("BillPayFragment", "onFailure: "+e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Intent intent=new Intent();  //构建一个Intent
                    intent.putExtra("data_return",R.id.rb_flow);  //数据内容
                    setResult(RESULT_OK,intent);  //向上一个活动返回数据
                    finish();  //结束这个活动
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    //弹窗显示信息，询问是否删除
    private void showDialogMsg(String msg) {
        final String title = msg;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在登录界面生成对话框提示
                new AlertDialog.Builder(ChangeBillPayActivity.this)
                        .setTitle(title)
                        .setCancelable(true)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        okhttpDeleteBill(getBill());
                                    }
                                }).start();
                            }

                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();//点击取消，对话框消失
                            }
                         }).create().show();
            }
        });
    }
}
