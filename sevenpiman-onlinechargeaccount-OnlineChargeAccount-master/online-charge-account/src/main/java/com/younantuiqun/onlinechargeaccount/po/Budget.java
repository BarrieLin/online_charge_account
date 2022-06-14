package com.younantuiqun.onlinechargeaccount.po;

public class Budget {
    private String userId;
    private Integer monthTime;
    private float predictMoney;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(Integer monthTime) {
        this.monthTime = monthTime;
    }

    public float getPredictMoney() {
        return predictMoney;
    }

    public void setPredictMoney(float predictMoney) {
        this.predictMoney = predictMoney;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "userId='" + userId + '\'' +
                ", monthTime=" + monthTime +
                ", predictMoney=" + predictMoney +
                '}';
    }
}
