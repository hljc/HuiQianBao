package com.ceq.app.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.push.interfaces.callback.Inter_Push_Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class PushBroadcast extends BroadcastReceiver {
    static Inter_Push_Callback inter_push_callback;

    public static Inter_Push_Callback getInter_push_callback() {
        return inter_push_callback;
    }

    public static void setInter_push_callback(Inter_Push_Callback inter_push_callback) {
        PushBroadcast.inter_push_callback = inter_push_callback;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();

            Bean_Push bean_push = new Bean_Push();
            bean_push.setExtra_alert(bundle.getString(JPushInterface.EXTRA_ALERT));
            bean_push.setExtra_extra(bundle.getString(JPushInterface.EXTRA_EXTRA));
            bean_push.setExtra_notification_id(String.valueOf(bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)));
            bean_push.setExtra_alert_type(bundle.getString(JPushInterface.EXTRA_ALERT_TYPE));
            bean_push.setExtra_msg_id(bundle.getString(JPushInterface.EXTRA_MSG_ID));
            bean_push.setExtra_title(bundle.getString(JPushInterface.EXTRA_TITLE));
            bean_push.setExtra_notification_title(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));

                /*bean_push.setExtra_activity_param(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                bean_push.setExtra_activity_param(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                bean_push.setExtra_activity_param(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                bean_push.setExtra_activity_param(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                bean_push.setExtra_activity_param(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                bean_push.setExtra_activity_param(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                bean_push.setExtra_activity_param(bundle.getString(JPushInterface.EXTRA_MESSAGE));*/

            Util_Log.logPush("[PushBroadcast] onReceive - ", intent.getAction(), ", extras: ", printBundle(bundle));
            Util_Log.logPush(this, inter_push_callback);
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                inter_push_callback.onCustomPush(bean_push);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                inter_push_callback.onNotificationPush(bean_push);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                inter_push_callback.onNotificationOpened(bean_push);
            }
           /* if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Util_Log.logPush("[PushBroadcast] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                inter_push_callback.onCustomPush(bean_push);
                Util_Log.logPush("[PushBroadcast] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                inter_push_callback.onNotificationPush(bean_push);
                Util_Log.logPush("[PushBroadcast] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Util_Log.logPush("[PushBroadcast] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                inter_push_callback.onNotificationOpened(bean_push);
                Util_Log.logPush("[PushBroadcast] 用户点击打开了通知");

                //打开自定义的Activity
                Intent i = new Intent(context, TestActivity.class);
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Util_Log.logPush("[PushBroadcast] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Util_Log.logPush("[PushBroadcast]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Util_Log.logPush("[PushBroadcast] Unhandled intent - " + intent.getAction());
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Util_Log.logPush("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Util_Log.logPush("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

  /*  //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }*/
}
