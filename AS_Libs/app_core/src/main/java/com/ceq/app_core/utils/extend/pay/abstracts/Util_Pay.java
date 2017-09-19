package com.ceq.app_core.utils.extend.pay.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.pay.enums.Enum_Platform_Pay;
import com.ceq.app_core.utils.extend.pay.interfaces.implement.Inter_Pay_Base;

/**
 * Created by Administrator on 2016/12/26.
 */

public abstract class Util_Pay implements Inter_Extends,Inter_Pay_Base {
    private static Util_Pay 微信;
    private static Util_Pay 支付宝;

    public static void initial(Enum_Platform_Pay enum_platform_pay,  String appId) {
        try {
            switch (enum_platform_pay) {
                case 微信:
                    (微信 = ((Util_Pay) Class.forName("com.ceq.app.core.util.Pay_WeiXin").newInstance())).init(appId);
                    break;
                case 支付宝:
                    (支付宝 = ((Util_Pay) Class.forName("com.ceq.app.core.Pay_ZhiFuBao").newInstance())).init(appId);
                    break;
            }
            Util_Log.logPay(enum_platform_pay, Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logPay(enum_platform_pay,Constants_International.framework_init_fail);
        }
    }

    public static Util_Pay getInstance(Enum_Platform_Pay enum_platform_pay) {
        switch (enum_platform_pay) {
            case 微信:
                return 微信;
            case 支付宝:
                return 支付宝;
        }
        return null;
    }

}
