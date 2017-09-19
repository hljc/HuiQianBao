package com.ceq.app.core.constants;

public interface Constant_Api {
    String WEB_APP_URL = "/v1.0";
    String HOST_RELEASE_URL = "http://ds.jiepaypal.cn".concat(WEB_APP_URL);
    String HOST_DEBUG_URL = "http://192.168.1.101:8765".concat(WEB_APP_URL);

    /**
     * 【模块一:登录注册】
     */
    // 登陆
    String URL_LOGIN_POST = HOST_RELEASE_URL.concat("/user/login/");
    // 验证码登陆
    String URL_SMS_LOGIN_POST = HOST_RELEASE_URL.concat("/user/smslogin/");
    // 注册
    String URL_REGISTER_POST = HOST_RELEASE_URL.concat("/user/register/");
    //重置密码
    String URL_RESET_PASSWORD_POST = HOST_RELEASE_URL.concat("/user/resetpwd.php/");
    //完善资料
    String URL_PERFECT_INFO_POST = HOST_RELEASE_URL.concat("/user/finishinfo.php/");
    //发送验证码
    String URL_SEND_VERIFICATION_CODE_GET = HOST_RELEASE_URL.concat("/notice/sms/send/");
    //修改用户密码(忘记密码)
    String URL_ACCOUNT_MODIFY_PASSWORD_POST = HOST_RELEASE_URL.concat("/user/password/update/");
    //修改用户手机号
    String URL_MODIFY_MOBILE_POST = HOST_RELEASE_URL.concat("/user/phone/update/");
    //更新Apk URL_UPDATE_APK_GET
    String URL_UPDATE_APK_GET = HOST_RELEASE_URL.concat("/user/versionnumber");
    /**
     * 【模块二:银行卡】
     */
    // 获取用户的所有银行卡信息接口
    String URL_GET_BANK_CARD_INFO_GET = HOST_RELEASE_URL.concat("/user/bank/query/userid/");
    // 获取用户指定类型的银行卡信息接口
    String URL_GET_SPECIFY_BANK_CARD_INFO_POST = HOST_RELEASE_URL.concat("/user/bank/query/userid/type/");
    // 新增一张银行卡并且验卡
    String URL_ADD_BANK_CARD_INFO_POST = HOST_RELEASE_URL.concat("/user/bank/add/");
    // 删除一张银行卡
    String URL_DEL_BANK_CARD_INFO_POST = HOST_RELEASE_URL.concat("/user/bank/del/");
    //设置一张默认银行卡
    String URL_SET_DEFAULT_BANK_CARD_INFO_POST = HOST_RELEASE_URL.concat("/user/bank/default/");
    //获取用户默认银行卡
    String URL_GET_DEFAULT_BANK_CARD_INFO_POST = HOST_RELEASE_URL.concat("/user/bank/default/userid/");
    /**
     * 【模块三:我】
     */
    //积分
    String URL_GET_USER_INTEGRAL_POST = HOST_RELEASE_URL.concat("/user/coin/query/");
    //余额
    String URL_GET_USER_BALANCE_POST = HOST_RELEASE_URL.concat("/user/balance/query/");
    //分润
    String URL_GET_USER_SHARE_POST = HOST_RELEASE_URL.concat("/user/rebate/query/");
    //结算费率(通道获取)
    String URL_RATE_INFO_POST = HOST_RELEASE_URL.concat("/user/channel/query/all/brandid/");
    //获取产品等级费率
    String URL_PRODUCTION_GRADE_RATE_POST = HOST_RELEASE_URL.concat("/user/thirdlevel/rate/query/thirdlevelid/");
    //获取用户目前的账户信息
    String URL_OBTAIN_USER_INFO_GET = HOST_RELEASE_URL.concat("/user/account/query/");
    //个人信息
    String URL_USER_INFO_POST = HOST_RELEASE_URL.concat("/user/info/query/");
    //实名认证(上传照片)
    String URL_CERTIFICATION_UPLOAD_IMAGE_POST = HOST_RELEASE_URL.concat("/user/upload/realname/");
    //实名认证(验证信息)
    String URL_CERTIFICATION_VERIFICATION_INFO_POST = HOST_RELEASE_URL.concat("/paymentchannel/realname/auth/");
    //实名认证(查询)
    String URL_CERTIFICATION_QUERY_INFO_POST = HOST_RELEASE_URL.concat("/paymentchannel/realname/");
    //收益
    String URL_INCOME_BASE_INFO_POST = HOST_RELEASE_URL.concat("/transactionclear/payment/query/sum/userid");
    //获取回扣（返佣）
    String URL_GET_BASE_REBATE_INFO_POST = HOST_RELEASE_URL.concat("/user/rebate/query/sumrebate/");
    /**
     * 【升级】
     */
    //获取用户的下级会员
    String URL_GET_VIP_MEMBER_POST = HOST_RELEASE_URL.concat("/user/afer/");
    //获取指定等级的下级会员
    String URL_GET_APPOINT_VIP_MEMBER_POST = HOST_RELEASE_URL.concat("/user/info/");
    //获取鼓励金
    String URL_GET_ENCOURAGE_MONEY_GET = HOST_RELEASE_URL.concat("/facade/red/payment/");

    /**
     * 【明细】
     */
    //获取用户的交易明细
    String URL_GET_DETAILED_TRANSACTION_POST = HOST_RELEASE_URL.concat("/transactionclear/payment/query/");
    //获取用户的分润明细
    String URL_GET_DETAILED_SHARE_POST = HOST_RELEASE_URL.concat("/transactionclear/profit/query/");
    //获取用户的积分明细
    String URL_GET_DETAILED_INTEGRAL_POST = HOST_RELEASE_URL.concat("/transactionclear/coin/query/");
    /**
     * 三级分销
     */
    //快捷收款、获取二维码
    String URL_FAST_COLLECTION_AND_QRCODE__POST = HOST_RELEASE_URL.concat("/facade/topup/");
    //商家收款
    String URL_MERCHANT_COLLECTION_POST = HOST_RELEASE_URL.concat("/user/shops/query/uid/");
    //提现
    String URL_WITHDRAWALS_POST = HOST_RELEASE_URL.concat("/facade/withdraw/");
    //购买产品
    String URL_PURCHASE_POST = HOST_RELEASE_URL.concat("/facade/purchase/");
    //验证支付密码
    String URL_VERIFICATION_PAY_CODE_POST = HOST_RELEASE_URL.concat("/user/paypass/auth/");
    //按品牌查询所有三级分销产品
    String URL_QUERY_PRODUCTION_LIST_GET = HOST_RELEASE_URL.concat("/user/thirdlevel/prod/brand/");
    //查询指定产品
    String URL_QUERY_PRODUCTION_INFO_GET = HOST_RELEASE_URL.concat("/user/thirdlevel/prod/query/");
    /**
     * 【修改】
     */
    //修改支付密码
    String URL_MODIFY_PAY_PASSWORD_POST = HOST_RELEASE_URL.concat("/user/paypass/update/");
    //修改登录密码
    String URL_MODIFY_LOGIN_PASSWORD_POST = HOST_RELEASE_URL.concat("/user/password/update/");
    /**
     * 【分享】
     */
    //获取贴牌信息
    String URL_GET_OEM_INFO_GET = HOST_RELEASE_URL.concat("/user/brand/query/id/");
    //获取轮播图信息
    String URL_GET_CAROUSEL_INFO_POST = HOST_RELEASE_URL.concat("/user/app/slideshow/query/brandid");
    /**
     * 【返佣】
     */
    String URL_RAKE_BACK_POST = HOST_RELEASE_URL.concat("/facade/withdraw/rebate/");
    /**
     * 【余额】
     */
    //余额查询
    String URL_BALANCE_QUERY_POST = HOST_RELEASE_URL.concat("/user/balance/profit/");
    /**
     * 【推送】
     */
    //平台消息
    String URL_PUSH_MESSAGE_PLATFORM_GET = HOST_RELEASE_URL.concat("/user/jpush/history/brand/");
    //个人消息
    String URL_PUSH_MESSAGE_PERSONAL_GET = HOST_RELEASE_URL.concat("/user/jpush/history/");
    /**
     * 【商户】
     */
    //商家
    String URL_MERCHANT_CERTIFICATION_POST = HOST_RELEASE_URL.concat("/user/shops/add/");
    String URL_MERCHANT_INFO_POST = HOST_RELEASE_URL.concat("/user/app/usersys/query/");
    //判断是否第一次登录
    String URL_ADJUST_IS_FIRST_LOGIN_POST = HOST_RELEASE_URL.concat("/user/verify/id/");
    /**
     * 【话费充值】
     */
    //手机归属地查询
    String URL_TELEPHONE_ATTRIBUTION_AND_FARE_PRICE_QUERY_POST = HOST_RELEASE_URL.concat("/user/added/phone/telquery/");
    //校验话费支付密码
    String URL_TELEPHONE_FARE_PAY_PASS_POST = HOST_RELEASE_URL.concat("/user/paypass/auth/");
    //话费价格查询
    String URL_TELEPHONE_FARE_COMMIT_POST = HOST_RELEASE_URL.concat("/facade/purchasebill/");
}
