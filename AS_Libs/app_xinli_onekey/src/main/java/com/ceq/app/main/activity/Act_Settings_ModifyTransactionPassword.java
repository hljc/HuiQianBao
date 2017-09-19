package com.ceq.app.main.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.utils.extend.sms.abstracts.Util_SMS;
import com.ceq.app_core.utils.extend.sms.interfaces.callback.Inter_SMS_Callback;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import static com.ceq.app.core.activity.Act_Login_Register.sendVerificationCode;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_IDENTIFY_CODE;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;
import static com.ceq.app_core.constants.Constants_Common.expression_mobile;

/**
 * Created by ceq on 2017/4/16.
 */

public class Act_Settings_ModifyTransactionPassword extends Framework_Activity {
    TextView tv_commit, tv_mobileAreaNum, tv_getIdentify;
    EditText et_mobile, et_password, et_password2, et_identify;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        init(R.layout.app_act_login_forgetsecret);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "修改交易密码", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        //手机号
        et_mobile = util_init.initEditText(R.id.icd_mobile, R.id.et_input, "请输入手机号", null, InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
        tv_mobileAreaNum = util_init.initTextView(R.id.icd_mobile, R.id.tv_left, null, "+86", View.VISIBLE);
        util_init.initView(R.id.icd_mobile, R.id.v_split).setVisibility(View.VISIBLE);
        //验证码
        et_identify = util_init.initEditText(R.id.icd_identifyCode, R.id.et_input, "请输入验证码", null, InputType.TYPE_CLASS_NUMBER, LENGTH_IDENTIFY_CODE, View.VISIBLE);
        //获取验证码
        tv_getIdentify = util_init.initTextView(R.id.tv_getIdentify, "获取验证码");
        //输入密码
        et_password = util_init.initEditText(R.id.icd_setPassword, R.id.et_input, "请输入新的支付密码", null, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD, 6, View.VISIBLE);
        //再次输入密码
        et_password2 = util_init.initEditText(R.id.icd_setPassword2, R.id.et_input, "请再次输入新的支付密码", null, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD, 6, View.VISIBLE);
        //完成
        tv_commit = util_init.initTextView(R.id.icd_commit, R.id.tv_button, null, "立即提交", View.VISIBLE);


        Util_SMS.getInstance().setSMSView(tv_getIdentify, et_mobile, new Inter_SMS_Callback.OnCustomSendSMSListener() {
            @Override
            public void customSendSMS() {
                sendVerificationCode(getActivity(), et_mobile.getText().toString());
            }
        });
    }


    @Override
    public void initData() {
    }

    @Override
    public void initAdapter() {
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_commit, tv_mobileAreaNum);
        Util_View.viewOnClick(this, iv_back);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == tv_commit.getId()) {
            if (Util_Empty.isEmptyAndMatchToToast(et_mobile.getText().toString(), Constant_International.error_mobile_not_null, expression_mobile, Constant_International.error_expression_mobile))
                return;
            if (Util_Empty.isEmptyToToast(et_identify.getText().toString(), Constant_International.error_code_not_null))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_password.getText().toString(), Constant_International.error_password_not_null, "\\d{6}", "只支持6位数字"))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_password2.getText().toString(), Constant_International.error_password_not_null, "\\d{6}", "只支持6位数字"))
                return;
            if (!et_password.getText().toString().equals(et_password2.getText().toString())) {
                Util_Toast.toast("2次密码不一致");
                return;
            }
            modifyPayPassword();
        } else if (v.getId() == tv_mobileAreaNum.getId()) {

        } else if (v.getId() == iv_back.getId()) {
            onBackPressed();
        }
    }


    private void modifyPayPassword() {
        Util_Http.httpToRequest(getActivity(),Constant_Api.URL_MODIFY_PAY_PASSWORD_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("paypass", et_password2.getText().toString());
                httpParams.put("vericode", et_identify.getText().toString());
                httpParams.put("phone",Abstract_App.bean_userInfo.getPhone());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_modify, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast("修改成功");
                        onBackPressed();
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
