package com.ceq.app.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.bean.Bean_PushData;
import com.ceq.app.main.activity.Act_Mine_Settings$JFB;
import com.ceq.app.main.activity.Act_Mine_UserInfo;
import com.ceq.app.main.bean.Bean_PersonalInfo;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.modules.interfaces.Inter_OKModule_MineUI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.attr.MarginLeftAttr;
import com.zhy.autolayout.attr.MarginRightAttr;
import com.zhy.autolayout.attr.MarginTopAttr;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.methods.Method_Static.getPersonalInfoData;
import static com.ceq.app.main.methods.Method_Static.getUserGradeRole;
import static com.ceq.app.main.methods.Method_Static.initUserPurseBaseInfo;
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;
import static com.ceq.app_core.framework.Framework_Activity.screenWidth;
import static com.ceq.app_xinli_onekey.R.id.tv_category;

/**
 * Created by ceq on 2017/4/14.
 */
public class Frag_Module_Mine$JFB extends Abstract_OkModule_Fragment<Inter_OKModule_MineUI.Inter_Mine$JFB> {
    RecyclerView rv_mainFunction, rv_money;
    SimpleDraweeView sdv_header;
    RecyclerView.Adapter rva_money;
    ImageView iv_user, iv_setting;
    ImageView iv_rightImgFlag, iv_leftImgFlag;
    TextView tv_username;
    View_XRefreshLayout xrl;

    List<Bean_Item> purseInfoList = new ArrayList<>();
    List<Bean_Item> mainFunctionList = new ArrayList<>();

    public Frag_Module_Mine$JFB() {
    }

    public Frag_Module_Mine$JFB(String tabBarTitle, int tabBarImgId ) {
        super(tabBarTitle, tabBarImgId );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_module_mine_jfb);
    }

    @Override
    public void initView() {
        int color = getResources().getColor(R.color.primaryColorOff);
        //标题栏

        //功能
        rv_money = (RecyclerView) findViewById(R.id.rv_money);
        rv_money.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //功能

        rv_mainFunction = (RecyclerView) findViewById(R.id.rv_mainFunction);
        rv_mainFunction.setLayoutManager(new LinearLayoutManager(getActivity()));

        sdv_header = (SimpleDraweeView) findViewById(R.id.sdv_header);
        sdv_header.getHierarchy().setFailureImage(R.mipmap.app_default_head);
        sdv_header.getHierarchy().setPlaceholderImage(R.mipmap.app_logo);

        /*tv_customServiceMobile = (TextView) findViewById(tv_customServiceMobile);
        tv_customServiceMobile.setText("客服热线:".concat(Abstract_App.bean_oneKey.getBean_oneKeyProps().getTelephone()));

        tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_logout.setVisibility(GONE);*/

        tv_username = (TextView) findViewById(R.id.tv_username);

        iv_user = (ImageView) findViewById(R.id.iv_user);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);


        iv_rightImgFlag = (ImageView) findViewById(R.id.iv_rightImgFlag);
        iv_rightImgFlag.setColorFilter(new LightingColorFilter(Color.BLACK, Color.WHITE));

        iv_leftImgFlag = (ImageView) findViewById(R.id.iv_leftImgFlag);
        iv_leftImgFlag.setColorFilter(new LightingColorFilter(Color.BLACK, Color.WHITE));


        xrl = (View_XRefreshLayout) findViewById(R.id.xrl);
        xrl.setColor(Color.WHITE);
    }

    public void updateView(View_XRefreshStatusView view_xRefreshStatusView) {
        Util_Log.logTest("rrr", this, tv_category, getUserGradeRole(Abstract_App.bean_userInfo.getGrade()));
        final String noName = "未获得姓名";
        getPersonalInfoData(getActivity(), view_xRefreshStatusView, false, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object[] data) {
                Bean_PersonalInfo bean = (Bean_PersonalInfo) data[0];
                Abstract_App.bean_userInfo.setGrade(bean.getGrade());
                Abstract_App.bean_userInfo.setRealnameStatus(bean.getRealnameStatus());

                switch (Abstract_App.bean_userInfo.getRealnameStatus()) {
                    case "1":
                        String name = (String) Util_Empty.isEmptyToReplace(bean.getRealname(), "");
                        tv_username.setHint(name.length() > 0 ? "" : noName);
                        tv_username.setText(name);
                        break;
                    default:
                        tv_username.setText("");
                        tv_username.setHint(noName);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    public void initAdapter() {
        rv_money.setAdapter(rva_money = new RecyclerView.Adapter() {
            int fontSize1 = screenWidth / 27;
            int fontSize2 = screenWidth / 28;

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_value;
                LinearLayout ll_bg;
                int color = getResources().getColor(R.color.text_color_1);

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize1);
                    tv_name.setTextColor(color);
                    tv_name.getPaint().setFakeBoldText(true);

                    tv_value = (TextView) itemView.findViewById(R.id.tv_value);
                    tv_value.setVisibility(View.VISIBLE);
                    tv_value.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize2);
                    tv_value.setTextColor(color);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    ViewGroup.LayoutParams lp = ll_bg.getLayoutParams();
                    lp.height = (screenWidth / 8);
                    ll_bg.setLayoutParams(lp);


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

        rv_mainFunction.setAdapter(
                new RecyclerView.Adapter() {
                    class MyViewHolder extends RecyclerView.ViewHolder {
                        TextView tv_name, tv_right;
                        RelativeLayout rl_bg;
                        ImageView iv_forward;
                        SimpleDraweeView sdv_img;
                        AutoLinearLayout ll_bg;

                        public MyViewHolder(View itemView) {
                            super(itemView);
                            itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                            sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                            setImageLayoutParams(sdv_img, 60, 0);

                            iv_forward = (ImageView) itemView.findViewById(R.id.iv_forward);
                            iv_forward.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.text_color_1)));
                            iv_forward.setVisibility(View.VISIBLE);

                            rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);

                            ll_bg = (AutoLinearLayout) itemView.findViewById(R.id.ll_bg);
                            GradientDrawable drawable = new GradientDrawable();
                            drawable.setColor(Color.argb(50, 255, 255, 255));
                            drawable.setCornerRadius(30);
                            ll_bg.setBackgroundDrawable(drawable);

                            AutoLinearLayout.LayoutParams lp = (AutoLinearLayout.LayoutParams) ll_bg.getLayoutParams();
                            AutoLayoutInfo ali = lp.getAutoLayoutInfo();
                            ali.addAttr(new MarginLeftAttr(30, 0, 0));
                            ali.addAttr(new MarginRightAttr(30, 0, 0));
                            ali.addAttr(new MarginTopAttr(20, 0, 0));
                            ll_bg.setLayoutParams(ll_bg.getLayoutParams());

                            tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                            tv_right.setTextColor(getResources().getColor(R.color.text_color_3));

                            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                            tv_name.setVisibility(View.VISIBLE);
                            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                            tv_name.setTextColor(getResources().getColor(R.color.text_color_1));
                        }
                    }

                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                        MyViewHolder mvh = (MyViewHolder) holder;
                        final Bean_Item bean = mainFunctionList.get(position);
                        mvh.tv_name.setText(bean.getName().toString());
                        mvh.sdv_img.setImageResource(bean.getImgId());
                        if (bean.getValue() != null) {
                            mvh.tv_right.setVisibility(View.VISIBLE);
                            mvh.tv_right.setText(bean.getValue());
                        } else
                            mvh.tv_right.setVisibility(View.GONE);
                        mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Util_Runnable.Util_ArgsRunnable util_Args_runnable = bean.getUtil_Args_runnable();
                                if (util_Args_runnable != null) util_Args_runnable.run(position);
                            }
                        });
                    }

                    @Override
                    public int getItemCount() {
                        return mainFunctionList.size();
                    }
                }
        );

    }

    @Override
    public void initListener() {
        // Util_View.viewOnClick(this, tv_customServiceMobile, tv_logout);
        Util_View.viewOnClick(this, iv_user, iv_setting);
    }


    @Override
    public void onClick(View v) {
   /*     if (v.getId() == tv_logout.getId()) {
            Abstract_App.getInstance().logout(getActivity());
        } else if (v.getId() == tv_customServiceMobile.getId()) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse(new StringBuilder("tel:").append(Abstract_App.bean_oneKey.getBean_oneKeyProps().getTelephone()).toString());
            intent.setData(data);
            startActivity(intent);
        } */
        if (v.getId() == iv_user.getId())
            startActivity(new Intent(getActivity(), Act_Mine_UserInfo.class));
        if (v.getId() == iv_setting.getId())
            startActivity(new Intent(getActivity(), Act_Mine_Settings$JFB.class));
    }

    @Override
    public void onSelected() {
        Util_Screen.statusBarToTranslucent(getActivity(), getResources().getColor(R.color.primaryColorOff));
    }

    @Override
    public void run(Inter_OKModule_MineUI.Inter_Mine$JFB data) {
        data.initUserBaseData$JFB(this, purseInfoList);
        data.initMainFunctionData$JFB(this, mainFunctionList);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshMainModuleData();
    }


    @Override
    public void updateViewByPushMessage(Bean_PushData bean_pushData, Bean_Push bean_push) {
        refreshMainModuleData();
    }

    private void refreshMainModuleData() {
        initUserPurseBaseInfo(xrl, false, rva_money, purseInfoList, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                updateView((View_XRefreshStatusView) data[0]);
            }
        });
    }
}
