package com.ceq.app.main.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ceq.app.core.activity.Act_Main_Web;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_Dialog;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Input;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.zhy.autolayout.AutoLinearLayout;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;
import static com.ceq.app_core.constants.Constants_Common.expression_bank_num;
import static com.ceq.app_core.constants.Constants_Common.expression_mobile;
import static com.ceq.app_core.framework.Framework_Web.Extra_String_Url;


/**
 * Created by ceq on 2017/4/15.
 */

public class Act_BankCard_Add extends Framework_Activity {
    EditText et_bankNum, et_bankMobile, et_creditCardSafeCode, et_creditCardExpireTime;
    //EditText et_bankUserName, et_bankIdentify;
    TextView tv_commit, tv_supportBank;
    ImageView iv_back;
    AutoLinearLayout ll_rechargeCard, ll_withdrawalCard;
    ImageView iv_rechargeCard, iv_withdrawalsCard;
    boolean T_RechargeCard$F_WithdrawalsCard = true;
    static int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_addbankcard);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "添加银行卡", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        tv_supportBank = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "支持银行", View.VISIBLE);

        //银行卡号
        et_bankNum = util_init.initEditText(R.id.icd_bankNum, R.id.et_input, "请输入银行卡号", null, InputType.TYPE_CLASS_NUMBER, 23, View.VISIBLE);
        util_init.initTextView(R.id.icd_bankNum, R.id.tv_left, null, "银行卡", View.VISIBLE);
        util_init.initView(R.id.icd_bankNum, R.id.v_split, View.VISIBLE);
     /*   //银行用户名
        et_bankUserName = util_init.initEditText(R.id.icd_bankUserName, R.id.et_input, "请输入银行用户名", "陈恩强", InputType.TYPE_CLASS_TEXT, LENGTH_USER, View.GONE);
        util_init.initTextView(R.id.icd_bankUserName, R.id.tv_left, null, "用户名", View.GONE);
        util_init.initView(R.id.icd_bankUserName, R.id.v_split, View.GONE);
        //身份证
        et_bankIdentify = util_init.initEditText(R.id.icd_bankIdentify, R.id.et_input, "请输入身份证", "342623199412052519", InputType.TYPE_CLASS_TEXT, 18, View.GONE);
        util_init.initTextView(R.id.icd_bankIdentify, R.id.tv_left, null, "身份证", View.GONE);
        util_init.initView(R.id.icd_bankIdentify, R.id.v_split, View.GONE);*/
        //预留手机号
        et_bankMobile = util_init.initEditText(R.id.icd_bankMobile, R.id.et_input, "请输入预留手机号", null, InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
        util_init.initTextView(R.id.icd_bankMobile, R.id.tv_left, null, "手机号", View.VISIBLE);
        util_init.initView(R.id.icd_bankMobile, R.id.v_split, View.VISIBLE);

        et_creditCardSafeCode = util_init.initEditText(R.id.icd_creditCardSafeCode, R.id.et_input, "信用卡反面查看安全码后三位", null, InputType.TYPE_CLASS_NUMBER, 3, View.VISIBLE);
        util_init.initTextView(R.id.icd_creditCardSafeCode, R.id.tv_left, null, "安全码", View.VISIBLE);
        util_init.initView(R.id.icd_creditCardSafeCode, R.id.v_split, View.VISIBLE);

        et_creditCardExpireTime = util_init.initEditText(R.id.icd_creditCardExpireTime, R.id.et_input, "信用卡正面查找日期,如:01/20", null, InputType.TYPE_CLASS_NUMBER, 5, View.VISIBLE);
        util_init.initTextView(R.id.icd_creditCardExpireTime, R.id.tv_left, null, "有效期", View.VISIBLE);
        util_init.initView(R.id.icd_creditCardExpireTime, R.id.v_split, View.VISIBLE);

        //提交按钮
        tv_commit = util_init.initTextView(R.id.icd_commit, R.id.tv_button, null, "提交", View.VISIBLE);

        ll_rechargeCard = (AutoLinearLayout) findViewById(R.id.ll_rechargeCard);
        ll_withdrawalCard = (AutoLinearLayout) findViewById(R.id.ll_withdrawalCard);

        iv_rechargeCard = (ImageView) findViewById(R.id.iv_rechargeCard);
        iv_withdrawalsCard = (ImageView) findViewById(R.id.iv_withdrawalsCard);
        selectCardType();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, tv_commit, tv_supportBank);
        Util_View.viewOnClick(this, ll_rechargeCard, ll_withdrawalCard);
        et_bankNum.addTextChangedListener(new Util_Input.SeparatorTextWatcher(et_bankNum, ' ', 5, 23));

        et_creditCardExpireTime.addTextChangedListener(new Util_Input.SeparatorTextWatcher(et_creditCardExpireTime, '/', 3, 5));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId())
            onBackPressed();
        else if (v.getId() == tv_supportBank.getId())
            startActivity(new Intent(getActivity(), Act_Main_Web.class).putExtra(Extra_String_Url, "http://1.xinli2017.applinzi.com/%E5%B9%B3%E5%8F%B0%E5%BA%94%E7%94%A8/%E9%93%B6%E8%A1%8C%E5%8D%A1%E6%94%AF%E6%8C%81%E5%88%97%E8%A1%A8.html"));
        else if (v.getId() == tv_commit.getId()) {
            String bankNum = et_bankNum.getText().toString();
            if (bankNum.contains(" "))
                bankNum = bankNum.replace(" ", "");
            if (Util_Empty.isEmptyAndMatchToToast(bankNum, et_bankNum.getHint().toString(), expression_bank_num, Constant_International.error_expression_bank_num))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_bankMobile.getText().toString(), et_bankMobile.getHint().toString(), expression_mobile, Constant_International.error_expression_mobile))
                return;
            String creditCardExpireTime = et_creditCardExpireTime.getText().toString();
            if (!Util_Empty.isEmpty(creditCardExpireTime)) {
                if (!creditCardExpireTime.matches("(0[1-9]|1[0-2])/[1-9]\\d")) {
                    Util_Toast.toast(et_creditCardExpireTime.getHint());
                    return;
                }
            }
            seconds = 100;
            tv_commit.post(runnable);
            getCertificationInfo(tv_commit, bankNum);
        } else if (ll_rechargeCard == v) {
            T_RechargeCard$F_WithdrawalsCard = !T_RechargeCard$F_WithdrawalsCard;
            selectCardType();
        } else if (ll_withdrawalCard == v) {
            T_RechargeCard$F_WithdrawalsCard = !T_RechargeCard$F_WithdrawalsCard;
            selectCardType();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tv_commit.setEnabled(seconds == 0);
            if (seconds == 0) {
                tv_commit.setText("提交");
                tv_commit.removeCallbacks(this);
            } else {
                tv_commit.setText(new StringBuilder("提交(").append(seconds).append("s)"));
                tv_commit.postDelayed(this, 1000);
                --seconds;
            }
        }
    };

    private void selectCardType() {
        iv_rechargeCard.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(T_RechargeCard$F_WithdrawalsCard ? R.color.primaryColorOff : R.color.text_color_3)));
        iv_withdrawalsCard.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(!T_RechargeCard$F_WithdrawalsCard ? R.color.primaryColorOff : R.color.text_color_3)));
    }

    private void getCertificationInfo(final View viewToken, final String bankNum) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_CERTIFICATION_QUERY_INFO_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_verification, false, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        JSONObject jsonObject = JSON.parseObject(parseObject(data).getString(Http_Key_Data));
                        if (jsonObject.getString("realname") == null || !Abstract_App.bean_userInfo.getRealnameStatus().equals("1"))
                            Util_Dialog.showDefaultDialog(viewToken, "你还未通过实名认证，是否前往实名？", "确定", "取消", new Util_Dialog.DialogListener() {

                                @Override
                                public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                                    framework_dialog.dismiss();
                                }

                                @Override
                                public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                                    framework_dialog.startActivity(new Intent(framework_dialog, Act_Mine_Certification.class));
                                    framework_dialog.dismiss();
                                }

                                @Override
                                public void onDismissListener() {

                                }
                            });
                        else
                            commitBankCardInfo(
                                    bankNum,
                                    jsonObject.getString("realname"),
                                    jsonObject.getString("idcard"),
                                    et_bankMobile.getText().toString(),
                                    et_creditCardExpireTime.getText().toString(),
                                    et_creditCardSafeCode.getText().toString());
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void commitBankCardInfo(final String bankcard, final String realname, final String idcard, final String mobile, final String creditCardExpireTime, final String creditCardSafeCode) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_ADD_BANK_CARD_INFO_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("realname", realname);
                httpParams.put("bankcard", bankcard);
                httpParams.put("idcard", idcard);
                httpParams.put("mobile", mobile);
                httpParams.put("type", T_RechargeCard$F_WithdrawalsCard ? 0 : 2);
                httpParams.put("securitycode", creditCardSafeCode);
                if (creditCardExpireTime.length() != 0) {
                    String[] date = creditCardExpireTime.split("/");
                    httpParams.put("expiretime", date[1].concat(date[0]));
                }

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_commit, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        startActivity(new Intent(getActivity(), Act_Mine_BankCard.class));
                        finish();
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
