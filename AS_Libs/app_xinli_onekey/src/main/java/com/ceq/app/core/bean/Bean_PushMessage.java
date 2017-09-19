package com.ceq.app.core.bean;

/**
 * Created by ceq on 2017/5/16.
 */

public class Bean_PushMessage {
    String id,userid,title,content,btype,createTime;

    @Override
    public String toString() {
        return "Bean_PushMessage{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", btype='" + btype + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
