package com.ceq.app_core.utils.core;
import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_DateTime {
    @SuppressLint("SimpleDateFormat")
    public static  long dateToDecimal(String date, String format, boolean isGMT) {
        if (date == null || format == null)
            return 0;
        long time = 0;
        try {
            time = new SimpleDateFormat(format).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isGMT ? time : time + 28800000;
    }

    @SuppressLint("SimpleDateFormat")
    public static  String dateToString(long milliseconds, String format, boolean isGMT) {
        return new SimpleDateFormat(format).format(new Date(isGMT?milliseconds:milliseconds-28800000));
    }
}
