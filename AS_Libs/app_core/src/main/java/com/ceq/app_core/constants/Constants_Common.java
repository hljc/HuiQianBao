package com.ceq.app_core.constants;

/**
 * Created by Administrator on 2016/8/2.
 */
public interface Constants_Common {
    /**
     * 一页个数
     */
    int LENGTH_USER=11,LENGTH_PASSWORD=16,LENGTH_IDENTIFY_CODE=6,LENGTH_BANK_NUM=19;
    int Page_Size_10=10,Page_Size_20=20;


    /**
     * 正则：手机号
     */
    String expression_mobile="[1][34578][0-9]{9}";
    String expression_bank_num="\\d{15,19}";
    /**
     * 正则：大小写字母、数字组合（6-16）
     */
    String expression_password="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

}
