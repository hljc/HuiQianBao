package com.ceq.app_core.utils.core;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public interface Util_Runnable {
    interface Util_ArgsRunnable {
        void run(Object... data);
    }
    interface Util_TypeRunnable<T> {
        void run(T data);
    }

}
