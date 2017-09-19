package com.ceq.app_xinli_onekey.core.props.beans;


import com.ceq.app.main.activity.Act_Purse_MerchantCertification$JFB;
import com.ceq.app.main.activity.Act_Share_QRCodeShare$ZDQB;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.fragment.Frag_Module_Upgrade$JFB;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;

import java.io.Serializable;
import java.util.List;

import static com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Oem.Enum_OrderStatusFilter.显示全部;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class Bean_OKProp_Oem implements Serializable {
    public enum Enum_OrderStatusFilter implements Serializable {
        显示全部(""), 不显示待完成("1,2,3,4"), 只显示待完成("0"), 只显示已成功("1"), 只显示已取消("2"), 只显示待处理("3"), 只显示待结算("4");
        public String orderStatus;

        Enum_OrderStatusFilter(String orderStatus) {
            this.orderStatus = orderStatus;
        }
    }

    int $brandId;
    String $companyName;
    Util_Runnable.Util_ArgsRunnable $customerServiceRunnable;

    boolean needReferee;
    int carouselHeight;
    String aboutUsContent;
    Class remitActivity;
    Util_Runnable.Util_ArgsRunnable loginFilterRunnable;
    Util_Runnable.Util_ArgsRunnable mainUIRunnable;
    Util_Runnable.Util_ArgsRunnable marqueeClickRunnable;
    Util_Runnable.Util_TypeRunnable<List<Bean_Channel>> channelFilterRunnable;
    Class QRCodeShareActivity = Act_Share_QRCodeShare$ZDQB.class;
    Class MerchantCertificationActivity = Act_Purse_MerchantCertification$JFB.class;
    Enum_OrderStatusFilter orderStatusFilter = 显示全部;
    Abstract_OkModule_Fragment productionUpgradeFragment = new Frag_Module_Upgrade$JFB();

    String telephone="";

    public int getCarouselHeight() {
        return carouselHeight;
    }

    public void setCarouselHeight(int carouselHeight) {
        this.carouselHeight = carouselHeight;
    }

    public Class getMerchantCertificationActivity() {
        return MerchantCertificationActivity;
    }

    public void setMerchantCertificationActivity(Class merchantCertificationActivity) {
        MerchantCertificationActivity = merchantCertificationActivity;
    }

    public Util_Runnable.Util_ArgsRunnable getMainUIRunnable() {
        return mainUIRunnable;
    }

    public void setMainUIRunnable(Util_Runnable.Util_ArgsRunnable mainUIRunnable) {
        this.mainUIRunnable = mainUIRunnable;
    }

    public Util_Runnable.Util_ArgsRunnable getMarqueeClickRunnable() {
        return marqueeClickRunnable;
    }

    public void setMarqueeClickRunnable(Util_Runnable.Util_ArgsRunnable marqueeClickRunnable) {
        this.marqueeClickRunnable = marqueeClickRunnable;
    }

    public Util_Runnable.Util_TypeRunnable<List<Bean_Channel>> getChannelFilterRunnable() {
        return channelFilterRunnable;
    }

    public void setChannelFilterRunnable(Util_Runnable.Util_TypeRunnable<List<Bean_Channel>> channelFilterRunnable) {
        this.channelFilterRunnable = channelFilterRunnable;
    }


    public int get$brandId() {
        return $brandId;
    }

    public void set$brandId(int $brandId) {
        this.$brandId = $brandId;
    }

    public String get$companyName() {
        return $companyName;
    }

    public void set$companyName(String $companyName) {
        this.$companyName = $companyName;
    }

    public Util_Runnable.Util_ArgsRunnable get$customerServiceRunnable() {
        return $customerServiceRunnable;
    }

    public void set$customerServiceRunnable(Util_Runnable.Util_ArgsRunnable $customerServiceRunnable) {
        this.$customerServiceRunnable = $customerServiceRunnable;
    }

    public Enum_OrderStatusFilter getOrderStatusFilter() {
        return orderStatusFilter;
    }

    public void setOrderStatusFilter(Enum_OrderStatusFilter orderStatusFilter) {
        this.orderStatusFilter = orderStatusFilter;
    }


    public boolean isNeedReferee() {
        return needReferee;
    }

    public void setNeedReferee(boolean needReferee) {
        this.needReferee = needReferee;
    }

    public String getAboutUsContent() {
        return aboutUsContent;
    }

    public void setAboutUsContent(String aboutUsContent) {
        this.aboutUsContent = aboutUsContent;
    }

    public Class getQRCodeShareActivity() {
        return QRCodeShareActivity;
    }

    public void setQRCodeShareActivity(Class QRCodeShareActivity) {
        this.QRCodeShareActivity = QRCodeShareActivity;
    }

    public Class getRemitActivity() {
        return remitActivity;
    }

    public void setRemitActivity(Class remitActivity) {
        this.remitActivity = remitActivity;
    }

    public Abstract_OkModule_Fragment getProductionUpgradeFragment() {
        return productionUpgradeFragment;
    }

    public void setProductionUpgradeFragment(Abstract_OkModule_Fragment productionUpgradeFragment) {
        this.productionUpgradeFragment = productionUpgradeFragment;
    }

    public Util_Runnable.Util_ArgsRunnable getLoginFilterRunnable() {
        return loginFilterRunnable;
    }

    public void setLoginFilterRunnable(Util_Runnable.Util_ArgsRunnable loginFilterRunnable) {
        this.loginFilterRunnable = loginFilterRunnable;
    }

    public String getTelephone() {
        return telephone;
    }

    @Deprecated
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
