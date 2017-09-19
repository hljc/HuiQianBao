package com.ceq.app_core.utils.extend.qrcode.interfaces.implement;


import android.app.Activity;
import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_QRCode_Base {
    Bitmap createQRCode(Activity activity, int qrSize, String content) ;
    Bitmap createLogoQRCode(Activity activity, int qrSize, String content);
}
