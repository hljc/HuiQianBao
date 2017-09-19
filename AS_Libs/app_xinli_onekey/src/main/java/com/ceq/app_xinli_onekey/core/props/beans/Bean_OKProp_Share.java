package com.ceq.app_xinli_onekey.core.props.beans;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_xinli_onekey.R;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class Bean_OKProp_Share implements Serializable{
    String shareUrl;
    String shareTitle;
    String shareContent;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String phone,String brandId) {
        this.shareUrl = new StringBuilder("http://1.xinli2017.applinzi.com/login/index2.html?phone=").append(Util_Empty.isEmptyToReplace(phone, "")).append("&brand_id=").append(brandId).toString();
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle == null ? Abstract_App.getInstance().getResources().getString(R.string.app_name).concat("，改变您的赚钱方式！") : shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent == null ? "全球化金融电商新平台，支付、购物、信用卡、贷款、便民……互联网+分享经济时代，别人的手机就是您的印钞机！" : shareContent;
    }
}
