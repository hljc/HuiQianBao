package com.ceq.app_core.utils.extend.pay.interfaces.callback;

import com.ceq.app_core.utils.extend.pay.interfaces.bean.Bean_PayInfoWX;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_Pay_CallBack {
    interface Inter_ZFB_CallBack extends Inter_Pay_CallBack {
        String getSignOrderInfo();
    }

    interface Inter_WX_CallBack extends Inter_Pay_CallBack {
        Bean_PayInfoWX getSignOrderInfo();
    }

    void payResult(boolean isPaySuccess);
}