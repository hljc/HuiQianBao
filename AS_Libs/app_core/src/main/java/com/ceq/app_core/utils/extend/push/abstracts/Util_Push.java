package com.ceq.app_core.utils.extend.push.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.push.enums.Enum_Platform_Push;
import com.ceq.app_core.utils.extend.push.interfaces.implement.Inter_Push_Base;

/**
 * Created by Administrator on 2016/12/25.
 */

public abstract class Util_Push implements Inter_Extends, Inter_Push_Base {
    private static Util_Push instance;

    public static void initial(Enum_Platform_Push sms_platform, String apiKey) {
        try {
            switch (sms_platform) {
                case 百度:
                    (instance = ((Util_Push) Class.forName("com.ceq.app.core.Push_BaiDu").newInstance())).init(apiKey);
                    break;
                case 极光:
                    (instance = ((Util_Push) Class.forName("com.ceq.app.core.Push_JiGuang").newInstance())).init();
                    break;
            }
            Util_Log.logPush(sms_platform,Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logPush(sms_platform,Constants_International.framework_init_fail);
        }
    }

    public static Util_Push getInstance() {
        return instance;
    }
}