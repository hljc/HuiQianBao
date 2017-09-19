package com.ceq.app.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.main.activity.Act_Mine_TransactionDetailed;
import com.ceq.app.main.activity.Act_TransactionDetailed;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Share;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.attr.PaddingBottomAttr;
import com.zhy.autolayout.attr.PaddingTopAttr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.activity.Act_TransactionDetailed.Extra_Bean_TransactionDetailed_Share;

/**
 * Created by ceq on 2017/4/15.
 */

public class Frag_TransactionDetailed_Share extends Framework_Fragment {
    RecyclerView rv_transactionDetailed;
    RecyclerView.Adapter rva_transactionDetailed;
    View_XRefreshLayout xrv;

    List<Bean_TransactionDetailed_Share> bean_transactionDetailedList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_transationdeatiled_recharge);
    }

    @Override
    public void initView() {
        rv_transactionDetailed = (RecyclerView) findViewById(R.id.rv_transactionDetailed);
        rv_transactionDetailed.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrv = (View_XRefreshLayout) findViewById(R.id.xrv);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_transactionDetailed.setAdapter(rva_transactionDetailed = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_subName, tv_right, tv_bottom;
                RelativeLayout rl_bg;
                ImageView iv_forward;

                public MyViewHolder(View itemView) {
                    super(itemView);

                    rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);
                    AutoLinearLayout.LayoutParams layoutParams0 = (AutoLinearLayout.LayoutParams) rl_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo0=layoutParams0.getAutoLayoutInfo();
                    autoLayoutInfo0.addAttr(new PaddingTopAttr(20,0,0));
                    autoLayoutInfo0.addAttr(new PaddingBottomAttr(20,0,0));
                    ViewGroup.LayoutParams lp0 = rl_bg.getLayoutParams();
                    rl_bg.setLayoutParams(lp0);

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
                    tv_name.getPaint().setFakeBoldText(true);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_3));

                    tv_subName = (TextView) itemView.findViewById(R.id.tv_subName);
                    tv_subName.setVisibility(View.VISIBLE);
                    tv_subName.setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);
                    tv_subName.setTextColor(getResources().getColor(R.color.text_color_3));

                    tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                    tv_right.setVisibility(View.VISIBLE);
                    tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);
                    tv_right.setTextColor(getResources().getColor(R.color.primaryColorOff));

                    tv_bottom = (TextView) itemView.findViewById(R.id.tv_bottom);
                    tv_bottom.setVisibility(View.VISIBLE);
                    tv_bottom.setTextColor(getResources().getColor(R.color.text_color_3));
                    tv_bottom.setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);


                    iv_forward = (ImageView) itemView.findViewById(R.id.iv_forward);
                    iv_forward.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.text_color_2)));
                    iv_forward.setVisibility(View.VISIBLE);
                }
            }



            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_TransactionDetailed_Share bean = bean_transactionDetailedList.get(position);
                mvh.tv_name.setText("分润返佣");
                mvh.tv_subName.setText(new SpanUtils().append("来自用户:").append(bean.getOriphone()).setForegroundColor(getResources().getColor(R.color.primaryColorOff)).create());
                mvh.tv_bottom.setText(bean.getCreateTime());
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                decimalFormat.setRoundingMode(RoundingMode.DOWN);
                mvh.tv_right.setText(new StringBuilder("￥").append(decimalFormat.format(new BigDecimal(bean.getAcqAmount()))));

                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Act_TransactionDetailed.class).putExtra(Extra_Bean_TransactionDetailed_Share,bean));
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_transactionDetailedList.size();
            }
        });

        xrv.setOnRefreshHttpListener(0, bean_transactionDetailedList, rva_transactionDetailed, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                ((Act_Mine_TransactionDetailed) getActivity()).getTransactionDetailedData(view_xRefreshStatusView, Constant_Api.URL_GET_DETAILED_SHARE_POST, currentPagePosition, list, -1);
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
