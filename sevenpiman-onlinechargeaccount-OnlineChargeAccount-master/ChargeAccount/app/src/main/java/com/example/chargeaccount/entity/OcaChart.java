package com.example.chargeaccount.entity;

import java.util.List;
/*
 * 2021/5/29 by工头
 * 用于返回给前端图表模块所需数据
 * */
public class OcaChart {
    private String userId;
    //用于显示图表模块上面的总收入，总收入，总收入
    private Float currentIncome;
    //用于显示图表模块上面的总支出，总支出，总支出
    private Float currentOutcome;
    //用于显示月收入折线图
    private List<MonSumOIncome> listCurrentMonthIncome;
    //用于显示月支出折线图
    private List<MonSumOIncome> listCurrentMonthOutcome;
    //用于显示月支出饼状图（各类型支出占比）
    private List<Float> listCurrMonthTypeOutcome;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Float getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(Float currentIncome) {
        this.currentIncome = currentIncome;
    }

    public Float getCurrentOutcome() {
        return currentOutcome;
    }

    public void setCurrentOutcome(Float currentOutcome) {
        this.currentOutcome = currentOutcome;
    }

    public List<MonSumOIncome> getListCurrentMonthIncome() {
        return listCurrentMonthIncome;
    }

    public void setListCurrentMonthIncome(List<MonSumOIncome> listCurrentMonthIncome) {
        this.listCurrentMonthIncome = listCurrentMonthIncome;
    }

    public List<MonSumOIncome> getListCurrentMonthOutcome() {
        return listCurrentMonthOutcome;
    }

    public void setListCurrentMonthOutcome(List<MonSumOIncome> listCurrentMonthOutcome) {
        this.listCurrentMonthOutcome = listCurrentMonthOutcome;
    }

    public List<Float> getListCurrMonthTypeOutcome() {
        return listCurrMonthTypeOutcome;
    }

    public void setListCurrMonthTypeOutcome(List<Float> listCurrMonthTypeOutcome) {
        this.listCurrMonthTypeOutcome = listCurrMonthTypeOutcome;
    }
}
