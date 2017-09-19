package com.ceq.app.main.bean;

import java.io.Serializable;

/**
 * Created by ceq on 2017/4/17.
 */

public class Bean_TransactionDetailed_Share implements Serializable{
    String id;
    String ordercode;
    String amount;
    String usoriuseriderid;
    String oriphone;//下级
    String orirate;
    String acquserid;
    String acqphone;
    String acqrate;
    String scale;
    String acqAmount;
    String remark;
    String createTime;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUsoriuseriderid() {
        return usoriuseriderid;
    }

    public void setUsoriuseriderid(String usoriuseriderid) {
        this.usoriuseriderid = usoriuseriderid;
    }

    public String getOriphone() {
        return oriphone;
    }

    public void setOriphone(String oriphone) {
        this.oriphone = oriphone;
    }

    public String getOrirate() {
        return orirate;
    }

    public void setOrirate(String orirate) {
        this.orirate = orirate;
    }

    public String getAcquserid() {
        return acquserid;
    }

    public void setAcquserid(String acquserid) {
        this.acquserid = acquserid;
    }

    public String getAcqphone() {
        return acqphone;
    }

    public void setAcqphone(String acqphone) {
        this.acqphone = acqphone;
    }

    public String getAcqrate() {
        return acqrate;
    }

    public void setAcqrate(String acqrate) {
        this.acqrate = acqrate;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getAcqAmount() {
        return acqAmount;
    }

    public void setAcqAmount(String acqAmount) {
        this.acqAmount = acqAmount;
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

    @Override
    public String toString() {
        return "Bean_TransactionDetailed_Share{" +
                "id='" + id + '\'' +
                ", ordercode='" + ordercode + '\'' +
                ", amount='" + amount + '\'' +
                ", usoriuseriderid='" + usoriuseriderid + '\'' +
                ", oriphone='" + oriphone + '\'' +
                ", orirate='" + orirate + '\'' +
                ", acquserid='" + acquserid + '\'' +
                ", acqphone='" + acqphone + '\'' +
                ", acqrate='" + acqrate + '\'' +
                ", scale='" + scale + '\'' +
                ", acqAmount='" + acqAmount + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
