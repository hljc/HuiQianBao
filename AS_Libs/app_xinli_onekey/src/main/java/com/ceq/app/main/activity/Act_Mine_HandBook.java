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

import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;
import static com.ceq.app.main.methods.Method_Static.skipToHelp;

/**
 * Created by ceq on 2017/5/13.
 */

public class Act_Mine_HandBook extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_handbook1, rv_handbook2, rv_handbook3;
    List<Bean_Item> bean_itemList1 = new ArrayList<>();
    List<Bean_Item> bean_itemList2 = new ArrayList<>();
    List<Bean_Item> bean_itemList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_mine_handbook);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "操作手册", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_handbook1 = (RecyclerView) findViewById(R.id.rv_handbook1);
        rv_handbook1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_handbook2 = (RecyclerView) findViewById(R.id.rv_handbook2);
        rv_handbook2.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_handbook3 = (RecyclerView) findViewById(R.id.rv_handbook3);
        rv_handbook3.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        bean_itemList1.add(new Bean_Item("如何下载", R.mipmap.icon_handbook_how_download, false));
        bean_itemList1.add(new Bean_Item("如何注册", R.mipmap.icon_handbook_how_register, R.mipmap.bg_handbook_how_register));
        bean_itemList1.add(new Bean_Item("如何实名认证", R.mipmap.icon_handbook_how_certification, R.mipmap.bg_handbook_how_certification));
        bean_itemList1.add(new Bean_Item("如何更改密码", R.mipmap.icon_handbook_how_password, R.mipmap.bg_handbook_how_modify_password));

        bean_itemList2.add(new Bean_Item("如何收款", R.mipmap.icon_handbook_how_collection, R.mipmap.bg_handbook_how_collection));
        bean_itemList2.add(new Bean_Item("如何提现", R.mipmap.icon_handbook_how_withdraw, R.mipmap.bg_handbook_how_withdrawals));
        bean_itemList2.add(new Bean_Item("如何升级", R.mipmap.icon_handbook_how_upgrade, R.mipmap.bg_handbook_how_upgrade));

        bean_itemList3.add(new Bean_Item("如何分享", R.mipmap.icon_handbook_how_share, R.mipmap.bg_handbook_how_share));
        bean_itemList3.add(new Bean_Item("用户须知", R.mipmap.icon_handbook_how_help, false));
    }

    @Override
    public void initAdapter() {
        rv_handbook1.setAdapter(new RecyclerView.Adapter() {

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
                final Bean_Item bean = bean_itemList1.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.iv_forward.setVisibility(Util_Empty.isEmpty(bean.getValue()) ? View.VISIBLE : View.GONE);
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bean.getObject() != null)
                            skipToHelp(getActivity(), bean.getName().toString(), (Integer) bean.getObject());
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_itemList1.size();
            }
        });

        rv_handbook2.setAdapter(new RecyclerView.Adapter() {

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
                final Bean_Item bean = bean_itemList2.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.iv_forward.setVisibility(Util_Empty.isEmpty(bean.getValue()) ? View.VISIBLE : View.GONE);
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bean.getObject() != null)
                            skipToHelp(getActivity(), bean.getName().toString(), (Integer) bean.getObject());
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_itemList2.size();
            }
        });

        rv_handbook3.setAdapter(new RecyclerView.Adapter() {

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
                final Bean_Item bean = bean_itemList3.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.iv_forward.setVisibility(Util_Empty.isEmpty(bean.getValue()) ? View.VISIBLE : View.GONE);
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bean.getObject() != null)
                            skipToHelp(getActivity(), bean.getName().toString(), (Integer) bean.getObject());
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_itemList3.size();
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
