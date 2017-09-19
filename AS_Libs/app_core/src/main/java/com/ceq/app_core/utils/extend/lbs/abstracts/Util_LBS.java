package com.ceq.app_core.utils.extend.lbs.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.lbs.enums.Enum_Platform_LBS;
import com.ceq.app_core.utils.extend.lbs.interfaces.implement.Inter_LBS_Base;

/**
 * Created by Administrator on 207/1/14.
 */

public abstract class Util_LBS implements Inter_Extends,Inter_LBS_Base {

    private static Util_LBS instance;
    public static void initial(Enum_Platform_LBS enum_platform_lbs) {
        try {
            switch (enum_platform_lbs) {
                case 百度:
                    (instance = (Util_LBS) Class.forName("com.ceq.app.core.utils.LBS_BaiDu").newInstance()).init();
                    break;
                case 高德:
                    break;
            }
            Util_Log.logLBS(enum_platform_lbs, Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logLBS(enum_platform_lbs,Constants_International.framework_init_fail);
        }
    }

    public static Util_LBS getInstance() {
        return instance;
    }
}
