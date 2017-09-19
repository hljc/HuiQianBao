package com.ceq.app_core.utils.extend.sms.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.sms.enums.Enum_Platform_SMS;
import com.ceq.app_core.utils.extend.sms.interfaces.implement.Inter_SMS_Base;

/**
 * Created by Administrator on 2016/12/25.
 */

public abstract class Util_SMS implements Inter_Extends, Inter_SMS_Base {
    private static Util_SMS instance;

    public static void initial(Enum_Platform_SMS sms_platform, String appKey, String appSecret) {
        try {
            switch (sms_platform) {
                case Mob:
                    (instance = ((Util_SMS) Class.forName("com.ceq.app.core.util.SMS_Mob").newInstance())).init(appKey, appSecret);
                    break;
            }
            getInstance().initScheduleTime();
            Util_Log.logSMS(sms_platform,Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logSMS(sms_platform,Constants_International.framework_init_fail);
        }

    }

    public static Util_SMS getInstance() {
        return instance;
    }


}