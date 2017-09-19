package com.ceq.app.core.activity;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.main.bean.Bean_OemInfo;
import com.ceq.app.main.bean.Bean_Production;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.framework.Framework_Web;
import com.ceq.app_core.utils.core.Util_Fragment;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_System;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Module;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.attr.HeightAttr;
import com.zhy.autolayout.attr.MarginBottomAttr;
import com.zhy.autolayout.attr.WidthAttr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.ceq.app.core.application.Abstract_App.bean_oneKeyBootstrap;
import static com.ceq.app.main.methods.Method_Static.initModuleFragmentDataAndPush;

/**
 * Created by Administrator on 2016/9/26.
 */
public class Act_Main_Module extends Framework_Activity {
    RecyclerView rv_module;
    public RecyclerView.Adapter rva_module;
    public ImageView iv_moduleMiddle;
    public LinearLayout ll_moduleMiddle;

    public static int moduleFragmentPosition;
    public static final String YILIAN = "YILIAN";//完整通道名
    public static Util_Fragment util_fragment;
    public static Bean_OemInfo bean_oemInfo;
    Bean_OKProp_Module.Bean_MiddleModuleImg bean_middleModuleImg;

    public List<Bean_Item> bean_items = new ArrayList<>();
    public static Map<String, Bean_Production> gradeRoleMaps = new TreeMap<>();

    public enum Enum_Customer {
        美洽, QQ, 微信
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Abstract_App) Abstract_App.getInstance()).initOneKey();
        moduleFragmentPosition = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_module().getDefaultModuleFragmentId();
        bean_oneKeyBootstrap.initModuleFragment(this, util_fragment = new Util_Fragment(), bean_items, R.id.rl_fragment, moduleFragmentPosition);
        init(R.layout.app_act_main_module);
        initModuleFragmentDataAndPush(getRootView());
    }

    @Override
    public void onResume() {
        super.onResume();
        //用于点击Act模块
        for (int i = 0, size = bean_items.size(); i < size; i++)
            bean_items.get(i).setChecked(false);
        bean_items.get(moduleFragmentPosition).setChecked(true);
        util_fragment.fragmentToShow(moduleFragmentPosition);
        rva_module.notifyDataSetChanged();
        //getPurseBaseInfo(null,null);
    }

    @Override
    public void initView() {
        bean_middleModuleImg = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_module().getBean_middleModuleImg();
        rv_module = (RecyclerView) findViewById(R.id.rv_module);
        iv_moduleMiddle = (ImageView) findViewById(R.id.iv_moduleMiddle);
        ll_moduleMiddle = (LinearLayout) findViewById(R.id.ll_moduleMiddle);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iv_moduleMiddle.getLayoutParams();
        lp.width = lp.height = screenWidth / 6;
        iv_moduleMiddle.setLayoutParams(lp);

        rv_module.setLayoutManager(new GridLayoutManager(getActivity(), util_fragment.getTm_fragment().size()));
        if (bean_middleModuleImg != null && util_fragment.getTm_fragment().size() % 2 == 1) {
            ll_moduleMiddle.setVisibility(View.VISIBLE);
            iv_moduleMiddle.setImageResource(moduleFragmentPosition == util_fragment.getTm_fragment().size() / 2 ? bean_middleModuleImg.getMiddleModuleImgOnId() : bean_middleModuleImg.getMiddleModuleImgOffId());
        } else
            ll_moduleMiddle.setVisibility(View.GONE);
    }

    @Override
    public void onAttachFragment(final Fragment fragment) {
        super.onAttachFragment(fragment);
        if (util_fragment.getTm_fragment().get(Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_module().getDefaultModuleFragmentId()) == fragment) {
            rv_module.post(new Runnable() {
                @Override
                public void run() {
                    if (bean_middleModuleImg != null && bean_middleModuleImg.getEnum_middleModuleImgType() != null) {
                        AutoLinearLayout.LayoutParams layoutParams = (AutoLinearLayout.LayoutParams) iv_moduleMiddle.getLayoutParams();
                        AutoLayoutInfo autoLayoutInfo = layoutParams.getAutoLayoutInfo();
                        switch (bean_middleModuleImg.getEnum_middleModuleImgType()) {
                            case Outer:
                                autoLayoutInfo.addAttr(new HeightAttr(170, 0, 0));
                                autoLayoutInfo.addAttr(new WidthAttr(170, 0, 0));
                                autoLayoutInfo.addAttr(new MarginBottomAttr(10, 0, 0));
                                break;
                            case Inner:
                                autoLayoutInfo.addAttr(new HeightAttr(120, 0, 0));
                                autoLayoutInfo.addAttr(new WidthAttr(120, 0, 0));
                                autoLayoutInfo.addAttr(new MarginBottomAttr(10, 0, 0));
                                break;
                        }
                        iv_moduleMiddle.setLayoutParams(iv_moduleMiddle.getLayoutParams());
                    }

                    ((Framework_Fragment) fragment).onSelected();
                }
            });
        }
    }

    @Override
    public void initData() {

    }


    @Override
    public void initAdapter() {
        rv_module.setAdapter(rva_module = new RecyclerView.Adapter() {
            class MyViewHolder extends RecyclerView.ViewHolder {
                SimpleDraweeView sdv_img;
                TextView tv_name;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    (sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img)).setVisibility(View.VISIBLE);

                    (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
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
                final Bean_Item bean = bean_items.get(position);

                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.tv_name.setText(bean.getName().toString());
                mvh.sdv_img.setColorFilter(new LightingColorFilter(Color.BLACK, bean.isChecked() ? getResources().getColor(R.color.primaryColorOff) : getResources().getColor(R.color.text_color_3)));
                mvh.tv_name.setTextColor(bean.isChecked() ? getResources().getColor(R.color.primaryColorOff) : getResources().getColor(R.color.text_color_3));
                if (util_fragment.getTm_fragment().size() % 2 == 1 && bean_middleModuleImg != null) {
                    mvh.ll_bg.setVisibility(position == bean_items.size() / 2 ? View.GONE : View.VISIBLE);
                }
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int x = 0, count = bean_items.size(); x < count; x++) {
                            Bean_Item bean2 = bean_items.get(x);
                            bean2.setChecked(x == position ? true : false);
                        }
                        if (bean_middleModuleImg != null)
                            iv_moduleMiddle.setImageResource(bean_middleModuleImg.getMiddleModuleImgOffId());
                        notifyDataSetChanged();
                        util_fragment.fragmentToShow(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_items.size();
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_moduleMiddle);
    }

    @Override
    public void onClick(View view) {
        if (view == iv_moduleMiddle) {
            for (int x = 0, count = bean_items.size(); x < count; x++) {
                Bean_Item bean2 = bean_items.get(x);
                bean2.setChecked(false);
            }
            if (bean_middleModuleImg != null)
                iv_moduleMiddle.setImageResource(bean_middleModuleImg.getMiddleModuleImgOnId());
            rva_module.notifyDataSetChanged();
            Util_Runnable.Util_ArgsRunnable util_Args_runnable = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_module().getBean_middleModuleImg().getCoverMiddleModuleRunnable();
            if (util_Args_runnable != null)
                util_Args_runnable.run(getActivity());
            else
                util_fragment.fragmentToShow(bean_items.size() / 2);

        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = util_fragment.getTm_fragment().get(moduleFragmentPosition);
        if (fragment instanceof Framework_Web && ((Framework_Web) fragment).webView.canGoBack())
            ((Framework_Web) fragment).webView.goBack();
        else
            Util_System.systemToExitAndToast(Constants_International.keyboard_back_exit, 2);
    }

}
