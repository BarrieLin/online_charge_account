package com.example.chargeaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
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

//记账里的收入界面
public class BillIncomFragment extends Fragment {
    private View view;
    private TextView date_income,type_income;
    private EditText number_income,remarks_income;
    private Calendar calendar= Calendar.getInstance(Locale.CHINA);
    private Button btn_icome;
    //
    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.bill_income,container,false);
         init();
         onClick();
        return view;
    }
    //初始化控件
    public void init(){
        number_income=view.findViewById(R.id.number_income);
        date_income=view.findViewById(R.id.date_income);
        type_income=view.findViewById(R.id.type_income);
        remarks_income=view.findViewById(R.id.remarks_income);
        btn_icome = view.findViewById(R.id.bt_income);

        initViewModel();
    }

    private void initViewModel() {
        viewModel=new ViewModelProvider(requireActivity(),new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
    }
    //点击事件
    public void onClick(){
        number_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        date_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayUtils.HideKeyboard(view);
                showDatePickerDialog(getActivity(),  4,date_income , calendar);
            }
        });
        type_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayUtils.HideKeyboard(view);
                OptionsPickerView<String> mOptionsPickerView = new OptionsPickerView<>(getActivity());
                final ArrayList<String> list = new ArrayList<>();
                list.add("生活费");
                list.add("工资");
                list.add("红包");
                list.add("股票基金");
                // 设置数据
                mOptionsPickerView.setPicker(list);
                // 设置选项单位
                mOptionsPickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int option1, int option2, int option3) {
                        String type = list.get(option1);
                        type_income.setText(type);
                    }
                });
                mOptionsPickerView.show();
            }
        });
        remarks_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_icome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    /*
                     *      输入支出Bill功能代码
                     *
                     * */
                    String userId = ocaUser.getUserId();
                    Float payMoney = Float.parseFloat(number_income.getText().toString().trim());
                    String time = date_income.getText().toString().trim();
                    String check_Type = type_income.getText().toString().trim();
                    Integer checkType = null;
                    if (check_Type.equals("生活费"))
                        checkType=8;
                    else if (check_Type.equals("工资"))
                        checkType=9;
                    else if (check_Type.equals("红包"))
                        checkType=10;
                    else if (check_Type.equals("股票基金"))
                        checkType=11;
                    String remark = remarks_income.getText().toString().trim();
                    final OcaBill tempOcaBill = new OcaBill();
                    tempOcaBill.setBillId(null);
                    tempOcaBill.setUserId(userId);
                    tempOcaBill.setCheckStatus(0);
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
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("BillIncomFragment", "onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    viewModel.isPush.postValue(R.id.rb_flow);
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
