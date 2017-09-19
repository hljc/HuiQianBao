package com.ceq.app.core.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.activity.Act_Main_Login;
import com.ceq.app.core.bean.Bean_Properties;
import com.ceq.app.core.bean.Bean_UserInfo;
import com.ceq.app_core.bean.Bean_BaseUserInfo;
import com.ceq.app_core.framework.Framework_App;
import com.ceq.app_core.framework.Framework_Dialog;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.im.abstracts.Util_IM;
import com.ceq.app_core.utils.extend.im.enums.Enum_Platform_IM;
import com.ceq.app_core.utils.extend.pay.abstracts.Util_Pay;
import com.ceq.app_core.utils.extend.pay.enums.Enum_Platform_Pay;
import com.ceq.app_core.utils.extend.push.abstracts.Util_Push;
import com.ceq.app_core.utils.extend.push.enums.Enum_Platform_Push;
import com.ceq.app_core.utils.extend.sms.abstracts.Util_SMS;
import com.ceq.app_core.utils.extend.sms.enums.Enum_Platform_SMS;
import com.ceq.app_xinli_onekey.core.bootstrap.bean.Bean_OneKeyBootstrap;
import com.ceq.dao.Bean_PropertiesDao;
import com.ceq.dao.Bean_UserInfoDao;
import com.ceq.dao.DaoMaster;
import com.ceq.dao.DaoSession;
import com.lzy.okgo.model.Response;

import org.greenrobot.greendao.query.QueryBuilder;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public abstract class Abstract_App extends Framework_App {
    public static Bean_OneKeyBootstrap bean_oneKeyBootstrap;
    public static Bean_UserInfo bean_userInfo;
    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Util_Pay.initial(Enum_Platform_Pay.支付宝, "");
        Util_IM.initial(Enum_Platform_IM.环信, "");
        Util_Pay.initial(Enum_Platform_Pay.微信, "");
        Util_SMS.initial(Enum_Platform_SMS.Mob, "", "");
        Util_Push.initial(Enum_Platform_Push.极光, "");
        initOneKey();
    }

    @Override
    public void initialGreenDao() {
        daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "db_base").getEncryptedWritableDb(getPackageName())/*getWritableDb()*/).newSession();
        QueryBuilder<?> queryBuilder = daoSession.getBean_UserInfoDao().queryBuilder();
        if (queryBuilder.count() > 0) {
            bean_userInfo = (Bean_UserInfo) queryBuilder.orderDesc(Bean_UserInfoDao.Properties.ModifyDate).limit(1).unique();
            queryBuilder = daoSession.getBean_PropertiesDao().queryBuilder();
            if (queryBuilder.count() > 0)
                bean_userInfo.setBean_properties((Bean_Properties) queryBuilder.where(Bean_PropertiesDao.Properties.Pid.eq(bean_userInfo.getPid())).unique());
            Util_Log.logTest(bean_userInfo, queryBuilder.where(Bean_PropertiesDao.Properties.Pid.eq(bean_userInfo.getPid())).unique());
        }
    }

    @Override
    public void updateDatabaseOnPause() {
        if (bean_userInfo != null) {
            daoSession.getBean_UserInfoDao().insertOrReplaceInTx(bean_userInfo);
            if (bean_userInfo.getBean_properties() != null) {
                if (!bean_userInfo.getBean_properties().getRememberPassword()) {
                    bean_userInfo.setPassword(null);
                }
                daoSession.getBean_PropertiesDao().insertOrReplaceInTx(bean_userInfo.getBean_properties());
                daoSession.getBean_UserInfoDao().insertOrReplaceInTx(bean_userInfo);
            }
        }
        bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().setShareUrl(bean_userInfo == null ? null : bean_userInfo.getPhone(),String.valueOf(bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId()));
    }

    @Override
    public Bean_BaseUserInfo getBean_BaseUserInfo() {
        return bean_userInfo;
    }


    @Override
    public void logout(final Context context) {
        Util_IM util_im = Util_IM.getInstance();
        if (util_im != null)
            util_im.logout(null);
        if (bean_userInfo != null) {
            bean_userInfo.setPassword(null);
            daoSession.getBean_UserInfoDao().insertOrReplaceInTx(bean_userInfo);
        }
        context.startActivity(new Intent(context, Act_Main_Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        initOneKey();
    }

    @Override
    public boolean beforeHttpDealResult(final Activity activity, String data, Response response) {
        String result = JSON.parseObject(data).getString(Http_Key_Name);
        if (result != null)
            switch (result) {
                case "000005":
                    Util_Dialog.showDefaultDialog(new View(activity), "该账号长期处于登录状态,请重新登录核实信息!", "确定", null, new Util_Dialog.DialogListener() {

                        @Override
                        public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {

                        }

                        @Override
                        public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                            logout(activity);
                        }

                        @Override
                        public void onDismissListener() {

                        }
                    });
                    return false;
                case "999999":
                    Util_Dialog.showDefaultDialog(new View(activity), JSON.parseObject(data).getString(Http_Key_Toast), "我知道了", null, new Util_Dialog.DialogListener() {

                        @Override
                        public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {

                        }

                        @Override
                        public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                            framework_dialog.dismiss();
                        }

                        @Override
                        public void onDismissListener() {

                        }
                    });
                    return false;
            }
        return true;
    }

    public abstract void initOneKey();

    @Override
    public void startService() {

    }

    @Override
    public void stopService() {

    }
}
