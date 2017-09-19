package com.ceq.app_core.utils.extend.im.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.im.enums.Enum_Platform_IM;
import com.ceq.app_core.utils.extend.im.interfaces.implement.Inter_IM_Base;

/**
 * Created by Administrator on 2017/1/9.
 */

public abstract class Util_IM implements Inter_Extends,Inter_IM_Base {
    private static Util_IM instance;
    public static void initial(Enum_Platform_IM enum_platform_im, String appKey) {
        try {
            switch (enum_platform_im) {
                case 环信:
                    (instance = (Util_IM) Class.forName("com.ceq.app.core.util.IM_EM").newInstance()).init(appKey);
                    break;
                case 云旺:
                    (instance = (Util_IM) Class.forName("com.ceq.app_albb_im.IM_YW").newInstance()).init(appKey);
                    break;
            }
            Util_Log.logIM(enum_platform_im, Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logIM(enum_platform_im,Constants_International.framework_init_fail);
        }
    }

    public static Util_IM getInstance() {
        return instance;
    }
}
