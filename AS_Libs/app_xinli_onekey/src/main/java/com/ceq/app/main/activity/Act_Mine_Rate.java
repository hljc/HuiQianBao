package com.ceq.app.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SpanUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_Production;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.activity.Act_Main_Module.YILIAN;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.methods.Method_Static.getProductionList;
import static com.ceq.app.main.methods.Method_Static.getUserGradeInfo;
import static com.ceq.app.main.methods.Method_Static.getUserGradeRole;
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;

/**
 * Created by ceq on 2017/4/20.
 */

public class Act_Mine_Rate extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_rate;
    List<Bean_Channel> bean_channelList = new ArrayList<>();
    RecyclerView.Adapter rva_rate, rva_production;
    TextView tv_channelName, tv_channelWay, tv_channelRate, tv_channelLimit;
    TextView tv_rateInfo, tv_productionCount;
    View_XRefreshLayout xrl;
    public static Map<String, String> channelLogoMap = new HashMap<>();
    RecyclerView rv_production;
    List<Bean_Production> bean_productionList = new ArrayList<>();
    int primaryColor;
    String productionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_mine_rate);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "费率详情", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_rate = (RecyclerView) findViewById(R.id.rv_rate);
        rv_rate.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_rateInfo = (TextView) findViewById(R.id.tv_rateInfo);
        tv_productionCount = (TextView) findViewById(R.id.tv_productionCount);

        tv_channelName = (TextView) findViewById(R.id.tv_channelName);
        tv_channelWay = (TextView) findViewById(R.id.tv_channelWay);
        tv_channelRate = (TextView) findViewById(R.id.tv_channelRate);
        tv_channelLimit = (TextView) findViewById(R.id.tv_channelLimit);
        tv_channelName.setText("通道名称");
        tv_channelWay.setText("结算方式");
        tv_channelRate.setText("费率");
        tv_channelLimit.setText("单笔限额");

        xrl = (View_XRefreshLayout) findViewById(R.id.xrl);

        rv_production = (RecyclerView) findViewById(R.id.rv_production);
        rv_production.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        primaryColor = getResources().getColor(R.color.primaryColorOff);
        String productionGrade = Abstract_App.bean_userInfo.getGrade();
        Bean_Production bean_production = getUserGradeInfo(productionGrade);
        productionId = bean_production == null ? "0" : bean_production.getId();
        tv_rateInfo.setText(new SpanUtils().append("当前等级:").append(getUserGradeRole(productionGrade)).setForegroundColor(primaryColor).append("  注意:产品多时可进行左右滑动查看更多费率！").create());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_production.setAdapter(rva_production = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                SimpleDraweeView sdv_img;
                TextView tv_name;
                LinearLayout ll_bg;
                int itemWidth = (int) (screenWidth / 3);

                public MyViewHolder(View itemView) {
                    super(itemView);
                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                    (sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img)).setVisibility(View.VISIBLE);
                    setImageLayoutParams(sdv_img, 180, 0);

                    (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_4));

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);

                    RelativeLayout rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);
                    ViewGroup.LayoutParams lp3 = rl_bg.getLayoutParams();
                    lp3.width = itemWidth;
                    rl_bg.setLayoutParams(lp3);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Production bean = bean_productionList.get(position);
                mvh.tv_name.setTextColor(bean.isChecked() ? primaryColor : getResources().getColor(R.color.text_color_3));
                mvh.tv_name.setText(bean.getName().toString());
                switch (bean.getGrade()) {
                    case "0":
                        mvh.sdv_img.setImageResource(R.mipmap.scale0);
                        break;
                    case "1":
                        mvh.sdv_img.setImageResource(R.mipmap.scale1);
                        break;
                    case "2":
                        mvh.sdv_img.setImageResource(R.mipmap.scale2);
                        break;
                    case "3":
                        mvh.sdv_img.setImageResource(R.mipmap.scale3);
                        break;
                    case "4":
                        mvh.sdv_img.setImageResource(R.mipmap.scale4);
                        break;
                    case "5":
                        mvh.sdv_img.setImageResource(R.mipmap.scale5);
                        break;
                    case "6":
                        mvh.sdv_img.setImageResource(R.mipmap.scale6);
                        break;
                    case "7":
                        mvh.sdv_img.setImageResource(R.mipmap.scale7);
                        break;
                    case "8":
                        mvh.sdv_img.setImageResource(R.mipmap.scale8);
                        break;
                    case "9":
                        mvh.sdv_img.setImageResource(R.mipmap.scale9);
                        break;
                    case "10":
                        mvh.sdv_img.setImageResource(R.mipmap.scale10);
                        break;
                }
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0, size = bean_productionList.size(); i < size; i++) {
                            bean_productionList.get(i).setChecked(false);
                        }
                        bean_productionList.get(position).setChecked(true);
                        rva_production.notifyDataSetChanged();
                        switch (bean.getGrade()) {
                            case "0":
                                productionId = "0";
                                break;
                            default:
                                productionId = bean.getId();
                                break;
                        }
                        xrl.startRefresh();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_productionList.size();
            }
        });

        rv_rate.setAdapter(rva_rate = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_channelName, tv_channelWay, tv_channelRate, tv_channelLimit;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_channelName = (TextView) itemView.findViewById(R.id.tv_channelName);
                    tv_channelWay = (TextView) itemView.findViewById(R.id.tv_channelWay);
                    tv_channelRate = (TextView) itemView.findViewById(R.id.tv_channelRate);
                    tv_channelLimit = (TextView) itemView.findViewById(R.id.tv_channelLimit);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_rv_channel, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Channel bean = bean_channelList.get(position);
                mvh.tv_channelName.setText(Util_Empty.isEmptyToReplace(bean.getName(), "-").toString());
                mvh.tv_channelWay.setText(bean.getChannelParams());
                mvh.tv_channelRate.setText(Util_Empty.isEmpty(bean.getRate()) ? "-" : new BigDecimal(bean.getRate()).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString().concat("%"));
                mvh.tv_channelLimit.setText(new StringBuilder().append(Util_Empty.isEmptyToReplace(bean.getSingleMinLimit(), "0")).append("-").append(Util_Empty.isEmptyToReplace(bean.getSingleMaxLimit(), "0")));
            }

            @Override
            public int getItemCount() {
                return bean_channelList.size();
            }
        });

        xrl.setOnRefreshHttpListener(false, 0, bean_channelList, rva_rate, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                if (!productionId.equals("0"))
                    getRateDataByProduction(getActivity(), view_xRefreshStatusView, productionId, true, new Util_Runnable.Util_ArgsRunnable() {
                        @Override
                        public void run(Object... data) {
                            bean_channelList.addAll((List<Bean_Channel>) data[0]);
                        }
                    });
                else {
                    getRateData(getActivity(), view_xRefreshStatusView, true, new Util_Runnable.Util_ArgsRunnable() {
                        @Override
                        public void run(Object... data) {
                            bean_channelList.addAll((List<Bean_Channel>) data[0]);
                        }
                    });
                }
            }
        });

        getProductionList(getActivity(), null, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                bean_productionList.clear();
                bean_productionList.addAll(JSONObject.parseArray(parseObject((String) data[0]).getString(Http_Key_Data), Bean_Production.class));
                Bean_Production bean = new Bean_Production();
                bean.setGrade("0");
                bean.setName("当前费率");
                bean_productionList.add(bean);
                int size = bean_productionList.size();
                int position = 0;
                for (int i = 0; i < size; i++) {
                    if (bean_productionList.get(i).getGrade().equals(Abstract_App.bean_userInfo.getGrade())) {
                        bean_productionList.get(position = i).setChecked(true);
                        break;
                    }
                }
                tv_productionCount.setText(new SpanUtils().append("当前总共").append(String.valueOf(size - 1)).setForegroundColor(primaryColor).append("个产品等级").create());
                rva_production.notifyDataSetChanged();
                rv_production.scrollToPosition(position);
            }
        });

      /*  getMyMerchantInfo(getActivity(), null, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                Bean_MyMerchantInfo bean_myMerchantInfo = JSON.parseObject(JSON.parseObject((String) data[0]).getString(Http_Key_Data), Bean_MyMerchantInfo.class);
                bean_productionPeopleInfoList.clear();
                bean_productionPeopleInfoList.addAll(bean_myMerchantInfo.getThirdLevelDistribution());
                int size = bean_productionPeopleInfoList.size();
                for (int i = 0; i < size; i++) {
                    if (bean_productionPeopleInfoList.get(i).getGrade().equals(Abstract_App.bean_userInfo.getGrade())) {
                        bean_productionPeopleInfoList.get(i).setChecked(true);
                        break;
                    }
                }
                tv_productionCount.setText(new SpanUtils().append("当前总共").append(String.valueOf(size)).setForegroundColor(primaryColor).append("个产品等级").create());
                rva_production.notifyDataSetChanged();
            }
        });*/
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


    public static void getRateDataByProduction(final Activity activity, View_XRefreshStatusView view_xRefreshStatusView, final String productionId, boolean isAutoDismiss, final Util_Runnable.Util_ArgsRunnable channelDataRunnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_PRODUCTION_GRADE_RATE_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("thirdLevelId", productionId);

            }
        }, new Util_Http.HttpDealStringListener(activity, view_xRefreshStatusView, "正在获取通道信息，请稍后！", isAutoDismiss, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        channelDataFilter(data, activity, channelDataRunnable);
                        break;
                    default:
                        Util_Http.dismiss();
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void channelDataFilter(String data, Activity activity, Util_Runnable.Util_ArgsRunnable channelDataRunnable) {
        List<Bean_Channel> channelList = JSON.parseArray(parseObject(data).getString(Http_Key_Data), Bean_Channel.class);
        List<Bean_Channel> tempChannelList = new ArrayList<>();
        Util_Runnable.Util_TypeRunnable<List<Bean_Channel>> filter = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getChannelFilterRunnable();
        if (!(activity instanceof Act_Mine_Rate))//判断是否是费率详情
            for (int i = 0, size = channelList.size(); i < size; i++) {
                Bean_Channel bean = channelList.get(i);
                channelLogoMap.put(bean.getName(), bean.getLog());
                if (bean.getChannelNo().equals("3") || bean.getChannelNo().equals("4"))//3.过滤公众号4.过滤特殊通道
                    tempChannelList.add(bean);
            }
        else
            for (int i = 0, size = channelList.size(); i < size; i++) {
                Bean_Channel bean = channelList.get(i);
                channelLogoMap.put(bean.getName(), bean.getLog());
                if (bean.getChannelNo().equals("4"))//4.过滤特殊通道
                    tempChannelList.add(bean);
            }
        channelList.removeAll(tempChannelList);
        if (filter != null)//过滤通道
            filter.run(channelList);
        if (channelDataRunnable != null)//通道数据事件回调
            channelDataRunnable.run(channelList);
    }

    public static void getYiLianChannelData(final Activity activity, boolean isAutoDismiss, final Util_Runnable.Util_TypeRunnable<Bean_Channel> yiLianRunnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_RATE_INFO_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("user_id", Abstract_App.bean_userInfo.getId());

            }
        }, new Util_Http.HttpDealStringListener(activity, "正在获取通道信息", isAutoDismiss, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        List<Bean_Channel> channelList = JSON.parseArray(parseObject(data).getString(Http_Key_Data), Bean_Channel.class);
                        for (int i = 0, size = channelList.size(); i < size; i++) {
                            Bean_Channel bean = channelList.get(i);
                            if (yiLianRunnable != null && bean.getChannelTag().equals(YILIAN)) {
                                yiLianRunnable.run(bean);
                                break;
                            }
                        }

                        break;
                    default:
                        Util_Http.dismiss();
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void getRateData(final Activity activity, View_XRefreshStatusView view_xRefreshStatusView, boolean isAutoDismiss, final Util_Runnable.Util_ArgsRunnable channelDataRunnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_RATE_INFO_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("user_id", Abstract_App.bean_userInfo.getId());

            }
        }, new Util_Http.HttpDealStringListener(activity, view_xRefreshStatusView, "正在获取通道信息，请稍后！", isAutoDismiss, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        channelDataFilter(data, activity, channelDataRunnable);
                        break;
                    default:
                        Util_Http.dismiss();
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }
}
