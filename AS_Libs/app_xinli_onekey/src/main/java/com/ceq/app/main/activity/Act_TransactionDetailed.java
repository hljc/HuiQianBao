package com.ceq.app.main.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceq.app.main.bean.Bean_TransactionDetailed_Integral;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Pay;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Recharge;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Share;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Withdrawals;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.MinHeightAttr;
import com.zhy.autolayout.attr.PaddingBottomAttr;
import com.zhy.autolayout.attr.PaddingTopAttr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ceq on 2017/5/21.
 */

public class Act_TransactionDetailed extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_detailed;
    RecyclerView.Adapter rva_detailed;
    List<Bean_Item> bean_itemList = new ArrayList<>();
    TextView tv_name, tv_value;
    public static final String Extra_Bean_TransactionDetailed_Recharge = "Extra_Bean_TransactionDetailed_Recharge";
    public static final String Extra_Bean_TransactionDetailed_Pay = "Extra_Bean_TransactionDetailed_Pay";
    public static final String Extra_Bean_TransactionDetailed_Withdrawals = "Extra_Bean_TransactionDetailed_Withdrawals";
    public static final String Extra_Bean_TransactionDetailed_Share = "Extra_Bean_TransactionDetailed_Share";
    public static final String Extra_Bean_TransactionDetailed_Integral = "Extra_Bean_TransactionDetailed_Integral";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_transactiondetailed);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "交易详情", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_detailed = (RecyclerView) findViewById(R.id.rv_detailed);
        rv_detailed.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_value = (TextView) findViewById(R.id.tv_value);

    }

    @Override
    public void initData() {
        showWithdrawalsInfo((Bean_TransactionDetailed_Withdrawals) getIntent().getSerializableExtra(Extra_Bean_TransactionDetailed_Withdrawals));
        showRechargeInfo((Bean_TransactionDetailed_Recharge) getIntent().getSerializableExtra(Extra_Bean_TransactionDetailed_Recharge));
        showPayInfo((Bean_TransactionDetailed_Pay) getIntent().getSerializableExtra(Extra_Bean_TransactionDetailed_Pay));
        showShareInfo((Bean_TransactionDetailed_Share) getIntent().getSerializableExtra(Extra_Bean_TransactionDetailed_Share));
        showIntegralInfo((Bean_TransactionDetailed_Integral) getIntent().getSerializableExtra(Extra_Bean_TransactionDetailed_Integral));


    }

    private void showIntegralInfo(Bean_TransactionDetailed_Integral bean_transactionDetailed_integral) {
        if (bean_transactionDetailed_integral == null)
            return;
        tv_name.setText("积分值");

        tv_value.setText(new StringBuilder(bean_transactionDetailed_integral.getType().equals("0") ? "+" : "-").append(bean_transactionDetailed_integral.getCoin()).append("分"));
        bean_itemList.add(new Bean_Item("创建时间", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_integral.getCreateTime(), "-")));
        bean_itemList.add(new Bean_Item("交易单号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_integral.getOrdercode(), "-")));
        bean_itemList.add(new Bean_Item("描述", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_integral.getRemark(), "-")));
        String phone = (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_integral.getUserphone(), "-");
        String integral = (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_integral.getCoin(), "");
        bean_itemList.add(new Bean_Item("获取人/获取积分", new StringBuilder(phone).append("/").append(integral).append("分").toString()));
        bean_itemList.add(new Bean_Item("积分平台", getOrderBrandName(bean_transactionDetailed_integral.getBrand_id())));
    }

    private void showShareInfo(Bean_TransactionDetailed_Share bean_transactionDetailed_share) {
        if (bean_transactionDetailed_share == null)
            return;
        tv_name.setText("分润金额");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        tv_value.setText(new StringBuilder("￥").append(decimalFormat.format(new BigDecimal(bean_transactionDetailed_share.getAcqAmount()))));
        bean_itemList.add(new Bean_Item("创建时间", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_share.getCreateTime(), "-")));
        bean_itemList.add(new Bean_Item("交易单号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_share.getOrdercode(), "-")));
        String poundage = new BigDecimal((String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_share.getAcqrate(), "0.00")).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString().concat("%");
        bean_itemList.add(new Bean_Item("费率", poundage));
        bean_itemList.add(new Bean_Item("备注", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_share.getRemark(), "-")));
        bean_itemList.add(new Bean_Item("交易金额", getMoney(bean_transactionDetailed_share.getAmount())));
        String phone1 = (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_share.getOriphone(), "-");
        String poundage1 = new BigDecimal((String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_share.getOrirate(), "0.00")).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString().concat("%");
        bean_itemList.add(new Bean_Item("下级用户/费率", new StringBuilder(phone1).append("/").append(poundage1).toString()));
        String phone2 = (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_share.getAcqphone(), "-");
        String poundage2 = new BigDecimal((String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_share.getAcqrate(), "0.00")).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString().concat("%");
        bean_itemList.add(new Bean_Item("获取人/费率", new StringBuilder(phone2).append("/").append(poundage2).toString()));

    }

    private void showPayInfo(Bean_TransactionDetailed_Pay bean_transactionDetailed_pay) {
        if (bean_transactionDetailed_pay == null)
            return;
        tv_name.setText("支付金额");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        tv_value.setText(new StringBuilder("￥").append(decimalFormat.format(new BigDecimal(bean_transactionDetailed_pay.getAmount()))));
        bean_itemList.add(new Bean_Item("创建时间", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_pay.getCreateTime(), "-")));
        bean_itemList.add(new Bean_Item("交易单号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_pay.getOrdercode(), "-")));
        bean_itemList.add(new Bean_Item("商户单号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_pay.getThirdOrdercode(), "-")));
        bean_itemList.add(new Bean_Item("订单描述", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_pay.getDesc(), "-")));
        bean_itemList.add(new Bean_Item("手机号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_pay.getPhone(), "-")));
        String poundage = new BigDecimal((String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_pay.getRate(), "0.00")).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString().concat("%");
        String channel = (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_pay.getChannelname(), "-");
        bean_itemList.add(new Bean_Item("费率/通道", new StringBuilder(poundage).append("/").append(channel).toString()));
        bean_itemList.add(new Bean_Item("备注", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_pay.getRemark(), "-")));
        bean_itemList.add(new Bean_Item("总金额", getMoney(bean_transactionDetailed_pay.getAmount())));
        bean_itemList.add(new Bean_Item("实际金额", getMoney(bean_transactionDetailed_pay.getRealAmount())));
        bean_itemList.add(new Bean_Item("额外费用", getMoney(bean_transactionDetailed_pay.getExtraFee())));
        bean_itemList.add(new Bean_Item("订单类型", (String) getOrderType(bean_transactionDetailed_pay.getType()).getName()));
        bean_itemList.add(new Bean_Item("订单状态", getOrderStatus(bean_transactionDetailed_pay.getStatus())));
        bean_itemList.add(new Bean_Item("平台", getOrderBrandName(bean_transactionDetailed_pay.getBrandid())));
    }

    @NonNull
    private String getMoney(String money) {
        return money == null ? "-" : money.concat("元");
    }

    private void showRechargeInfo(Bean_TransactionDetailed_Recharge bean_transactionDetailed_recharge) {
        if (bean_transactionDetailed_recharge == null)
            return;
        tv_name.setText("充值金额");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        tv_value.setText(new StringBuilder("￥").append(decimalFormat.format(new BigDecimal(bean_transactionDetailed_recharge.getRealAmount()))));
        bean_itemList.add(new Bean_Item("创建时间", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_recharge.getCreateTime(), "-")));
        bean_itemList.add(new Bean_Item("交易单号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_recharge.getOrdercode(), "-")));
        bean_itemList.add(new Bean_Item("商户单号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_recharge.getThirdOrdercode(), "-")));
        bean_itemList.add(new Bean_Item("订单描述", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_recharge.getDesc(), "-")));
        bean_itemList.add(new Bean_Item("手机号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_recharge.getPhone(), "-")));
        String poundage = new BigDecimal((String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_recharge.getRate(), "0.00")).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString().concat("%");
        String channel = (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_recharge.getChannelname(), "-");
        bean_itemList.add(new Bean_Item("费率/通道", new StringBuilder(poundage).append("/").append(channel).toString()));
        bean_itemList.add(new Bean_Item("备注", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_recharge.getRemark(), "-")));
        bean_itemList.add(new Bean_Item("总金额", getMoney(bean_transactionDetailed_recharge.getAmount())));
        bean_itemList.add(new Bean_Item("实际金额", getMoney(bean_transactionDetailed_recharge.getRealAmount())));
        bean_itemList.add(new Bean_Item("额外费用", getMoney(bean_transactionDetailed_recharge.getExtraFee())));
        bean_itemList.add(new Bean_Item("订单类型", (String) getOrderType(bean_transactionDetailed_recharge.getType()).getName()));
        bean_itemList.add(new Bean_Item("订单状态", getOrderStatus(bean_transactionDetailed_recharge.getStatus())));
        bean_itemList.add(new Bean_Item("平台", getOrderBrandName(bean_transactionDetailed_recharge.getBrandid())));
    }


    private void showWithdrawalsInfo(Bean_TransactionDetailed_Withdrawals bean_transactionDetailed_withdrawals) {
        if (bean_transactionDetailed_withdrawals == null)
            return;
        tv_name.setText("提现金额");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        tv_value.setText(new StringBuilder("￥").append(decimalFormat.format(new BigDecimal(bean_transactionDetailed_withdrawals.getRealAmount()))));
        bean_itemList.add(new Bean_Item("创建时间", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_withdrawals.getCreateTime(), "-")));
        bean_itemList.add(new Bean_Item("交易单号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_withdrawals.getOrdercode(), "-")));
        bean_itemList.add(new Bean_Item("商户单号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_withdrawals.getThirdOrdercode(), "-")));
        bean_itemList.add(new Bean_Item("订单描述", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_withdrawals.getDesc(), "-")));
        bean_itemList.add(new Bean_Item("手机号", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_withdrawals.getPhone(), "-")));
        String poundage = new BigDecimal((String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_withdrawals.getRate(), "0.00")).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString().concat("%");
        String channel = (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_withdrawals.getChannelname(), "-");
        bean_itemList.add(new Bean_Item("费率/通道", new StringBuilder(poundage).append("/").append(channel).toString()));
        bean_itemList.add(new Bean_Item("备注", (String) Util_Empty.isEmptyToReplace(bean_transactionDetailed_withdrawals.getRemark(), "-")));
        bean_itemList.add(new Bean_Item("总金额", getMoney(bean_transactionDetailed_withdrawals.getAmount())));
        bean_itemList.add(new Bean_Item("实际金额", getMoney(bean_transactionDetailed_withdrawals.getRealAmount())));
        bean_itemList.add(new Bean_Item("额外费用", getMoney(bean_transactionDetailed_withdrawals.getExtraFee())));
        bean_itemList.add(new Bean_Item("订单类型", (String) getOrderType(bean_transactionDetailed_withdrawals.getType()).getName()));
        bean_itemList.add(new Bean_Item("订单状态", getOrderStatus(bean_transactionDetailed_withdrawals.getStatus())));
        bean_itemList.add(new Bean_Item("平台", getOrderBrandName(bean_transactionDetailed_withdrawals.getBrandid())));
    }

    @Override
    public void initAdapter() {
        rv_detailed.setAdapter(rva_detailed = new RecyclerView.Adapter() {
            int fontSize = (int) (Framework_Activity.screenWidth / 28);

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_right;
                AutoRelativeLayout rl_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    rl_bg = (AutoRelativeLayout) itemView.findViewById(R.id.rl_bg);
                    AutoLinearLayout.LayoutParams lp = (AutoLinearLayout.LayoutParams) rl_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo = lp.getAutoLayoutInfo();
                    autoLayoutInfo.addAttr(new MinHeightAttr(0, 0, 0));
                    autoLayoutInfo.addAttr(new PaddingBottomAttr(10, 0, 0));
                    autoLayoutInfo.addAttr(new PaddingTopAttr(10, 0, 0));
                    ViewGroup.LayoutParams lp2 = rl_bg.getLayoutParams();
                    rl_bg.setLayoutParams(lp2);

                    itemView.findViewById(R.id.ll_bg).setBackgroundColor(Color.argb(0, 0, 0, 0));

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_3));


                    tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                    tv_right.setVisibility(View.VISIBLE);
                    tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    tv_right.setTextColor(getResources().getColor(R.color.primaryColorOff));
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
            }


            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                Bean_Item bean = bean_itemList.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_right.setText(bean.getValue());
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

    public static Bean_Item getOrderType(String orderType) {
        int imgId=0;
        String type = "-";
        if (orderType != null)
            switch (orderType) {
                case "0":
                    type = "订单充值";
                    break;
                case "1":
                    type = "订单支付";
                    break;
                case "2":
                    type = "订单提现";
                    break;
                case "3":
                    type = "订单退款";
                    break;
                default:
                    type = orderType;
                    break;
            }
        return new Bean_Item(type,imgId);
    }

    public static String getOrderStatus(String orderStatus) {
        String status = "-";
        if (orderStatus != null)
            switch (orderStatus) {
                case "0":
                    status = "待完成";
                    break;
                case "1":
                    status = "已成功";
                    break;
                case "2":
                    status = "已取消";
                    break;
                case "3":
                    status = "待处理";
                    break;
                case "4":
                    status = "待结算";
                    break;
                default:
                    status = orderStatus;
                    break;
            }
        return status;
    }

    private String getOrderBrandName(String brandId) {
        return getResources().getString(R.string.app_name);
    }

}
