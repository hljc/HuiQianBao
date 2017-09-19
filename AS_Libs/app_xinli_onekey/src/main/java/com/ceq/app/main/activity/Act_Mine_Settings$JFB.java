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

import com.blankj.utilcode.util.AppUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Apk;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.core.activity.Act_Main.updateApk;
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;

/**
 * Created by ceq on 2017/4/15.
 */

public class Act_Mine_Settings$JFB extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_settings1, rv_settings2, rv_settings3;
    List<Bean_Item> functionList1 = new ArrayList<>();
    List<Bean_Item> functionList2 = new ArrayList<>();
    List<Bean_Item> functionList3 = new ArrayList<>();
    TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_mine_settings);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "设置", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        //设置列表
        rv_settings1 = (RecyclerView) findViewById(R.id.rv_settings1);
        rv_settings1.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_settings2 = (RecyclerView) findViewById(R.id.rv_settings2);
        rv_settings2.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_settings3 = (RecyclerView) findViewById(R.id.rv_settings3);
        rv_settings3.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_logout = (TextView) findViewById(R.id.tv_logout);
    }

    @Override
    public void initData() {
        functionList1.add(new Bean_Item("清除缓存", Util_Apk.getTotalCacheSize(), R.mipmap.icon_income_analysis, false));
        functionList2.add(new Bean_Item("版本更新", AppUtils.getAppVersionName(AppUtils.getAppPackageName()), R.mipmap.icon_settings_upgrade, false));
        functionList3.add(new Bean_Item("关于我们", R.mipmap.icon_settings_protocol, false));
    }


    @Override
    public void initAdapter() {
        rv_settings1.setAdapter(new RecyclerView.Adapter() {

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
                final Bean_Item bean = functionList1.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.iv_forward.setVisibility(Util_Empty.isEmpty(bean.getValue()) ? View.VISIBLE : View.GONE);
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0:
                                Util_Apk.clearAllCache();
                                functionList1.get(0).setValue( Util_Apk.getTotalCacheSize());
                                notifyDataSetChanged();
                                Util_Toast.toast("清除成功");
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return functionList1.size();
            }
        });

        rv_settings2.setAdapter(new RecyclerView.Adapter() {

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
                final MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = functionList2.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.iv_forward.setVisibility(Util_Empty.isEmpty(bean.getValue()) ? View.VISIBLE : View.GONE);
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0:
                                updateApk(mvh.rl_bg, true, null);
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return functionList2.size();
            }
        });

        rv_settings3.setAdapter(new RecyclerView.Adapter() {

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
                final MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = functionList3.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.iv_forward.setVisibility(Util_Empty.isEmpty(bean.getValue()) ? View.VISIBLE : View.GONE);
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(getActivity(), Act_Mine_AboutUs.class));
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return functionList3.size();
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);
        Util_View.viewOnClick(this, tv_logout);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v.getId() == tv_logout.getId()) {
            Abstract_App.getInstance().logout(getActivity());
        }
    }

}
