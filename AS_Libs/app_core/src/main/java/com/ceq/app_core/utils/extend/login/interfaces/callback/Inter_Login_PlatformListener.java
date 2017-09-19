package com.ceq.app_core.utils.extend.login.interfaces.callback;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_Login_PlatformListener {
    void onComplete(int action, HashMap<String, Object> hashMap);

    void onError(int action, Throwable throwable);

    void onCancel(int action);
}
