package com.ceq.app.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Fragment;
import com.ceq.app_xinli_onekey.R;

import java.util.TreeMap;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class Act_FragActivity extends Framework_Activity {
    public static Fragment fragment;
    Util_Fragment util_fragment = new Util_Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_fragactivity);
    }

    @Override
    public void initFragment() {
        super.initFragment();
        util_fragment.fragmentToInit((FragmentActivity) getActivity(), R.id.rl_fragActivity, new Util_Fragment.InitFragment() {
            @Override
            public void initFragment(TreeMap<Integer, Fragment> treeMap) {
                treeMap.put(0, fragment);
            }
        }, 0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (fragment instanceof Framework_Fragment)
            ((Framework_Fragment) fragment).onSelected();
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
}
