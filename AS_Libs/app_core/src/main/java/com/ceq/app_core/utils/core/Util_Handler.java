package com.ceq.app_core.utils.core;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.ceq.app_core.framework.Framework_App;


/**
 * Created by Administrator on 2016/10/21.
 */

public class Util_Handler {
    public static Handler handler;
    private Toast toast;
    private static Util_Handler util_handler;

    private Util_Handler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.obj == null)
                    return;
                if (toast == null)
                    toast = Toast.makeText(Framework_App.getInstance(), msg.obj.toString(), Toast.LENGTH_LONG);
                toast.setText(msg.obj.toString());
                toast.show();
            }
        };
    }

    public static void initHandler() {
        util_handler= util_handler == null ? new Util_Handler() : util_handler;
    }

}
