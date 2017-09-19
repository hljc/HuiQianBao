package com.ceq.app_core.utils.core;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ceq.app_core.framework.Framework_Fragment;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_Fragment {
    private TreeMap<Integer, Fragment> tm_fragment = new TreeMap<>();
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private InitFragment initFragment;

    public abstract static class InitFragment {
        public abstract void initFragment(TreeMap<Integer, Fragment> treeMap);

        public void onSelected(int currentFragmentId, Fragment currentFragment) {
            if (currentFragment instanceof Framework_Fragment)
                ((Framework_Fragment) currentFragment).onSelected();
        }
    }

    public TreeMap<Integer, Fragment> getTm_fragment() {
        return tm_fragment;
    }

    public void fragmentToInit(FragmentActivity fragmentActivity, int toggleFragmentId, boolean selectedAtOnce, InitFragment initFragment, int defaultCurrentFragmentId) {
        this.initFragment = initFragment;
        initFragment.initFragment(tm_fragment);
        if (Util_Empty.isEmpty(initFragment) || tm_fragment.isEmpty())
            return;
        FragmentTransaction ft = (fragmentManager = (fragmentActivity).getSupportFragmentManager()).beginTransaction();
        for (Map.Entry<Integer, Fragment> e : tm_fragment.entrySet()) {
            ft.add(toggleFragmentId, e.getValue());
            ft.hide(e.getValue());
        }
        ft.show(currentFragment = tm_fragment.get(defaultCurrentFragmentId));
        ft.commit();
        if (selectedAtOnce && currentFragment instanceof Framework_Fragment)
            ((Framework_Fragment) currentFragment).onSelected();
    }

    public void fragmentToInit(FragmentActivity fragmentActivity, int toggleFragmentId,InitFragment initFragment, int defaultCurrentFragmentId) {
        fragmentToInit(fragmentActivity,toggleFragmentId,false,initFragment,defaultCurrentFragmentId);
    }
    public void fragmentToShow(int currentFragmentId) {
        if (Util_Empty.isEmpty(tm_fragment)) return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (Map.Entry<Integer, Fragment> e : tm_fragment.entrySet()) {
            ft.hide(e.getValue());
        }
        ft.show(currentFragment = tm_fragment.get(currentFragmentId));
        ft.commitAllowingStateLoss();
        initFragment.onSelected(currentFragmentId, currentFragment);
    }
}
