package com.ceq.app.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_BalanceInfo;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.activity.Act_MyPurse_PurseDetailed.getDefaultBankCardInfo;

/**
 * Created by ceq on 2017/5/6.
 */

public class Act_Home_PurseBalance extends Framework_Activity {
    TextView tv_withdrawals, tv_balance, tv_totalIncome, tv_monthIncome;
    ImageView iv_back;
    ArcProgress arc_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_home_pursebalance);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "余额", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        arc_progress = (ArcProgress) findViewById(R.id.arc_progress);
        arc_progress.setMax(Integer.MAX_VALUE);

        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_totalIncome = (TextView) findViewById(R.id.tv_totalIncome);
        tv_monthIncome = (TextView) findViewById(R.id.tv_monthIncome);
        //提交按钮
        tv_withdrawals = util_init.initTextView(R.id.icd_withdrawals, R.id.tv_button, null, "提现", View.VISIBLE);
    }

    @Override
    public void initData() {
        getBalanceData();
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, tv_withdrawals);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v.getId() == tv_withdrawals.getId()) {
            getDefaultBankCardInfo(tv_withdrawals,getActivity());
        }

    }

    public void getBalanceData() {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_BALANCE_QUERY_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_request, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Bean_BalanceInfo bean = parseObject(parseObject(data).getString(Http_Key_Data), Bean_BalanceInfo.class);

                        tv_balance.setText((CharSequence) Util_Empty.isEmptyToReplace(bean.getRebatebalance(), "0.00"));
                        tv_totalIncome.setText((CharSequence) Util_Empty.isEmptyToReplace(bean.getAllsum(), "0.00"));
                        tv_monthIncome.setText((CharSequence) Util_Empty.isEmptyToReplace(bean.getMonsum(), "0.00"));
                        if (bean.getDaysum() == null)
                            arc_progress.setProgress(0);
                        else{
                            arc_progress.setProgress( Integer.parseInt(bean.getDaysum()));
                        }
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
