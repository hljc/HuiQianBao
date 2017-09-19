package com.ceq.app.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.core.bean.Bean_PushData;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;
import com.ceq.app_xinli_onekey.core.modules.interfaces.Inter_OKModule_PurseUI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gongwen.marqueen.MarqueeView;
import com.youth.banner.Banner;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.HeightAttr;
import com.zhy.autolayout.attr.MarginBottomAttr;
import com.zhy.autolayout.attr.WidthAttr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ceq.app.main.methods.Method_Static.getMarqueeData;
import static com.ceq.app.main.methods.Method_Static.initUserPurseBaseInfo;
import static com.ceq.app.main.methods.Method_Static.setBanner;
import static com.ceq.app.main.methods.Method_Static.setMarqueeView;
import static com.ceq.app.main.methods.Method_Static.setViewPager;
import static com.ceq.app.main.methods.Method_Static.setViewPagerIndicator;
import static com.ceq.app.main.methods.Method_Static.skipToPushMessage;
import static com.ceq.app.main.methods.Method_Static.updateMarqueeView;
import static com.ceq.app_core.framework.Framework_Activity.screenWidth;

/**
 * Created by ceq on 2017/5/11.
 */

public class Frag_Module_Purse$ZDQB extends Abstract_OkModule_Fragment<Inter_OKModule_PurseUI.Inter_Purse$ZDQB> {
    RecyclerView rv_secondaryFunction, rv_mainFunction, rv_money;
    ViewPager vp_function;
    Banner banner;
    RecyclerView.Adapter rva_money;
    ImageView iv_message;
    MarqueeView mv_notice;
    ImageView iv_vp_1, iv_vp_2;
    View_XRefreshLayout xrl;
    ImageView iv_secondaryFunctionTitle, iv_vpFunctionTitle;
    TextView tv_secondaryFunctionTitle, tv_vpFunctionTitle;

    List<Bean_Item> purseInfoList = new ArrayList<>();
    List<Bean_Item> mainFunctionItemList = new ArrayList<>();
    List<Bean_Item> secondaryFunctionItemList = new ArrayList<>();
    List<String> marqueeList = new ArrayList<>();
    List<Framework_Fragment> fragmentList = new ArrayList<>();

    public Frag_Purse_Function frag_Purse_Function1 = new Frag_Purse_Function();
    public Frag_Purse_Function frag_Purse_Function2 = new Frag_Purse_Function();

    Bean_OKModule_UIOptions mainFunctionUiOptions = new Bean_OKModule_UIOptions();
    Bean_OKModule_UIOptions secondaryFunctionUiOptions = new Bean_OKModule_UIOptions();
    Bean_Item secondaryFunctionTitleItem = new Bean_Item();
    Bean_Item vpFunctionTitleItem = new Bean_Item();

    public Frag_Module_Purse$ZDQB() {
    }

    public Frag_Module_Purse$ZDQB(String tabBarTitle, int tabBarImgId) {
        super(tabBarTitle, tabBarImgId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_module_purse_zdqb);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, getTabBarTitle(), View.VISIBLE);
 /*       tv_bill = util_init.initTextView(R.id.icd_title, R.id.tv_titleLeft, null, "账单", View.VISIBLE);
        iv_bill = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.icon_share_bill, View.VISIBLE);*/
        iv_message = util_init.initImageView(R.id.icd_title, R.id.iv_titleRight, R.mipmap.icon_share_message, View.VISIBLE);

        rv_secondaryFunction = (RecyclerView) findViewById(R.id.rv_secondaryFunction);
        rv_secondaryFunction.setLayoutManager(new GridLayoutManager(getActivity(), secondaryFunctionUiOptions.getColumns()));

        rv_mainFunction = (RecyclerView) findViewById(R.id.rv_mainFunction);
        rv_mainFunction.setLayoutManager(new GridLayoutManager(getActivity(), mainFunctionUiOptions.getColumns() == 0 ? 2 : mainFunctionUiOptions.getColumns()));

        if (mainFunctionUiOptions.getViewBackgroundResource() != 0)
            rv_mainFunction.setBackgroundResource(mainFunctionUiOptions.getViewBackgroundResource());


        rv_money = (RecyclerView) findViewById(R.id.rv_money);
        rv_money.setLayoutManager(new GridLayoutManager(getActivity(), 3));


        vp_function = (ViewPager) findViewById(R.id.vp_function);

        //轮播
        banner = (Banner) findViewById(R.id.banner);

        iv_vp_1 = (ImageView) findViewById(R.id.iv_vp_1);
        iv_vp_2 = (ImageView) findViewById(R.id.iv_vp_2);

        xrl = (View_XRefreshLayout) findViewById(R.id.xrl);

        mv_notice = (MarqueeView) findViewById(R.id.mv_notice);
        getMarqueeData(getActivity(), setMarqueeView(mv_notice, Arrays.asList("过期活动，好礼折扣送不断，敬请期待。。。")), marqueeList);

        setBanner(getActivity(), banner, false);

        iv_secondaryFunctionTitle = (ImageView) findViewById(R.id.iv_secondaryFunctionTitle);
        iv_vpFunctionTitle = (ImageView) findViewById(R.id.iv_vpFunctionTitle);
        tv_secondaryFunctionTitle = (TextView) findViewById(R.id.tv_secondaryFunctionTitle);
        tv_vpFunctionTitle = (TextView) findViewById(R.id.tv_vpFunctionTitle);

        iv_secondaryFunctionTitle.setVisibility(secondaryFunctionTitleItem.getImgId() != 0 ? View.VISIBLE : View.GONE);
        if (secondaryFunctionTitleItem.getImgId() != 0)
            iv_secondaryFunctionTitle.setImageResource(secondaryFunctionTitleItem.getImgId());
        iv_vpFunctionTitle.setVisibility(vpFunctionTitleItem.getImgId() != 0 ? View.VISIBLE : View.GONE);
        if (vpFunctionTitleItem.getImgId() != 0)
            iv_vpFunctionTitle.setImageResource(vpFunctionTitleItem.getImgId());

        tv_secondaryFunctionTitle.setText((CharSequence) Util_Empty.isEmptyToReplace(secondaryFunctionTitleItem.getName(), "金融电商"));
        tv_vpFunctionTitle.setText((CharSequence) Util_Empty.isEmptyToReplace(vpFunctionTitleItem.getName(), "便民服务"));
    }


    @Override
    public void initData() {
    }

    @Override
    public void initAdapter() {
        rv_mainFunction.setAdapter(new RecyclerView.Adapter() {
            int imgWidth = 120;

            class MyViewHolder extends RecyclerView.ViewHolder {
                SimpleDraweeView sdv_img;
                TextView tv_name;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    (sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img)).setVisibility(View.VISIBLE);
                    AutoLinearLayout.LayoutParams lp2 = (AutoLinearLayout.LayoutParams) sdv_img.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo = lp2.getAutoLayoutInfo();
                    autoLayoutInfo.addAttr(new WidthAttr(mainFunctionUiOptions.getImageWidth() == 0 ? imgWidth : mainFunctionUiOptions.getImageWidth(), 0, 0));
                    autoLayoutInfo.addAttr(new HeightAttr(mainFunctionUiOptions.getImageHeight() == 0 ? imgWidth : mainFunctionUiOptions.getImageHeight(), 0, 0));
                    autoLayoutInfo.addAttr(new MarginBottomAttr(mainFunctionUiOptions.getImageMarginBottom() == 0 ? 10 : mainFunctionUiOptions.getImageMarginBottom(), 0, 0));
                    ViewGroup.LayoutParams lp = sdv_img.getLayoutParams();
                    sdv_img.setLayoutParams(lp);

                    (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, mainFunctionUiOptions.getTextSize() == 50 ? imgWidth : mainFunctionUiOptions.getTextSize());
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_1));
                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);

                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_adapter_gv, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = mainFunctionItemList.get(position);

                mvh.tv_name.setText(bean.getName().toString());
                mvh.sdv_img.getHierarchy().setPlaceholderImage(bean.getImgId());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util_Runnable.Util_ArgsRunnable util_Args_runnable = bean.getUtil_Args_runnable();
                        if (util_Args_runnable != null) util_Args_runnable.run(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return mainFunctionItemList.size();
            }
        });

        rv_money.setAdapter(rva_money = new RecyclerView.Adapter() {
            int fontSize1 = screenWidth / 27;
            int fontSize2 = screenWidth / 28;

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_value;
                LinearLayout ll_bg;
                int color = getResources().getColor(R.color.text_color_3);
                int color2 = getResources().getColor(R.color.primaryColorOff);

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize1);
                    tv_name.setTextColor(color2);
                    tv_name.getPaint().setFakeBoldText(true);

                    tv_value = (TextView) itemView.findViewById(R.id.tv_value);
                    tv_value.setVisibility(View.VISIBLE);
                    tv_value.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize2);
                    tv_value.setTextColor(color);


                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    AutoRelativeLayout.LayoutParams layoutParams4 = (AutoRelativeLayout.LayoutParams) ll_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo4 = layoutParams4.getAutoLayoutInfo();
                    autoLayoutInfo4.addAttr(new HeightAttr(150, 0, 0));
                    ViewGroup.LayoutParams lp4 = ll_bg.getLayoutParams();
                    ll_bg.setLayoutParams(lp4);
                    ll_bg.setBackgroundResource(R.drawable.bg_keyboard);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = purseInfoList.get(position);
                mvh.tv_name.setText(bean.getValue());
                mvh.tv_value.setText(bean.getName().toString());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util_Runnable.Util_ArgsRunnable util_Args_runnable = bean.getUtil_Args_runnable();
                        if (util_Args_runnable != null) util_Args_runnable.run(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return purseInfoList.size();
            }
        });
        rv_secondaryFunction.setAdapter(new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name;
                TextView tv_description;
                ImageView iv_img;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_description = (TextView) itemView.findViewById(R.id.tv_description);

                    iv_img = (ImageView) itemView.findViewById(R.id.iv_img);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_newpurse_function, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = secondaryFunctionItemList.get(position);
                if(bean==null)
                    return;
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_description.setText(bean.getValue());
                mvh.iv_img.setImageResource(bean.getImgId());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util_Runnable.Util_ArgsRunnable util_Args_runnable = bean.getUtil_Args_runnable();
                        if (util_Args_runnable != null) util_Args_runnable.run(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return secondaryFunctionItemList.size();
            }
        });
    }


    @Override
    public void initViewPager() {
        super.initViewPager();
        setViewPager(vp_function, getFragmentManager(), fragmentList, frag_Purse_Function1, frag_Purse_Function2);
    }

    @Override
    public void initListener() {
        setViewPagerIndicator(vp_function, iv_vp_1, iv_vp_2);
        Util_View.viewOnClick(this, /*iv_bill, tv_bill,*/ iv_message);
    }


    @Override
    public void onClick(View v) {
    /*    if (v.getId() == tv_bill.getId() || v.getId() == iv_bill.getId()) {
            startActivity(new Intent(getActivity(), Act_Mine_TransactionDetailed.class));
        } else*/
        if (v.getId() == iv_message.getId()) {
            skipToPushMessage(getActivity());
        }
    }

    @Override
    public void onSelected() {
        Util_Screen.statusBarToTranslucent(getActivity(), getResources().getColor(R.color.title_background));
    }


    @Override
    public void run(Inter_OKModule_PurseUI.Inter_Purse$ZDQB data) {
        data.initMainFunctionData$ZDQB(this, mainFunctionItemList, mainFunctionUiOptions);
        data.initUserBaseData$ZDQB(this, purseInfoList);
        data.initSecondaryFunctionData$ZDQB(this, secondaryFunctionItemList,secondaryFunctionUiOptions);
        data.initViewPagerData$1(this, frag_Purse_Function1.bean_itemList, frag_Purse_Function1.bean_OK_moduleUIOptions);
        data.initViewPagerData$2(this, frag_Purse_Function2.bean_itemList, frag_Purse_Function2.bean_OK_moduleUIOptions);
        data.initTitleData$ZDQB(this, secondaryFunctionTitleItem, vpFunctionTitleItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mv_notice != null) mv_notice.startFlipping();
        refreshMainModuleData();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mv_notice != null) mv_notice.stopFlipping();
    }


    @Override
    public void updateViewByPushMessage(Bean_PushData bean_pushData, Bean_Push bean_push) {
        updateMarqueeView(getActivity(), marqueeList, bean_pushData, bean_push);
        refreshMainModuleData();
    }

    private void refreshMainModuleData() {
        initUserPurseBaseInfo(xrl, false, rva_money, purseInfoList, null);
    }
}
