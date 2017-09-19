package com.ceq.app_core.utils.core;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.ceq.app_core.R;
import com.ceq.app_core.constants.Constants_File$SharePreferences;
import com.ceq.app_core.framework.Framework_App;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_File implements Constants_File$SharePreferences {
    private static File sdDir, appDir;

    public static File useFileByUrl(Uri uri) {

        return null;
    }

    public static String capacityToString(long capacity) {
        double value = 0;
        String unit = null;
        if (capacity < 1024) {
            value = (double) capacity;
            unit = "B";
        } else if (capacity >= 1024 && capacity < Math.pow(1024, 2)) {
            value = (double) capacity / 1024;
            unit = "KB";
        } else if (capacity >= Math.pow(1024, 2) & capacity < Math.pow(1024, 3)) {
            value = (double) capacity / (1024 * 1024);
            unit = "MB";
        } else if (capacity >= Math.pow(1024, 3)) {
            value = (double) capacity / Math.pow(1024, 3);
            unit = "GB";
        }
        return new DecimalFormat("0.00").format(value) + unit;
    }

    /**
     * 过滤文件
     *
     * @return
     * @throws Exception
     */
    public static File fileToCreate(String dirName, String fileName, InputStream inputStream, boolean isPlain) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Util_Toast.toast("请检查内存卡是否存在！");
            return null;
        }
        fileToAppDir();
        String path = appDir + "/" + (Util_Empty.isEmpty(dirName) ? "" : dirName) + "/" + (Util_Empty.isEmpty(fileName) ? "" : fileName);
        File file = new File(path);
        try {
            if (Util_Empty.isEmpty(fileName))
                file.mkdirs();
            else {
                if (isPlain) {
                    if (Util_Empty.isEmpty(inputStream))
                        return null;
                    String line = null;
                    BufferedReader br = null;

                    br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"), true);
                    while ((line = br.readLine()) != null) {
                        pw.println(line);
                    }
                    br.close();
                    pw.close();
                } else {
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    int ch;
                    while ((ch = bis.read()) != -1) {
                        bos.write(ch);
                    }
                    bos.flush();
                    bos.close();
                    bis.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File fileToAppDir() {
        if (appDir != null)
            return appDir;
        appDir = new File(Environment.getExternalStorageDirectory(), Framework_App.getInstance().getResources().getString(R.string.app_name));
        if (!appDir.exists())
            appDir.mkdirs();
        return appDir;
    }


  /*  public static String fileRealPathByUri(Uri uri) {
        Cursor c = Framework_App.getInstance().getContentResolver().query(uri, null, null, null, null);
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        c.moveToNext();
        for (String s : c.getColumnNames()) {
            Util_Log.logE("key", s, "value", c.getString(c.getColumnIndex(s)));
        }
        String path = c.getString(c.getColumnIndex("_data"));
        c.close();
        return path;
    }*/

    public static String getFileNameByUri(final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = Framework_App.getInstance().getContentResolver().query(uri, new String[]{MediaStore.Files.FileColumns.DISPLAY_NAME}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static void fileToWriteObj(Object object, File targetPath) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(targetPath));
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static Object fileToReadObj(File srcObjPath) {
        if (!srcObjPath.exists())
            return null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(srcObjPath));
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null)
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    /**
     * Get a.html file path from a.html Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param uri The Uri to query.
     * @author paulburke
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathByUri(final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(Framework_App.getInstance(), uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                String a = Environment.getExternalStorageDirectory() + "/" + split[1];
                Util_Log.logE("加载", isExternalStorageDocument(uri), uri, type, "primary".equalsIgnoreCase(type), new File(a).exists(), a);
                Util_Log.logE("加载", Environment.getExternalStorageDirectory(), Environment.getExternalStorageDirectory().getParent());
                if ("primary".equalsIgnoreCase(type))
                    return Environment.getExternalStorageDirectory().toString().concat("/").concat(split[1]);
                else
                    return "/storage/".concat(type).concat("/").concat(split[1]);
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a.html file path.
     */
    private static String getDataColumn(Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = Framework_App.getInstance().getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
