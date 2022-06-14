package com.example.chargeaccount.utils;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.chargeaccount.LoginActivity;

public class DialogUtils {
    public static void showLoginMsg(String msg, final Activity activity) {
        final String title = msg;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //在登录界面生成对话框提示
                new AlertDialog.Builder(activity)
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
