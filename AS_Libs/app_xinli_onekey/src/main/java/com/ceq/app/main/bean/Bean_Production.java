package com.ceq.app.main.bean;

/**
 * Created by ceq on 2017/4/17.
 */

public class Bean_Production {
    String id;
    String grade;
    String name;
    String money;
    String deposit;
    String discount;
    String rate;
    String remark;
    String createTime;
    String brandId;
    String upgradestate;
    String earningsState;
    String trueFalseBuy;
    boolean isChecked;

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getTrueFalseBuy() {
        return trueFalseBuy;
    }

    public void setTrueFalseBuy(String trueFalseBuy) {
        this.trueFalseBuy = trueFalseBuy;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public String toString() {
        return "Bean_Production{" +
                "id='" + id + '\'' +
                ", grade='" + grade + '\'' +
                ", name='" + name + '\'' +
                ", money='" + money + '\'' +
                ", discount='" + discount + '\'' +
                ", rate='" + rate + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                ", brandId='" + brandId + '\'' +
                ", upgradestate='" + upgradestate + '\'' +
                ", earningsState='" + earningsState + '\'' +
                '}';
    }

    public String getUpgradestate() {
        return upgradestate;
    }

    public void setUpgradestate(String upgradestate) {
        this.upgradestate = upgradestate;
    }

    public String getEarningsState() {
        return earningsState;
    }

    public void setEarningsState(String earningsState) {
        this.earningsState = earningsState;
    }
}
