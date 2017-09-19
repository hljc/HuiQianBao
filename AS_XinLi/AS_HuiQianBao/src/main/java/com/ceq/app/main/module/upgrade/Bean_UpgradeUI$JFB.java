package com.ceq.app.main.module.upgrade;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ceq.app.main.activity.Act_Mine_Rate;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;
import com.ceq.app_xinli_onekey.core.modules.interfaces.Inter_OKModule_UpgradeUI;
import com.hqb.huiqianbao.R;

import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class Bean_UpgradeUI$JFB implements Inter_OKModule_UpgradeUI.Inter_Upgrade$JFB {

    @Override
    public void initMainFunctionData$JFB(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions) {
        Activity activity = fragment.getActivity();
        itemList.add(new Bean_Item("产品费率说明", R.mipmap.icon_buyspec, false).setUtil_Args_runnable(data ->    activity.startActivity(new Intent(activity, Act_Mine_Rate.class))));

    }
}
