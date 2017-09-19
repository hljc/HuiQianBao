package com.ceq.app.main.bean;

/**
 * Created by ceq on 2017/4/19.
 */

public class Bean_Purse_Share {
    String id;
    String userId;
    String rebate;
    String addOrSub;
    String curRebate;
    String orderCode;
    String orderType;
    String createTime;

    @Override
    public String toString() {
        return "Bean_Purse_Share{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", rebate='" + rebate + '\'' +
                ", addOrSub='" + addOrSub + '\'' +
                ", curRebate='" + curRebate + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", orderType='" + orderType + '\'' +
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

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public String getAddOrSub() {
        return addOrSub;
    }

    public void setAddOrSub(String addOrSub) {
        this.addOrSub = addOrSub;
    }

    public String getCurRebate() {
        return curRebate;
    }

    public void setCurRebate(String curRebate) {
        this.curRebate = curRebate;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
