package com.example.chargeaccount.entity;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
