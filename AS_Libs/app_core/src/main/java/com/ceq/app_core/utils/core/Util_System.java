package com.ceq.app_core.utils.core;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import android.provider.ContactsContract;

import com.ceq.app_core.bean.Bean_SystemContacts;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_App;


/**
 * Created by Administrator on 2016/4/29.
 */
public class Util_System {
    private static int exitCount = 1;
    private static final Handler handler = new Handler();

    public static void systemToExitAndToast(String toastText, int clickCount) {
        if (exitCount < clickCount) {
            if (!Util_Empty.isEmpty(toastText))
                Util_Toast.toast(toastText);
        } else {
            Framework_App.getInstance().stopService();
            for (Activity activity : Framework_Activity.getActList())
                activity.finish();
            Process.killProcess(Process.myPid());
        }
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                exitCount = 1;
            }
        }, 1500);
        exitCount++;
    }

    public static void selectContactInfoFromSystemContactsOnActivityResult(Intent data, Util_Runnable.Util_TypeRunnable<Bean_SystemContacts> runnable) {
        if (data == null)
            return;
        Application application = Framework_App.getInstance();
        Uri contactData = data.getData();
        ContentResolver contentResolver = application.getContentResolver();
        Cursor cursor = contentResolver.query(contactData, null, null, null, null);
        Bean_SystemContacts bean = new Bean_SystemContacts();
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            bean.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            bean.setHasPhone(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
            bean.setContactId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
            if (bean.getHasPhone().equals("1")) {
                Cursor phones = application.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, new StringBuilder(ContactsContract.CommonDataKinds.Phone.CONTACT_ID).append("=").append(bean.getContactId()).toString(), null, null);
                while (phones.moveToNext())
                    bean.setPhoneNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                phones.close();
            }
            cursor.close();
        }
        if (runnable != null)
            runnable.run(bean);
    }

    public static Bean_SystemContacts getConatactInfoByTelephoneNumber(String telephoneNumber) {
        Application application = Framework_App.getInstance();
        Bean_SystemContacts bean = new Bean_SystemContacts();
        Cursor cursor =application.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                new StringBuilder(ContactsContract.CommonDataKinds.Phone.NUMBER).
                        append("='").append(telephoneNumber).append("'").toString(), null, null);
        boolean isExists = cursor.getCount() > 0;
        if (isExists) {
            cursor.moveToFirst();
            bean.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            bean.setHasPhone(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
            bean.setContactId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
            if (bean.getHasPhone().equals("1")) {
                Cursor phones = application.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, new StringBuilder(ContactsContract.CommonDataKinds.Phone.CONTACT_ID).append("=").append(bean.getContactId()).toString(), null, null);
                while (phones.moveToNext())
                    bean.setPhoneNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                phones.close();
            }
        }
        cursor.close();
        return isExists ? bean : null;
    }
}
