package com.ceq.app_core.utils.core;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_Empty {
    public static boolean isEmpty(Object obj) {
        return obj == null ? true : (obj instanceof String && obj.toString().trim().length() == 0 ? true : false);
    }

    public static Object isEmptyToReplace(Object obj, Object replaceTo) {
        return isEmpty(obj) ? replaceTo : obj;
    }

    public static boolean isEmptyToToast(Object obj, String emptyToast) {
        if (isEmpty(obj))
            Util_Toast.toast(emptyToast);
        return isEmpty(obj);
    }

    public static boolean isEmptyAndMatchToToast(String text, String emptyToast, String regularExpression, String unMatchToast) {
        if (isEmpty(text.trim())) {
            Util_Toast.toast(emptyToast);
            return true;
        }
        if (!text.trim().matches(regularExpression)) {
            Util_Toast.toast(unMatchToast);
            return true;
        }
        return false;
    }
}
