package com.ceq.app.core.bean;

/**
 * Created by ceq on 2017/4/14.
 */

public class Bean_Register {
    String id;

    String phone;
    String password;
    String paypass;
    String nickName;
    String userHeadUrl;
    String province;
    String city;
    String email;
    String sex;
    String profession;
    String birthday;
    String openid;
    String unionid;
    String userToken;
    String createTime;
    String inviteCode;
    String brandId;
    String preUserId;

    @Override
    public String toString() {
        return "Bean_Register{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", paypass='" + paypass + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userHeadUrl='" + userHeadUrl + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", profession='" + profession + '\'' +
                ", birthday='" + birthday + '\'' +
                ", openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                ", userToken='" + userToken + '\'' +
                ", createTime='" + createTime + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", brandId='" + brandId + '\'' +
                ", preUserId='" + preUserId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaypass() {
        return paypass;
    }

    public void setPaypass(String paypass) {
        this.paypass = paypass;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getPreUserId() {
        return preUserId;
    }

    public void setPreUserId(String preUserId) {
        this.preUserId = preUserId;
    }
}
