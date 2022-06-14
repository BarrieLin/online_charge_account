package com.younantuiqun.onlinechargeaccount.po;

import java.util.List;

public class OcaFlow {
//    private Budget budget;
    Float monthIncome;
    Float monthOutcome;
    private List<Bill> billList;

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

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
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
