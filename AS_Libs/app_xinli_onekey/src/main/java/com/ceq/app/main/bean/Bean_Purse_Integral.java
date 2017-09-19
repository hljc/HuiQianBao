package com.ceq.app.main.bean;

/**
 * Created by ceq on 2017/4/19.
 */

public class Bean_Purse_Integral {
    String id;
    String userId;
    String coin;
    String addOrSub;
    String curCoin;
    String ordercode;
    String createTime;

    @Override
    public String toString() {
        return "Bean_Purse_Integral{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", coin='" + coin + '\'' +
                ", addOrSub='" + addOrSub + '\'' +
                ", curCoin='" + curCoin + '\'' +
                ", ordercode='" + ordercode + '\'' +
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

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getAddOrSub() {
        return addOrSub;
    }

    public void setAddOrSub(String addOrSub) {
        this.addOrSub = addOrSub;
    }

    public String getCurCoin() {
        return curCoin;
    }

    public void setCurCoin(String curCoin) {
        this.curCoin = curCoin;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
