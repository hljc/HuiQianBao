package com.ceq.app.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.HeightAttr;
import com.zhy.autolayout.attr.MinHeightAttr;
import com.zhy.autolayout.attr.PaddingBottomAttr;
import com.zhy.autolayout.attr.PaddingLeftAttr;
import com.zhy.autolayout.attr.PaddingRightAttr;
import com.zhy.autolayout.attr.TextSizeAttr;
import com.zhy.autolayout.attr.WidthAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceq on 2017/5/9.
 */

public class Frag_Share_Grade extends Framework_Fragment {
    RecyclerView rv_grade;
    RecyclerView.Adapter rva_grade;

    List<Bean_Item> itemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_share_grade);
    }

    @Override
    public void initView() {
        rv_grade = (RecyclerView) findViewById(R.id.rv_grade);
        rv_grade.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_grade.setAdapter(rva_grade = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                SimpleDraweeView sdv_img;
                TextView tv_name, tv_outerBottom;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    AutoRelativeLayout.LayoutParams layoutParams = (AutoRelativeLayout.LayoutParams) sdv_img.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo = layoutParams.getAutoLayoutInfo();
                    autoLayoutInfo.addAttr(new WidthAttr(90, 0, 0));
                    autoLayoutInfo.addAttr(new HeightAttr(90, 0, 0));
                    autoLayoutInfo.addAttr(new PaddingLeftAttr(20, 0, 0));
                    autoLayoutInfo.addAttr(new PaddingRightAttr(5, 0, 0));
                    ViewGroup.LayoutParams lp = sdv_img.getLayoutParams();
                    sdv_img.setLayoutParams(lp);
                    sdv_img.setVisibility(View.VISIBLE);

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_4));
                    AutoLinearLayout.LayoutParams layoutParams1 = (AutoLinearLayout.LayoutParams) tv_name.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo1 = layoutParams1.getAutoLayoutInfo();
                    autoLayoutInfo1.addAttr(new TextSizeAttr(50, 0, 0));
                    ViewGroup.LayoutParams lp1 = tv_name.getLayoutParams();
                    tv_name.setLayoutParams(lp1);


                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    AutoLinearLayout.LayoutParams layoutParams4 = (AutoLinearLayout.LayoutParams) ll_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo4 = layoutParams4.getAutoLayoutInfo();
                    autoLayoutInfo4.addAttr(new PaddingBottomAttr(20, 0, 0));
                    ViewGroup.LayoutParams lp4 = ll_bg.getLayoutParams();
                    ll_bg.setLayoutParams(lp4);

                    RelativeLayout rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);
                    AutoLinearLayout.LayoutParams layoutParams3 = (AutoLinearLayout.LayoutParams) rl_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo3 = layoutParams3.getAutoLayoutInfo();
                    autoLayoutInfo3.addAttr(new MinHeightAttr(0, 0, 0));
                    ViewGroup.LayoutParams lp3 = rl_bg.getLayoutParams();
                    rl_bg.setLayoutParams(lp3);

                    tv_outerBottom = (TextView) itemView.findViewById(R.id.tv_outerBottom);
                    tv_outerBottom.setVisibility(View.VISIBLE);
                    tv_outerBottom.setTextColor(getResources().getColor(R.color.text_color_3));
                    tv_outerBottom.setHintTextColor(getResources().getColor(R.color.text_color_2));
                    AutoLinearLayout.LayoutParams layoutParams5 = (AutoLinearLayout.LayoutParams) tv_outerBottom.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo5 = layoutParams5.getAutoLayoutInfo();
                    autoLayoutInfo5.addAttr(new TextSizeAttr(40, 0, 0));
                    autoLayoutInfo5.addAttr(new PaddingLeftAttr(100, 0, 0));
                    ViewGroup.LayoutParams lp5 = tv_outerBottom.getLayoutParams();
                    tv_outerBottom.setLayoutParams(lp5);
                }


            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = itemList.get(position);
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_outerBottom.setText(bean.getSubName());
                mvh.tv_outerBottom.setHint(bean.getValue());
            }

            @Override
            public int getItemCount() {
                return itemList.size();
            }
        });
    }


    @Override
    public void initListener() {
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onClick(View v) {
    }
}
