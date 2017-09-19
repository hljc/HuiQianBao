package com.ceq.app.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ceq.app.core.activity.Act_Main_Module;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Fragment;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app.main.methods.Method_Static;
import com.ceq.app_xinli_onekey.core.modules.enums.Enum_OKModule_Feature;

import java.util.TreeMap;


/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class Act_FragmentShare extends Framework_Activity {
    Framework_Fragment framework_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        framework_fragment.onSelected();
    }

    @Override
    public void initView() {

    }
    @Override
    public void initFragment() {
        super.initFragment();
        Util_Fragment util_fragment = new Util_Fragment();
        util_fragment.fragmentToInit((FragmentActivity) getActivity(), R.id.rl_fragment, new Util_Fragment.InitFragment() {
            @Override
            public void initFragment(TreeMap<Integer, Fragment> treeMap) {
                Fragment fragment = Act_Main_Module.util_fragment.getTm_fragment().get(Method_Static.getModuleFragmentPosition(Enum_OKModule_Feature.分享));
                if (fragment != null)
                    treeMap.put(0, fragment);
            }
        }, 0);

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
}
