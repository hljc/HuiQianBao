package com.ceq.app.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.activity.Act_BankCard_Add;
import com.ceq.app.main.activity.Act_Mine_BankCard;
import com.ceq.app.main.activity.Act_MyPurse_PurseDetailed;
import com.ceq.app.main.bean.Bean_BankCardInfo;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_PersonalInfo;
import com.ceq.app_core.framework.Framework_Dialog;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Input;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.ceq.app.core.activity.Act_Main_Module.YILIAN;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.verificationPayCode;
import static com.ceq.app.main.activity.Act_Mine_Rate.getYiLianChannelData;
import static com.ceq.app.main.activity.Act_MyPurse_PurseDetailed.Extra_Int_MyPurse;
import static com.ceq.app.main.methods.Method_Static.getPersonalInfoData;
import static com.ceq.app.main.methods.Method_Static.showCertificationDialog;
import static com.ceq.app.main.methods.Method_Static.withdraw;

/**
 * Created by ceq on 2017/5/16.
 */

public class Frag_MyPurse_NewBalance extends Framework_Fragment {
    EditText et_money, et_payPassword;
    TextView tv_withdrawals, tv_detailed, tv_withdrawalsAll, tv_customerLine;
    public TextView tv_balance, tv_balance2;
    SimpleDraweeView sdv_img;
    LinearLayout ll_bank;
    TextView tv_bankName, tv_bankInfo, tv_poundage, tv_minLimit;
    ImageView iv_caret;

    public static final int request_code_default_bank = 100;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_mypurse_newbalance);
    }

    @Override
    public void initView() {
        et_money = (EditText) findViewById(R.id.et_money);
        et_payPassword = (EditText) findViewById(R.id.et_payPassword);
        Util_Input.bindMoneyFilter(et_money);
        iv_caret = (ImageView) findViewById(R.id.iv_caret);
        iv_caret.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.primaryColorOff)));
        //按钮
        tv_withdrawals = util_init.initTextView(R.id.icd_withdrawals, R.id.tv_button, null, "确认提现", View.VISIBLE);
        tv_detailed = util_init.initTextView(R.id.icd_detailed, R.id.tv_button, null, "余额明细", View.VISIBLE);

        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_balance2 = (TextView) findViewById(R.id.tv_balance2);

        tv_withdrawalsAll = (TextView) findViewById(R.id.tv_withdrawalsAll);

        sdv_img = (SimpleDraweeView) findViewById(R.id.sdv_img);
        ll_bank = (LinearLayout) findViewById(R.id.ll_bank);
        tv_bankName = (TextView) findViewById(R.id.tv_bankName);
        tv_bankInfo = (TextView) findViewById(R.id.tv_bankInfo);

        tv_poundage = (TextView) findViewById(R.id.tv_poundage);

        tv_customerLine = (TextView) findViewById(R.id.tv_customerLine);
        tv_customerLine.setText("3.客服热线:".concat(Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getTelephone()));

        getPersonalInfoData(getActivity(), null, false, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                Bean_PersonalInfo bean = (Bean_PersonalInfo) data[0];
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                decimalFormat.setRoundingMode(RoundingMode.DOWN);
                String withdrawFee = bean.getWithdrawFee();
                if (withdrawFee == null)
                    withdrawFee = "未获取到手续费信息";
                else
                    withdrawFee = new StringBuilder("手续费").append(decimalFormat.format(new BigDecimal(withdrawFee))).append("元/笔，请合理安排提现").toString();
                tv_poundage.setText(withdrawFee);
            }


        });

        tv_minLimit = (TextView) findViewById(R.id.tv_minLimit);

        getYiLianChannelData(getActivity(), true, new Util_Runnable.Util_TypeRunnable<Bean_Channel>() {
            @Override
            public void run(Bean_Channel data) {
                tv_minLimit.setText(new StringBuilder("1.提现最低金额").append(Util_Empty.isEmpty(data) || Util_Empty.isEmpty(data.getSingleMinLimit()) ? "0" : data.getSingleMinLimit()).append("元，提现到默认银行卡"));
            }
        });

    }

    @Override
    public void initData() {
        getDefaultBankCardInfo(new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                Util_Dialog.showDefaultDialog(getRootView(), "你还未绑定银行卡(提现卡)，是否前往绑定？", "确定", "取消", new Util_Dialog.DialogListener() {


                    @Override
                    public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                        framework_dialog.dismiss();
                    }

                    @Override
                    public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                        startActivityForResult(new Intent(getActivity(), Act_BankCard_Add.class), request_code_default_bank);
                        framework_dialog.dismiss();
                    }

                    @Override
                    public void onDismissListener() {

                    }
                });
            }
        });
    }


    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_withdrawals, tv_detailed, tv_withdrawalsAll);
        Util_View.viewOnClick(this, ll_bank);
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onClick(View v) {
        if (v.getParent() == tv_detailed.getParent())
            startActivity(new Intent(getActivity(), Act_MyPurse_PurseDetailed.class).putExtra(Extra_Int_MyPurse, 1));
        else if (v.getParent() == tv_withdrawals.getParent()) {
            if (!Abstract_App.bean_userInfo.getRealnameStatus().equals("1")) {
                showCertificationDialog(tv_withdrawals);
                return;
            }
            if (tv_bankName.getText().length() == 0) {
                Util_Toast.toast("请先设置银行卡(提现卡)");
                return;
            }
            final String money = et_money.getText().toString();
            if (Util_Empty.isEmptyToToast(money, et_money.getHint().toString()))
                return;
            String balance = tv_balance.getText().toString();
            balance = balance.substring(balance.indexOf("￥") + 1, balance.lastIndexOf("元"));
            if (balance == null)
                return;
            final String payPassword = et_payPassword.getText().toString();
            if (Util_Empty.isEmptyToToast(payPassword, et_payPassword.getHint().toString()))
                return;
            et_payPassword.setText("");
            getPersonalInfoData(getActivity(), null, false, new Util_Runnable.Util_ArgsRunnable() {
                @Override
                public void run(Object... data) {
                    Bean_PersonalInfo bean = (Bean_PersonalInfo) data[0];
                    if (Util_Empty.isEmptyToToast(bean.getRealname(), "未获取到真实姓名")) {
                        Util_Http.dismiss();
                        return;
                    }
                    getYiLianChannelData(getActivity(), false, new Util_Runnable.Util_TypeRunnable<Bean_Channel>() {
                        @Override
                        public void run(Bean_Channel data) {
                            String singleMinLimit = Util_Empty.isEmpty(data) || Util_Empty.isEmpty(data.getSingleMinLimit()) ? "0" : data.getSingleMinLimit();
                            String singleMaxLimit = Util_Empty.isEmpty(data) || Util_Empty.isEmpty(data.getSingleMaxLimit()) ? "0" : data.getSingleMaxLimit();
                            BigDecimal minLimit = new BigDecimal(singleMinLimit);
                            BigDecimal maxLimit = new BigDecimal(singleMaxLimit);
                            BigDecimal currentMoney = new BigDecimal(money);
                            if (currentMoney.compareTo(minLimit) == -1) {
                                Util_Toast.toast(new StringBuilder("余额提现金额不得低于").append(singleMinLimit).append("元"));
                                Util_Http.dismiss();
                                return;
                            }
                            if (currentMoney.compareTo(maxLimit) == 1) {
                                Util_Toast.toast(new StringBuilder("余额提现金额不得超过").append(singleMaxLimit).append("元"));
                                Util_Http.dismiss();
                                return;
                            }
                            if (data == null) {
                                Util_Toast.toast("未获取到该通道信息,请稍后重试");
                                Util_Http.dismiss();
                                return;
                            }
                         /*   if (!checkChannelIsAvailable(bean_channel,"余额提现功能正在维护升级,请耐心等待!"))
                                return;*/
                            if (verificationPayCode(getActivity(), payPassword)) {
                                withdraw(getActivity(), "余额提现", Abstract_App.bean_userInfo.getPhone(), et_money.getText().toString(), YILIAN);
                            } else
                                Util_Http.dismiss();
                        }
                    });

                }
            });
        } else if (v.getId() == tv_balance2.getId()) {
            String balance = tv_balance.getText().toString();
            balance = balance.substring(balance.indexOf("￥") + 1, balance.lastIndexOf("元")).trim();
            et_money.setText(balance);
        } else if (v.getId() == tv_withdrawalsAll.getId()) {
            String balance = tv_balance.getText().toString();
            balance = balance.substring(balance.indexOf("￥") + 1, balance.lastIndexOf("元")).trim();
            et_money.setText("");
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
            et_money.setText(decimalFormat.format(new BigDecimal(balance)));
        } else if (v.getId() == ll_bank.getId()) {
            startActivityForResult(new Intent(getActivity(), Act_Mine_BankCard.class), request_code_default_bank);
        }
    }


    private void getDefaultBankCardInfo(final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_GET_DEFAULT_BANK_CARD_INFO_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("user_id", Abstract_App.bean_userInfo.getId());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_query, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Bean_BankCardInfo bean_bankCardInfo = JSONObject.parseObject(JSON.parseObject(data).getString(Http_Key_Data), Bean_BankCardInfo.class);
                        if (bean_bankCardInfo == null) {
                            sdv_img.setImageResource(R.mipmap.app_logo);
                            tv_bankName.setText("");
                            tv_bankInfo.setText("");
                            if (util_Args_runnable != null)
                                util_Args_runnable.run(bean_bankCardInfo);
                        } else {
                            sdv_img.setImageURI(bean_bankCardInfo.getLogo());
                            tv_bankName.setText(bean_bankCardInfo.getBankName());
                            tv_bankInfo.setText(new StringBuilder("尾号：").append(bean_bankCardInfo.getCardNo().substring(bean_bankCardInfo.getCardNo().length() - 4)).append("【").append(bean_bankCardInfo.getCardType()).append("】"));
                        }
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getDefaultBankCardInfo(null);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
