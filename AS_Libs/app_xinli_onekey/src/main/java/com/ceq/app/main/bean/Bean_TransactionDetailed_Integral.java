package com.ceq.app.main.bean;

import java.io.Serializable;

/**
 * Created by ceq on 2017/4/17.
 */

public  class Bean_TransactionDetailed_Integral implements Serializable{
    String id;
    String ordercode;
    String coin;
    String type;
    String userid;
    String brand_id;
    String userphone;
    String remark;
    String createTime;

    @Override
    public String toString() {
        return "Bean_TransactionDetailed_Integral{" +
                "id='" + id + '\'' +
                ", ordercode='" + ordercode + '\'' +
                ", coin='" + coin + '\'' +
                ", type='" + type + '\'' +
                ", userid='" + userid + '\'' +
                ", brand_id='" + brand_id + '\'' +
                ", userphone='" + userphone + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
