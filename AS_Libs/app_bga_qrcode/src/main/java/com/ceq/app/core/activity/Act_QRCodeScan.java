package com.ceq.app.core.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.example.app_qrcode.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class Act_QRCodeScan extends Framework_Activity implements QRCodeView.Delegate {
    private QRCodeView zxv_scan;
    ImageView iv_back;
    TextView tv_switchLight;
    final String lightOff = "关灯", lightOn = "开灯";
    public static final String Extra_String_QRCode_Result = "Extra_String_QRCode_Result";
    public static final String[] permissions_camera = {Manifest.permission.CAMERA, Manifest.permission.VIBRATE};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_qrcodescan);
    }


    @Override
    protected void onResume() {
        super.onResume();
        zxv_scan.startCamera();
        //zxv_scan.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        zxv_scan.showScanRect();
        zxv_scan.startSpot();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zxv_scan.stopCamera();
    }

    @Override
    protected void onDestroy() {
        zxv_scan.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Util_Log.logTest("二维码结果",result);
        vibrate();
        zxv_scan.stopSpot();
        setResult(RESULT_OK, new Intent().putExtra(Extra_String_QRCode_Result, result));
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Util_Toast.toast("打开相机出错,请查看相机权限或者重启");
    }


    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "扫一扫", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        tv_switchLight = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, lightOn, View.VISIBLE);

        zxv_scan = (ZXingView) findViewById(R.id.zxv_scan);
        zxv_scan.setDelegate(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);
        Util_View.viewOnClick(this, tv_switchLight);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v.getId() == tv_switchLight.getId()) {
            switch (tv_switchLight.getText().toString()) {
                case lightOn:
                    zxv_scan.openFlashlight();
                    tv_switchLight.setText(lightOff);
                    break;
                case lightOff:
                    zxv_scan.closeFlashlight();
                    tv_switchLight.setText(lightOn);
                    break;
            }
        }
    }
}