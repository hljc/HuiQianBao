package com.ceq.app.main.bean;

import java.io.Serializable;

/**
 * Created by ceq on 2017/4/21.
 */

public class Bean_Collection implements Serializable {
    String money;
    String remark;
    String channelTag;
    String channelWay;
    String product_id;
    String qrCodeUrl;
    String webUrl;
    String realName;

    @Override
    public String toString() {
        return "Bean_Collection{" +
                "money='" + money + '\'' +
                ", remark='" + remark + '\'' +
                ", channelTag='" + channelTag + '\'' +
                ", channelWay='" + channelWay + '\'' +
                ", product_id='" + product_id + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChannelTag() {
        return channelTag;
    }

    public void setChannelTag(String channelTag) {
        this.channelTag = channelTag;
    }

    public String getChannelWay() {
        return channelWay;
    }

    public void setChannelWay(String channelWay) {
        this.channelWay = channelWay;
    }
}
