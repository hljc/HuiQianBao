package com.ceq.app.main.module.mine;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ceq.app.core.activity.Act_Main_Web;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.main.activity.Act_Mine_BankCard;
import com.ceq.app.main.activity.Act_Mine_Certification;
import com.ceq.app.main.activity.Act_Mine_More$JFB;
import com.ceq.app.main.activity.Act_Mine_MyPurse;
import com.ceq.app.main.activity.Act_Mine_PasswordManager;
import com.ceq.app.main.activity.Act_Mine_Rate;
import com.ceq.app.main.activity.Act_Mine_TransactionDetailed;
import com.ceq.app.main.activity.Act_Settings_Income;
import com.ceq.app.main.activity.Act_Settings_Protocol;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Web;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_xinli_onekey.core.modules.interfaces.Inter_OKModule_MineUI;
import com.hqb.huiqianbao.R;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.activity.Act_MyPurse_PurseDetailed.Extra_Int_MyPurse;
import static com.ceq.app.main.methods.Method_Static.Enum_PurseType.余额;
import static com.ceq.app.main.methods.Method_Static.Enum_PurseType.分润;
import static com.ceq.app.main.methods.Method_Static.Enum_PurseType.积分;
import static com.ceq.app.main.methods.Method_Static.selectCustomerService;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class Bean_MineUI$JFB implements Inter_OKModule_MineUI.Inter_Mine$JFB {
    @Override
    public void initMainFunctionData$JFB(Fragment fragment, List<Bean_Item> mainFunctionList) {
        Activity activity = fragment.getActivity();
        mainFunctionList.add(new Bean_Item("银行卡", R.mipmap.listicon_bill, false).setUtil_Args_runnable(data -> activity.startActivity(new Intent(activity, Act_Mine_BankCard.class))));
        mainFunctionList.add(new Bean_Item("交易明细", R.mipmap.listicon_withdraw, false).setUtil_Args_runnable(data -> activity.startActivity(new Intent(activity, Act_Mine_TransactionDetailed.class))));
        mainFunctionList.add(new Bean_Item("在线客服", R.mipmap.listicon_kefu, false).setUtil_Args_runnable(data -> selectCustomerService(activity)));
        mainFunctionList.add(new Bean_Item("安全管理", R.mipmap.listicon_about, false).setUtil_Args_runnable(data -> activity.startActivity(new Intent(activity, Act_Mine_PasswordManager.class))));
        mainFunctionList.add(new Bean_Item("更多功能", R.mipmap.listicon_question, false).setUtil_Args_runnable(data -> {
            ArrayList<Bean_Item> bean_items = new ArrayList<>();
            bean_items.add(new Bean_Item("后台管理", R.mipmap.more_houtaiguanli, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Framework_Web.Extra_String_Url, "http://1.xinli2017.applinzi.com/Uptable/login.html"))));
            bean_items.add(new Bean_Item("收益分析", R.mipmap.more_shouyifenxi, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Settings_Income.class))));
            bean_items.add(new Bean_Item("刷卡费率", R.mipmap.more_shuakafeilv, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Mine_Rate.class))));
            bean_items.add(new Bean_Item("实名认证", R.mipmap.more_shiming, false).setUtil_Args_runnable(data1 -> {
                switch (Abstract_App.bean_userInfo.getRealnameStatus()) {
                    case "1":
                        Util_Toast.toast("您已通过实名审核！");
                        return;
                    case "0":
                        Util_Toast.toast("实名审核中,实名结果将会推送至设备上！");
                        return;
                }
                activity.startActivity(new Intent(activity, Act_Mine_Certification.class));
            }));
            bean_items.add(new Bean_Item("操作手册", R.mipmap.more_caozuoshouce, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Framework_Web.Extra_String_Url, "http://i.eqxiu.com/s/Pdmqwp63"))));
            bean_items.add(new Bean_Item("用户协议", R.mipmap.more_xieyi, false).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Settings_Protocol.class))));
            Act_Mine_More$JFB.mainFunctionList.clear();
            Act_Mine_More$JFB.mainFunctionList.addAll(bean_items);
            activity.startActivity( new Intent(activity, Act_Mine_More$JFB.class));
        }));
    }

    @Override
    public void initUserBaseData$JFB(Fragment fragment, List<Bean_Item> bean_itemList) {
        Activity activity = fragment.getActivity();
        bean_itemList.add(new Bean_Item("分润", "0.00", 分润).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Mine_MyPurse.class).putExtra(Extra_Int_MyPurse, 2))));
        bean_itemList.add(new Bean_Item("积分", "0", 积分).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Mine_MyPurse.class).putExtra(Extra_Int_MyPurse, 0))));
        bean_itemList.add(new Bean_Item("余额", "0.00", 余额).setUtil_Args_runnable(data1 -> activity.startActivity(new Intent(activity, Act_Mine_MyPurse.class).putExtra(Extra_Int_MyPurse, 1))));
    }
}
