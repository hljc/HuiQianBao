package com.ceq.app.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ceq.app.core.bean.Bean_PushData;
import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.framework.Framework_Web;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class Frag_Module_Web$All extends Abstract_OkModule_Fragment {
    Frag_Web_Custom frag_web_custom=new Frag_Web_Custom();

    public static class Frag_Web_Custom extends Framework_Web {
        @Override
        public void initView() {
            super.initView();
            iv_left.setVisibility(View.GONE);
        }
    }

    public Frag_Module_Web$All() {
    }

    public Frag_Module_Web$All(String tabBarTitle, int tabBarImgId, boolean showTitle, String url) {
        super(tabBarTitle, tabBarImgId);
        Bundle bundle = new Bundle();
        if (showTitle)
            bundle.putString(Framework_Web.Extra_String_Title, getTabBarTitle());
        bundle.putString(Framework_Web.Extra_String_Url, url);
        frag_web_custom.setArguments(bundle);
    }

    @Override
    public Fragment getFragmentInstance() {
        return frag_web_custom;
    }

    @Override
    public void run(Object data) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onSelected() {
    }

    @Override
    public void updateViewByPushMessage(Bean_PushData bean_pushData, Bean_Push bean_push) {

    }

    @Override
    public void onClick(View v) {

    }
}
