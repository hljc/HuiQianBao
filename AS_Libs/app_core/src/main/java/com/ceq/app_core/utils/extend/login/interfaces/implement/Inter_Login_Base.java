package com.ceq.app_core.utils.extend.login.interfaces.implement;


import com.ceq.app_core.utils.extend.login.enums.Enum_Login;
import com.ceq.app_core.utils.extend.login.interfaces.callback.Inter_Login_PlatformListener;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_Login_Base {
    void thirdLogin(Enum_Login loginPlatform, Inter_Login_PlatformListener platformListener);
}
