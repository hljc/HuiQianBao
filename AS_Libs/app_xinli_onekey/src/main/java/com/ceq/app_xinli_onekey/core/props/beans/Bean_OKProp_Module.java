package com.ceq.app_xinli_onekey.core.props.beans;


import com.ceq.app_core.utils.core.Util_Runnable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class Bean_OKProp_Module implements Serializable{
    int defaultModuleFragmentId;
    Bean_MiddleModuleImg bean_middleModuleImg;

    public int getDefaultModuleFragmentId() {
        return defaultModuleFragmentId;
    }

    public void setDefaultModuleFragmentId(int defaultModuleFragmentId) {
        this.defaultModuleFragmentId = defaultModuleFragmentId;
    }

    public Bean_MiddleModuleImg getBean_middleModuleImg() {
        return bean_middleModuleImg;
    }

    public void setBean_middleModuleImg(Bean_MiddleModuleImg bean_middleModuleImg) {
        this.bean_middleModuleImg = bean_middleModuleImg;
    }

    public enum Enum_MiddleModuleImgType implements Serializable{
        Outer, Inner
    }

    public static class Bean_MiddleModuleImg implements Serializable {
        int middleModuleImgOnId;
        int middleModuleImgOffId;
        Enum_MiddleModuleImgType enum_middleModuleImgType;
        Util_Runnable.Util_ArgsRunnable coverMiddleModuleRunnable;

        public Bean_MiddleModuleImg(int middleModuleImgOnId, int middleModuleImgOffId, Enum_MiddleModuleImgType enum_middleModuleImgType, Util_Runnable.Util_ArgsRunnable coverMiddleModuleRunnable) {
            this.middleModuleImgOnId = middleModuleImgOnId;
            this.middleModuleImgOffId = middleModuleImgOffId;
            this.enum_middleModuleImgType = enum_middleModuleImgType;
            this.coverMiddleModuleRunnable = coverMiddleModuleRunnable;
        }

        public Util_Runnable.Util_ArgsRunnable getCoverMiddleModuleRunnable() {
            return coverMiddleModuleRunnable;
        }

        public void setCoverMiddleModuleRunnable(Util_Runnable.Util_ArgsRunnable coverMiddleModuleRunnable) {
            this.coverMiddleModuleRunnable = coverMiddleModuleRunnable;
        }

        public Enum_MiddleModuleImgType getEnum_middleModuleImgType() {
            return enum_middleModuleImgType;
        }

        public void setEnum_middleModuleImgType(Enum_MiddleModuleImgType enum_middleModuleImgType) {
            this.enum_middleModuleImgType = enum_middleModuleImgType;
        }

        public int getMiddleModuleImgOffId() {
            return middleModuleImgOffId;
        }

        public void setMiddleModuleImgOffId(int middleModuleImgOffId) {
            this.middleModuleImgOffId = middleModuleImgOffId;
        }

        public int getMiddleModuleImgOnId() {
            return middleModuleImgOnId;
        }

        public void setMiddleModuleImgOnId(int middleModuleImgOnId) {
            this.middleModuleImgOnId = middleModuleImgOnId;
        }
    }
}
