package com.ceq.app.main.module.purse;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.activity.Act_Main_Module;
import com.ceq.app.core.activity.Act_Main_Web;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.main.activity.Act_LuckyPan;
import com.ceq.app.main.activity.Act_Mine_MyPurse;
import com.ceq.app.main.activity.Act_Purse_MySpread;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Web;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;
import com.ceq.app_xinli_onekey.core.modules.interfaces.Inter_OKModule_PurseUI;
import com.hqb.huiqianbao.R;

import java.util.List;

import static com.ceq.app.main.activity.Act_MyPurse_PurseDetailed.Extra_Int_MyPurse;
import static com.ceq.app.main.methods.Method_Static.Enum_PurseType.余额;
import static com.ceq.app.main.methods.Method_Static.Enum_PurseType.分润;
import static com.ceq.app.main.methods.Method_Static.Enum_PurseType.积分;
import static com.ceq.app.main.methods.Method_Static.selectCustomerService;
import static com.ceq.app.main.methods.Method_Static.skipToCollection;
import static com.ceq.app.main.methods.Method_Static.skipToCredit;
import static com.ceq.app.main.methods.Method_Static.skipToLoan;
import static com.ceq.app.main.methods.Method_Static.skipToMerchantCertification;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Bean_PurseUI$ZDQB implements Inter_OKModule_PurseUI.Inter_Purse$ZDQB {


    @Override
    public void initMainFunctionData$ZDQB(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions) {
        Activity activity = fragment.getActivity();
        itemList.add(new Bean_Item("在线收款", R.mipmap.icon_purse_onlinecollection, false).setUtil_Args_runnable(data1 -> skipToCollection((Act_Main_Module) activity)));
        itemList.add(new Bean_Item("商家收款", R.mipmap.icon_purse_merchantcollection, false).setUtil_Args_runnable(data1 -> skipToMerchantCertification(activity)));
        itemList.add(new Bean_Item("商家收款", R.mipmap.icon_purse_merchantcollection, false).setUtil_Args_runnable(data1 -> skipToMerchantCertification(activity)));
        itemList.add(new Bean_Item("商家收款", R.mipmap.icon_purse_merchantcollection, false).setUtil_Args_runnable(data1 -> skipToMerchantCertification(activity)));

        uiOptions.setColumns(4);
    }

    @Override
    public void initSecondaryFunctionData$ZDQB(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions) {
        Activity activity = fragment.getActivity();
        itemList.add(new Bean_Item("我的钱包", "资金实时掌握", R.mipmap.icon_purse_safe, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Mine_MyPurse.class).putExtra(Extra_Int_MyPurse, 1))));
        itemList.add(new Bean_Item("我的推广", "管理下级会员", R.mipmap.icon_purse_market, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Purse_MySpread.class))));
        itemList.add(new Bean_Item("贷款超市", "大额贷款任君选择", R.mipmap.icon_purse_loan, false).setUtil_Args_runnable(data1 -> skipToLoan(activity)));
        itemList.add(new Bean_Item("申请信用卡", "信用卡在线申请", R.mipmap.icon_purse_cerdit, false).setUtil_Args_runnable(data1 -> skipToCredit(activity)));
        uiOptions.setColumns(2);
    }

    //【赚道钱包】钱包明细3个功能（积分，余额，分润）
    @Override
    public void initUserBaseData$ZDQB(Fragment fragment, List<Bean_Item> itemList) {
        Activity activity = fragment.getActivity();
        itemList.add(new Bean_Item("积分", "0", 积分).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Mine_MyPurse.class).putExtra(Extra_Int_MyPurse, 0))));
        itemList.add(new Bean_Item("分润", "0.00", 分润).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Mine_MyPurse.class).putExtra(Extra_Int_MyPurse, 2))));
        itemList.add(new Bean_Item("余额", "0.00", 余额).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Mine_MyPurse.class).putExtra(Extra_Int_MyPurse, 1))));

    }

    @Override
    public void initTitleData$ZDQB(Fragment fragment, Bean_Item secondaryFunctionTitleItem, Bean_Item vpFunctionTitleItem) {
        secondaryFunctionTitleItem.setName("标题1");
        secondaryFunctionTitleItem.setImgId(R.mipmap.app_logo);

        vpFunctionTitleItem.setName("标题2");
        vpFunctionTitleItem.setImgId(R.mipmap.app_logo);
    }

    //【模块2(钱包):ViewPager初始化数据和点击事件】
    @Override//第1页
    public void initViewPagerData$1(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions) {
        Activity activity = fragment.getActivity();
        itemList.add(new Bean_Item("鼓励金", "每天抽一抽", R.mipmap.icon_purse_bill, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_LuckyPan.class))));
        itemList.add(new Bean_Item("在线客服", "随时咨询问题", R.mipmap.icon_purse_mobile, false).setUtil_Args_runnable(data1 -> selectCustomerService(activity)));
        itemList.add(new Bean_Item("收益排行", "了解收益详情", R.mipmap.icon_purse_flow_recharge, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Framework_Web.Extra_String_Url, "http://80.wechatpay.applinzi.com/src/%E6%94%B6%E7%9B%8A%E6%8E%92%E8%A1%8C.html?json=".concat(JSON.toJSONString(Abstract_App.bean_userInfo))))));
        itemList.add(new Bean_Item("积分排行", "了解积分详情", R.mipmap.icon_purse_trafficfines, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Framework_Web.Extra_String_Url, "http://80.wechatpay.applinzi.com/src/%E7%A7%AF%E5%88%86%E6%8E%92%E8%A1%8C.html?json=".concat(JSON.toJSONString(Abstract_App.bean_userInfo))))));
        uiOptions.setColumns(2);
    }

    @Override//第2页
    public void initViewPagerData$2(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions) {
        Activity activity = fragment.getActivity();
        itemList.add(new Bean_Item("火车票", "抢票的不二选", R.mipmap.icon_purse_train_tickets, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Framework_Web.Extra_String_Url, "http://1.xinli2017.applinzi.com/login/huochepiao.html?json=".concat(JSON.toJSONString(Abstract_App.bean_userInfo))))));
        itemList.add(new Bean_Item("加油卡充值", "更多折扣跟多惊喜", R.mipmap.icon_purse_fuelrecharge, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Framework_Web.Extra_String_Url, "http://1.xinli2017.applinzi.com/login/jiayou.html?json=".concat(JSON.toJSONString(Abstract_App.bean_userInfo))))));
        itemList.add(new Bean_Item("水电费", "水电物业即开即用", R.mipmap.icon_purse_water_electricity, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Framework_Web.Extra_String_Url, "http://1.xinli2017.applinzi.com/login/shuidianfei.html?json=".concat(JSON.toJSONString(Abstract_App.bean_userInfo))))));
        itemList.add(new Bean_Item("飞机票", "随时随地想订就订", R.mipmap.icon_purse_plane_ticket, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Framework_Web.Extra_String_Url, "http://1.xinli2017.applinzi.com/login/jipiao.html?json=".concat(JSON.toJSONString(Abstract_App.bean_userInfo))))));
        uiOptions.setColumns(3);
    }
}
