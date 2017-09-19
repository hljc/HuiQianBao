package com.ceq.app_core.utils.core;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.ceq.app_core.framework.Framework_App;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Administrator on 2016/4/24.
 */
public class Util_Permission {
    public static final String[] permissions_base = {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] permissions_location = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    public static final String[] permissions_sensor = {Manifest.permission.BODY_SENSORS};
    public static final int Permission_Code_Core = 100;
    private static Activity activity;
    private static PermissionCallBack permissionCallBack;

    public interface PermissionCallBack {
        void success();

        void fail();
    }

    //跳转到权限页面
    public static Intent permissionByIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.middle_settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", Framework_App.getInstance().getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.middle_settings", "com.android.middle_settings.InstalledAppDetails");
            localIntent.putExtra("com.android.middle_settings.ApplicationPkgName", Framework_App.getInstance().getPackageName());
        }
        return localIntent;
    }

    public static void openPermission(Activity activity,  PermissionCallBack permissionCallBack,String[]... permission) {
        if(permission==null)
            return;
        List<String> list=new ArrayList<>();
        for (int i = 0; i <permission.length ; i++) {
            list.addAll(Arrays.asList(permission[i]));
        }
        String[] permissions=list.toArray(new String[]{});
        Util_Permission.permissionCallBack = permissionCallBack;
        if (!AndPermission.hasPermission(activity, permissions))
            AndPermission.with(Util_Permission.activity = activity).requestCode(Permission_Code_Core).permission(permissions).callback(Util_Permission.listener).start();
        else if (permissionCallBack != null)
            permissionCallBack.success();
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private static RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int i, Rationale rationale) {
            AndPermission.rationaleDialog(activity, rationale).show();
        }
    };
    //权限回调
    public static PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            Util_Log.logPermission("成功", requestCode, grantedPermissions);
            if (permissionCallBack != null)
                permissionCallBack.success();
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            Util_Log.logPermission("失败", requestCode, deniedPermissions);
            if (permissionCallBack != null)
                permissionCallBack.fail();
          /*  // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                // 第一种：用默认的提示语。
                //AndPermission.defaultSettingDialog(activity, 1).show();

                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(activity, Permission_Code_Core)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();
*/
            // 第三种：自定义dialog样式。
            // SettingService settingService =
            //    AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
            // 你的dialog点击了确定调用：
            // settingService.execute();
            // 你的dialog点击了取消调用：
            // settingService.cancel();
        }
    };

}
