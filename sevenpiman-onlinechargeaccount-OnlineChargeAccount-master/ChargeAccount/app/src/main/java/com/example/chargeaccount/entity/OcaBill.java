package com.example.chargeaccount.entity;

public class OcaBill {
    private Integer billId;
    private String userId;
    private Float payMoney;

    private String time;


    //判断账单类型（0餐饮、1交通、2旅游等）
    private Integer checkType;
    //支出类型
    public static final Integer catering = 0;//餐饮
    public static final Integer transportation = 1;//交通
    public static final Integer shop = 2;//购物
    public static final Integer medical = 3;//医疗
    public static final Integer entertainment = 4;//娱乐
    public static final Integer learning = 5;//学习
    public static final Integer finance = 6;//金融
    public static final Integer transfer = 7;//转账
    //收入类型
    public static final Integer living_cost = 8;//生活费
    public static final Integer salary = 9;//工资
    public static final Integer red_packet = 10;//收红包
    public static final Integer equity_funds = 11;//股票基金
    private String remark;
    //判断是收入还是支出，0收入，1支出
    private Integer checkStatus;
    public static final Integer income = 0;//收入
    public static final Integer outcome = 1;//支出
    @Override
    public String toString() {
        return "OcaBill{" +
                "bill_id=" + billId +
                ", user_id='" + userId + '\'' +
                ", pay_money=" + payMoney +
                ", time='" + time + '\'' +
                ", check_type=" + checkType +
                ", remark='" + remark + '\'' +
                ", check_status=" + checkStatus +
                '}';
    }




    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Float getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Float payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}

