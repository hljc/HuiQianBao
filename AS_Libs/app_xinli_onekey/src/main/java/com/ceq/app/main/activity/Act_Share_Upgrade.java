package com.ceq.app.main.activity;

import android.content.Intent;
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

import com.blankj.utilcode.util.SpanUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.methods.Method_Static.getUserGradeRole;
import static com.ceq.app.main.methods.Method_Static.setBanner;
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;

/**
 * Created by ceq on 2017/5/15.
 */

public class Act_Share_Upgrade extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_upgrade;
    List<Bean_Item> bean_itemList = new ArrayList<>();
    TextView tv_currentRole;
    Banner banner;

    Bean_OKModule_UIOptions uiOptions=new Bean_OKModule_UIOptions();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_share_proxyupgrade);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "升级", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        //轮播
        banner = (Banner) findViewById(R.id.banner);
        setBanner(getActivity(), banner, false);

        rv_upgrade = (RecyclerView) findViewById(R.id.rv_upgrade);
        rv_upgrade.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_currentRole = (TextView) findViewById(R.id.tv_currentRole);
        tv_currentRole.setText(new SpanUtils().append("您当前为：").append(getUserGradeRole(Abstract_App.bean_userInfo.getGrade())).setForegroundColor(getResources().getColor(R.color.primaryColorOff)).create());
    }

    @Override
    public void initData() {
        bean_itemList.add(new Bean_Item("在线购买", R.mipmap.icon_proxy_onlinepay, false));
        bean_itemList.add(new Bean_Item("转账汇款", R.mipmap.icon_proxy_zhuanzhang, false));
        bean_itemList.add(new Bean_Item("积分兑换", R.mipmap.icon_proxy_scorereplace, false));
    }

    @Override
    public void initAdapter() {
        rv_upgrade.setAdapter(new RecyclerView.Adapter() {

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
                final Bean_Item bean = bean_itemList.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.iv_forward.setVisibility(Util_Empty.isEmpty(bean.getValue()) ? View.VISIBLE : View.GONE);
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(getActivity(), Act_Upgrade_OnlineBuy.class));
                                break;
                            case 1:
                                Class clazz = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getRemitActivity();
                                if (clazz == null)
                                    Util_Toast.toast(Constant_International.framework_function_no_open);
                                else
                                    startActivity(new Intent(getActivity(), Act_Upgrade_Remit$ZDQB.class));
                                break;
                            case 2:
                                Util_Toast.toast(Constant_International.framework_function_no_open);
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_itemList.size();
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
