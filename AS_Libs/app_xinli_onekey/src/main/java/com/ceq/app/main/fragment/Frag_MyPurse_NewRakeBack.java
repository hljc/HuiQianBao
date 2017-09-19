package com.ceq.app.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.main.activity.Act_MyPurse_PurseDetailed;
import com.ceq.app.main.bean.Bean_BaseRebate;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_PersonalInfo;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Input;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.ceq.app.main.activity.Act_Home_SelectChannel.verificationPayCode;
import static com.ceq.app.main.activity.Act_Mine_Rate.getYiLianChannelData;
import static com.ceq.app.main.activity.Act_MyPurse_PurseDetailed.Extra_Int_MyPurse;
import static com.ceq.app.main.methods.Method_Static.getPersonalInfoData;
import static com.ceq.app.main.methods.Method_Static.getRebateBaseData;
import static com.ceq.app.main.methods.Method_Static.rollOut;
import static com.ceq.app.main.methods.Method_Static.showCertificationDialog;

/**
 * Created by ceq on 2017/5/16.
 */
public class Frag_MyPurse_NewRakeBack extends Framework_Fragment {
    EditText et_money, et_payPassword;
    TextView tv_withdrawals, tv_detailed;
    public TextView tv_rakeBackMoney, tv_withdrawalsAll;
    TextView tv_baseDataNameLeft, tv_baseDataNameRight, tv_baseDataValueLeft, tv_baseDataValueRight;
    LinearLayout ll_baseDataLeft, ll_baseDataRight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_mypurse_newshare);
    }

    @Override
    public void initView() {
        ll_baseDataLeft = (LinearLayout) findViewById(R.id.ll_baseDataLeft);
        ll_baseDataRight = (LinearLayout) findViewById(R.id.ll_baseDataRight);

        tv_baseDataNameLeft = (TextView) findViewById(R.id.tv_baseDataNameLeft);
        tv_baseDataNameRight = (TextView) findViewById(R.id.tv_baseDataNameRight);
        tv_baseDataValueLeft = (TextView) findViewById(R.id.tv_baseDataValueLeft);
        tv_baseDataValueRight = (TextView) findViewById(R.id.tv_baseDataValueRight);

        et_payPassword = (EditText) findViewById(R.id.et_payPassword);
        et_money = (EditText) findViewById(R.id.et_money);
        Util_Input.bindMoneyFilter(et_money);

        tv_withdrawalsAll = (TextView) findViewById(R.id.tv_withdrawalsAll);
        //按钮
        tv_withdrawals = util_init.initTextView(R.id.icd_withdrawals, R.id.tv_button, null, "确认提现", View.VISIBLE);
        tv_detailed = util_init.initTextView(R.id.icd_detailed, R.id.tv_button, null, "分润明细", View.VISIBLE);

        tv_rakeBackMoney = (TextView) findViewById(R.id.tv_rakeBackMoney);
    }

    @Override
    public void initData() {
        getRebateBaseData(getActivity(),null, new Util_Runnable.Util_TypeRunnable<Bean_BaseRebate>() {
            @Override
            public void run(Bean_BaseRebate data) {
                tv_baseDataValueLeft.setText(data.getTodayRebate());
                tv_baseDataValueRight.setText(data.getAllRebate());
            }
        });
    }


    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_withdrawals, tv_detailed, tv_withdrawalsAll);
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onClick(View v) {
        if (v.getParent() == tv_detailed.getParent())
            startActivity(new Intent(getActivity(), Act_MyPurse_PurseDetailed.class).putExtra(Extra_Int_MyPurse, 2));
        else if (v.getParent() == tv_withdrawals.getParent()) {
            if (!Abstract_App.bean_userInfo.getRealnameStatus().equals("1")) {
                showCertificationDialog(tv_withdrawals);
                return;
            }
            final String money = et_money.getText().toString();
            if (Util_Empty.isEmptyToToast(money, et_money.getHint().toString()))
                return;
            String balance = tv_rakeBackMoney.getText().toString();
            balance = balance.substring(balance.indexOf("额") + 1, balance.lastIndexOf("元")).trim();
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
                                Util_Toast.toast(new StringBuilder("分润提现金额不得低于").append(minLimit).append("元"));
                                Util_Http.dismiss();
                                return;
                            }
                            if (currentMoney.compareTo(maxLimit) == 1) {
                                Util_Toast.toast(new StringBuilder("分润提现金额不得超过").append(maxLimit).append("元"));
                                Util_Http.dismiss();
                                return;
                            }
                            if (data == null) {
                                Util_Toast.toast("未获取到该通道信息,请稍后重试");
                                Util_Http.dismiss();
                                return;
                            }
                            if (verificationPayCode(getActivity(), payPassword)) {
                                rollOut(getActivity(), "分润提现", Abstract_App.bean_userInfo.getPhone(), et_money.getText().toString());
                            } else
                                Util_Http.dismiss();
                        }
                    });
                }
            });

        } else if (v.getId() == tv_withdrawalsAll.getId()) {
            String balance = tv_rakeBackMoney.getText().toString();
            balance = balance.substring(balance.indexOf("额") + 1, balance.lastIndexOf("元")).trim();
            et_money.setText("");
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
            et_money.setText(decimalFormat.format(new BigDecimal(balance)));
        }
    }

}
