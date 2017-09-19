package com.ceq.app.main.bean;

import java.io.Serializable;

/**
 * Created by ceq on 2017/4/17.
 */

public  class Bean_TransactionDetailed_Recharge implements Serializable{
    String id;
    String ordercode;
    String desc;
    String amount;
    String userid;
    String phone;
    String brandid;
    String rate;
    String extraFee;
    String realAmount;
    String type;
    String thirdlevelid;
    String channelid;
    String channelname;
    String status;
    String thirdOrdercode;
    String remark;
    String updateTime;
    String createTime;

    @Override
    public String toString() {
        return "Bean_TransactionDetailed_Recharge{" +
                "id='" + id + '\'' +
                ", ordercode='" + ordercode + '\'' +
                ", desc='" + desc + '\'' +
                ", amount='" + amount + '\'' +
                ", userid='" + userid + '\'' +
                ", phone='" + phone + '\'' +
                ", brandid='" + brandid + '\'' +
                ", rate='" + rate + '\'' +
                ", extraFee='" + extraFee + '\'' +
                ", realAmount='" + realAmount + '\'' +
                ", type='" + type + '\'' +
                ", thirdlevelid='" + thirdlevelid + '\'' +
                ", channelid='" + channelid + '\'' +
                ", channelname='" + channelname + '\'' +
                ", status='" + status + '\'' +
                ", thirdOrdercode='" + thirdOrdercode + '\'' +
                ", remark='" + remark + '\'' +
                ", updateTime='" + updateTime + '\'' +
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(String extraFee) {
        this.extraFee = extraFee;
    }

    public String getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(String realAmount) {
        this.realAmount = realAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThirdlevelid() {
        return thirdlevelid;
    }

    public void setThirdlevelid(String thirdlevelid) {
        this.thirdlevelid = thirdlevelid;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThirdOrdercode() {
        return thirdOrdercode;
    }

    public void setThirdOrdercode(String thirdOrdercode) {
        this.thirdOrdercode = thirdOrdercode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
