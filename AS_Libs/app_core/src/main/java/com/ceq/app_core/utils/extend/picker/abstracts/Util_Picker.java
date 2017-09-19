package com.ceq.app_core.utils.extend.picker.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.picker.enums.Enum_Platform_Picker;
import com.ceq.app_core.utils.extend.picker.interfaces.implement.Inter_Picker_Base;

/**
 * Created by Administrator on 2016/12/25.
 */

public abstract class Util_Picker implements Inter_Extends, Inter_Picker_Base {
    private static Util_Picker instance;

    public static void initial(Enum_Platform_Picker sms_platform) {
        try {
            switch (sms_platform) {
                case QQ:
                    (instance = ((Util_Picker) Class.forName("com.ceq.app.core.util.Picker_QQ").newInstance())).init();
                    break;
            }
            Util_Log.logE("选择器",Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logE("选择器", Constants_International.framework_init_fail);
        }
    }

    public static Util_Picker getInstance() {
        return instance;
    }


}