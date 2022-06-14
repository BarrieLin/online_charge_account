package com.example.chargeaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airsaid.pickerviewlibrary.OptionsPickerView;
import com.example.chargeaccount.entity.OcaBill;
import com.example.chargeaccount.utils.DisplayUtils;
import com.example.chargeaccount.viewmodel.MainViewModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.chargeaccount.temp.TempData.ip;
import static com.example.chargeaccount.temp.TempData.ocaUser;

//记账里的支出界面
public class BillPayFragment extends Fragment {
    private View view;
    private TextView date_pay,type_pay;
    private EditText number_pay,remarks_pay;
    private Calendar calendar= Calendar.getInstance(Locale.CHINA);
    private Button btn_pay;
    private MainViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bill_pay,container,false);
        initViewModel();
        init();
        onClick();
        return view;
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(requireActivity(),new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
    }

    //初始化控件
    public void init(){
        number_pay=view.findViewById(R.id.number_pay);
        date_pay=view.findViewById(R.id.date_pay);
        type_pay=view.findViewById(R.id.type_pay);
        remarks_pay=view.findViewById(R.id.remarks_pay);

        btn_pay = view.findViewById(R.id.bt_pay);


    }

    //点击事件
    public void onClick(){
        number_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        date_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayUtils.HideKeyboard(view);
                showDatePickerDialog(getActivity(),  4,date_pay , calendar);
            }
        });
       type_pay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DisplayUtils.HideKeyboard(view);
               OptionsPickerView<String> mOptionsPickerView = new OptionsPickerView<>(getActivity());
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
                       type_pay.setText(type);
                   }
               });
               mOptionsPickerView.show();
           }
       });
       remarks_pay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userId = ocaUser.getUserId();
                Float payMoney;
                if(!number_pay.getText().toString().trim().equals(""))
                    payMoney = Float.parseFloat(number_pay.getText().toString().trim());
                else
                    payMoney = null;
                String time = date_pay.getText().toString().trim();
                String check_Type = type_pay.getText().toString().trim();
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
                String remark = remarks_pay.getText().toString().trim();
                if (payMoney == null || check_Type.equals("") || remark.equals("") || time.equals("")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("错误")
                            .setTitle("存在空数据无法记账")
                            .setCancelable(true)
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();//点击确定，对话框消失
                                }
                            }).create().show();
                }else {
                    final OcaBill tempOcaBill = new OcaBill();
                    tempOcaBill.setBillId(null);
                    tempOcaBill.setUserId(userId);
                    tempOcaBill.setCheckStatus(1);
                    tempOcaBill.setCheckType(checkType);
                    tempOcaBill.setPayMoney(payMoney);
                    tempOcaBill.setTime(time);
                    tempOcaBill.setRemark(remark);

                    //将获取到的数据打包成对象
                    new Thread(){
                        @Override
                        public void run() {
                            okhttpPayin(tempOcaBill);
                        }
                    }.start();
                }


//                Intent intent=new Intent(BillPayFragment.this,NavigationActivity.class);
//                startActivity(intent);
            }
        });

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

    //post方式添加账单
    private void okhttpPayin(OcaBill ocaBill) {
        try {
            //将获取到的对象变为json字符串
            String json = new Gson().toJson(ocaBill);
            System.out.println(json);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ip + "/oca_add_bill")
                    .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json))
                    .build();
            //异步
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("BillPayFragment", "onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    Log.d("123", "onResponse: " +response.body().string());
                    //发送信息到主activity
                    viewModel.isPush.postValue(R.id.rb_flow);
//                    Intent intent=new Intent(getActivity(),NavigationActivity.class);
//                    startActivity(intent);
                }
            });
//            //添加成功跳转到主界面
//            Thread.sleep(85);
//            Intent intent=new Intent(getActivity(),NavigationActivity.class);
//            startActivity(intent);

        }catch (Exception e){
            System.out.println(e);
        }
    }



}
