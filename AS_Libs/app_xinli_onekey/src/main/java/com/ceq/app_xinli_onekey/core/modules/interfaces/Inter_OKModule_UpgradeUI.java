package com.ceq.app_xinli_onekey.core.modules.interfaces;

import android.support.v4.app.Fragment;

import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public interface Inter_OKModule_UpgradeUI {
    interface Inter_Upgrade$ZDQB {
    }

    interface Inter_Upgrade$JFB {
        void initMainFunctionData$JFB(Fragment fragment, List<Bean_Item> itemList,Bean_OKModule_UIOptions uiOptions);
    }
}
