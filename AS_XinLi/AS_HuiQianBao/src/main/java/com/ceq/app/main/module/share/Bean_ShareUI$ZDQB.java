package com.ceq.app.main.module.share;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_xinli_onekey.core.modules.interfaces.Inter_OKModule_ShareUI;
import com.hqb.huiqianbao.R;

import java.util.List;

import static com.ceq.app.main.methods.Method_Static.Enum_ProductionSpecType.升级说明;
import static com.ceq.app.main.methods.Method_Static.Enum_ProductionSpecType.收益说明;
import static com.ceq.app.main.methods.Method_Static.skipToUpgrade;

/**
 * Created by Administrator on 2017/6/2.
 */

public class Bean_ShareUI$ZDQB implements Inter_OKModule_ShareUI.Inter_Share$ZDQB {


    @Override
    public void initCircleUpgradeData$ZDQB(Fragment fragment, Bean_Item bean_item) {
        Activity activity = fragment.getActivity();
        bean_item.setUtil_Args_runnable(data -> skipToUpgrade(activity, true));
        bean_item.setImgId(R.mipmap.icon_upgrade);
        bean_item.setName("立即升级");
    }

    @Override
    public void initGradeData$ZDQB(Fragment fragment, List<Bean_Item> itemList) {
        itemList.add(new Bean_Item("享受收益", "暂无收益说明", R.mipmap.icon_share_money_rate, 收益说明));
        itemList.add(new Bean_Item("如何升级", "暂无升级说明", R.mipmap.icon_share_income, 升级说明));
    }
}
