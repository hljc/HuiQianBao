package com.ceq.app.main.activity;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_ChannelExtraInfo;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;
import static com.ceq.app.main.methods.Method_Static.setImageMark;
import static com.ceq.app.main.methods.Method_Static.setOrderDescription;
import static com.ceq.app.main.methods.Method_Static.submitToCollection;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class Act_Collection_Detailed extends Framework_Activity {
    ImageView iv_back;
    View_XRefreshLayout xrl;
    List<Bean_Channel> bean_channelList = new ArrayList<>();
    Bean_ChannelExtraInfo bean_channelExtraInfo;
    RecyclerView rv_channel;
    RecyclerView.Adapter rva_channel;
    TextView tv_money2, tv_money, tv_remark;
    public static final String Extra_Bean_ChannelExtraInfo = "Extra_Bean_ChannelExtraInfo";
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    String money;

    {
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean_channelExtraInfo = (Bean_ChannelExtraInfo) getIntent().getSerializableExtra(Extra_Bean_ChannelExtraInfo);
        init(R.layout.act_collection_detailed);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "选择收款通道", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        xrl = (View_XRefreshLayout) findViewById(R.id.xrl);

        rv_channel = (RecyclerView) findViewById(R.id.rv_channel);
        rv_channel.setLayoutManager(new LinearLayoutManager(getActivity()));
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_money2 = (TextView) findViewById(R.id.tv_money2);
        tv_remark = (TextView) findViewById(R.id.tv_remark);

        money = bean_channelExtraInfo.getMoney();
        money = decimalFormat.format(new BigDecimal(money));
        tv_money.setText("￥".concat(money));

        tv_remark.setText(bean_channelExtraInfo.getOrderDescription());
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_money2.setText(money.contains(".00") ? money.substring(0, money.indexOf(".00")) : money);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_channel.setAdapter(rva_channel = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_limitSingle, tv_settlement, tv_limitToday, tv_rate;
                LinearLayout ll_bg;
                ImageView iv_forward;
                SimpleDraweeView sdv_img;
                View v_mark;
                int forwardColor = getResources().getColor(R.color.text_color_3);

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_limitSingle = (TextView) itemView.findViewById(R.id.tv_limitSingle);
                    tv_settlement = (TextView) itemView.findViewById(R.id.tv_settlement);
                    tv_limitToday = (TextView) itemView.findViewById(R.id.tv_limitToday);
                    tv_rate = (TextView) itemView.findViewById(R.id.tv_rate);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    iv_forward = (ImageView) itemView.findViewById(R.id.iv_forward);
                    iv_forward.setColorFilter(new LightingColorFilter(Color.BLACK, forwardColor));
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    v_mark = itemView.findViewById(R.id.v_mark);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_channel_detailed, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                final MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Channel bean = bean_channelList.get(position);
                if (bean == null) return;
                String name = (String) Util_Empty.isEmptyToReplace(bean.getName(), "");
                StringBuilder sb = new StringBuilder();
                mvh.tv_name.setText(sb.append(name));

                mvh.tv_rate.setText("单笔费率：".concat(Util_Empty.isEmpty(bean.getRate()) ? "-" : new BigDecimal(bean.getRate()).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString().concat("%")));
                mvh.tv_settlement.setText("结算方式：".concat(bean.getChannelParams()));
                mvh.tv_limitSingle.setText(new StringBuilder("单笔限额：").append(Util_Empty.isEmptyToReplace(bean.getSingleMinLimit(), "0")).append("-").append(Util_Empty.isEmptyToReplace(bean.getSingleMaxLimit(), "0")));
                mvh.tv_limitToday.setText(new StringBuilder("当日限额：").append(decimalFormat.format(new BigDecimal((String) Util_Empty.isEmptyToReplace(bean.getEveryDayMaxLimit(), "0")))));

                mvh.sdv_img.setImageURI(bean.getLog());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bean_channelExtraInfo.setPayChannel(bean.getChannelTag());
                        setOrderDescription(bean, bean_channelExtraInfo);
                        submitToCollection(mvh.ll_bg, tv_money2, null, bean_channelList, bean_channelExtraInfo);
                    }
                });
                setImageMark(mvh.v_mark, bean);
                setImageLayoutParams(mvh.v_mark, 80, 0);
            }

            @Override
            public int getItemCount() {
                return bean_channelList.size();
            }
        });
        xrl.setOnRefreshHttpListener(false, 0, bean_channelList, rva_channel, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, final List list, RecyclerView.Adapter adapter) {
                Act_Mine_Rate.getRateData(getActivity(), view_xRefreshStatusView, true, new Util_Runnable.Util_ArgsRunnable() {
                    @Override
                    public void run(Object... data) {
                        List<Bean_Channel> beans = (List<Bean_Channel>) data[0];
                        for (int i = 0, size = beans.size(); i < size; i++) {
                            Bean_Channel bean_channel = beans.get(i);
                            if (bean_channel.getSubName().equals(bean_channelExtraInfo.getChannelPlatform()))
                                list.add(bean_channel);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId())
            onBackPressed();
    }
}
