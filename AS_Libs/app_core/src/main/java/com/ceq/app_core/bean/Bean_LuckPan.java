package com.ceq.app_core.bean;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class Bean_LuckPan {
    String name;
    int img;
    String other;

    public Bean_LuckPan(String name, int img, String other) {
        this.name = name;
        this.img = img;
        this.other = other;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "Bean_LuckPan{" +
                "name='" + name + '\'' +
                ", img=" + img +
                ", other='" + other + '\'' +
                '}';
    }
}
