package com.younantuiqun.onlinechargeaccount.po;


/*
 * 2021/5/29 by工头
 * */
//用于把每个月的收入（或支出）总和，对应时间的包装
public class MonSumOIncome {
    //某年某月的收入（或支出）总和
    private Float outOrIncome;
    //某年某月
    private String date;

    @Override
    public String toString() {
        return "MonSumOIncome{" +
                "outOrIncome=" + outOrIncome +
                ", date=" + date +
                '}';
    }

    public Float getOutOrIncome() {
        return outOrIncome;
    }

    public void setOutOrIncome(Float outOrIncome) {
        this.outOrIncome = outOrIncome;
    }

//    public Date getDate() {
//        return date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
}
