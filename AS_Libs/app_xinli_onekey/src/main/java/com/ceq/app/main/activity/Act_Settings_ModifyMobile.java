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
import com.ceq.app_core.framework.Framework_Dialog;
import com.ceq.app_core.utils.core.Util_Dialog;
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
import static com.ceq.app_core.constants.Constants_Common.LENGTH_PASSWORD;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;
import static com.ceq.app_core.constants.Constants_Common.expression_mobile;

/**
 * Created by ceq on 2017/4/16.
 */

public class Act_Settings_ModifyMobile extends Framework_Activity {
    ImageView iv_back;
    EditText  et_newMobile,et_identify,et_password;

    TextView tv_commit, tv_mobileAreaNum, tv_getIdentify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_settings_modifymobile);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "修改手机号", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        //手机号
        et_newMobile = util_init.initEditText(R.id.icd_newMobile, R.id.et_input, "请输入新的手机号码", null, InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
        tv_mobileAreaNum = util_init.initTextView(R.id.icd_newMobile, R.id.tv_left, null, "+86", View.VISIBLE);
        util_init.initView(R.id.icd_newMobile, R.id.v_split).setVisibility(View.VISIBLE);
        //验证码
        et_identify = util_init.initEditText(R.id.icd_identifyCode, R.id.et_input, "请输入验证码", null, InputType.TYPE_CLASS_NUMBER, LENGTH_IDENTIFY_CODE, View.VISIBLE);
        //获取验证码
        tv_getIdentify = util_init.initTextView(R.id.tv_getIdentify, "获取验证码");
        //输入密码
        et_password = util_init.initEditText(R.id.icd_setPassword, R.id.et_input, "请输入原始登录密码", null, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, LENGTH_PASSWORD, View.VISIBLE);
        //完成
        tv_commit = util_init.initTextView(R.id.icd_commit, R.id.tv_button, null, "立即提交", View.VISIBLE);

        Util_SMS.getInstance().setSMSView(tv_getIdentify, et_newMobile, new Inter_SMS_Callback.OnCustomSendSMSListener() {
            @Override
            public void customSendSMS() {
                sendVerificationCode(getActivity(), et_newMobile.getText().toString());
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
        Util_View.viewOnClick(this, iv_back);
        Util_View.viewOnClick(this, tv_commit);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v.getId() == tv_commit.getId()) {
            if (Util_Empty.isEmptyAndMatchToToast(et_newMobile.getText().toString(), et_newMobile.getHint().toString(), expression_mobile, Constant_International.error_expression_mobile))
                return;
            if (Util_Empty.isEmptyToToast(et_identify.getText().toString(), Constant_International.error_code_not_null))
                return;
            modifyMobile(tv_commit,et_newMobile.getText().toString(),et_password.getText().toString(),et_identify.getText().toString());
        }
    }

    private void modifyMobile(final View viewToken, final String phone, final String password, final String vericode) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_MODIFY_MOBILE_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", phone);
                httpParams.put("password", password);
                httpParams.put("vericode", vericode);

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_modify, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        if (Abstract_App.bean_userInfo != null) {
                            Abstract_App.bean_userInfo.setModifyDate(0);
                            Abstract_App.bean_userInfo.setPassword(null);
                            Abstract_App.daoSession.getBean_UserInfoDao().insertOrReplaceInTx(Abstract_App.bean_userInfo);
                        }
                        Util_Dialog.showDefaultDialog(viewToken, "修改手机成功，请重新登陆！", "重新登录", null, new Util_Dialog.DialogListener() {

                            @Override
                            public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {

                            }

                            @Override
                            public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel,View v_split) {
                                Abstract_App.getInstance().logout(framework_dialog);
                            }

                            @Override
                            public void onDismissListener() {

                            }
                        });
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }
}
