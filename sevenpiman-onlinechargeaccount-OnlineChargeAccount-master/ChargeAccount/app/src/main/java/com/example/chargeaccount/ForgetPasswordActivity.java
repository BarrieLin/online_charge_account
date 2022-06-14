package com.example.chargeaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText number;//账号
    private EditText password;//密码
    private EditText confirm_password;//确认密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initLayout();
    }
    //初始化控件
    public void initLayout(){
        toolbar=findViewById(R.id.forget_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        number=findViewById(R.id.forget_number);
        password=findViewById(R.id.forger_password);
        confirm_password=findViewById(R.id.confirm_password);
    }
    @Override
    //toolbar点击事件
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(ForgetPasswordActivity.this, LoginActivity.class);//跳转到登陆界面
                startActivity(intent);
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}

