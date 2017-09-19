package com.ceq.app_core.utils.extend.sms.interfaces.callback;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_SMS_Callback {
    interface OnCustomSendSMSListener {
        void customSendSMS();
    }

    interface OnMobCommitSMSListener {
        void success();

        void fail();
    }

}