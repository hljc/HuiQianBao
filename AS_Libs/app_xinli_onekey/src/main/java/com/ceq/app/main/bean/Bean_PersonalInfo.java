package com.ceq.app.main.bean;

/**
 * Created by ceq on 2017/5/17.
 */

public class Bean_PersonalInfo {
    String userid;
    String sex;
    String name;
    String phone;
    String realname;
    String idcard;
    String grade;
    String realnameStatus;
    String withdrawFee;
    String createTime;
    String usershopStatus;

    @Override
    public String toString() {
        return "Bean_PersonalInfo{" +
                "userid='" + userid + '\'' +
                ", sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", realname='" + realname + '\'' +
                ", idcard='" + idcard + '\'' +
                ", grade='" + grade + '\'' +
                ", realnameStatus='" + realnameStatus + '\'' +
                ", withdrawFee='" + withdrawFee + '\'' +
                ", createTime='" + createTime + '\'' +
                ", usershopStatus='" + usershopStatus + '\'' +
                '}';
    }

    public String getUsershopStatus() {
        return usershopStatus;
    }

    public void setUsershopStatus(String usershopStatus) {
        this.usershopStatus = usershopStatus;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRealnameStatus() {
        return realnameStatus;
    }

    public void setRealnameStatus(String realnameStatus) {
        this.realnameStatus = realnameStatus;
    }

    public String getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(String withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
