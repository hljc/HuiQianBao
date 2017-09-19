package com.ceq.app.core.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.core.constants.Constant_International;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_System;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;

import static com.ceq.app.core.application.Abstract_App.bean_userInfo;
import static com.ceq.app.core.constants.Constant_Common.expression_password;
import static com.ceq.app.main.methods.Method_Static.loginByHttp;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_PASSWORD;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;
import static com.ceq.app_core.constants.Constants_Common.expression_mobile;
import static com.ceq.app_core.framework.Framework_Web.Extra_String_Title;
import static com.ceq.app_xinli_onekey.R.mipmap.icon_login_mobile_off;
import static com.ceq.app_xinli_onekey.R.mipmap.icon_login_mobile_on;
import static com.ceq.app_xinli_onekey.R.mipmap.icon_login_password_off;
import static com.ceq.app_xinli_onekey.R.mipmap.icon_login_password_on;

public class Act_Main_Login extends Framework_Activity {
    TextView tv_login, tv_forgetPassword, tv_register, tv_customerLine;
    EditText et_mobile, et_password;
    ImageView iv_rememberPass;
    boolean rememberPassword;
    LinearLayout ll_rememberPass;
    ImageView iv_mobile, iv_password;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        init(R.layout.app_act_main_login,Color.WHITE);
    }

    @Override
    public void initView() {
        //手机号
        et_mobile = util_init.initEditText(R.id.icd_mobile, R.id.et_input, "请输入手机号码", (String) Util_Empty.isEmptyToReplace(bean_userInfo == null ? null : bean_userInfo.getPhone(), ""), InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
        iv_mobile = util_init.initImageView(R.id.icd_mobile, R.id.iv_leftImg, et_mobile.getText().length() == 0 ? icon_login_mobile_off : icon_login_mobile_on, View.VISIBLE);
        setColor(iv_mobile, et_mobile.getText().length() != 0);
        //密码
        et_password = util_init.initEditText(R.id.icd_password, R.id.et_input, "输入登录密码", null, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, LENGTH_PASSWORD, View.VISIBLE);
        iv_password = util_init.initImageView(R.id.icd_password, R.id.iv_leftImg, et_password.getText().length() == 0 ? icon_login_password_off : icon_login_password_on, View.VISIBLE);
        setColor(iv_password, et_password.getText().length() != 0);
        //按钮
        tv_login = util_init.initTextView(R.id.icd_login, R.id.tv_button, null, "登      录", View.VISIBLE);
        //按钮
        tv_register = util_init.initTextView(0, R.id.tv_register, null, "注      册", View.VISIBLE);
        //忘记密码
        tv_forgetPassword = util_init.initTextView(R.id.tv_forgetPassword, "忘记密码?");
        //记住密码
        ll_rememberPass = (LinearLayout) findViewById(R.id.ll_rememberPass);
        iv_rememberPass = (ImageView) findViewById(R.id.iv_rememberPass);

   /*     tv_customerLine = (TextView) findViewById(R.id.tv_customerLine);
        tv_customerLine.setText("客服热线:".concat(Abstract_App.bean_oneKey.getBean_oneKeyProps().getTelephone()));*/

        iv_rememberPass.setImageResource((rememberPassword = bean_userInfo != null && bean_userInfo.getBean_properties().isRememberPassword()) ? R.mipmap.app_radios_on : R.mipmap.app_radios_off);
        setColor(iv_rememberPass, rememberPassword);
    }

    private void setColor(ImageView imageView, boolean isFocus) {
        imageView.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(isFocus ? R.color.primaryColorOff : R.color.text_color_2)));
    }


    @Override
    public void initAdapter() {


    }

    @Override
    public void initListener() {
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setColor(iv_mobile, et_mobile.getText().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setColor(iv_password, et_password.getText().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Util_View.viewOnClick(this, tv_login, tv_forgetPassword, tv_register, ll_rememberPass);
    }

    @Override
    public void initData() {
    }


    @Override
    public void onClick(View v) {
        if (v.getParent() == tv_login.getParent()) {
            if (Util_Empty.isEmptyAndMatchToToast(et_mobile.getText().toString(), et_mobile.getHint().toString(), expression_mobile, Constant_International.error_expression_mobile))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_password.getText().toString(), et_password.getHint().toString(), expression_password, Constant_International.error_expression_password))
                return;
            loginByHttp(getActivity(), et_mobile.getText().toString(), et_password.getText().toString(), rememberPassword);
        } else if (v.getId() == ll_rememberPass.getId()) {
            rememberPassword = !rememberPassword;
            iv_rememberPass.setImageResource(rememberPassword ? R.mipmap.app_radios_on : R.mipmap.app_radios_off);
            setColor(iv_rememberPass, rememberPassword);
            if (bean_userInfo != null)
                bean_userInfo.getBean_properties().setRememberPassword(rememberPassword);
        } else if (v.getId() == tv_forgetPassword.getId()) {
            startActivity(new Intent(getActivity(), Act_Login_ForgetPassword.class).putExtra(Extra_String_Title, "忘记密码"));
        } else if (v.getParent() == tv_register.getParent()) {
            startActivity(new Intent(getActivity(), Act_Login_Register.class));
        }

    }

    @Override
    public void onBackPressed() {
        Util_System.systemToExitAndToast(Constant_International.keyboard_back_exit, 2);
    }

}
