package com.ceq.app_core.utils.extend.sms.interfaces.implement;

import android.widget.EditText;
import android.widget.TextView;

import com.ceq.app_core.utils.extend.sms.interfaces.callback.Inter_SMS_Callback;


/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_SMS_Base {
    void getVerificationCode(EditText et_mobile);

    boolean submitVerificationCode(EditText et_verificationCode, Inter_SMS_Callback.OnMobCommitSMSListener onMobCommitSMSListener);

    void initScheduleTime();

    void setSMSView(TextView tv_sendCode, EditText et_mobile, Inter_SMS_Callback.OnCustomSendSMSListener onCustomSendSMSListener);

    void setSMSCommitCallback(EditText et_verificationCode, Inter_SMS_Callback.OnMobCommitSMSListener onMobCommitSMSListener);
}
