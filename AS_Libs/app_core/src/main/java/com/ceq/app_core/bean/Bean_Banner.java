package com.ceq.app_core.bean;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/3.
 */
public class Bean_Banner implements Serializable {
    String img,href;

    public String getImg() {
        return img;
    }


    public String getHref() {
        return href;
    }


    public Bean_Banner(int img, String href) {
        this.img=new Uri.Builder().scheme("res").path(String.valueOf(img)).build().toString();
        this.href = href;
    }
}
