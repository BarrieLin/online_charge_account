package com.example.chargeaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chargeaccount.entity.EnterStatus;
import com.example.chargeaccount.entity.OcaUser;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.chargeaccount.temp.TempData.ip;
import static com.example.chargeaccount.temp.TempData.ocaUser;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private EditText reg_user_id;//注册账号
    private EditText reg_password;//注册密码
    private Button reg_btn;//注册按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar();
        //工头新增
        init();
        reg_btnOnClick();
    }

    //注册按钮点击事件
    private void reg_btnOnClick() {
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取账号输入框的账号
                String userId = reg_user_id.getText().toString().trim();
                //获取密码输入框的密码
                String password = reg_password.getText().toString().trim();
                final OcaUser tempOcaUser = new OcaUser();
                //将获取到的数据打包成对象
                tempOcaUser.setUserId(userId);
                tempOcaUser.setPassword(password);
                ocaUser = tempOcaUser;
                new Thread(){
                    @Override
                    public void run() {
                        okhttpRegister(tempOcaUser);
                    }
                }.start();
            }
        });
    }

    //post方式注册
    private void okhttpRegister(OcaUser ocaUser) {
        try {
            //将获取到的对象变为json字符串
            String json = new Gson().toJson(ocaUser);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ip + "/oca_register")
                    .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json))
                    .build();
            //获取后端返回值
            Response response = client.newCall(request).execute();
            String backJson = response.body().string();
            EnterStatus regStatus = new Gson().fromJson(backJson, EnterStatus.class);
            if (regStatus.getCode() == EnterStatus.named){}
            //如果状态码为1,则注册的用户名已存在
            else if (regStatus.getCode() == EnterStatus.unnamed){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //如果状态码为2,则注册的用户名账号密码不匹配，这个状态码在注册这里不存在啥意义，空函数就行
            else if (regStatus.getCode() == EnterStatus.wrongPass){}

            //如果状态码为3,则注册成功
            else if (regStatus.getCode() == EnterStatus.pass){
                //okhttp需要在线程内运行，如果Toast需要在okhttp内运行需要使用runOnUiThread方法
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    }
                });
                //注册成功跳转到主界面
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
            //如果状态码为4,则注册过程密码为空
            else if (regStatus.getCode() == EnterStatus.invalid){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this,"密码为空",Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }catch (Exception e){
            //其他注册失败异常，比如网络未连接或其他问题
//            System.out.println(e);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RegisterActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    //初始化控件
    private void init() {
        reg_user_id = findViewById(R.id.reg_user_id);
        reg_password = findViewById(R.id.reg_password);
        reg_btn = findViewById(R.id.reg_button);

        reg_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    //初始化导航栏
    public void initToolbar(){
        toolbar=findViewById(R.id.reg_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);//跳转到登陆界面
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

