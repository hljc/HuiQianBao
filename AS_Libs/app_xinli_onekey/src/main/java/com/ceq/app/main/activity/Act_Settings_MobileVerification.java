package com.ceq.app.main.activity;

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
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_IDENTIFY_CODE;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;
import static com.ceq.app_core.framework.Framework_Web.Extra_String_Title;

/**
 * Created by ceq on 2017/4/16.
 */

public class Act_Settings_MobileVerification extends Framework_Activity {
    TextView tv_next, tv_mobileAreaNum, tv_getIdentify;
    EditText et_mobile, et_identify;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_settings_mobileverification);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "手机验证", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        //手机号
        et_mobile = util_init.initEditText(R.id.icd_mobile, R.id.et_input, "请输入手机号", null, InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
        tv_mobileAreaNum = util_init.initTextView(R.id.icd_mobile, R.id.tv_left, null, "+86", View.VISIBLE);
        util_init.initView(R.id.icd_mobile, R.id.v_split, View.VISIBLE);
        //验证码
        et_identify = util_init.initEditText(R.id.icd_identifyCode, R.id.et_input, "请输入验证码", null, InputType.TYPE_CLASS_NUMBER, LENGTH_IDENTIFY_CODE, View.VISIBLE);
        //获取验证码
        tv_getIdentify = util_init.initTextView(R.id.tv_getIdentify, "获取验证码");
        //下一步
        tv_next = util_init.initTextView(R.id.icd_next, R.id.tv_button, null, "下一步", View.VISIBLE);

      /*  Util_SMS.getInstance().setSMSView(tv_getIdentify, et_mobile, new Inter_Push_Callback.OnCustomSendSMSListener() {
            @Override
            public void customSendSMS() {
                sendVerificationCode(getActivity(), et_mobile.getText().toString());
            }
        });*/
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_next);
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
        if (v.getId() == tv_next.getId()) {
            /*if (Util_Empty.isEmptyAndMatchToToast(et_mobile.getText().toString(), Constant_International.error_mobile_not_null, expression_mobile, Constant_International.error_expression_mobile))
                return;
            if (Util_Empty.isEmptyToToast(et_identify.getText().toString(), Constant_International.error_code_not_null))
                return;
            mobileVerificationByHttp(et_mobile.getText().toString(), et_identify.getText().toString());*/
            Act_Mine_PasswordManager.Function function = (Act_Mine_PasswordManager.Function) getIntent().getSerializableExtra(Extra_String_Title);
            switch (function) {
                case 修改登录密码:
                    startActivity(new Intent(getActivity(), Act_Settings_ModifyUserPassword.class).putExtra(Extra_String_Title, function.toString()));
                    break;
                case 修改交易密码:
                    startActivity(new Intent(getActivity(), Act_Settings_ModifyTransactionPassword.class).putExtra(Extra_String_Title, function.toString()));
                    break;
            }
        } else if (v.getId() == tv_mobileAreaNum.getId()) {

        } else if (v.getId() == iv_back.getId()) {
            onBackPressed();
        }
    }

    /*public static void sendVerificationCode(Activity activity, final String phone) {
        Util_Http.httpToRequest(Constant_Api.URL_SEND_VERIFICATION_CODE_GET, Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
                return null;
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", phone);

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
    }*/

    private void mobileVerificationByHttp(final String phone, final String vericode) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_SMS_LOGIN_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", phone);
                httpParams.put("vericode", vericode);
                httpParams.put("brand_id", Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_request, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Intent intent = getIntent();
                        if (intent != null) {
                            Act_Mine_PasswordManager.Function function = (Act_Mine_PasswordManager.Function) intent.getSerializableExtra(Extra_String_Title);
                            switch (function) {

                                case 修改登录密码:
                                    startActivity(new Intent(getActivity(), Act_Settings_ModifyUserPassword.class).putExtra(Extra_String_Title, function.toString()));
                                    break;
                                case 修改交易密码:
                                    startActivity(new Intent(getActivity(), Act_Settings_ModifyTransactionPassword.class).putExtra(Extra_String_Title, function.toString()));
                                    break;
                            }
                        }
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }
}
