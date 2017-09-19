package com.ceq.app_xinli_onekey.core.modules.interfaces;

import android.support.v4.app.Fragment;

import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public interface Inter_OKModule_PurseUI {
    interface Inter_Purse$HYF extends Inter_Purse$All_ViewPager {
        void initMainFunctionData$HYF(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions);
    }

    interface Inter_Purse$NMF extends Inter_Purse$All_ViewPager {
        void initMainFunctionData$NMF(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions);

        void initSecondaryFunctionData$NMF(Fragment fragment, List<Bean_Item> itemList);
    }

    interface Inter_Purse$XZG extends Inter_Purse$All_ViewPager {
        void initMainFunctionData$XZG(Fragment fragment, List<Bean_Item> itemList);

        void initBaseData$XZG(Fragment fragment, Bean_Item itemLeft, Bean_Item itemMiddle, Bean_Item itemRight);
    }

    interface Inter_Purse$ZDQB extends Inter_Purse$All_ViewPager {

        void initMainFunctionData$ZDQB(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions);

        void initSecondaryFunctionData$ZDQB(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions);

        void initUserBaseData$ZDQB(Fragment fragment, List<Bean_Item> itemList);

        void initTitleData$ZDQB(Fragment fragment, Bean_Item secondaryFunctionTitleItem, Bean_Item vpFunctionTitleItem);
    }

    interface Inter_Purse$ZDQB2 extends Inter_Purse$All_ViewPager {

        void initMainFunctionData$ZDQB2(Fragment fragment, Bean_Item mainFunctionItem, Bean_OKModule_UIOptions uiOptions);

        void initSecondaryFunctionData$ZDQB2(Fragment fragment, List<Bean_Item> itemList);

        void initTitleData$ZDQB2(Fragment fragment, Bean_Item secondaryFunctionTitleItem, Bean_Item vpFunctionTitleItem);
    }

    interface Inter_Purse$XXQB {
        void initMainFunctionData$XXQB(Fragment fragment, List<Bean_Item> itemList);

        void initSecondaryFunctionData$XXQB(Fragment fragment, List<Bean_Item> itemList);

    }

    interface Inter_Purse$YBB {
        void initMainFunctionData$YBB(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions);
    }

    interface Inter_Purse$All_ViewPager {
        void initViewPagerData$1(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions);

        void initViewPagerData$2(Fragment fragment, List<Bean_Item> itemList, Bean_OKModule_UIOptions uiOptions);
    }

}
