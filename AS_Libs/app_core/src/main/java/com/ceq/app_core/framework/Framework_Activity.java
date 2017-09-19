package com.ceq.app_core.framework;


import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.ceq.app_core.interfaces.Inter_Global;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Init;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Permission;
import com.ceq.app_core.utils.core.Util_Screen;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;

public abstract class Framework_Activity extends FragmentActivity implements Inter_Global {
    private static final List<Activity> al_activity = new ArrayList<>();
    public Util_Init util_init;
    public static int screenWidth, screenHeight;

    public final Activity getActivity() {
        return this;
    }

    public static List<Activity> getActList() {
        return al_activity;
    }

    private View rootView;

    public View getRootView() {
        return rootView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9)
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        screenWidth = ScreenUtils.getScreenWidth();
        screenHeight = ScreenUtils.getScreenHeight();
        al_activity.add(this);
        util_init = new Util_Init(this);
    }

    @Override
    public View init(int layoutResID, boolean enableStatusBarSettings, int statusColor, boolean fitsSystemWindows, Util_Screen.Enum_BarFontColor enum_barFontColor) {
        Util_Log.logApp("Activity启动", this, getApplication(), layoutResID, fitsSystemWindows);
        return rootView = util_init.init(layoutResID, enableStatusBarSettings, statusColor, fitsSystemWindows,enum_barFontColor);
    }

    public View init(int layoutResID, int statusColor, Util_Screen.Enum_BarFontColor enum_barFontColor) {
        return init(layoutResID, true, statusColor, true, enum_barFontColor);
    }

    public View init(int layoutResID, int statusColor) {
        return init(layoutResID, true, statusColor, true, null);
    }

    public View init(int layoutResID, boolean fitsSystemWindows) {
        return init(layoutResID, true, Integer.MAX_VALUE, fitsSystemWindows, null);
    }

    public View init(int layoutResID) {
        return init(layoutResID, Integer.MAX_VALUE);
    }


    @Override
    public void initViewPager() {
    }

    @Override
    public void initFragment() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        Framework_App.getInstance().updateDatabaseOnPause();
    }

    @Override
    protected void onDestroy() {
        Util_Http.getOkGo().cancelTag(getActivity());
        al_activity.remove(this);
        util_init = null;
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AndPermission.with(getActivity()).requestCode(requestCode).permission(permissions).callback(Util_Permission.listener).start();
    }

}
