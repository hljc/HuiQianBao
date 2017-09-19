package com.ceq.app_core.bean;


import com.ceq.app_core.utils.core.Util_Runnable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class Bean_Item implements Serializable {
    Object name;
    String subName;
    String value;
    List<?> list_value;
    int imgId;
    String imgPath;
    boolean isChecked;
    Object object;
    Util_Runnable.Util_ArgsRunnable util_Args_runnable;

    public Util_Runnable.Util_ArgsRunnable getUtil_Args_runnable() {
        return util_Args_runnable;
    }

    public Bean_Item setUtil_Args_runnable(Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        this.util_Args_runnable = util_Args_runnable;
        return this;
    }

    public Bean_Item(Object name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public Bean_Item(Object name, int imgId, List<?> list_value) {
        this.name = name;
        this.list_value = list_value;
        this.imgId = imgId;
    }

    public Bean_Item(Object name, String subName, Object object, boolean isChecked) {
        this.name = name;
        this.subName = subName;
        this.object = object;
        this.isChecked = isChecked;
    }

    public Bean_Item(Object name, String subName, String value, int imgId, Object object, boolean isChecked) {
        this.name = name;
        this.subName = subName;
        this.value = value;
        this.imgId = imgId;
        this.object = object;
        this.isChecked = isChecked;
    }

    public Bean_Item(Object name, String subName, String value, String imgPath, Object object, boolean isChecked) {
        this.name = name;
        this.subName = subName;
        this.value = value;
        this.imgPath = imgPath;
        this.object = object;
        this.isChecked = isChecked;
    }


    public Bean_Item(Object name, String value, int imgId, Object object) {
        this.name = name;
        this.value = value;
        this.imgId = imgId;
        this.object = object;
    }

    public Bean_Item(Object name, String value, int imgId, Object object, boolean isChecked) {
        this.name = name;
        this.value = value;
        this.imgId = imgId;
        this.object = object;
        this.isChecked = isChecked;
    }


    public Object getObject() {

        return object;
    }

    public Bean_Item setObject(Object object) {
        this.object = object;
        return this;
    }

    public Bean_Item() {
    }

    public Bean_Item(Object name) {
        this.name = name;
    }

    public Bean_Item(String imgPath, Object name, String subName, String value, List<?> list_value) {
        this.name = name;
        this.subName = subName;
        this.value = value;
        this.imgPath = imgPath;
        this.list_value = list_value;
    }

    public Bean_Item(Object name, String subName, String value, int imgId, List<?> list_value) {
        this.name = name;
        this.subName = subName;
        this.value = value;
        this.imgId = imgId;
        this.list_value = list_value;
    }

    public Bean_Item(Object name, String subName, String value, int imgId) {
        this.name = name;
        this.subName = subName;
        this.value = value;
        this.imgId = imgId;
    }

    public Bean_Item(Object name, String subName, String value, boolean isChecked) {
        this.name = name;
        this.subName = subName;
        this.value = value;
        this.isChecked = isChecked;
    }

    public Bean_Item(Object name, String subName, String value) {

        this.name = name;
        this.subName = subName;
        this.value = value;
    }

    public Bean_Item(Object name, String subName, boolean isChecked) {
        this.name = name;
        this.subName = subName;
        this.isChecked = isChecked;
    }

    public Bean_Item(Object name, String value, int imgId, boolean isChecked) {
        this.name = name;
        this.value = value;
        this.imgId = imgId;
        this.isChecked = isChecked;
    }

    public Bean_Item(Object name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public Bean_Item(Object name, String value, Object object) {
        this.name = name;
        this.value = value;
        this.object = object;
    }

    public Bean_Item(Object name, int imgId, boolean isChecked) {
        this.name = name;
        this.imgId = imgId;
        this.isChecked = isChecked;
    }

    public Bean_Item(Object name, int imgId, Object object) {
        this.name = name;
        this.imgId = imgId;
        this.object = object;
    }

    public Bean_Item(Object name, String value) {
        this.name = name;
        this.value = value;
    }

    public Bean_Item(Object name, List<?> list_value) {
        this.name = name;
        this.list_value = list_value;
    }

    public Object getName() {
        return name;
    }

    public Bean_Item setName(Object name) {
        this.name = name;
        return this;
    }

    public String getSubName() {
        return subName;
    }

    public Bean_Item setSubName(String subName) {
        this.subName = subName;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Bean_Item setValue(String value) {
        this.value = value;
        return this;
    }

    public List<?> getList_value() {
        return list_value;
    }

    public void setList_value(List<?> list_value) {
        this.list_value = list_value;
    }

    public int getImgId() {
        return imgId;
    }

    public Bean_Item setImgId(int imgId) {
        this.imgId = imgId;
        return this;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Bean_Item{" +
                "name=" + name +
                ", subName='" + subName + '\'' +
                ", value='" + value + '\'' +
                ", list_value=" + list_value +
                ", imgId=" + imgId +
                ", imgPath='" + imgPath + '\'' +
                ", isChecked=" + isChecked +
                ", object=" + object +
                ", util_Args_runnable=" + util_Args_runnable +
                '}';
    }
}
