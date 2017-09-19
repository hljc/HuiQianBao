package com.ceq.app.main.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.bean.Bean_PushData;
import com.ceq.app.main.activity.Act_Purse_MySpread;
import com.ceq.app.main.bean.Bean_Production;
import com.ceq.app.main.methods.Method_Static;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.utils.extend.share.abstracts.Util_Share;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;
import com.ceq.app_xinli_onekey.core.modules.interfaces.Inter_OKModule_ShareUI;
import com.youth.banner.Banner;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.MarginBottomAttr;
import com.zhy.autolayout.attr.PaddingAttr;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.activity.Act_Main_Module.gradeRoleMaps;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.main.methods.Method_Static.getProductionList;
import static com.ceq.app.main.methods.Method_Static.setBanner;
import static com.ceq.app.main.methods.Method_Static.setShareAdapter;
import static com.ceq.app.main.methods.Method_Static.skipToScanCodeShare;
import static com.ceq.app_core.framework.Framework_Activity.screenWidth;
import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.QQ;
import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.Wechat;
import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.WechatMoments;

/**
 * Created by ceq on 2017/4/14.
 */

public class Frag_Module_Share$ZDQB extends Abstract_OkModule_Fragment<Inter_OKModule_ShareUI.Inter_Share$ZDQB> {
    Banner banner;
    TextView tv_share;
    RecyclerView rv_share;
    RecyclerView.Adapter rva_share, rva_productionFragment;
    FragmentPagerAdapter vpa_share;
    RecyclerView rv_productionFragment;
    ViewPager vp_share;
    LinearLayout ll_upgrade;
    ImageView iv_upgrade;
    TextView tv_upgrade;
    View_XRefreshLayout xrl;

    int spanCount = 3;
    public int upgradePeoples;
    int vpPosition;

    List<Bean_Item> bean_items = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();
    List<Bean_Item> specItemList = new ArrayList<>();
    List<Bean_Production> productionList = new ArrayList<>();

    Bean_Item bean_upgrade = new Bean_Item();
    Bean_OKModule_UIOptions uiOptions = new Bean_OKModule_UIOptions();

    public Frag_Module_Share$ZDQB() {
    }

    public Frag_Module_Share$ZDQB(String tabBarTitle, int tabBarImgId) {
        super(tabBarTitle, tabBarImgId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_module_share);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, getTabBarTitle(), View.VISIBLE);
        tv_share = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "我的推广", View.VISIBLE);
        //轮播
        banner = (Banner) findViewById(R.id.banner);

        ll_upgrade = (LinearLayout) findViewById(R.id.ll_upgrade);


        rv_share = (RecyclerView) findViewById(R.id.rv_share);
        rv_share.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        rv_productionFragment = (RecyclerView) findViewById(R.id.rv_productionFragment);
        rv_productionFragment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        vp_share = (ViewPager) findViewById(R.id.vp_share);

        iv_upgrade = (ImageView) findViewById(R.id.iv_upgrade);
        tv_upgrade = (TextView) findViewById(R.id.tv_upgrade);

        setBanner(getActivity(), banner, false);

        iv_upgrade.setImageResource(bean_upgrade.getImgId());
        tv_upgrade.setText((CharSequence) bean_upgrade.getName());

        xrl = (View_XRefreshLayout) findViewById(R.id.xrl);
    }

    @Override
    public void initData() {
        bean_items.clear();
        fragmentList.clear();

        bean_items.add(new Bean_Item("微信", R.mipmap.icon_share_wx, true));
        bean_items.add(new Bean_Item("扫码分享", R.mipmap.icon_share_qrcode, false));
        bean_items.add(new Bean_Item("短信", R.mipmap.icon_share_sms, false));
        bean_items.add(new Bean_Item("QQ", R.mipmap.icon_share_qq, false));
    }

    @Override
    public void initAdapter() {
        vp_share.setAdapter(vpa_share = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        rva_share = setShareAdapter(getActivity(), rv_share, bean_items, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                final String shareTitle = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareTitle();
                final String shareContent = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareContent();
                final String shareUrl = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareUrl();
                switch ((Integer) data[0]) {
                    case 0:
                        Util_Dialog.dialogByAct((View) data[1], R.layout.dialog_share, false, false, false, false, new Util_Dialog.ActDialog() {
                            List<Bean_Item> bean_itemList = new ArrayList<>();
                            RecyclerView rv_share;
                            TextView tv_cancel;
                            Util_Dialog.DialogContext dialogContext;

                            @Override
                            public void onClick(View v) {
                                if (v == tv_cancel)
                                    dialogContext.dismiss();
                            }

                            @Override
                            public void initDialogData() {
                                bean_itemList.add(new Bean_Item("微信", R.mipmap.icon_share_wx, true));
                                bean_itemList.add(new Bean_Item("朋友圈", R.mipmap.icon_share_friendtrends, true));
                            }

                            @Override
                            public View initDialogView(final Util_Dialog.DialogContext dialogContext, View view) {
                                this.dialogContext = dialogContext;
                                rv_share = (RecyclerView) view.findViewById(R.id.rv_share);
                                rv_share.setLayoutManager(new GridLayoutManager(getActivity(), 4));

                                tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
                                Util_View.viewOnClick(this, tv_cancel);
                                setShareAdapter(getActivity(), rv_share, bean_itemList, new Util_Runnable.Util_ArgsRunnable() {
                                    @Override
                                    public void run(Object... data) {
                                        switch ((int) data[0]) {
                                            case 0:
                                                Util_Share.getInstance().share(Wechat, shareTitle, shareContent, shareUrl, null);
                                                break;
                                            case 1:
                                                Util_Share.getInstance().share(WechatMoments, shareTitle, shareContent, shareUrl, null);
                                                break;
                                        }
                                        dialogContext.dismiss();
                                    }
                                });
                                return view;
                            }

                            @Override
                            public void onDismissListener() {
                            }
                        });
                        break;
                    case 1:
                        skipToScanCodeShare(getActivity());
                        break;
                    case 2:
                        Uri smsToUri = Uri.parse("smsto:");
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
                        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
                        //短信内容
                        sendIntent.putExtra("sms_body", new StringBuilder("【").append(getResources().getString(R.string.app_name)).append("】").append(shareContent).append(shareUrl).toString());
                        sendIntent.setType("vnd.android-dir/mms-sms");
                        startActivityForResult(sendIntent, 1002);
                        break;
                    case 3:
                        Util_Share.getInstance().share(QQ, shareTitle, shareContent, shareUrl, null);
                        break;
                }
            }
        });

        rv_productionFragment.setAdapter(rva_productionFragment = new RecyclerView.Adapter() {
            int primaryColor = getResources().getColor(R.color.primaryColorOff);
            int textColor4 = getResources().getColor(R.color.text_color_4);
            int textColor2 = getResources().getColor(R.color.text_color_0);

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name;
                LinearLayout ll_bg;
                View v_split;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                    AutoLinearLayout.LayoutParams lp1 = (AutoLinearLayout.LayoutParams) tv_name.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo1 = lp1.getAutoLayoutInfo();
                    autoLayoutInfo1.addAttr(new MarginBottomAttr(20, 0, 0));
                    tv_name.setLayoutParams(lp1);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    AutoRelativeLayout.LayoutParams lp2 = (AutoRelativeLayout.LayoutParams) ll_bg.getLayoutParams();
                    AutoLayoutInfo ali2 = lp2.getAutoLayoutInfo();
                    ali2.addAttr(new PaddingAttr(0, 0, 0));
                    ll_bg.setLayoutParams(lp2);

                    ViewGroup view = (ViewGroup) ll_bg.getParent();
                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                    lp.width = screenWidth / spanCount;
                    view.setLayoutParams(lp);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);

                    v_split = itemView.findViewById(R.id.v_split);
                    v_split.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_adapter_gv, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Production bean = productionList.get(position);
                mvh.tv_name.setTextColor(position == vpPosition ? primaryColor : textColor4);
                mvh.v_split.setBackgroundColor(position == vpPosition ? primaryColor : textColor2);
                mvh.tv_name.setText(bean.getName());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp_share.setCurrentItem(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return productionList.size();
            }
        });
    }


    @Override
    public void initListener() {
        vp_share.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                rv_productionFragment.smoothScrollToPosition(vpPosition = position);
                rva_productionFragment.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_share.setOffscreenPageLimit(productionList.size());
        Util_View.viewOnClick(this, tv_share);
        Util_View.viewOnClick(this, ll_upgrade);

        xrl.setOnRefreshHttpListener(false, false, 0, productionList, rva_productionFragment, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, final List list, final RecyclerView.Adapter adapter) {
                getProductionList(getActivity(), view_xRefreshStatusView, new Util_Runnable.Util_ArgsRunnable() {
                    @Override
                    public void run(Object... data) {
                        list.clear();
                        list.addAll(JSONObject.parseArray(parseObject((String) data[0]).getString(Http_Key_Data), Bean_Production.class));
                        for (int i = 0, size = list.size(); i < size; i++) {
                            Bean_Production bean = (Bean_Production) list.get(i);
                            gradeRoleMaps.put(bean.getGrade(), bean);
                        }
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                        getProductionSpecData();
                    }
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == tv_share.getId()) {
            startActivity(new Intent(getActivity(), Act_Purse_MySpread.class));
        } else if (v == ll_upgrade) {
            bean_upgrade.getUtil_Args_runnable().run();
        }

    }

    @Override
    public void onSelected() {
        Util_Screen.statusBarToTranslucent(getActivity(), getResources().getColor(R.color.title_background));
    }

    @Override
    public void run(final Inter_OKModule_ShareUI.Inter_Share$ZDQB bean) {
        bean.initCircleUpgradeData$ZDQB(this, bean_upgrade);
        bean.initGradeData$ZDQB(this, specItemList);
    }

    private void getProductionSpecData() {
        fragmentList.clear();
        for (int i = 0, size = productionList.size(); i < size; i++) {
            Bean_Production production = productionList.get(i);
            Frag_Share_Grade fragment = new Frag_Share_Grade();
            fragment.itemList.clear();
            fragment.itemList.addAll(specItemList);
            fragmentList.add(fragment);
            for (int j = 0, count = fragment.itemList.size(); j < count; j++) {
                Method_Static.Enum_ProductionSpecType productionSpecType = (Method_Static.Enum_ProductionSpecType) fragment.itemList.get(j).getObject();
                switch (productionSpecType) {
                    case 收益说明:
                        fragment.itemList.get(j).setSubName(production.getEarningsState());
                        break;
                    case 升级说明:
                        fragment.itemList.get(j).setSubName(production.getUpgradestate());
                        break;
                }
            }
        }
        if (vpa_share != null)
            vpa_share.notifyDataSetChanged();
    }


    @Override
    public void updateViewByPushMessage(Bean_PushData bean_pushData, Bean_Push bean_push) {

    }
}
