package com.ceq.app.main.bean;

import java.io.Serializable;

/**
 * Created by ceq on 2017/4/17.
 */

public class Bean_Channel implements Serializable {

    String id;
    String channelTag;
    String channelNo;
    String name;
    String subChannelTag;
    String subName;
    String singleMinLimit;
    String startTime;
    String endTime;
    String rate;
    String createTime;
    String singleMaxLimit;
    String everyDayMaxLimit;
    String channelParams;
    String notifyURL;
    String status;
    String log;
    String remarks;

    @Override
    public String toString() {
        return "Bean_Channel{" +
                "id='" + id + '\'' +
                ", channelTag='" + channelTag + '\'' +
                ", channelNo='" + channelNo + '\'' +
                ", name='" + name + '\'' +
                ", subChannelTag='" + subChannelTag + '\'' +
                ", subName='" + subName + '\'' +
                ", singleMinLimit='" + singleMinLimit + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", rate='" + rate + '\'' +
                ", createTime='" + createTime + '\'' +
                ", singleMaxLimit='" + singleMaxLimit + '\'' +
                ", everyDayMaxLimit='" + everyDayMaxLimit + '\'' +
                ", channelParams='" + channelParams + '\'' +
                ", notifyURL='" + notifyURL + '\'' +
                ", status='" + status + '\'' +
                ", log='" + log + '\'' +
                '}';
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




    public String getChannelParams() {
        return channelParams;
    }

    public void setChannelParams(String channelParams) {
        this.channelParams = channelParams;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

    public String getChannelTag() {
        return channelTag;
    }

    public void setChannelTag(String channelTag) {
        this.channelTag = channelTag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubChannelTag() {
        return subChannelTag;
    }

    public void setSubChannelTag(String subChannelTag) {
        this.subChannelTag = subChannelTag;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSingleMinLimit() {
        return singleMinLimit;
    }

    public void setSingleMinLimit(String singleMinLimit) {
        this.singleMinLimit = singleMinLimit != null && singleMinLimit.contains(".") ? singleMinLimit.substring(0, singleMinLimit.indexOf(".")) : singleMinLimit;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSingleMaxLimit() {
        return singleMaxLimit;
    }

    public void setSingleMaxLimit(String singleMaxLimit) {
        this.singleMaxLimit = singleMaxLimit != null && singleMaxLimit.contains(".") ? singleMaxLimit.substring(0, singleMaxLimit.indexOf(".")) : singleMaxLimit;
    }

    public String getEveryDayMaxLimit() {
        return everyDayMaxLimit;
    }

    public void setEveryDayMaxLimit(String everyDayMaxLimit) {
        this.everyDayMaxLimit = everyDayMaxLimit;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
