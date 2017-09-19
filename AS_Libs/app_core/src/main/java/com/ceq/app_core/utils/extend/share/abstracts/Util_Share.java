package com.ceq.app_core.utils.extend.share.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.share.enums.Enum_Platform_Share;
import com.ceq.app_core.utils.extend.share.interfaces.implement.Inter_Share_Base;

/**
 * Created by Administrator on 2016/12/27.
 */

public abstract class Util_Share implements Inter_Extends,Inter_Share_Base {
    private static Util_Share instance;

    public static void initial(Enum_Platform_Share share_platform) {
        try {
            switch (share_platform) {
                case Mob:
                    (instance = ((Util_Share) Class.forName("com.ceq.app.core.util.Share_Mob").newInstance())).init();
                    break;
            }
            Util_Log.logShare(share_platform,Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logShare(share_platform,Constants_International.framework_init_fail);
        }
    }

    public static Util_Share getInstance() {
        return instance;
    }
}
