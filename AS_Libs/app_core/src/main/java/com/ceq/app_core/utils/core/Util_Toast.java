package com.ceq.app_core.utils.core;

import android.os.Message;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_Toast {

    public static void toast(Object toastContent) {
        Message message = new Message();
        message.obj = toastContent;
       Util_Handler.handler.sendMessage(message);
    }
}
