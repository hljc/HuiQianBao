package com.ceq.app.main.bean;

/**
 * Created by ceq on 2017/4/16.
 */

public class Bean_UserAccount {
    String id;
    String userId;
    String coin;
    String balance;
    String rebateBalance;
    String createTime;

    @Override
    public String toString() {
        return "Bean_UserAccount{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", coin='" + coin + '\'' +
                ", balance='" + balance + '\'' +
                ", rebateBalance='" + rebateBalance + '\'' +
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRebateBalance() {
        return rebateBalance;
    }

    public void setRebateBalance(String rebateBalance) {
        this.rebateBalance = rebateBalance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
