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
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class Act_Mine_More$JFB extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_mainFunction;
    public static final List<Bean_Item> mainFunctionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_mine_more);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "更多", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_mainFunction = (RecyclerView) findViewById(R.id.rv_mainFunction);
        rv_mainFunction.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_mainFunction.setAdapter(
                new RecyclerView.Adapter() {
                    class MyViewHolder extends RecyclerView.ViewHolder {
                        TextView tv_name, tv_right;
                        RelativeLayout rl_bg;
                        ImageView iv_forward;
                        SimpleDraweeView sdv_img;

                        public MyViewHolder(View itemView) {
                            super(itemView);
                            itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                            sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                            setImageLayoutParams(sdv_img, 90, 0);

                            iv_forward = (ImageView) itemView.findViewById(R.id.iv_forward);
                            iv_forward.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.text_color_2)));
                            iv_forward.setVisibility(View.VISIBLE);

                            rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);

                            tv_right = (TextView) itemView.findViewById(R.id.tv_right);
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
        Util_View.viewOnClick(this, iv_back);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back) {
            onBackPressed();
        }
    }
}
