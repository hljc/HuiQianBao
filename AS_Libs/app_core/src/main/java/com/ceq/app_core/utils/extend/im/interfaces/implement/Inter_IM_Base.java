package com.ceq.app_core.utils.extend.im.interfaces.implement;

import android.app.Activity;

import com.ceq.app_core.utils.extend.im.interfaces.callback.Inter_IM_CallBack;
import com.ceq.app_core.utils.extend.im.interfaces.callback.Inter_IM_Listener;


/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_IM_Base {
    void login(String userName, String password, Inter_IM_CallBack imCallBack);

    void logout(Inter_IM_CallBack imCallBack);

    void setConnectStatusListener(Activity activity, Inter_IM_Listener.ConnectStatusListener connectStatusListener);

    Inter_IM_Message getMessageInstance();

    Inter_IM_Friends getFriendInstance();

    Inter_IM_Frag getFragInstance();

    Inter_IM_Act getActInstance();
}
