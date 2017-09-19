package com.ceq.app.main.activity;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;

/**
 * Created by ceq on 2017/5/13.
 */

public class Act_Settings_Income extends Framework_Activity {
    TextView tv_income,tv_transactionCount;
    int dark, white;
    RecyclerView rv_money;
    List<Bean_Item> moneyList = new ArrayList<>();
    List<Bean_Item> merchantList = new ArrayList<>();
    RecyclerView.Adapter rva_money;
    LinearLayout ll_rakeBack;
    ImageView iv_back;
    View_XRefreshLayout xrv;
    RelativeLayout rl_1, rl_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dark = getResources().getColor(R.color.dark);
        white = getResources().getColor(R.color.text_color_1);
        init(R.layout.act_settings_income, dark, Util_Screen.Enum_BarFontColor.White);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "当日交易", View.VISIBLE).setTextColor(white);
        findViewById(R.id.icd_title).setBackgroundColor(dark);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        iv_back.setColorFilter(new LightingColorFilter(Color.BLACK, white));
        tv_income = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "收益分析", View.GONE);

        tv_income.setTextColor(white);

        tv_transactionCount= (TextView) findViewById(R.id.tv_transactionCount);

        //功能
        rv_money = (RecyclerView) findViewById(R.id.rv_money);
        rv_money.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        ll_rakeBack = (LinearLayout) findViewById(R.id.ll_rakeBack);

        xrv = (View_XRefreshLayout) findViewById(R.id.xrv);

        rl_1 = (RelativeLayout) findViewById(R.id.rl_1);
        rl_2 = (RelativeLayout) findViewById(R.id.rl_2);
    }

    @Override
    public void initData() {
        moneyList.add(new Bean_Item("提现总额", "0.00", false));
        moneyList.add(new Bean_Item("", "", true));
        moneyList.add(new Bean_Item("充值总额", "0.00", false));

        getIncomeData();
    }

    private void getIncomeData() {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_INCOME_BASE_INFO_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("user_id", Abstract_App.bean_userInfo.getId());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_request, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        JSONObject jsonObject = JSON.parseObject(parseObject(data).getString(Http_Key_Data));
                        moneyList.clear();
                        moneyList.add(new Bean_Item("提现总额",jsonObject.getString("withdrawSum")));
                        moneyList.add(new Bean_Item("",jsonObject.getString("")));
                        moneyList.add(new Bean_Item("充值总额",jsonObject.getString("RechargeSum")));
                        rva_money.notifyDataSetChanged();
                        tv_transactionCount.setText("笔数:".concat(jsonObject.getString("transactionCount")));
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }

    @Override
    public void initAdapter() {
        rv_money.setAdapter(rva_money = new RecyclerView.Adapter() {
            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_value;
                LinearLayout ll_bg;


                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    tv_name.setTextColor(white);

                    tv_value = (TextView) itemView.findViewById(R.id.tv_value);
                    tv_value.setVisibility(View.VISIBLE);
                    tv_value.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    tv_value.setTextColor(white);


                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = moneyList.get(position);
                Util_Log.logTest("xxxx",bean);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_value.setText(bean.getValue());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return moneyList.size();
            }
        });
        xrv.setOnRefreshHttpListener(0, merchantList, rva_money, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                xrv.onHttpEnd();
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);
        Util_View.viewOnClick(this, rl_1, rl_2);
    }


    @Override
    public void onClick(View v) {
        if (v == iv_back) {
            onBackPressed();
        } else if (v == rl_1) {
            Util_Toast.toast(Constants_International.framework_function_no_open);
        } else if (v == rl_2) {
            Util_Toast.toast(Constants_International.framework_function_no_open);
        }
    }
}
