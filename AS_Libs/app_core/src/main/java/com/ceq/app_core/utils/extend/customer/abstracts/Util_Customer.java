package com.ceq.app_core.utils.extend.customer.abstracts;


import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.interfaces.Inter_Extends;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.customer.enums.Enum_Platform_Customer;
import com.ceq.app_core.utils.extend.customer.interfaces.implement.Inter_Customer_Base;

/**
 * Created by Administrator on 2017/1/9.
 */

public abstract class Util_Customer implements Inter_Extends,Inter_Customer_Base{
    private static Util_Customer instance;
    public static void initial(Enum_Platform_Customer enum_platform_customer, String appKey) {
        try {
            switch (enum_platform_customer) {
                case 美洽:
                    (instance = (Util_Customer) Class.forName("com.ceq.app.core.util.Customer_MeiQia").newInstance()).init(appKey);
                    break;
            }
            Util_Log.logCustomer(enum_platform_customer, Constants_International.framework_init_success);
        } catch (Exception e) {
            e.printStackTrace();
            Util_Log.logCustomer(enum_platform_customer,Constants_International.framework_init_fail);
        }
    }

    public static Util_Customer getInstance() {
        return instance;
    }
}
