package com.ceq.app.core.bean;

/**
 * Created by Administrator on 2016/11/8.
 */

public class Bean_ApkInfo {
    String version,url;

    @Override
    public String toString() {
        return "Bean_ApkInfo{" +
                "version='" + version + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
