package com.ceq.app_core.utils.extend.push.interfaces.callback;

import com.ceq.app_core.bean.Bean_Push;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_Push_Callback {
    void onCustomPush(Bean_Push bean_push);

    void onNotificationPush(Bean_Push bean_push);

    void onNotificationOpened(Bean_Push bean_push);
}