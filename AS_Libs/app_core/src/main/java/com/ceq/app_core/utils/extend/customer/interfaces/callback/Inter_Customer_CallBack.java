package com.ceq.app_core.utils.extend.customer.interfaces.callback;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_Customer_CallBack {

    void onSuccess();

    void onError(int code, String message);
}