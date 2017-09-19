package com.ceq.app_core.utils.extend.push.interfaces.implement;

import com.ceq.app_core.utils.extend.push.interfaces.callback.Inter_Push_Callback;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_Push_Base {
    void bindUserId(String userId,Inter_Push_Callback inter_push_callback);
}
