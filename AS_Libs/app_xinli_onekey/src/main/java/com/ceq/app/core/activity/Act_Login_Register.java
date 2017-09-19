package com.ceq.app.core.activity;

import android.app.Activity;
import android.content.Intent;
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

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.core.constants.Constant_Common.expression_password;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_IDENTIFY_CODE;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_PASSWORD;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;
import static com.ceq.app_core.constants.Constants_Common.expression_mobile;


/**
 * Created by Administrator on 2016/8/30.
 */
public class Act_Login_Register extends Framework_Activity {
    TextView tv_register, tv_mobileAreaNum, tv_getIdentify;
    EditText et_mobile, et_password, et_password2, et_identify, et_refereeMobile, et_payPassword;
    ImageView iv_back;

    boolean needReferee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.app_act_login_register);
    }

    @Override
    public void initView() {
        needReferee = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().isNeedReferee();
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "注册", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        //手机号
        et_mobile = util_init.initEditText(R.id.icd_mobile, R.id.et_input, "请输入手机号", null, InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
        tv_mobileAreaNum = util_init.initTextView(R.id.icd_mobile, R.id.tv_left, null, "+86", View.VISIBLE);
        util_init.initView(R.id.icd_mobile, R.id.v_split, View.VISIBLE);
        //推荐人手机号
        et_refereeMobile = util_init.initEditText(R.id.icd_refereeMobile, R.id.et_input, "请输入推荐人手机号".concat(needReferee?"":"(选填)"), null, InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
        //验证码
        et_identify = util_init.initEditText(R.id.icd_identifyCode, R.id.et_input, "请输入验证码", null, InputType.TYPE_CLASS_NUMBER, LENGTH_IDENTIFY_CODE, View.VISIBLE);
        //获取验证码
        tv_getIdentify = util_init.initTextView(R.id.tv_getIdentify, "获取验证码");
        //设置密码
        et_password = util_init.initEditText(R.id.icd_password, R.id.et_input, "请设置您的密码", null, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, LENGTH_PASSWORD, View.VISIBLE);
        //再次输入密码
        et_password2 = util_init.initEditText(R.id.icd_password2, R.id.et_input, "再次输入密码", null, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, LENGTH_PASSWORD, View.VISIBLE);
        //再次输入密码
        et_payPassword = util_init.initEditText(R.id.icd_payPassword, R.id.et_input, "请输入支付密码", null, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, LENGTH_PASSWORD, View.VISIBLE);
        //注册
        tv_register = util_init.initTextView(R.id.icd_register, R.id.tv_button, null, "注册", View.VISIBLE);

        Util_SMS.getInstance().setSMSView(tv_getIdentify, et_mobile, new Inter_SMS_Callback.OnCustomSendSMSListener() {
            @Override
            public void customSendSMS() {
                sendVerificationCode(getActivity(), et_mobile.getText().toString());
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_register, tv_mobileAreaNum);
        Util_View.viewOnClick(this, iv_back);
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == tv_register.getId()) {
            if (Util_Empty.isEmptyAndMatchToToast(et_mobile.getText().toString(), et_mobile.getHint().toString(), expression_mobile, Constant_International.error_expression_mobile))
                return;
            if (Util_Empty.isEmptyToToast(et_identify.getText().toString(), et_identify.getHint().toString()))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_password.getText().toString(), et_password.getHint().toString(), expression_password, Constant_International.error_expression_password))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_password2.getText().toString(), et_password2.getHint().toString(), expression_password, Constant_International.error_expression_password))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_payPassword.getText().toString(), et_payPassword.getHint().toString(), "\\d{6}", "支付密码只支持6位数字"))
                return;
            if (!et_password.getText().toString().equals(et_password2.getText().toString())) {
                Util_Toast.toast("2次密码不一致");
                return;
            }
            if (needReferee&&Util_Empty.isEmptyToToast(et_refereeMobile.getText().toString(), et_refereeMobile.getHint().toString()))
                return;
            registerByHttp(et_mobile.getText().toString(), et_password.getText().toString(), et_identify.getText().toString(), et_payPassword.getText().toString(), "", "", et_refereeMobile.getText().toString());
        } else if (v.getId() == tv_mobileAreaNum.getId()) {

        } else if (v.getId() == iv_back.getId()) {
            onBackPressed();
        }
    }

    private void registerByHttp(final String phone, final String password, final String vericode, final String paypass, final String openid, final String unionid, final String invitecode) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_REGISTER_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", phone);
                httpParams.put("vericode", vericode);
                httpParams.put("paypass", paypass);
                httpParams.put("password", password);
                httpParams.put("openid", openid);
                httpParams.put("unionid", unionid);
                httpParams.put("invitecode", invitecode);
                //httpParams.put("brand_name", getResources().getString(R.string.app_name));
                httpParams.put("brand_id", Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_register, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast("注册成功");
                        startActivity(new Intent(getActivity(), Act_Main_Login.class));
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void sendVerificationCode(Activity activity, final String phone) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_SEND_VERIFICATION_CODE_GET, Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", phone);
                httpParams.put("brand_id", Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId());

            }
        }, new Util_Http.HttpDealStringListener(activity, Constant_International.message_code_send, false, false) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast(Constant_International.message_code_send);
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
