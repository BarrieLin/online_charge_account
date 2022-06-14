package com.example.chargeaccount.entity;

import java.util.ArrayList;
import java.util.List;

//流水页面数据
public class OcaFlow {
    //    private Budget budget;
    Float monthIncome;
    Float monthOutcome;
    private ArrayList<OcaBill> billList;

    public Float getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(Float monthIncome) {
        this.monthIncome = monthIncome;
    }

    public Float getMonthOutcome() {
        return monthOutcome;
    }

    public void setMonthOutcome(Float monthOutcome) {
        this.monthOutcome = monthOutcome;
    }

    public ArrayList<OcaBill> getBillList() {
        return billList;
    }

    public void setBillList(ArrayList<OcaBill> billList) {
        this.billList = billList;
    }

    @Override
    public String toString() {
        return "OcaFlow{" +
                "monthIncome=" + monthIncome +
                ", monthOutcome=" + monthOutcome +
                ", billList=" + billList +
                '}';
    }
}
