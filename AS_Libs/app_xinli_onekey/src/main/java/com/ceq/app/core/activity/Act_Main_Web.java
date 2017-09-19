package com.ceq.app.core.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_Web;
import com.ceq.app_core.utils.core.Util_Fragment;
import com.ceq.app_xinli_onekey.R;

import java.util.TreeMap;

import static com.ceq.app_core.framework.Framework_Web.Extra_String_Title;
import static com.ceq.app_core.framework.Framework_Web.Extra_String_Url;

/**
 * Created by Administrator on 2016/9/20.
 */
public class Act_Main_Web extends Framework_Activity {
    Framework_Web framework_web = new Framework_Web();
    Util_Fragment util_fragment = new Util_Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        String title = intent.getStringExtra(Extra_String_Title);
        String url = intent.getStringExtra(Extra_String_Url);
        Bundle bundle = new Bundle();
        if (title != null)
            bundle.putString(Extra_String_Title, title);
        if (url != null)
            bundle.putString(Framework_Web.Extra_String_Url, url);
        framework_web.setArguments(bundle);
        init(R.layout.act_main_web);
    }

    @Override
    public void initFragment() {
        super.initFragment();
        util_fragment.fragmentToInit((FragmentActivity) getActivity(), R.id.rl_webFragment, new Util_Fragment.InitFragment() {
            @Override
            public void initFragment(TreeMap<Integer, Fragment> treeMap) {
                treeMap.put(0, framework_web);
            }

        }, 0);
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
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        if (framework_web.webView.canGoBack())
            framework_web.webView.goBack();
        else
            finish();
    }
}
