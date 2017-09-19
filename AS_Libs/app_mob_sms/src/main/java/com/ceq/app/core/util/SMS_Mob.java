package com.ceq.app.core.util;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.framework.Framework_App;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.extend.sms.abstracts.Util_SMS;
import com.ceq.app_core.utils.extend.sms.interfaces.callback.Inter_SMS_Callback;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

import static com.ceq.app_core.constants.Constants_Common.expression_mobile;
import static com.ceq.app_core.constants.Constants_International.message_code_send;
import static com.ceq.app_core.utils.core.Util_Handler.handler;


/**
 * Created by Administrator on 2016/12/25.
 */

public class SMS_Mob extends Util_SMS implements OnSendMessageHandler {
    protected static TextView tv_sendCode;
    protected static EditText et_mobile;
    protected static Inter_SMS_Callback.OnMobCommitSMSListener onMobCommitSMSListener;
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private static int remainingTime = -1;
    private static EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    if (onMobCommitSMSListener != null)
                        onMobCommitSMSListener.success();
                    Util_Log.logSMS("验证码", "提交成功", result, data);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Util_Toast.toast(message_code_send);
                    Util_Log.logSMS("验证码", "获取成功", result, data);
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    Util_Log.logSMS("验证码", "返回国家列表成功", result, data);
                }
            } else {
                if (onMobCommitSMSListener != null)
                    onMobCommitSMSListener.fail();
                Util_Log.logSMS("验证码", "失败", result, data);
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @Override
    public boolean onSendMessage(String country, String phone) {
        return false;
    }

    @Override
    public void getVerificationCode(EditText et_mobile) {
        SMSSDK.getVerificationCode("86", et_mobile.getText().toString());
    }

    @Override
    public boolean submitVerificationCode(EditText et_verificationCode, Inter_SMS_Callback.OnMobCommitSMSListener onMobCommitSMSListener) {
        SMS_Mob.onMobCommitSMSListener = onMobCommitSMSListener;
        SMSSDK.submitVerificationCode("86", et_mobile.getText().toString(), et_verificationCode.getText().toString());
        return false;
    }

    @Override
    public void initScheduleTime() {
        executorService.scheduleAtFixedRate(r_remaining, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void init(Object... initParams) {
        SMSSDK.initSDK(Framework_App.getInstance(), (String) initParams[0], (String) initParams[1]);
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    @Override
    public void setSMSView(TextView tv_sendCode, final EditText et_mobile, final Inter_SMS_Callback.OnCustomSendSMSListener onCustomSendSMSListener) {
        SMS_Mob.tv_sendCode = tv_sendCode;
        SMS_Mob.et_mobile = et_mobile;
        handler.post(r_update);
        tv_sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util_Empty.isEmptyAndMatchToToast(et_mobile.getText().toString(), Constants_International.error_mobile_not_null, expression_mobile, Constants_International.error_expression_mobile))
                    return;
                if (!NetworkUtils.isConnected()) {
                    Util_Toast.toast(Constants_International.error_net_disconnect);
                    return;
                }
                if (remainingTime < 0) {
                    if (onCustomSendSMSListener != null)
                        onCustomSendSMSListener.customSendSMS();
                    else if (getInstance() != null)
                        getInstance().getVerificationCode(et_mobile);

                    remainingTime = 60;
                }
            }
        });
    }

    @Override
    public void setSMSCommitCallback(EditText et_verificationCode,  Inter_SMS_Callback.OnMobCommitSMSListener onMobCommitSMSListener ) {
        if (getInstance() != null)
            getInstance().submitVerificationCode(et_verificationCode, onMobCommitSMSListener);
    }

    private static Runnable r_remaining = new Runnable() {
        @Override
        public void run() {
            if (remainingTime >= 0)
                remainingTime--;
            if (tv_sendCode != null) {
                handler.post(r_update);
            }
        }
    };


    private static Runnable r_update = new Runnable() {
        @Override
        public void run() {
            tv_sendCode.setText(remainingTime > 0 ? String.valueOf(remainingTime).concat("s") : "获取验证码");
            tv_sendCode.setEnabled(remainingTime > 0 ? false : true);
        }
    };


}
