package com.example.chargeaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chargeaccount.entity.EnterStatus;
import com.example.chargeaccount.entity.OcaUser;
import com.example.chargeaccount.temp.TempData;
import com.example.chargeaccount.utils.DialogUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.chargeaccount.temp.TempData.ip;
import static com.example.chargeaccount.temp.TempData.ocaUser;

public class UserMsgActivity extends AppCompatActivity {
    private LinearLayout back;
    private Button save;
    private EditText userId;
    private EditText nickname;
    private EditText email;
    private EditText phone;
    private EditText sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_msg);
        init();
        onClick();
    }

    private void init() {
        back = findViewById(R.id.back);
        save = findViewById(R.id.saveMsg);
        userId = findViewById(R.id.user_id);
        nickname = findViewById(R.id.nickname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        sex = findViewById(R.id.sex);

        userId.setText(ocaUser.getUserId());
        userId.setFocusableInTouchMode(false);
        userId.setKeyListener(null);//不可粘贴，长按不会弹出粘贴框
        userId.setClickable(false);//不可点击，但是这个效果我这边没体现出来，不知道怎没用

        if (ocaUser.getNickname() != null)
            nickname.setText(ocaUser.getNickname());
        if (ocaUser.getEmail() != null)
            email.setText(ocaUser.getEmail());
        if (ocaUser.getPhone() != null)
            phone.setText(ocaUser.getPhone());
        if (ocaUser.getSex() != null)
            sex.setText(ocaUser.getSex());
    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OcaUser tempOcaUser = new OcaUser();
                tempOcaUser.setUserId(userId.getText().toString().trim());
                tempOcaUser.setNickname(nickname.getText().toString().trim());
                tempOcaUser.setEmail(email.getText().toString().trim());
                tempOcaUser.setPhone(phone.getText().toString().trim());
                tempOcaUser.setSex(sex.getText().toString().trim());
                okhttpChangeUser(tempOcaUser);
            }
        });
    }

    //post方式修改用户信息
    private void okhttpChangeUser(final OcaUser ocaUser) {
        try {
            //将获取到的对象变为json字符串
            String json = new Gson().toJson(ocaUser);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ip + "/change_user_msg")
                    .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json))
                    .build();
            //获取后端返回值
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    DialogUtils.showLoginMsg("服务器异常",UserMsgActivity.this);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String backJson = response.body().string();
                    if (backJson.equals("true")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UserMsgActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                        TempData.ocaUser = ocaUser;
                        Intent intent=new Intent();  //构建一个Intent
                        intent.putExtra("data_return",R.id.rb_center);  //数据内容
                        setResult(RESULT_OK,intent);  //向上一个活动返回数据
                        finish();
                    }else {
                        DialogUtils.showLoginMsg("未知错误",UserMsgActivity.this);
                    }
                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
