package com.ceq.app_core.utils.extend.qrcode.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.qrcode.enums.Enum_Platform_QRCode;
import com.ceq.app_core.utils.extend.qrcode.interfaces.implement.Inter_QRCode_Base;

/**
 * Created by Administrator on 2016/12/25.
 */

public abstract class Util_QRCode implements Inter_Extends, Inter_QRCode_Base {
    private static Util_QRCode instance;

    public static void initial(Enum_Platform_QRCode sms_platform) {
        try {
            switch (sms_platform) {
                case ZXing:
                    (instance = ((Util_QRCode) Class.forName("com.ceq.app.core.util.QRCode_ZXing").newInstance())).init();
                    break;
            }
            Util_Log.logE("二维码",Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logE("二维码", Constants_International.framework_init_fail);
        }
    }

    public static Util_QRCode getInstance() {
        return instance;
    }


}