package com.ceq.app.core.bean;

import com.youth.banner.BannerConfig;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class Bean_WelcomeOptions {
    int bannerConfig = BannerConfig.NOT_INDICATOR;
    int entryColor = Integer.MAX_VALUE;
    int entryMarginBottom = Integer.MAX_VALUE;

    public int getBannerConfig() {
        return bannerConfig;
    }

    public Bean_WelcomeOptions setBannerConfig(int bannerConfig) {
        this.bannerConfig = bannerConfig;
        return this;
    }

    public int getEntryColor() {
        return entryColor;
    }

    public Bean_WelcomeOptions setEntryColor(int entryColor) {
        this.entryColor = entryColor;
        return this;
    }

    public int getEntryMarginBottom() {
        return entryMarginBottom;
    }

    public Bean_WelcomeOptions setEntryMarginBottom(int entryMarginBottom) {
        this.entryMarginBottom = entryMarginBottom;
        return this;
    }
}
