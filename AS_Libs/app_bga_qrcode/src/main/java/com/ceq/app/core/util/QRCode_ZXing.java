package com.ceq.app.core.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.ceq.app_core.utils.extend.qrcode.abstracts.Util_QRCode;
import com.example.app_qrcode.R;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class QRCode_ZXing extends Util_QRCode {

    @Override
    public void init(Object... initParams) {

    }

    public Bitmap createQRCode(Activity activity, int qrSize, String content) {
        return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(activity, qrSize));
    }

    public Bitmap createLogoQRCode(Activity activity, int qrSize, String content) {
        Bitmap logoBitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.app_logo);
        return QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(activity, qrSize), Color.WHITE, logoBitmap);
    }
}