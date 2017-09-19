package com.ceq.app_core.framework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ceq.app_core.interfaces.Inter_Global;
import com.ceq.app_core.utils.core.Util_Init;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Permission;
import com.ceq.app_core.utils.core.Util_Screen;
import com.yanzhenjie.permission.AndPermission;


public abstract class Framework_Fragment extends Fragment implements Inter_Global {
    private View rootView;
    public Util_Init util_init;

    public View getRootView() {
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View findViewById(int viewId) {
        return rootView.findViewById(viewId);
    }

    @Override
    public void setContentView(View view) {
        this.rootView = view;
    }

    @Override
    public View init(int layoutResID, boolean enableStatusBarSettings, int statusColor, boolean fitsSystemWindows, Util_Screen.Enum_BarFontColor enum_barFontColor) {
        Util_Log.logApp("Fragment启动", this, statusColor, layoutResID, fitsSystemWindows);
        (util_init = new Util_Init(this)).init(layoutResID, enableStatusBarSettings, statusColor, fitsSystemWindows,enum_barFontColor);
        return rootView;
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

    public View initDisableStatusBar(int layoutResID) {
        return init(layoutResID, false, Integer.MAX_VALUE, true, null);
    }

    public abstract void onSelected();

    @Override
    public void initViewPager() {

    }

    @Override
    public void initFragment() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AndPermission.with(getActivity()).requestCode(requestCode).permission(permissions).callback(Util_Permission.listener).start();
    }

}
