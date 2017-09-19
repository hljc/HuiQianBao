package com.ceq.app_core.utils.core;

import android.annotation.SuppressLint;
import android.os.Build;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_Encrypt {
    @SuppressLint("DefaultLocale")
    public static String md5(String text, boolean isUppercase) {
        if (text == null)
            return null;
        StringBuilder sb = new StringBuilder();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                md.update(text.getBytes("utf-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (byte hash : md.digest()) {
            int value = hash & (Byte.MAX_VALUE - Byte.MIN_VALUE);
            if (value < 16) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(value));
        }
        Util_Log.logEncrypt( "MD5", text, sb.toString());
        return isUppercase ? sb.toString().toUpperCase() : sb.toString().toLowerCase();
    }


    public static String sha_1(String text, boolean isUppercase) {
        if (text == null)
            return null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(text.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            String result = new String(buf);
            Util_Log.logEncrypt( "SHA_1", text, result);
            return isUppercase ? result.toUpperCase() : result.toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getConcatSecretData(String userId, String userKey, Object... needSecretParam) {
        List<String> list = new ArrayList<>();
        for (int i = 0, size = needSecretParam.length; i < size; i++)
            list.add(String.valueOf(needSecretParam[i]));
        Util_Log.logEncrypt( "校验信息", userId, userKey, list);
        StringBuilder sb = new StringBuilder();
        Collections.sort(list);
        for (int i = 0, size = list.size(); i < size; i++)
            sb.append(list.get(i));
        sb.append(userKey);
        return userId.concat(sha_1(sb.toString(), false));
    }
}
