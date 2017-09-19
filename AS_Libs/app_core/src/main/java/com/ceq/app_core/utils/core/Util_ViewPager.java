package com.ceq.app_core.utils.core;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ceq.app_core.framework.Framework_Fragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_ViewPager {
    private static ViewPager viewPager;

    public interface InitViewPager {
        ArrayList<Framework_Fragment> initViewPager(
                ArrayList<Framework_Fragment> al_viewPager);
    }

    public static void viewPageToInit(ViewPager viewPager, ViewPagerAdapter adpter,
                                      int pageLimit, int defaultShowFragmentId,
                                      ViewPager.OnPageChangeListener listener) {
        if (viewPager == null || adpter == null)
            return;
        Util_ViewPager.viewPager = viewPager;
        viewPager.setAdapter(adpter);
        viewPager.setCurrentItem(defaultShowFragmentId);
        viewPager.setOffscreenPageLimit(pageLimit);
        viewPager.addOnPageChangeListener(listener);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Framework_Fragment> al_frag;

        public ViewPagerAdapter(FragmentManager fm, InitViewPager initViewPager) {
            super(fm);
            this.al_frag = initViewPager
                    .initViewPager(new ArrayList<Framework_Fragment>());
        }

        @Override
        public Fragment getItem(int arg0) {
            return al_frag.get(arg0);
        }

        @Override
        public int getCount() {
            return al_frag.size();
        }

    }

    public static void viewPageToShow(int showFragmentId) {
        if (!Util_Empty.isEmpty(viewPager))
            viewPager.setCurrentItem(showFragmentId);
    }
}
