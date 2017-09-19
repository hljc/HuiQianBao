package com.ceq.app.core.bean;

/**
 * Created by Administrator on 2016/9/23.
 */
public class Bean_Module {
    String name;
    int imgId;
    boolean isChecked;

    public Bean_Module(String name, int imgId, boolean isChecked) {
        this.name = name;
        this.imgId = imgId;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    @Override
    public String toString() {
        return "Bean_Module{" +
                "name='" + name + '\'' +
                ", imgId=" + imgId +
                '}';
    }
}
