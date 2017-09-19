package com.ceq.app.main.fragment;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SpanUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.bean.Bean_PushData;
import com.ceq.app.main.bean.Bean_Production;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;
import com.ceq.app_xinli_onekey.core.modules.interfaces.Inter_OKModule_UpgradeUI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.main.fragment.Frag_Module_Upgrade$JFB.Enum_UpgradeStatus.升级;
import static com.ceq.app.main.fragment.Frag_Module_Upgrade$JFB.Enum_UpgradeStatus.客服;
import static com.ceq.app.main.fragment.Frag_Module_Upgrade$JFB.Enum_UpgradeStatus.已购;
import static com.ceq.app.main.methods.Method_Static.getProductionList;
import static com.ceq.app.main.methods.Method_Static.getUserGradeRole;
import static com.ceq.app.main.methods.Method_Static.selectCustomerService;
import static com.ceq.app.main.methods.Method_Static.setBanner;
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;
import static com.ceq.app.main.methods.Method_Static.getProductionToBuy;

/**
 * Created by Administrator on 2017/6/1.
 */

public class Frag_Module_Upgrade$JFB extends Abstract_OkModule_Fragment<Inter_OKModule_UpgradeUI.Inter_Upgrade$JFB> {
    View_XRefreshLayout xrv;
    LinearLayout all_noBanner;
    RecyclerView.Adapter rva_production;
    RecyclerView rv_function, rv_production;
    Banner banner;

    List<Bean_Item> functionList = new ArrayList<>();
    List<Bean_Production> bean_productionList = new ArrayList<>();

    public static final String Extra_Bool_Show_Title = "Extra_Bool_Show_Title";

    Bean_OKModule_UIOptions uiOptions = new Bean_OKModule_UIOptions();

    public enum Enum_UpgradeStatus {
        升级, 已购, 客服
    }

    public Frag_Module_Upgrade$JFB() {
    }

    public Frag_Module_Upgrade$JFB(String tabBarTitle, int tabBarImgId ) {
        super(tabBarTitle, tabBarImgId );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_module_upgrade_jfb);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "升级", VISIBLE);
        if (getArguments() != null)
            findViewById(R.id.icd_title).setVisibility(getArguments().getBoolean(Extra_Bool_Show_Title, true) ? View.VISIBLE : View.GONE);

        all_noBanner = (LinearLayout) findViewById(R.id.all_noBanner);

        rv_production = (RecyclerView) findViewById(R.id.rv_production);
        rv_production.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_function = (RecyclerView) findViewById(R.id.rv_function);
        rv_function.setLayoutManager(new LinearLayoutManager(getActivity()));

        banner = (Banner) findViewById(R.id.banner);
        setBanner(getActivity(), banner, false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void run(Inter_OKModule_UpgradeUI.Inter_Upgrade$JFB data) {
        functionList.clear();
        data.initMainFunctionData$JFB(this, functionList, uiOptions);
    }

    @Override
    public void initAdapter() {
        rv_function.setAdapter(new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_right;
                RelativeLayout rl_bg;
                ImageView iv_forward;
                SimpleDraweeView sdv_img;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    itemView.findViewById(R.id.rl_img).setVisibility(VISIBLE);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    setImageLayoutParams(sdv_img, 60, 0);

                    iv_forward = (ImageView) itemView.findViewById(R.id.iv_forward);
                    iv_forward.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.text_color_2)));
                    iv_forward.setVisibility(VISIBLE);

                    rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);

                    tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                    tv_right.setVisibility(VISIBLE);
                    tv_right.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    tv_right.setTextColor(getResources().getColor(R.color.text_color_3));

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_3));
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = functionList.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.iv_forward.setVisibility(Util_Empty.isEmpty(bean.getValue()) ? VISIBLE : GONE);
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Util_Runnable.Util_ArgsRunnable util_Args_runnable = bean.getUtil_Args_runnable();
                        if (util_Args_runnable != null) util_Args_runnable.run(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return functionList.size();
            }
        });

        rv_production.setAdapter(rva_production = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_subName, tv_right, tv_bottom;
                TextView tv_button;
                SimpleDraweeView sdv_img;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    setImageLayoutParams(sdv_img, 200, 20);


                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_3));
                    tv_name.setTypeface(Typeface.DEFAULT_BOLD);

                    tv_subName = (TextView) itemView.findViewById(R.id.tv_subName);
                    tv_subName.setVisibility(VISIBLE);
                    tv_subName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    tv_subName.setTextColor(getResources().getColor(R.color.text_color_3));

                    tv_bottom = (TextView) itemView.findViewById(R.id.tv_bottom);
                    tv_bottom.setVisibility(VISIBLE);
                    tv_bottom.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    tv_bottom.setTextColor(getResources().getColor(R.color.text_color_3));

                    tv_button = (TextView) itemView.findViewById(R.id.tv_button);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_rv_upgrade_production, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                final MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Production bean = bean_productionList.get(position);
                int color = getResources().getColor(R.color.primaryColorOff);
                SpannableStringBuilder name = null;
                boolean canBuy = Integer.parseInt(bean.getGrade()) > Integer.parseInt(Abstract_App.bean_userInfo.getGrade());
                switch (bean.getTrueFalseBuy()) {
                    case "0":
                        name = new SpanUtils().append(bean.getName()).append("   ¥").setBold().setForegroundColor(color).append(bean.getMoney()).setForegroundColor(color).create();
                        mvh.tv_button.setText(canBuy ? 升级.name() : 已购.name());
                        break;
                    case "1":
                        name = new SpanUtils().append(bean.getName()).append("   ¥").setBold().setForegroundColor(color).append(bean.getMoney()).setForegroundColor(color).create();
                        mvh.tv_button.setText(canBuy ? 客服.name() : 已购.name());
                        break;
                    case "2":
                        name = new SpanUtils().append(bean.getName()).create();
                        mvh.tv_button.setText(canBuy ? 升级.name() : 已购.name());
                        break;
                    case "3":
                        name = new SpanUtils().append(bean.getName()).append("   需联系客服").setBold().setForegroundColor(color).create();
                        mvh.tv_button.setText(canBuy ? 客服.name() : 已购.name());
                        break;
                }
                mvh.tv_name.setText(name == null ? "" : name);
                mvh.tv_subName.setText(bean.getUpgradestate());
                mvh.tv_bottom.setText(bean.getEarningsState());

                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(10);
                gradientDrawable.setColor(canBuy ? color : getResources().getColor(R.color.text_color_3));
                mvh.tv_button.setBackgroundDrawable(gradientDrawable);

                switch (bean.getGrade()) {
                    case "0":
                        mvh.sdv_img.setImageResource(R.mipmap.scale0);
                        break;
                    case "1":
                        mvh.sdv_img.setImageResource(R.mipmap.scale1);
                        break;
                    case "2":
                        mvh.sdv_img.setImageResource(R.mipmap.scale2);
                        break;
                    case "3":
                        mvh.sdv_img.setImageResource(R.mipmap.scale3);
                        break;
                    case "4":
                        mvh.sdv_img.setImageResource(R.mipmap.scale4);
                        break;
                    case "5":
                        mvh.sdv_img.setImageResource(R.mipmap.scale5);
                        break;
                    case "6":
                        mvh.sdv_img.setImageResource(R.mipmap.scale6);
                        break;
                    case "7":
                        mvh.sdv_img.setImageResource(R.mipmap.scale7);
                        break;
                    case "8":
                        mvh.sdv_img.setImageResource(R.mipmap.scale8);
                        break;
                    case "9":
                        mvh.sdv_img.setImageResource(R.mipmap.scale9);
                        break;
                    case "10":
                        mvh.sdv_img.setImageResource(R.mipmap.scale10);
                        break;
                }
                mvh.tv_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Enum_UpgradeStatus enum_upgradeStatus = Enum_UpgradeStatus.valueOf(mvh.tv_button.getText().toString());
                        switch (enum_upgradeStatus) {
                            case 升级:
                                getProductionToBuy(mvh.tv_button, null, bean, bean_productionList);
                                break;
                            case 客服:
                                selectCustomerService(getActivity());
                                break;
                            case 已购:
                                Util_Toast.toast(new StringBuilder("您已经是").append(getUserGradeRole(Abstract_App.bean_userInfo.getGrade())).append(",无需再购买此产品"));
                                break;
                        }

                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_productionList.size();
            }
        });
        final View_XRefreshLayout xrv = (View_XRefreshLayout) findViewById(R.id.xrv);
        xrv.setOnRefreshHttpListener(false, 0, bean_productionList, rva_production, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, final List list, RecyclerView.Adapter adapter) {
                getProductionList(getActivity(), view_xRefreshStatusView, new Util_Runnable.Util_ArgsRunnable() {
                    @Override
                    public void run(Object... data) {
                        list.clear();
                        list.addAll(JSONObject.parseArray(parseObject((String) data[0]).getString(Http_Key_Data), Bean_Production.class));
                    }
                });
            }
        });
    }


    @Override
    public void initListener() {
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSelected() {
        Util_Screen.statusBarToTranslucent(getActivity(), getResources().getColor(R.color.title_background));
    }

    @Override
    public void updateViewByPushMessage(Bean_PushData bean_pushData, Bean_Push bean_push) {

    }
}
