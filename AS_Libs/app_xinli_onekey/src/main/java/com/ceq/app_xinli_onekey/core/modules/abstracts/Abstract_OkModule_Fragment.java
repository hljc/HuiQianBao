package com.ceq.app_xinli_onekey.core.modules.abstracts;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.ceq.app.core.bean.Bean_PushData;
import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_xinli_onekey.core.modules.enums.Enum_OKModule_Feature;
import com.ceq.app_xinli_onekey.core.modules.enums.Enum_OKModule_Template;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public abstract class Abstract_OkModule_Fragment<T> extends Framework_Fragment implements Util_Runnable.Util_TypeRunnable<T> {
    Enum_OKModule_Feature enum_okModule_feature;
    Enum_OKModule_Template enum_okModule_template;
    String tabBarTitle;
    int tabBarImgId;

    public Abstract_OkModule_Fragment() {
        this(null, 0);
    }

    public Abstract_OkModule_Fragment(String tabBarTitle, int tabBarImgId) {
        this.tabBarTitle = tabBarTitle;
        this.tabBarImgId = tabBarImgId;
    }

    public Fragment getFragmentInstance() {
        return this;
    }

    public Util_Runnable.Util_TypeRunnable<T> getOkModuleRunnable() {
        return this;
    }

    public void setOkModuleRunnable(Util_Runnable.Util_TypeRunnable<T> okModuleRunnable) {
        if (enum_okModule_feature == null || enum_okModule_template == null)
            return;
        T bean;
        String feature = enum_okModule_feature.feature;
        String template = enum_okModule_template.template;
        StringBuilder sb = new StringBuilder("com.ceq.app.main.module.");
        String className = sb.append(feature.toLowerCase()).append(".Bean_").append(feature).append("UI$").append(template).toString();
        try {
            bean = (T) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(new StringBuilder(className).append("不存在或者类名、包名错误！").toString());
        }
        okModuleRunnable.run(bean);
    }

    public void obtainModuleMetadata() {
        Enum_OKModule_Feature[] feature = Enum_OKModule_Feature.values();
        Enum_OKModule_Template[] template = Enum_OKModule_Template.values();
        String className = getFragmentInstance().getClass().getSimpleName();
        if (className.contains("Frag_Module")) {
            String templateName = className.substring(className.lastIndexOf("$") + 1);
            String featureName = className.substring(className.lastIndexOf("_") + 1, className.lastIndexOf("$"));
            for (int i = 0, length = feature.length; i < length; i++) {
                if (feature[i].feature.equals(featureName)) {
                    this.enum_okModule_feature = feature[i];
                    break;
                }
            }
            for (int i = 0, length = template.length; i < length; i++) {
                if (template[i].template.equals(templateName)) {
                    this.enum_okModule_template = template[i];
                    break;
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        obtainModuleMetadata();
        setOkModuleRunnable(getOkModuleRunnable());
    }

    public abstract void updateViewByPushMessage(Bean_PushData bean_pushData, Bean_Push bean_push);

    public String getTabBarTitle() {
        return tabBarTitle;
    }


    public int getTabBarImgId() {
        return tabBarImgId;
    }

    public Enum_OKModule_Template getEnum_okModule_template() {
        return enum_okModule_template;
    }

    public Enum_OKModule_Feature getEnum_okModule_feature() {
        return enum_okModule_feature;
    }
}
