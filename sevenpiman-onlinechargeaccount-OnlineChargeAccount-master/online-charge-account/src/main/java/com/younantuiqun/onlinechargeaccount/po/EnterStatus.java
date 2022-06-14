package com.younantuiqun.onlinechargeaccount.po;

public class EnterStatus {
    private Integer code;
    private String curMessage;
    public static final Integer named = 0;
    public static final Integer unnamed = 1;
    public static final Integer wrongPass = 2;
    public static final Integer pass = 3;
    public static final Integer invalid = 4;
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCurMessage() {
        return curMessage;
    }

    public void setCurMessage(String curMessage) {
        this.curMessage = curMessage;
    }

    @Override
    public String toString() {
        return "EnterStatus{" +
                "code=" + code +
                ", curMessage='" + curMessage + '\'' +
                '}';
    }
}
