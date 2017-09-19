package com.ceq.app_xinli_onekey.core.modules.interfaces;

import android.support.v4.app.Fragment;

import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public interface Inter_OKModule_MineUI {
    interface Inter_Mine$DYQF {
        void initMainFunctionData$DYQF(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions);

        void initUserBaseData$DYQF(Fragment fragment, List<Bean_Item> itemList);
    }

    interface Inter_Mine$ZDQB {
        void initMainFunctionData$ZDQB(Fragment fragment, List<Bean_Item> itemList);

        void initUserBaseData$ZDQB(Fragment fragment, List<Bean_Item> itemList);
    }

    interface Inter_Mine$JFB {
        void initMainFunctionData$JFB(Fragment fragment, List<Bean_Item> itemList);

        void initUserBaseData$JFB(Fragment fragment, List<Bean_Item> itemList);
    }

    interface Inter_Mine$YF {
        void initMainFunctionData$YF(Fragment fragment, List<Bean_Item> itemList);
    }
    interface Inter_Mine$YBB {
        void initMainFunctionData$YBB(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions);

        void initSecondaryFunctionData$YBB(Fragment fragment, List<Bean_Item> itemList);

        void initBaseData$YBB(Fragment fragment, Bean_Item itemLeft, Bean_Item itemMiddle, Bean_Item itemRight);

    }
}
