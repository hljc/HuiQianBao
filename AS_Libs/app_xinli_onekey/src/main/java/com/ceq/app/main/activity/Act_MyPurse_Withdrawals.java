package com.ceq.app.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.KeyboardUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_BankCardInfo;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_PersonalInfo;
import com.ceq.app.main.bean.Bean_UserAccount;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lnyp.pswkeyboard.Act_PayKeyboard;
import com.lnyp.pswkeyboard.widget.PopEnterPassword;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.verificationPayCode;
import static com.ceq.app.main.activity.Act_Mine_Rate.getYiLianChannelData;
import static com.ceq.app.main.activity.Act_MyPurse_PurseDetailed.getDefaultBankCardInfo;
import static com.ceq.app.main.methods.Method_Static.getPersonalInfoData;
import static com.ceq.app.main.methods.Method_Static.obtainUserPurseBaseInfo;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;

/**
 * Created by ceq on 2017/4/15.
 */

public class Act_MyPurse_Withdrawals extends Framework_Activity {
    ImageView iv_back;
    TextView tv_detailed, tv_balance, tv_allWithdrawals;
    TextView tv_bankCarCompany, tv_bankCardType, tv_bankCardNum, tv_withdrawals;
    EditText et_withdrawalsMoney, et_collectionSpec;
    public static final String Extra_Bean_MyPurse_Withdrawals = "Extra_Bean_MyPurse_Withdrawals";
    CardView cv_bg;
    TextView tv_channelName, tv_channelWay, tv_channelRate;
    RelativeLayout rl_channelBg;
    SimpleDraweeView sdv_img;

    Bean_Channel bean_channel = new Bean_Channel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_mypurse_withdrawals);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "提现", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        tv_detailed = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "明细", View.VISIBLE);

        //银行卡信息
        tv_bankCarCompany = (TextView) findViewById(R.id.tv_bankCarCompany);
        tv_bankCardType = (TextView) findViewById(R.id.tv_bankCardType);
        tv_bankCardNum = (TextView) findViewById(R.id.tv_bankCardNum);
        cv_bg = (CardView) findViewById(R.id.cv_bg);
        //提现、余额
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_allWithdrawals = (TextView) findViewById(R.id.tv_allWithdrawals);

        et_withdrawalsMoney = util_init.initEditText(R.id.icd_withdrawalsMoney, R.id.et_input, "提现金额", null, InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
        util_init.initImageView(R.id.icd_withdrawalsMoney, R.id.iv_leftImg, R.mipmap.icon_money, View.VISIBLE);
        //收款说明
        et_collectionSpec = util_init.initEditText(R.id.icd_collectionSpec, R.id.et_input, "收款说明", null, InputType.TYPE_CLASS_TEXT, LENGTH_USER, View.VISIBLE);
        util_init.initImageView(R.id.icd_collectionSpec, R.id.iv_leftImg, R.mipmap.icon_desc, View.VISIBLE);

        //快捷通道
        tv_channelName = (TextView) findViewById(R.id.tv_channelName);
        tv_channelWay = (TextView) findViewById(R.id.tv_channelWay);
        tv_channelRate = (TextView) findViewById(R.id.tv_channelRate);
        sdv_img = (SimpleDraweeView) findViewById(R.id.sdv_img);

        rl_channelBg = (RelativeLayout) findViewById(R.id.rl_channelBg);
        //提现按钮
        tv_withdrawals = util_init.initTextView(R.id.icd_withdrawals, R.id.tv_button, null, "提    现", View.VISIBLE);

    }

    @Override
    public void initData() {
        setBankCardData(getIntent());
        obtainUserPurseBaseInfo(getActivity(), null, false, new Util_Runnable.Util_TypeRunnable<Bean_UserAccount>() {
            @Override
            public void run(Bean_UserAccount data) {
                String balance = new BigDecimal(Float.parseFloat(data.getBalance()) == 0 ? "0" : data.getBalance()).stripTrailingZeros().toPlainString();
                tv_balance.setText(new StringBuilder("可用余额:").append(balance).append("元"));
            }
        });
        getYiLianChannelData(getActivity(),true, new Util_Runnable.Util_TypeRunnable<Bean_Channel>() {
            @Override
            public void run(Bean_Channel data) {
                bean_channel = data;
            }
        });
        getPersonalInfoData(getActivity(), null, false, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                Bean_PersonalInfo bean = (Bean_PersonalInfo) data[0];
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                decimalFormat.setRoundingMode(RoundingMode.DOWN);
                bean_channel.setRate(decimalFormat.format(new BigDecimal(bean.getWithdrawFee())).concat("元/笔"));
                setChannelData(bean_channel);
            }
        });
    }

    public void setBankCardData(Intent intent) {
        Bean_BankCardInfo bean_bankCardInfo = (Bean_BankCardInfo) intent.getSerializableExtra(Extra_Bean_MyPurse_Withdrawals);
        Util_Log.logTest(bean_bankCardInfo);

        tv_bankCarCompany.setText(bean_bankCardInfo.getBankName());
        tv_bankCardType.setText(bean_bankCardInfo.getCardType());
        tv_bankCardNum.setText(bean_bankCardInfo.getCardNo());
        sdv_img.setImageURI(bean_bankCardInfo.getLogo());
    }

    void setChannelData(Bean_Channel bean_channel) {
        if (bean_channel != null) {
            this.bean_channel = bean_channel;
            tv_channelWay.setText("T+0");
            tv_channelName.setText(bean_channel.getChannelTag().toString());
            tv_channelRate.setText(Util_Empty.isEmpty(bean_channel.getRate()) ? "-" : bean_channel.getRate());
        }
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_withdrawals, tv_detailed, tv_allWithdrawals);
        Util_View.viewOnClick(this, iv_back);
        Util_View.viewOnClick(this, cv_bg);
        Util_View.viewOnClick(this, rl_channelBg);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId())
            onBackPressed();
        else if (v.getId() == tv_withdrawals.getId()) {
            if (Util_Empty.isEmptyToToast(et_collectionSpec.getText().toString(), "请输入收款说明"))
                return;
            if (Util_Empty.isEmptyToToast(tv_channelName.getText().toString(), "请选择快捷通道"))
                return;
            if (Util_Empty.isEmptyToToast(et_withdrawalsMoney.getText().toString(), "请输入提现金额"))
                return;
            BigDecimal withdrawalsMoney = getBigDecimal(et_withdrawalsMoney, tv_balance);
            if (withdrawalsMoney == null) return;
            if (bean_channel != null) {
                //BigDecimal poundage = withdrawalsMoney.multiply(new BigDecimal(bean_channel.getRate()));//计算手续费
                String minMoney = bean_channel.getSingleMinLimit();//最低限额
               /* if (minMoney != null) {
                    BigDecimal minLimit = new BigDecimal(minMoney);
                    if (withdrawalsMoney.compareTo(minLimit) < 0) {//判断是否满足提现条件
                        Util_Toast.toast(new StringBuilder("该通道单笔提现金额不得低于").append(minLimit.stripTrailingZeros().toPlainString()).append("元"));
                        return;
                    }
                }*/
                KeyboardUtils.hideSoftInput(getActivity());
                Act_PayKeyboard.showPayKeyboard(getActivity(), v.getId(), "提现金额", et_withdrawalsMoney.getText().toString(), new StringBuilder("手续费").append(bean_channel.getRate()).toString(), new Act_PayKeyboard.OnFinishInputListener() {
                    @Override
                    public void OnPasswordInputFinish(PopEnterPassword popEnterPassword, String password) {
                        popEnterPassword.dismiss();
                        if (verificationPayCode(getActivity(), password))
                            withdraw(et_collectionSpec.getText().toString(), Abstract_App.bean_userInfo.getPhone(), et_withdrawalsMoney.getText().toString(), tv_channelName.getText().toString());
                    }
                });
            }
        } else if (v.getId() == tv_detailed.getId()) {
            startActivity(new Intent(getActivity(), Act_Mine_TransactionDetailed.class));
        } else if (v.getId() == tv_allWithdrawals.getId()) {
            String value = tv_balance.getText().toString();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
            et_withdrawalsMoney.setText(decimalFormat.format(Double.parseDouble(value.substring(value.indexOf(":") + 1, value.lastIndexOf("元")))));
        } else if (v.getId() == cv_bg.getId()) {
            startActivity(new Intent(getActivity(), Act_Mine_BankCard.class));
        } /*else if (v.getId() == rl_channelBg.getId()) {
            showChannelDataDialog(getActivity(), bean_channelList, Result_Code_Withdrawals,null);
           *//* Picker_QQ.getInstance().useOptionPicker(getActivity(), "快捷通道", 0, optionList.toArray(new String[]{}), new Inter_QRCode_Callback.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    String[] channel = item.split("   ");
                    tv_channelName.setText(channel[0]);
                    tv_channelRate.setText(channel[1]);
                }
            });*//*
        }*/
    }

    @Nullable
    public static BigDecimal getBigDecimal(EditText et_withdrawalsMoney, TextView tv_balance) {
        BigDecimal withdrawalsMoney = new BigDecimal(et_withdrawalsMoney.getText().toString());//支付金额
        String value = tv_balance.getText().toString();//余额
        BigDecimal balance = new BigDecimal(value.substring(value.indexOf(":") + 1, value.lastIndexOf("元")));
        if (withdrawalsMoney.compareTo(balance) > 0) {//比较提现金额和余额
            Util_Toast.toast("余额不足");
            return null;
        }
        return withdrawalsMoney;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setBankCardData(intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDefaultBankCardInfo(getRootView(), getActivity());
    }

    public void withdraw(final String order_desc, final String phone, final String amount, final String channe_tag) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_WITHDRAWALS_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("order_desc", order_desc);
                httpParams.put("channe_tag", channe_tag);
                httpParams.put("phone", phone);
                httpParams.put("amount", amount);

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_request, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        startActivity(new Intent(getActivity(), Act_Mine_BankCard.class));
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
