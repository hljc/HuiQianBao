package com.ceq.app_core.utils.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.ceq.app_core.framework.Framework_App;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_SharePreference {
    public interface InitSP {
        HashMap<String, Object> initParameters(HashMap<String, Object> hm_values);
    }

    public static void spToSet( String fileName, InitSP initSp,
                               boolean isReplaceRepeat) {
        if (initSp == null || fileName == null)
            return;
        SharedPreferences sp =  Framework_App.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        HashMap<String, Object> hm = initSp .initParameters(new HashMap<String, Object>());
        if (!Util_Empty.isEmpty(hm)) {
            for (Map.Entry<String, Object> entry : hm.entrySet()) {
                String key = entry.getKey();
                if (!isReplaceRepeat && sp.contains(key))
                    continue;
                e.putString(key, entry.getValue().toString());
            }
        }
        e.commit();
    }

    public static void spToRemove(String fileName, String... keyName) {
        if (keyName == null || fileName == null)
            return;
        if (!Util_Empty.isEmpty(fileName) && !Util_Empty.isEmpty(keyName)) {
            SharedPreferences.Editor e =  Framework_App.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
            for (String key : keyName)
                e.remove(key);
            e.commit();
        }
    }

    public static String spToQueryString( String fileName, String keyName) {
        if (fileName == null || keyName == null)
            return null;
        return Framework_App.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE).getString(keyName,  "");
    }

    public static boolean spToQueryBoolean( String fileName, String keyName) {
        if (fileName == null || keyName == null)
            return false;
        String value = spToQueryString( fileName, keyName);
        return Util_Empty.isEmpty(value) ? false : Boolean.parseBoolean(value);
    }

    public static long spToQueryLong( String fileName, String keyName) {
        if (fileName == null || keyName == null)
            return 0;
        String value = spToQueryString( fileName, keyName);
        return Util_Empty.isEmpty(value) ? 0 : Long.parseLong(value);
    }
}
