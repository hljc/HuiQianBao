package com.ceq.app_core.utils.extend.pay.interfaces.implement;

import android.app.Activity;

import com.ceq.app_core.utils.extend.pay.interfaces.callback.Inter_Pay_CallBack;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_Pay_Base {
 boolean isCanPayByCurrentVersion();
 void pay(Activity activity, Inter_Pay_CallBack inter_pay_callBack);
}
