package com.ceq.app_core.framework;


import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.Utils;
import com.ceq.app_core.bean.Bean_BaseUserInfo;
import com.ceq.app_core.constants.Constants_File$SharePreferences;
import com.ceq.app_core.interfaces.Inter_Global_Service;
import com.ceq.app_core.utils.core.Util_Apk;
import com.ceq.app_core.utils.core.Util_Handler;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.extend.lbs.abstracts.Util_LBS;
import com.ceq.app_core.utils.extend.lbs.enums.Enum_Platform_LBS;
import com.ceq.app_core.utils.extend.login.abstracts.Util_Login;
import com.ceq.app_core.utils.extend.login.enums.Enum_Platform_Login;
import com.ceq.app_core.utils.extend.picker.abstracts.Util_Picker;
import com.ceq.app_core.utils.extend.picker.enums.Enum_Platform_Picker;
import com.ceq.app_core.utils.extend.qrcode.abstracts.Util_QRCode;
import com.ceq.app_core.utils.extend.qrcode.enums.Enum_Platform_QRCode;
import com.ceq.app_core.utils.extend.share.abstracts.Util_Share;
import com.ceq.app_core.utils.extend.share.enums.Enum_Platform_Share;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.greendao.query.QueryBuilder;


public abstract class Framework_App extends MultiDexApplication implements Constants_File$SharePreferences, Inter_Global_Service, Util_Http.HttpDealFilterListener {
    private static Framework_App app_main;

    @Override
    public void onCreate() {
        super.onCreate();
        app_main = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        Util_Apk.isPrintLog=true;
        Utils.init(this);
        CrashUtils.init();
        QueryBuilder.LOG_SQL = Util_Apk.isApkDebug();
        QueryBuilder.LOG_VALUES = Util_Apk.isApkDebug();
        initialGreenDao();
        Util_Http.init(this);
        Fresco.initialize(this);
        Util_Handler.initHandler();
        Util_Login.initial(Enum_Platform_Login.Mob);
        Util_Share.initial(Enum_Platform_Share.Mob);
        Util_Picker.initial(Enum_Platform_Picker.QQ);
        Util_LBS.initial(Enum_Platform_LBS.百度);
        Util_QRCode.initial(Enum_Platform_QRCode.ZXing);

        startService();

        Util_Log.logApp("Application启动", this);
        //AutoLayoutConifg.getInstance().useDeviceSize().init(this);
    }

    public abstract void initialGreenDao();

    public abstract void updateDatabaseOnPause();

    public abstract Bean_BaseUserInfo getBean_BaseUserInfo();

    public abstract void logout(Context context);

    public static Framework_App getInstance() {
        return app_main;
    }

}
