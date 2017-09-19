package com.ceq.app.main.activity;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
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

import com.blankj.utilcode.util.AppUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.methods.Method_Static.callTelephone;
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class Act_Mine_AboutUs extends Framework_Activity {
    TextView tv_aboutUs, tv_version;
    ImageView iv_back;
    RecyclerView rv_function;
    List<Bean_Item> functionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_mine_aboutus);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "关于我们", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_function = (RecyclerView) findViewById(R.id.rv_function);
        rv_function.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_aboutUs = (TextView) findViewById(R.id.tv_aboutUs);
        tv_version = (TextView) findViewById(R.id.tv_version);
        String appName = (String) getResources().getText(R.string.app_name);
        String content = new StringBuilder("\t\t随着移动互联网的高速发展，手机里的移动支付应用给日常生活带来极大便利，近日，")
                .append(Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$companyName())
                .append("特推出一款可实现无卡支付的掌上钱包——")
                .append(appName)
                .append("\n")
                .append("\t\t本产品颠覆传统POS机，通过无卡支付技术，实现一键支付;该软件支持支付宝、财付通、易宝、银联、微信和几乎所有的银行卡和信用卡，省去现金交易的不安全性和繁琐性，节约成本、简单易用。商家只需一键安装该软件，输入银行卡号，提供验证即可一键支付，")
                .append(appName)
                .append("对商户进行实名认证，短信认证，为您打造一个绿色、安全、快捷的支付平台。").toString();
        String aboutUsContent = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getAboutUsContent();
        tv_aboutUs.setText((CharSequence) Util_Empty.isEmptyToReplace(aboutUsContent, content));
        tv_version.setText("V ".concat(AppUtils.getAppVersionName(AppUtils.getAppPackageName())));
    }

    @Override
    public void initData() {
        functionList.add(new Bean_Item("客服热线", Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getTelephone(), R.mipmap.icon_aboutus_hotline, false));
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
                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    setImageLayoutParams(sdv_img, 60, 0);

                    iv_forward = (ImageView) itemView.findViewById(R.id.iv_forward);
                    iv_forward.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.text_color_2)));
                    iv_forward.setVisibility(View.VISIBLE);

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
                final Bean_Item bean = functionList.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.iv_forward.setVisibility(Util_Empty.isEmpty(bean.getValue()) ? View.VISIBLE : View.GONE);
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0:
                                callTelephone(getActivity());
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return functionList.size();
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back)
            onBackPressed();
    }
}
