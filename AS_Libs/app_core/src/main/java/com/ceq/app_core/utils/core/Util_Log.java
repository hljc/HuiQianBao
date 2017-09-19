package com.ceq.app_core.utils.core;

import android.util.Log;

/**
 * Created by Administrator on 2016/4/29.
 */
public class Util_Log {
    private static String log(String logTag, Object... toastContent) {
        if (!Util_Apk.isApkDebug()) return "Release版本";
        StringBuilder sb = new StringBuilder();
        if (!Util_Empty.isEmpty(logTag))
            sb.append(" =========>>").append(logTag);
        for (int i = 0, count = toastContent.length; i < count; i++)
            sb.append(" =========>>").append(toastContent[i]);
        return sb.toString();
    }

    public static void logE(Object... toastContent) {
        Log.e("Util_Log===>>>>", log(null, toastContent));
    }

    public static void logI(Object... toastContent) {
        Log.i("Util_Log===>>>>", log(null, toastContent));
    }

    /**
     * 扩展日志
     *
     * @param toastContent
     */
    public static void logTest(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("测试日志", toastContent));
    }
    public static void logStack() {
        Log.e("Util_Log===>>>>", log("堆栈日志", Log.getStackTraceString(new Throwable())));
    }

    public static void logNet(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("OkGo日志", toastContent));
    }

    public static void logApp(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("App日志", toastContent));
    }

    public static void logEncrypt(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("加密日志", toastContent));
    }

    public static void logPermission(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("权限日志", toastContent));
    }

    public static void logPay(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("支付日志", toastContent));
    }

    public static void logThirdLogin(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("第三方登录日志", toastContent));
    }

    public static void logShare(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("分享日志", toastContent));
    }

    public static void logIM(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("IM日志", toastContent));
    }

    public static void logSMS(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("短信日志", toastContent));
    }

    public static void logLBS(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("LBS日志", toastContent));
    }

    public static void logPush(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("推送日志", toastContent));
    }
    public static void logCustomer(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("客服日志", toastContent));
    }
    public static void logWeb(Object... toastContent) {
        Log.e("Util_Log===>>>>", log("Web日志", toastContent));
    }
}
