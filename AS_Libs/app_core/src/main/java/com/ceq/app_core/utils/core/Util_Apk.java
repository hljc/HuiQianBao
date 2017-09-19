package com.ceq.app_core.utils.core;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.ceq.app_core.R;
import com.ceq.app_core.framework.Framework_App;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.blankj.utilcode.util.FileUtils.deleteDir;


/**
 * Created by Administrator on 2016/4/29.
 */
public class Util_Apk {
    private static boolean isUpdatingApk;//正在更新apk
    public static boolean isPrintLog;
    public static final String Apk_Download_Success = "下载完成，点击安装！";

    public static void apkToDownloadByCustom(Context context, final String apkDownloadUrl, final Util_Runnable.Util_ArgsRunnable util_downloading, final Util_Runnable.Util_ArgsRunnable util_error) {
        if (isUpdatingApk) {
            Util_Toast.toast("已经在下载");
            return;
        }
        if (Util_Empty.isEmpty(apkDownloadUrl)) return;
        final String appName = Framework_App.getInstance().getString(R.string.app_name);

        Util_Http.httpToFile(context, apkDownloadUrl, new Util_Http.HttpHeadersAndFile() {

            @Override
            public PostRequest addFile(PostRequest postRequest) {
                return null;
            }

            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealFileListener(context, null, null, appName.concat(".apk"), false, false) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            StringBuilder stringBuilder = new StringBuilder();


            @Override
            public void onDealResult(String data, File file, com.lzy.okgo.model.Response response) {

            }

            @Override
            public void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                Util_Log.logTest("下载onProgress", currentSize, totalSize, progress, networkSpeed);
                isUpdatingApk = true;
                builder.setProgress(100, (int) (progress * 100f), false);
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(Util_File.capacityToString(currentSize)).append("/").append(Util_File.capacityToString(totalSize));
                builder.setContentInfo(stringBuilder.toString());
                if (progress == 1) {
                    ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
                    isUpdatingApk = false;
                    builder.setContentText("下载完成，点击安装");
                    builder.setAutoCancel(true);
                    Intent intent = installApk((Activity) context);
                    if (intent != null) {
                        PendingIntent pi = PendingIntent.getActivities(context, 0, new Intent[]{intent}, PendingIntent.FLAG_CANCEL_CURRENT);
                        builder.setContentIntent(pi);
                    }
                    ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, builder.build());
                    installApk((Activity) context);
                } else
                    ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, builder.build());
                if (util_downloading != null)
                    util_downloading.run(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                Util_Log.logTest("下载onBefore", request);
                Util_Toast.toast("开始后台下载，请不要退出程序！");
                isUpdatingApk = true;
                File file = getApkFile();
                if (file != null && file.exists())
                    file.delete();
                builder.setTicker((appName).concat("开始后台下载，请不要退出程序！"));
                builder.setSmallIcon(R.mipmap.app_logo);
                builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_logo));
                builder.setPriority(Integer.MAX_VALUE);
                builder.setContentText("正在下载");
                builder.setContentTitle(appName);
                // builder.setOngoing(true);
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                if (util_error != null)
                    util_error.run(response);
                Util_Log.logE("APK下载异常", apkDownloadUrl, response);
                Util_Toast.toast("下载失败，请稍后重试！");
                builder.setContentText("APK下载异常");
                builder.setAutoCancel(true);
                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, builder.build());
                isUpdatingApk = false;
            }

        });
    }

    public static Util_Runnable.Util_ArgsRunnable foreignDownloadApk(final Activity activity, final TextView tv_content) {
        final StringBuilder sb = new StringBuilder();
        final DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        return new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                if ((float) data[2] == 1) {
                    tv_content.setText(Apk_Download_Success);
                    tv_content.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Util_Apk.installApk(activity);
                        }
                    });
                } else {
                    sb.delete(0, sb.length());
                    tv_content.setText(sb.append("已下载").append(decimalFormat.format((float) data[2] * 100)).append("%，请耐心等待..."));
                }
            }
        };
    }

    public static Util_Runnable.Util_ArgsRunnable foreignDownloadApkError(final Activity activity, final TextView tv_content, final String downloadUrl) {
        return new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                tv_content.setText("下载失败，点击重新下载！");
                tv_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util_Apk.apkToDownloadByCustom(activity, downloadUrl, foreignDownloadApk(activity, tv_content), foreignDownloadApkError(activity, tv_content, downloadUrl));
                    }
                });
            }
        };
    }

    /**
     * 从一个apk文件去获取该文件的版本信息
     * <p>
     * 本应用程序上下文
     *
     * @param apkFile APK文件的路径。如：/sdcard/download/XX.apk
     * @return
     */
    public static String getVersionNameFromApk(File apkFile) {
        if (apkFile == null)
            return "0.0.0";
        PackageManager pm = Framework_App.getInstance().getPackageManager();
        Util_Log.logTest(apkFile.exists());
        PackageInfo packInfo = pm.getPackageArchiveInfo(apkFile.getPath(), PackageManager.GET_ACTIVITIES);
        if (packInfo != null) {
            ApplicationInfo appInfo = packInfo.applicationInfo;
            String appName = pm.getApplicationLabel(appInfo).toString();
            String packageName = appInfo.packageName;  //得到安装包名称
            String version = packInfo.versionName;       //得到版本信息
            Util_Log.logTest(version);
        }
        return packInfo == null ? "0.0.0" : packInfo.versionName;
    }

    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值.
     *
     * @return
     * @author SHANHY
     * @date 2015-8-7
     */
    public static boolean isApkDebug() {
        try {
            ApplicationInfo info = Framework_App.getInstance().getApplicationInfo();
            return isPrintLog || (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    public static Intent installApk(Activity activity) {
        //调用系统安装程序
        File file = getApkFile();
        if (file == null || !file.exists())
            return null;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        activity.startActivity(intent);
        return intent;
    }

    public static File getApkFile() {
        File file = new File(Util_File.fileToAppDir(), Framework_App.getInstance().getString(R.string.app_name).concat(".apk"));
        return file.exists() ? file : null;
    }

    public static void skipToQQ(Activity activity, String qqNum) {
        if (AppUtils.isInstallApp("com.tencent.mobileqq")) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new StringBuilder("mqqwpa://im/chat?chat_type=wpa&uin=").append(qqNum).append("&version=1").toString())));
        } else
            Util_Toast.toast("您还未安装QQ应用");
    }

    public static void skipToWX(Activity activity, String weiXinNumber) {
        try {
            // 获取剪贴板管理服务
            ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            //将文本数据（微信号）复制到剪贴板
            cm.setText(weiXinNumber);
            //跳转微信
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            activity.startActivity(intent);
            Util_Toast.toast("微信号已复制到粘贴板，请使用");
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Util_Toast.toast("您还未安装微信应用");
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize() {
        Context context = Framework_App.getInstance();
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return Util_File.capacityToString(cacheSize);
    }

    /**
     * 清除缓存
     */
    public static void clearAllCache() {
        Context context = Framework_App.getInstance();
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    // 获取文件大小
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
