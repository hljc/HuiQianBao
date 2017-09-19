package com.ceq.app.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.main.bean.Bean_PersonalInfo;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.methods.Method_Static.getPersonalInfoData;
import static com.ceq.app.main.methods.Method_Static.getRealNameStatus;
import static com.ceq.app.main.methods.Method_Static.getUserGradeRole;

/**
 * Created by ceq on 2017/5/17.
 */

public class Act_Mine_UserInfo extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_info;
    List<Bean_Item> userInfoList = new ArrayList<>();
    RecyclerView.Adapter rva_info;
    View_XRefreshLayout xrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_mine_userinfo);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "个人信息", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_info = (RecyclerView) findViewById(R.id.rv_info);
        rv_info.setLayoutManager(new LinearLayoutManager(getActivity()));

        xrv = (View_XRefreshLayout) findViewById(R.id.xrv);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initAdapter() {
        rv_info.setAdapter(rva_info = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_right;
                RelativeLayout rl_bg;
                SimpleDraweeView sdv_img;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    sdv_img.setVisibility(View.GONE);

                    rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);

                    tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                    tv_right.setVisibility(View.VISIBLE);
                    tv_right.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    tv_right.setTextColor(getResources().getColor(R.color.text_color_3));

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
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
                final Bean_Item bean = userInfoList.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
            }

            @Override
            public int getItemCount() {
                return userInfoList.size();
            }
        });
        xrv.setOnRefreshHttpListener(false, 0, userInfoList, rva_info, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                getPersonalInfoData(getActivity(), view_xRefreshStatusView,true, new Util_Runnable.Util_ArgsRunnable() {
                    @Override
                    public void run(Object... data) {
                        Bean_PersonalInfo bean = (Bean_PersonalInfo) data[0];
                        Abstract_App.bean_userInfo.setRealnameStatus(bean.getRealnameStatus());
                        Abstract_App.bean_userInfo.setGrade(bean.getGrade());
                        userInfoList.add(new Bean_Item("姓名", (String) Util_Empty.isEmptyToReplace(bean.getRealname(), "-")));
                        userInfoList.add(new Bean_Item("手机号", (String) Util_Empty.isEmptyToReplace(bean.getPhone(), "-")));
                        userInfoList.add(new Bean_Item("系统编号", (String) Util_Empty.isEmptyToReplace(bean.getUserid(), "-")));
                        userInfoList.add(new Bean_Item("注册日期", (String) Util_Empty.isEmptyToReplace(new SimpleDateFormat("yyyy-MM-dd").format(Long.parseLong(bean.getCreateTime())), "-")));
                        userInfoList.add(new Bean_Item("身份证号", bean.getIdcard() == null ? "-" : "**** **** **** ".concat(bean.getIdcard().substring(bean.getIdcard().length() - 4))));
                        userInfoList.add(new Bean_Item("等级", (String) Util_Empty.isEmptyToReplace(getUserGradeRole(bean.getGrade()), "-")));
                        userInfoList.add(new Bean_Item("实名状态", (String) Util_Empty.isEmptyToReplace(getRealNameStatus(), "-")));
                        String userShopStatus = null;
                        if (bean.getUsershopStatus() != null)
                            switch (bean.getUsershopStatus()) {
                                case "0":
                                    userShopStatus = "审核中";
                                    break;
                                case "1":
                                    userShopStatus = "审核通过";
                                    break;
                                case "2":
                                    userShopStatus = "审核失败";
                                    break;
                                case "3":
                                    userShopStatus = "未审核";
                                    break;
                            }
                        userInfoList.add(new Bean_Item("商家状态", (String) Util_Empty.isEmptyToReplace(userShopStatus, "-")));
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        decimalFormat.setRoundingMode(RoundingMode.DOWN);
                        String withdrawFee = bean.getWithdrawFee();
                        if (withdrawFee == null)
                            withdrawFee = "-";
                        else
                            withdrawFee = decimalFormat.format(new BigDecimal(withdrawFee)).concat("元/笔");
                        userInfoList.add(new Bean_Item("提现手续费", withdrawFee));
                    }
                });
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        }
    }

}
