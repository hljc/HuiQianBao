package com.ceq.app.core.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class Bean_Banner2 implements Serializable {
    String imgurl,brandId,id,title,url;

    public Bean_Banner2() {
    }

    public Bean_Banner2(String imgurl, String url) {
        this.imgurl = imgurl;
        this.url = url;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
