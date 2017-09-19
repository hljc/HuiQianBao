package com.ceq.app_xinli_onekey.core.bootstrap.bean;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.ceq.app.core.activity.Act_Main_Module;
import com.ceq.app.core.bean.Bean_Banner2;
import com.ceq.app.core.bean.Bean_WelcomeOptions;
import com.ceq.app_core.bean.Bean_Banner;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.utils.core.Util_Fragment;
import com.ceq.app_xinli_onekey.core.bootstrap.interfaces.Inter_OneKeyBootstrap;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Share;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OneKeyProps;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static com.ceq.app.core.application.Abstract_App.bean_userInfo;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Bean_OneKeyBootstrap {
    Inter_OneKeyBootstrap inter_oneKeyBootstrap;
    Bean_OneKeyProps bean_oneKeyProps = new Bean_OneKeyProps();

    Bean_WelcomeOptions bean_welcomeOptions = new Bean_WelcomeOptions();

    List<Bean_Banner> bean_welcomeList = new ArrayList<>();
    List<Bean_Banner2> bean_carouselList = new ArrayList<>();
    List<Abstract_OkModule_Fragment> abstract_okModule_fragmentList = new ArrayList<>();

    public Bean_OneKeyBootstrap(Inter_OneKeyBootstrap inter_oneKeyBootstrap) {
        this.inter_oneKeyBootstrap = inter_oneKeyBootstrap;

        Bean_OKProp_Share bean_okProp_share = bean_oneKeyProps.getBean_okProp_share();

        bean_okProp_share.setShareTitle(null);
        bean_okProp_share.setShareContent(null);

        inter_oneKeyBootstrap.initOneKeyProps(bean_oneKeyProps.getBean_okProp_oem(), bean_oneKeyProps.getBean_okProp_module(), bean_oneKeyProps.getBean_okProp_share());
        bean_okProp_share.setShareUrl(bean_userInfo == null ? null : bean_userInfo.getPhone(), String.valueOf(bean_oneKeyProps.getBean_okProp_oem().get$brandId()));
        inter_oneKeyBootstrap.initOneKeyModules(abstract_okModule_fragmentList);
        inter_oneKeyBootstrap.initOneKeyWelcome(bean_welcomeList, bean_welcomeOptions);

        int moduleSize = abstract_okModule_fragmentList.size();
        if (moduleSize < 1 || moduleSize > 5)
            throw new RuntimeException("傻吊，inter_oneKeyModuleList最起码添加1个主模块，不能超过5个，understand？");
    }

    public void initModuleFragment(FragmentActivity fragmentActivity, Util_Fragment util_fragment, final List<Bean_Item> moduleTabsList, int toggleFragmentId, final int defaultFragmentPosition) {
        util_fragment.fragmentToInit(fragmentActivity, toggleFragmentId, new Util_Fragment.InitFragment() {
            @Override
            public void initFragment(TreeMap<Integer, Fragment> treeMap) {
                for (int i = 0, size = abstract_okModule_fragmentList.size(); i < size; i++) {
                    Abstract_OkModule_Fragment abstract_okModule_fragment = abstract_okModule_fragmentList.get(i);
                    if (abstract_okModule_fragment == null)
                        continue;
                        treeMap.put(treeMap.size(), abstract_okModule_fragment.getFragmentInstance());
                        moduleTabsList.add(new Bean_Item(abstract_okModule_fragment.getTabBarTitle(), abstract_okModule_fragment.getTabBarImgId(), false));
                }
                if (defaultFragmentPosition >= treeMap.size())
                    throw new RuntimeException(new StringBuilder("傻吊，你现在一共就").append(treeMap.size()).append("个模块，看仔细！修改defaultModuleFragmentId字段").toString());
            }

            @Override
            public void onSelected(int currentFragmentId, Fragment currentFragment) {
                super.onSelected(currentFragmentId, currentFragment);
                Act_Main_Module.moduleFragmentPosition = currentFragmentId;
            }
        }, defaultFragmentPosition);
        moduleTabsList.get(defaultFragmentPosition).setChecked(true);
    }

    public Bean_OneKeyProps getBean_oneKeyProps() {
        return bean_oneKeyProps;
    }

    public Bean_WelcomeOptions getBean_welcomeOptions() {
        return bean_welcomeOptions;
    }

    public List<Bean_Banner> getBean_welcomeList() {
        return bean_welcomeList;
    }

    public List<Bean_Banner2> getBean_carouselList() {
        return bean_carouselList;
    }

    public void setBean_carouselList(List<Bean_Banner2> bean_carouselList) {
        this.bean_carouselList = bean_carouselList;
    }

    public List<Abstract_OkModule_Fragment> getAbstract_okModule_fragmentList() {
        return abstract_okModule_fragmentList;
    }
}
