package com.ceq.app.core.constants;


import com.ceq.app_core.constants.Constants_Common;

/**
 * Created by Administrator on 2016/8/2.
 */
public interface Constant_Common extends Constants_Common {
    /**
     * 请求key、value
     */
    String Http_Key_Name = "resp_code";
    String Http_Key_Toast = "resp_message";
    String Http_Key_Data = "result";
    String Http_Value_Success = "000000";
    String Http_Value_FAIL = "";
    String Http_Value_ERROR = "";

    String expression_password="[0-9a-zA-Z]{6,16}";

    int bannerDelayTime=5000;

}
