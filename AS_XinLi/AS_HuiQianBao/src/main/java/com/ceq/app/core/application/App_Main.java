package com.ceq.app.core.application;

import android.app.Activity;

import com.ceq.app.core.bean.Bean_WelcomeOptions;
import com.ceq.app.main.activity.Act_Share_QRCodeShare$SKJF;
import com.ceq.app.main.fragment.Frag_Module_Collection$MGQB2;
import com.ceq.app.main.fragment.Frag_Module_Mine$JFB;
import com.ceq.app.main.fragment.Frag_Module_Purse$ZDQB;
import com.ceq.app.main.fragment.Frag_Module_Upgrade$JFB;
import com.ceq.app_core.bean.Bean_Banner;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.utils.extend.customer.abstracts.Util_Customer;
import com.ceq.app_core.utils.extend.customer.enums.Enum_Platform_Customer;
import com.ceq.app_xinli_onekey.core.bootstrap.bean.Bean_OneKeyBootstrap;
import com.ceq.app_xinli_onekey.core.bootstrap.interfaces.Inter_OneKeyBootstrap;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Module;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Oem;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Share;
import com.hqb.huiqianbao.R;
import com.youth.banner.BannerConfig;

import java.util.List;

import static com.ceq.app.core.activity.Act_Main_Module.Enum_Customer.QQ;
import static com.ceq.app.core.activity.Act_Main_Module.Enum_Customer.微信;
import static com.ceq.app.main.methods.Method_Static.callCustomerService;
import static com.ceq.app.main.methods.Method_Static.callTelephone;
import static com.ceq.app.main.methods.Method_Static.skipToShareDialog$JFB;


public class App_Main extends Abstract_App {
    /**
     * 一、【AndroidManifest.xml、assets、mipmap-xxxhdpi】               修改包名、APP名字、APP图标、公司名、电话号码【使用全局查找替换】
     * 二、【strings.xml、colors.xml】                                   修改文字、颜色
     * 三、【mipmap-xxxhdpi】                                            修改图片
     * 四、【Constant_Common.java】                                      修改分享内容
     * 五、【App_Main.java、ShareSDK.xml】                               修改第三方SDK集成AppKey，AppId等信息（分享、在线客服）
     * 六、【App_Main.java】                                             修改【模块二：钱包】功能，目前支持【和易付、牛码付、星星钱包】三个模板
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //要修改
        Util_Customer.initial(Enum_Platform_Customer.美洽, "dbf82fc5a2300c94b7b1ab30e94b3880");
    }

    //要修改
    @Override
    public void initOneKey() {
        bean_oneKeyBootstrap = new Bean_OneKeyBootstrap(new Inter_OneKeyBootstrap() {
            @Override
            public void initOneKeyProps(Bean_OKProp_Oem oem, Bean_OKProp_Module module, Bean_OKProp_Share share) {
                oem.set$brandId(86);
                oem.set$companyName("汇钱包");
                oem.set$customerServiceRunnable(data -> {
                    Activity activity = (Activity) data[0];
                    List<Bean_Item> customerList = (List<Bean_Item>) data[1];
                    customerList.add(new Bean_Item("电话客服", "优先推荐", R.mipmap.icon_customer_mobile, null).setUtil_Args_runnable(data1 -> callTelephone(activity)));
//                    customerList.add(new Bean_Item("美洽客服", "优先推荐", R.mipmap.icon_customer_meiqia, 美洽).setUtil_Args_runnable(data1 -> callCustomerService(activity, 美洽)));
                    customerList.add(new Bean_Item("QQ客服", "暂未开放", R.mipmap.icon_customer_qq, QQ).setUtil_Args_runnable(data1 -> callCustomerService(activity, QQ)));
                    customerList.add(new Bean_Item("微信客服", "暂未开放", R.mipmap.icon_customer_wx, 微信).setUtil_Args_runnable(data1 -> callCustomerService(activity, 微信)));
                });
                oem.setQRCodeShareActivity(Act_Share_QRCodeShare$SKJF.class);
                oem.setCarouselHeight(320);

                module.setDefaultModuleFragmentId(1);
                module.setBean_middleModuleImg(new Bean_OKProp_Module.Bean_MiddleModuleImg(R.mipmap.icon_module_middle_on, R.mipmap.icon_module_middle_on, Bean_OKProp_Module.Enum_MiddleModuleImgType.Inner, data -> skipToShareDialog$JFB((Activity) data[0])));

            }

            @Override
            public void initOneKeyModules(List<Abstract_OkModule_Fragment> fragmentList) {
                //   fragmentList.add(new Frag_Module_Purse$ZDQB("首页", R.mipmap.icon_purse_off));
                //  fragmentList.add(new Frag_Module_Purse$NMF("首页", R.mipmap.icon_purse_off));
                fragmentList.add(new Frag_Module_Collection$MGQB2("收款", R.mipmap.icon_collection_off));
                fragmentList.add(new Frag_Module_Purse$ZDQB("首页", R.mipmap.icon_purse_off));
                fragmentList.add(new Frag_Module_Purse$ZDQB("首页", R.mipmap.icon_purse_off));
                fragmentList.add(new Frag_Module_Upgrade$JFB("升级", R.mipmap.icon_upgrade_off));
                fragmentList.add(new Frag_Module_Mine$JFB("我的", R.mipmap.icon_mine_off));
            }

            @Override
            public void initOneKeyWelcome(List<Bean_Banner> bannerList, Bean_WelcomeOptions welcomeOptions) {
                bannerList.add(new Bean_Banner(R.mipmap.welcome1, null));
                bannerList.add(new Bean_Banner(R.mipmap.welcome2, null));
                bannerList.add(new Bean_Banner(R.mipmap.welcome3, null));
                welcomeOptions.setBannerConfig(BannerConfig.NUM_INDICATOR).setEntryMarginBottom(20);
            }
        });
    }
}
