package com.ceq.app.main.bean;

import java.io.Serializable;

/**
 * Created by ceq on 2017/4/15.
 */

public class Bean_BankCardInfo implements Serializable {
    String id;
    String userId;
    String bankName;
    String bankBrand;
    String cardNo;
    String lineNo;
    String securityCode;
    String expiredTime;
    String phone;
    String idcard;
    String cardType;
    String state;
    String idDef;
    String createTime;
    String logo;
    String type;

    @Override
    public String toString() {
        return "Bean_BankCardInfo{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankBrand='" + bankBrand + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", lineNo='" + lineNo + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", expiredTime='" + expiredTime + '\'' +
                ", phone='" + phone + '\'' +
                ", idcard='" + idcard + '\'' +
                ", cardType='" + cardType + '\'' +
                ", state='" + state + '\'' +
                ", idDef='" + idDef + '\'' +
                ", logo='" + logo + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBrand() {
        return bankBrand;
    }

    public void setBankBrand(String bankBrand) {
        this.bankBrand = bankBrand;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIdDef() {
        return idDef;
    }

    public void setIdDef(String idDef) {
        this.idDef = idDef;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
