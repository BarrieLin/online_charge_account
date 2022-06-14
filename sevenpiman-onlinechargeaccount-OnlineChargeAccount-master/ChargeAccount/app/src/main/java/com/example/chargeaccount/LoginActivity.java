package com.example.chargeaccount;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import android.widget.Toolbar;


public class LoginActivity extends AppCompatActivity {
    private TextView register;//立即登录
    private TextView forget_password;//忘记密码
    private EditText login_number;//登陆账号
    private EditText login_password;//登陆密码
    private Button login_button;//登陆按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        click();

    }
    //初始化控件
    public void init(){
        login_number=findViewById(R.id.login_number);
        login_password=findViewById(R.id.login_password);
        login_button=findViewById(R.id.login_button);
        register=findViewById(R.id.login_reg);
        forget_password=findViewById(R.id.forget);

        login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    //点击跳转
    public void click(){
        //立即注册跳转
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册界面
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        //忘记密码跳转
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到忘记密码界面
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                *      输入账号密码登录功能代码
                *
                * */
                String userId = login_number.getText().toString().trim();
                String password = login_password.getText().toString().trim();
                //账号或密码为空时
                if (userId.equals("") || userId == null || password.equals("") || password == null){
                    showLoginMsg("账号密码不能为空");
                }
                else {
                    final OcaUser tempOcaUser = new OcaUser();
                    //将获取到的数据打包成对象
                    tempOcaUser.setUserId(userId);
                    tempOcaUser.setPassword(password);
                    ocaUser = tempOcaUser;
                    new Thread(){
                        @Override
                        public void run() {
                            okhttpLogin(tempOcaUser);
                        }
                    }.start();
                }

                //登录成功跳转到主界面
//                Intent intent=new Intent(LoginActivity.this,NavigationActivity.class);
//                startActivity(intent);
//
//                finish();
            }
        });
    }

    //post方式登录
    private void okhttpLogin(OcaUser ocaUser) {
        try {
            //将获取到的对象变为json字符串
            String json = new Gson().toJson(ocaUser);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ip + "/oca_login")
                    .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json))
                    .build();
            //获取后端返回值
            Response response = client.newCall(request).execute();
            String backJson = response.body().string();
            EnterStatus regStatus = new Gson().fromJson(backJson, EnterStatus.class);
            if (regStatus.getCode() == EnterStatus.named){}
            //如果状态码为1,则登录的用户名已存在，这个状态码在登录这里不存在啥意义，空函数就行
            else if (regStatus.getCode() == EnterStatus.unnamed){}
            //如果状态码为2,则登录的用户名账号密码不匹配
            else if (regStatus.getCode() == EnterStatus.wrongPass){
                showLoginMsg("账号或密码错误");
            }
            //如果状态码为3,则登录成功
            else if (regStatus.getCode() == EnterStatus.pass){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    }
                });
                //登录成功跳转到主界面
                Intent intent=new Intent(LoginActivity.this,NavigationActivity.class);
                startActivity(intent);
                finish();
            }
            //如果状态码为4,则登录过程密码为空,由于前端设置了账号密码不能为空，所以这个状态码在登录这里不存在啥意义，空函数就行
            else if (regStatus.getCode() == EnterStatus.invalid){}

        }catch (Exception e){
            //其他登录失败异常，比如网络未连接或其他问题
            showLoginMsg("网络异常");
        }
    }

    private void showLoginMsg(String msg) {
        final String title = msg;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                progressDialog.dismiss();//前面点击登录按钮时出现进度条，这里登录失败进度条消失
                //在登录界面生成对话框提示
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("错误")
                        .setTitle(title)
                        .setCancelable(true)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();//点击确定，对话框消失
                            }
                        }).create().show();
            }
        });
    }
}