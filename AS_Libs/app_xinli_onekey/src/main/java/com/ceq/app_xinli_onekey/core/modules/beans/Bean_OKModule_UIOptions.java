package com.ceq.app_xinli_onekey.core.modules.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class Bean_OKModule_UIOptions implements Serializable {
    int rows, columns, viewBackgroundResource,viewBackgroundColor, itemBackgroundResource, itemHeight, imageHeight, imageWidth, imageMarginBottom, imageMarginTop, imageMarginLeft, imageMarginRight, textSize;
    int keyboardPressOnBackgroundColor, keyboardPressOffBackgroundColor, keyboardPressOnBackgroundResource, keyboardPressOffBackgroundResource, keyboardPressOnContentColor, keyboardPressOffContentColor;

    public int getViewBackgroundColor() {
        return viewBackgroundColor;
    }

    public void setViewBackgroundColor(int viewBackgroundColor) {
        this.viewBackgroundColor = viewBackgroundColor;
    }

    public int getKeyboardPressOnBackgroundColor() {
        return keyboardPressOnBackgroundColor;
    }

    public void setKeyboardPressOnBackgroundColor(int keyboardPressOnBackgroundColor) {
        this.keyboardPressOnBackgroundColor = keyboardPressOnBackgroundColor;
    }

    public int getKeyboardPressOffBackgroundColor() {
        return keyboardPressOffBackgroundColor;
    }

    public void setKeyboardPressOffBackgroundColor(int keyboardPressOffBackgroundColor) {
        this.keyboardPressOffBackgroundColor = keyboardPressOffBackgroundColor;
    }

    public int getKeyboardPressOnBackgroundResource() {
        return keyboardPressOnBackgroundResource;
    }

    public void setKeyboardPressOnBackgroundResource(int keyboardPressOnBackgroundResource) {
        this.keyboardPressOnBackgroundResource = keyboardPressOnBackgroundResource;
    }

    public int getKeyboardPressOffBackgroundResource() {
        return keyboardPressOffBackgroundResource;
    }

    public void setKeyboardPressOffBackgroundResource(int keyboardPressOffBackgroundResource) {
        this.keyboardPressOffBackgroundResource = keyboardPressOffBackgroundResource;
    }

    public int getKeyboardPressOnContentColor() {
        return keyboardPressOnContentColor;
    }

    public void setKeyboardPressOnContentColor(int keyboardPressOnContentColor) {
        this.keyboardPressOnContentColor = keyboardPressOnContentColor;
    }

    public int getKeyboardPressOffContentColor() {
        return keyboardPressOffContentColor;
    }

    public void setKeyboardPressOffContentColor(int keyboardPressOffContentColor) {
        this.keyboardPressOffContentColor = keyboardPressOffContentColor;
    }

    public int getImageMarginBottom() {
        return imageMarginBottom;
    }

    public int getImageMarginTop() {
        return imageMarginTop;
    }

    public int getImageMarginLeft() {
        return imageMarginLeft;
    }

    public int getImageMarginRight() {
        return imageMarginRight;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getColumns() {
        return columns;
    }

    public Bean_OKModule_UIOptions setColumns(int columns) {
        this.columns = columns;
        return this;
    }

    public int getRows() {
        return rows;
    }

    public Bean_OKModule_UIOptions setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public int getViewBackgroundResource() {
        return viewBackgroundResource;
    }

    public void setViewBackgroundResource(int viewBackgroundResource) {
        this.viewBackgroundResource = viewBackgroundResource;
    }

    public int getItemBackgroundResource() {
        return itemBackgroundResource;
    }

    public void setItemBackgroundResource(int itemBackgroundResource) {
        this.itemBackgroundResource = itemBackgroundResource;
    }

    public void setImageMarginBottom(int imageMarginBottom) {
        this.imageMarginBottom = imageMarginBottom;
    }

    public void setImageMarginTop(int imageMarginTop) {
        this.imageMarginTop = imageMarginTop;
    }

    public void setImageMarginLeft(int imageMarginLeft) {
        this.imageMarginLeft = imageMarginLeft;
    }

    public void setImageMarginRight(int imageMarginRight) {
        this.imageMarginRight = imageMarginRight;
    }
}
