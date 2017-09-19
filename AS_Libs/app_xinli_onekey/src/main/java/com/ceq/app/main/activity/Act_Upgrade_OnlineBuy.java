package com.ceq.app.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Fragment;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;

import java.util.TreeMap;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ceq.app.main.fragment.Frag_Module_Upgrade$JFB.Extra_Bool_Show_Title;

/**
 * Created by ceq on 2017/5/11.
 */

public class Act_Upgrade_OnlineBuy extends Framework_Activity {
    ImageView iv_back;
    TextView tv_vip;

    Util_Fragment util_fragment = new Util_Fragment();
    Abstract_OkModule_Fragment productionUpgradeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productionUpgradeFragment = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getProductionUpgradeFragment();
        init(R.layout.act_share_upgrade);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "在线购买", VISIBLE);
        tv_vip = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "查看会员", GONE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, VISIBLE);

    }

    @Override
    public void initFragment() {
        super.initFragment();
        util_fragment.fragmentToInit((FragmentActivity) getActivity(), R.id.rl_upgradeFragment, new Util_Fragment.InitFragment() {
            @Override
            public void initFragment(TreeMap<Integer, Fragment> treeMap) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Extra_Bool_Show_Title, false);
                productionUpgradeFragment.setArguments(bundle);
                treeMap.put(0, productionUpgradeFragment);
            }
        }, 0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
    }


    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_vip);
        Util_View.viewOnClick(this, iv_back);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        }
    }


}
