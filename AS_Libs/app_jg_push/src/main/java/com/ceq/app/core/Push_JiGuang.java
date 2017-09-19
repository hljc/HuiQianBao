package com.ceq.app.core;

import android.content.IntentFilter;

import com.ceq.app_core.framework.Framework_App;
import com.ceq.app_core.utils.core.Util_Apk;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.push.abstracts.Util_Push;
import com.ceq.app_core.utils.extend.push.interfaces.callback.Inter_Push_Callback;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by ceq on 2017/5/8.
 */

public class Push_JiGuang extends Util_Push {
    private static PushBroadcast pushBroadcast;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    private Inter_Push_Callback inter_push_callback;

    @Override
    public void init(Object... initParams) {
        initPushManager();
    }

    @Override
    public void bindUserId(String userId, final Inter_Push_Callback inter_push_callback) {
        JPushInterface.setAlias(Framework_App.getInstance(), userId, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Util_Log.logPush(i, s, set);
                Push_JiGuang.this.inter_push_callback = inter_push_callback;
                registerMessageReceiver();
                Util_Log.logPush(inter_push_callback);
            }
        });
    }

    private void initPushManager() {
        JPushInterface.setDebugMode(Util_Apk.isApkDebug());    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(Framework_App.getInstance());            // 初始化 JPush
    }

    private void registerMessageReceiver() {
        pushBroadcast = new PushBroadcast();
        PushBroadcast.setInter_push_callback(inter_push_callback);
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(Framework_App.getInstance()).registerReceiver(pushBroadcast, filter);
    }

}
