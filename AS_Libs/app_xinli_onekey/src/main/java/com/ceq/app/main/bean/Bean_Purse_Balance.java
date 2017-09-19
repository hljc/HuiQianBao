package com.ceq.app.main.bean;

/**
 * Created by ceq on 2017/4/19.
 */

public class Bean_Purse_Balance {
    String id;
    String userId;
    String amount;
    String addOrSub;
    String curBal;
    String orderCode;
    String createTime;

    @Override
    public String toString() {
        return "Bean_Purse_Balance{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", amount='" + amount + '\'' +
                ", addOrSub='" + addOrSub + '\'' +
                ", curBal='" + curBal + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddOrSub() {
        return addOrSub;
    }

    public void setAddOrSub(String addOrSub) {
        this.addOrSub = addOrSub;
    }

    public String getCurBal() {
        return curBal;
    }

    public void setCurBal(String curBal) {
        this.curBal = curBal;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
