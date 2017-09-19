package com.ceq.app.core.util;

import android.app.Activity;
import android.content.Intent;

import com.ceq.app.core.CustomizedMQConversationActivity;
import com.ceq.app_core.bean.Bean_CustomerUi;
import com.ceq.app_core.framework.Framework_App;
import com.ceq.app_core.utils.core.Util_Apk;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.customer.abstracts.Util_Customer;
import com.meiqia.core.MQManager;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;

import app_mq_customer.R;

/**
 * Created by Administrator on 2017/5/26.
 */

public class Customer_MeiQia extends Util_Customer {
    @Override
    public void init(Object... initParams) {
        MQManager.setDebugMode(Util_Apk.isApkDebug());
        MQConfig.init(Framework_App.getInstance(), initParams[0].toString(), new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
                Util_Log.logCustomer(clientId);
            }

            @Override
            public void onFailure(int code, String message) {
                Util_Log.logCustomer(code, message);
            }
        });

        // 配置自定义信息
        initUiParams(null);
    }

    @Override
    public void skipToOnlineCustomerAct(Activity activity, Bean_CustomerUi bean_customerUi) {
        initUiParams(bean_customerUi);
        activity.startActivity(new Intent(activity, CustomizedMQConversationActivity.class));
    }

    private void initUiParams(Bean_CustomerUi bean_customerUi) {
        MQConfig.ui.titleGravity = MQConfig.ui.MQTitleGravity.CENTER;
        if (bean_customerUi == null)
            return;
        if (bean_customerUi.getBackArrowIconResId() != 0)
            MQConfig.ui.backArrowIconResId = bean_customerUi.getBackArrowIconResId();
        if (bean_customerUi.getTitleBackgroundResId() != 0)
            MQConfig.ui.titleBackgroundResId = bean_customerUi.getTitleBackgroundResId();
        if (bean_customerUi.getTitleTextColorResId() != 0)
            MQConfig.ui.titleTextColorResId = bean_customerUi.getTitleTextColorResId();
        if (bean_customerUi.getLeftChatBubbleColorResId() != 0)
            MQConfig.ui.leftChatBubbleColorResId = bean_customerUi.getLeftChatBubbleColorResId();
        if (bean_customerUi.getLeftChatTextColorResId() != 0)
            MQConfig.ui.leftChatTextColorResId = bean_customerUi.getLeftChatTextColorResId();
        if (bean_customerUi.getRightChatBubbleColorResId() != 0)
            MQConfig.ui.rightChatBubbleColorResId = bean_customerUi.getRightChatBubbleColorResId();
        if (bean_customerUi.getRightChatTextColorResId() != 0)
            MQConfig.ui.rightChatTextColorResId = bean_customerUi.getRightChatTextColorResId();
        if (bean_customerUi.getRobotEvaluateTextColorResId() != bean_customerUi.getRobotEvaluateTextColorResId())
            MQConfig.ui.robotEvaluateTextColorResId = R.color.test_red;
        if (bean_customerUi.getRobotMenuItemTextColorResId() != 0)
            MQConfig.ui.robotMenuItemTextColorResId = bean_customerUi.getRobotMenuItemTextColorResId();
        if (bean_customerUi.getRobotMenuTipTextColorResId() != 0)
            MQConfig.ui.robotMenuTipTextColorResId = bean_customerUi.getRobotMenuTipTextColorResId();
    }
}
