package com.ceq.app_core.bean;

import java.io.Serializable;

/**
 * Created by ceq on 2017/5/20.
 */

public class Bean_ImageOptions implements Serializable {
    boolean enableCompress = true;
    boolean showCompressProgress = true;
    boolean enableCrop;
    boolean systemCrop = true;
    boolean onlyTakePhoto;
    int maxPhotoCount = 9;
    int maxCompressPixel = 1280;
    int maxCompressCapacity = 1024 * 1000 * 1000;
    int cropWidth = 1280;
    int cropHeight = 1280;

    public boolean isEnableCompress() {
        return enableCompress;
    }

    public void setEnableCompress(boolean enableCompress) {
        this.enableCompress = enableCompress;
    }

    public boolean isShowCompressProgress() {
        return showCompressProgress;
    }

    public void setShowCompressProgress(boolean showCompressProgress) {
        this.showCompressProgress = showCompressProgress;
    }

    public boolean isEnableCrop() {
        return enableCrop;
    }

    public void setEnableCrop(boolean enableCrop) {
        this.enableCrop = enableCrop;
    }

    public boolean isSystemCrop() {
        return systemCrop;
    }

    public void setSystemCrop(boolean systemCrop) {
        this.systemCrop = systemCrop;
    }

    public boolean isOnlyTakePhoto() {
        return onlyTakePhoto;
    }

    public void setOnlyTakePhoto(boolean onlyTakePhoto) {
        this.onlyTakePhoto = onlyTakePhoto;
    }

    public int getMaxPhotoCount() {
        return maxPhotoCount;
    }

    public void setMaxPhotoCount(int maxPhotoCount) {
        this.maxPhotoCount = maxPhotoCount;
    }

    public int getMaxCompressPixel() {
        return maxCompressPixel;
    }

    public void setMaxCompressPixel(int maxCompressPixel) {
        this.maxCompressPixel = maxCompressPixel;
    }

    public int getMaxCompressCapacity() {
        return maxCompressCapacity;
    }

    public void setMaxCompressCapacity(int maxCompressCapacity) {
        this.maxCompressCapacity = maxCompressCapacity;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    @Override
    public String toString() {
        return "Bean_ImageOptions{" +
                "enableCompress=" + enableCompress +
                ", showCompressProgress=" + showCompressProgress +
                ", enableCrop=" + enableCrop +
                ", systemCrop=" + systemCrop +
                ", onlyTakePhoto=" + onlyTakePhoto +
                ", maxPhotoCount=" + maxPhotoCount +
                ", maxCompressPixel=" + maxCompressPixel +
                ", maxCompressCapacity=" + maxCompressCapacity +
                ", cropWidth=" + cropWidth +
                ", cropHeight=" + cropHeight +
                '}';
    }
}
