package com.ceq.app.main.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class Bean_ChannelExtraInfo implements Serializable {
    String payChannel,orderDescription,money,channelPlatform;

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getChannelPlatform() {
        return channelPlatform;
    }

    public void setChannelPlatform(String channelPlatform) {
        this.channelPlatform = channelPlatform;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
