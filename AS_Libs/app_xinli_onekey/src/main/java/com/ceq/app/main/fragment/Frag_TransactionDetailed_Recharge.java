package com.ceq.app.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.main.activity.Act_Mine_TransactionDetailed;
import com.ceq.app.main.activity.Act_TransactionDetailed;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Recharge;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Oem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.activity.Act_Mine_Rate.channelLogoMap;
import static com.ceq.app.main.activity.Act_TransactionDetailed.Extra_Bean_TransactionDetailed_Recharge;
import static com.ceq.app.main.activity.Act_TransactionDetailed.getOrderStatus;

/**
 * Created by ceq on 2017/4/15.
 */

public class Frag_TransactionDetailed_Recharge extends Framework_Fragment {
    RecyclerView rv_transactionDetailed;
    RecyclerView.Adapter rva_transactionDetailed;
    public  View_XRefreshLayout xrv;
   public Bean_OKProp_Oem.Enum_OrderStatusFilter enum_orderStatus;

    List<Bean_TransactionDetailed_Recharge> bean_transactionDetailedList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        enum_orderStatus = ((Act_Mine_TransactionDetailed) getActivity()).defaultOrderStatus;
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
            DecimalFormat decimalFormat = new DecimalFormat("0.00");

            {
                decimalFormat.setRoundingMode(RoundingMode.DOWN);
            }

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_remark, tv_date, tv_orderStatus, tv_transactionMoney, tv_poundage, tv_realMoney, tv_rate, tv_orderNum;
                ImageView iv_status, iv_order;
                SimpleDraweeView sdv_img;
                LinearLayout all_rate, all_bg;
                int primaryColor = getResources().getColor(R.color.primaryColorOff);

                public MyViewHolder(View itemView) {
                    super(itemView);
                    all_bg = (LinearLayout) itemView.findViewById(R.id.all_bg);

                    tv_remark = (TextView) itemView.findViewById(R.id.tv_remark);
                    tv_date = (TextView) itemView.findViewById(R.id.tv_date);
                    tv_orderStatus = (TextView) itemView.findViewById(R.id.tv_orderStatus);
                    tv_orderNum = (TextView) itemView.findViewById(R.id.tv_orderNum);
                    tv_transactionMoney = (TextView) itemView.findViewById(R.id.tv_transactionMoney);
                    tv_poundage = (TextView) itemView.findViewById(R.id.tv_poundage);
                    tv_realMoney = (TextView) itemView.findViewById(R.id.tv_realMoney);
                    tv_rate = (TextView) itemView.findViewById(R.id.tv_rate);

                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                 /*   rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);
                    AutoLinearLayout.LayoutParams layoutParams0 = (AutoLinearLayout.LayoutParams) rl_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo0 = layoutParams0.getAutoLayoutInfo();
                    autoLayoutInfo0.addAttr(new PaddingTopAttr(20, 0, 0));
                    autoLayoutInfo0.addAttr(new PaddingBottomAttr(20, 0, 0));
                    ViewGroup.LayoutParams lp0 = rl_bg.getLayoutParams();
                    rl_bg.setLayoutParams(lp0);

                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                    (sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img)).setVisibility(View.VISIBLE);
                    AutoRelativeLayout.LayoutParams lp2 = (AutoRelativeLayout.LayoutParams) sdv_img.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo = lp2.getAutoLayoutInfo();
                    autoLayoutInfo.addAttr(new WidthAttr(120, 0, 0));
                    autoLayoutInfo.addAttr(new HeightAttr(120, 0, 0));
                    autoLayoutInfo.addAttr(new MarginRightAttr(10, 0, 0));
                    ViewGroup.LayoutParams lp = sdv_img.getLayoutParams();
                    sdv_img.setLayoutParams(lp);


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
                    iv_forward.setVisibility(View.VISIBLE);*/

                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(primaryColor);
                    gradientDrawable.setCornerRadii(new float[]{0, 10, 30, 30, 10, 0, 30, 30});
                    all_rate = (LinearLayout) itemView.findViewById(R.id.all_rate);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        all_rate.setBackground(gradientDrawable);
                    }

                    iv_status = (ImageView) itemView.findViewById(R.id.iv_status);
                    iv_order = (ImageView) itemView.findViewById(R.id.iv_order);
                    iv_order.setColorFilter(new LightingColorFilter(Color.BLACK, primaryColor));
                }
            }


            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_bill, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_TransactionDetailed_Recharge bean = bean_transactionDetailedList.get(position);
                mvh.tv_date.setText(bean.getCreateTime());
                mvh.tv_remark.setText(bean.getDesc());
                mvh.tv_orderNum.setText(bean.getOrdercode());
                mvh.tv_realMoney.setText(decimalFormat.format(new BigDecimal(bean.getRealAmount())));
                mvh.tv_poundage.setText(decimalFormat.format(new BigDecimal(bean.getAmount()).multiply(new BigDecimal(bean.getRate()))));
                mvh.tv_transactionMoney.setText(decimalFormat.format(new BigDecimal(bean.getAmount())));
                mvh.tv_rate.setText(Util_Empty.isEmpty(bean.getRate()) ? "-" : decimalFormat.format(new BigDecimal(bean.getRate()).multiply(new BigDecimal("100"))).concat("%"));
                mvh.tv_orderStatus.setText(getOrderStatus(bean.getStatus()));
                mvh.sdv_img.setImageURI(channelLogoMap.get(bean.getChannelname()));
                mvh.all_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Act_TransactionDetailed.class).putExtra(Extra_Bean_TransactionDetailed_Recharge, bean));
                    }
                });
              /*  mvh.sdv_img.setImageURI(channelLogoMap.get(bean.getChannelname()));
                mvh.tv_name.setText(bean.getDesc());
                mvh.tv_bottom.setText(bean.getCreateTime());
                mvh.tv_subName.setText(new SpanUtils().append("订单状态:").append(getOrderStatus(bean.getStatus())).setForegroundColor(getResources().getColor(R.color.primaryColorOff)).create());
                mvh.tv_right.setText(new StringBuilder("￥").append(decimalFormat.format(new BigDecimal(bean.getRealAmount()))));
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Act_TransactionDetailed.class).putExtra(Extra_Bean_TransactionDetailed_Recharge, bean));
                    }
                });*/

            }

            @Override
            public int getItemCount() {
                return bean_transactionDetailedList.size();
            }
        });
        xrv.setOnRefreshHttpListener(0, bean_transactionDetailedList, rva_transactionDetailed, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                ((Act_Mine_TransactionDetailed) getActivity()).getTransactionDetailedData(view_xRefreshStatusView, Constant_Api.URL_GET_DETAILED_TRANSACTION_POST, currentPagePosition, list, 0);
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
