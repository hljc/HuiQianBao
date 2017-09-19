package com.ceq.app_core.utils.extend.login.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.login.enums.Enum_Platform_Login;
import com.ceq.app_core.utils.extend.login.interfaces.implement.Inter_Login_Base;

/**
 * Created by Administrator on 2016/12/26.
 */

public abstract class Util_Login implements Inter_Extends,Inter_Login_Base {
    private static Util_Login instance;

    public static void initial(Enum_Platform_Login enum_platform_login) {
        try {
            switch (enum_platform_login) {
                case Mob:
                    (instance = ((Util_Login) Class.forName("com.ceq.app.core.util.Login_Mob").newInstance())).init();
                    break;
                case 友盟:
                    break;
            }
            Util_Log.logThirdLogin(enum_platform_login, Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logThirdLogin(enum_platform_login,Constants_International.framework_init_fail);
        }
    }

    public static Util_Login getInstance() {
        return instance;
    }

}
