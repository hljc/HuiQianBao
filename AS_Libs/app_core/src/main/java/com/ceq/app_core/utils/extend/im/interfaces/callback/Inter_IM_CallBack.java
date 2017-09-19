package com.ceq.app_core.utils.extend.im.interfaces.callback;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_IM_CallBack {
    void onSuccess();

    void onError(int code, String message);
}