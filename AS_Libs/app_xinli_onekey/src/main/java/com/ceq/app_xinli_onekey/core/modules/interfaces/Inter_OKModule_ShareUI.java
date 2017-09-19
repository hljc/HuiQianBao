package com.ceq.app_xinli_onekey.core.modules.interfaces;

import android.support.v4.app.Fragment;

import com.ceq.app_core.bean.Bean_Item;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public interface Inter_OKModule_ShareUI {
    interface Inter_Share$ZDQB {
        void initCircleUpgradeData$ZDQB(Fragment fragment, Bean_Item item);

        void initGradeData$ZDQB(Fragment fragment, List<Bean_Item> itemList);
    }
}
