package com.ceq.app.main.bean;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class Bean_MerchantInfo {
    String id;
    String userId;
    String name;
    String address;
    String shopsaddress;
    String src;
    String status;
    String createTime;

    @Override
    public String toString() {
        return "Bean_MerchantInfo{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", shopsaddress='" + shopsaddress + '\'' +
                ", src='" + src + '\'' +
                ", status='" + status + '\'' +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopsaddress() {
        return shopsaddress;
    }

    public void setShopsaddress(String shopsaddress) {
        this.shopsaddress = shopsaddress;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
