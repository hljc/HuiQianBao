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
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.core.constants.Constant_Common.expression_password;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_PASSWORD;

/**
 * Created by ceq on 2017/4/16.
 */

public class Act_Settings_ModifyUserPassword extends Framework_Activity{
    ImageView iv_back;
    EditText et_oldPassword,et_newPassword,et_newPassword2;
    TextView tv_modify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_settings_modifyuserpassword);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "修改登录密码", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        tv_modify = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "修改", View.VISIBLE);

        //密码
        et_oldPassword = util_init.initEditText(0, R.id.et_oldPassword, "请输入旧的登录密码", null, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, LENGTH_PASSWORD, View.VISIBLE);
        et_newPassword = util_init.initEditText(0, R.id.et_newPassword, "请输入新的登录密码", null, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, LENGTH_PASSWORD, View.VISIBLE);
        et_newPassword2 = util_init.initEditText(0, R.id.et_newPassword2, "请再次输入新的登录密码", null, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, LENGTH_PASSWORD, View.VISIBLE);
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
        Util_View.viewOnClick(this, tv_modify);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v.getId() == tv_modify.getId()) {
            if (Util_Empty.isEmptyAndMatchToToast(et_oldPassword.getText().toString(), Constant_International.error_password_not_null, expression_password, Constant_International.error_expression_password))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_newPassword.getText().toString(), Constant_International.error_password_not_null, expression_password, Constant_International.error_expression_password))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_newPassword2.getText().toString(), Constant_International.error_password_not_null, expression_password, Constant_International.error_expression_password))
                return;
            if(et_newPassword.getText().toString().equals(et_newPassword2.getText().toString())){
                Util_Toast.toast("二次密码不一致");
                return;
            }
            modifyUserPassword();
        }
    }
    private void modifyUserPassword() {
        Util_Http.httpToRequest(getActivity(),Constant_Api.URL_MODIFY_LOGIN_PASSWORD_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone",Abstract_App.bean_userInfo.getPhone());
                httpParams.put("vericode","123456");
                httpParams.put("password", et_newPassword2.getText().toString());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_modify, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast("修改成功");
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
