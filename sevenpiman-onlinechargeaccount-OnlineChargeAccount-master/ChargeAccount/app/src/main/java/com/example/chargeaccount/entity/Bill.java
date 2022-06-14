package com.example.chargeaccount.entity;

import android.widget.TextView;

public class Bill {
    private String type,ddd,number;
    private int Drawable;//收入或支出类型图片
    private int in_or_out;//收入或支出1=收入 0=支出

    public Bill(String type, String date, String number, int drawable, int in_or_out) {
        this.type = type;
        this.ddd = date;
        this.number = number;
        Drawable = drawable;
        this.in_or_out = in_or_out;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String date) {
        this.ddd = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getDrawable() {
        return Drawable;
    }

    public void setDrawable(int drawable) {
        Drawable = drawable;
    }

    public int getIn_or_out() {
        return in_or_out;
    }

    public void setIn_or_out(int in_or_out) {
        this.in_or_out = in_or_out;
    }
}
