package com.ceq.app_core.interfaces;

import android.view.View;
import android.view.View.OnClickListener;

import com.ceq.app_core.utils.core.Util_Screen;

public interface Inter_Global_Init extends OnClickListener {
    View init(int layoutResID, boolean enableStatusBarSettings, int statusColor, boolean fitsSystemWindows, Util_Screen.Enum_BarFontColor enum_barFontColor);

    void setContentView(View view);

    View findViewById(int viewId);

    void initView();

    void initData();

    void initViewPager();

    void initFragment();

    void initAdapter();

    void initListener();


}
