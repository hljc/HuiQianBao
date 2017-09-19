package com.ceq.app.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_UserAccount;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Input;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.math.BigDecimal;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.activity.Act_MyPurse_Withdrawals.getBigDecimal;
import static com.ceq.app.main.methods.Method_Static.obtainUserPurseBaseInfo;
import static com.ceq.app.main.methods.Method_Static.showCertificationDialog;

/**
 * Created by ceq on 2017/5/5.
 */

public class Act_Home_RakeBack extends Framework_Activity {
    ImageView iv_back;
    TextView tv_rollOut, tv_allRollOut, tv_shareBalance;
    EditText et_money, et_payPassword, et_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_home_rakeback);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "返佣", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        //立即登录
        tv_rollOut = util_init.initTextView(R.id.icd_rollOut, R.id.tv_button, null, "确认转出", View.VISIBLE);
        tv_allRollOut = (TextView) findViewById(R.id.tv_allRollOut);
        tv_shareBalance = (TextView) findViewById(R.id.tv_shareBalance);

        et_money = (EditText) findViewById(R.id.et_money);
        Util_Input.bindMoneyFilter(et_money);

        et_payPassword = (EditText) findViewById(R.id.et_payPassword);
        et_description = (EditText) findViewById(R.id.et_description);
    }

    @Override
    public void initData() {
      obtainUserPurseBaseInfo(getActivity(), null, true, new Util_Runnable.Util_TypeRunnable<Bean_UserAccount>() {
          @Override
          public void run(Bean_UserAccount data) {
              String balance = new BigDecimal(Float.parseFloat(data.getRebateBalance()) == 0 ? "0" : data.getRebateBalance()).stripTrailingZeros().toPlainString();
              tv_shareBalance.setText(new StringBuilder("返佣账户余额:").append(balance).append("元"));
          }
      });
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, tv_rollOut, tv_allRollOut);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v.getId() == tv_rollOut.getId()) {
            if (!Abstract_App.bean_userInfo.getRealnameStatus().equals("1")) {
                showCertificationDialog(tv_rollOut);
                return;
            }
            String money = et_money.getText().toString();
            if (Util_Empty.isEmptyToToast(money, "请输入转出金额"))
                return;
            BigDecimal withdrawalsMoney = getBigDecimal(et_money, tv_shareBalance);
            if (withdrawalsMoney == null)
                return;
            if (Util_Empty.isEmptyToToast(et_description.getText().toString(), "请输入转出说明"))
                return;
            if (Util_Empty.isEmptyToToast(et_payPassword.getText().toString(), "请输入交易密码"))
                return;
            if (Float.parseFloat(money) == 0) {
                Util_Toast.toast("转出金额不能为0");
                return;
            }
            if (Act_Home_SelectChannel.verificationPayCode(getActivity(), et_payPassword.getText().toString()))
                rollOut();
        } else if (v.getId() == tv_allRollOut.getId()) {
            String value = tv_shareBalance.getText().toString();
            et_money.setText("");
            et_money.setText(value.substring(value.indexOf(":") + 1, value.lastIndexOf("元")));
        }
    }

    public void rollOut() {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_RAKE_BACK_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone",Abstract_App.bean_userInfo.getPhone());
                httpParams.put("amount", et_money.getText().toString());
                httpParams.put("order_desc", et_description.getText().toString());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_request, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast("转出成功");
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
