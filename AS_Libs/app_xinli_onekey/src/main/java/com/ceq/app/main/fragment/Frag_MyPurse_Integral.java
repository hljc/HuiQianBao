package com.ceq.app.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.main.activity.Act_MyPurse_PurseDetailed;
import com.ceq.app.main.bean.Bean_Purse_Integral;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app_core.framework.Framework_Activity.screenWidth;

/**
 * Created by ceq on 2017/4/15.
 */

public class Frag_MyPurse_Integral extends Framework_Fragment {
    RecyclerView rv_myPurse;
    RecyclerView.Adapter rva_myPurse;

    List<Bean_Purse_Integral> bean_purseIntegrals = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_mypurse_integral);
    }

    @Override
    public void initView() {
        rv_myPurse = (RecyclerView) findViewById(R.id.rv_myPurse);
        rv_myPurse.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_myPurse.setAdapter(rva_myPurse = new RecyclerView.Adapter() {
            int fontSize1 = screenWidth / 26;
            int fontSize2 = screenWidth / 28;
            int fontSize3 = screenWidth / 29;
            int w = screenWidth / 9 *7;

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_subName, tv_right;
                RelativeLayout rl_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);

                    rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);
                    rl_bg.setPadding(rl_bg.getPaddingLeft(), rl_bg.getPaddingTop() + 10, rl_bg.getPaddingRight(), rl_bg.getPaddingBottom() + 10);

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize1);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_3));

                    tv_subName = (TextView) itemView.findViewById(R.id.tv_subName);
                    tv_subName.setVisibility(View.VISIBLE);
                    tv_subName.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize2);
                    tv_subName.setTextColor(getResources().getColor(R.color.text_color_2));

                    tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                    tv_right.setVisibility(View.VISIBLE);
                    tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize3);
                    tv_right.setTextColor(getResources().getColor(R.color.text_color_3));

                }
            }


            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Purse_Integral bean = bean_purseIntegrals.get(position);
                mvh.tv_name.setText("积分来源:".concat(bean.getOrdercode()));
                mvh.tv_name.setMaxWidth(w);
                mvh.tv_subName.setText("时间:".concat(bean.getCreateTime()));
                mvh.tv_right.setText((bean.getAddOrSub().equals("0") ? "+" : "-").concat(bean.getCoin()).concat("分"));

            }

            @Override
            public int getItemCount() {
                return bean_purseIntegrals.size();
            }
        });
        final View_XRefreshLayout xrv = (View_XRefreshLayout) findViewById(R.id.xrv);
        xrv.setOnRefreshHttpListener(0, bean_purseIntegrals, rva_myPurse, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                ((Act_MyPurse_PurseDetailed) getActivity()).getScoreBalanceShareMoneyData(view_xRefreshStatusView, Constant_Api.URL_GET_USER_INTEGRAL_POST, currentPagePosition, list, 0);
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
