package com.ceq.app_core.utils.extend.lbs.interfaces.implement;

import android.app.Activity;

import com.ceq.app_core.utils.extend.lbs.interfaces.callback.Inter_LBS_CallBack;


/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_LBS_Base {
 void startLocation(Activity activity, Inter_LBS_CallBack locationCallBack);
 void stopLocation();
 String[] getLogLatByByCity(String city);
}
