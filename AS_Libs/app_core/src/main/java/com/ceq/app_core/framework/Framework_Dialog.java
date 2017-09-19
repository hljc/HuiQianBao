package com.ceq.app_core.framework;


import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.ceq.app_core.R;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Permission;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_View;
import com.yanzhenjie.permission.AndPermission;

import java.util.HashMap;

public class Framework_Dialog extends Framework_Activity implements Util_Dialog.DialogContext {
    public static String Extra_String_Dialog = "Extra_String_Dialog";
    public static String Extra_Boolean_Is_Can_Back = "Extra_Boolean_Is_Can_Back";
    public static String Extra_Boolean_Is_Full_Screen = "Extra_Boolean_Is_Full_Screen";
    public static String Extra_Boolean_Is_Land_Screen = "Extra_Boolean_Is_Land_Screen";
    public static String Extra_Boolean_Is_Sensor_Land_Screen = "Extra_Boolean_Is_Sensor_Land_Screen";
    public static String Extra_Int_Layout_Id = "Extra_Int_Layout_Id";
    public static String Extra_Int_DialogBackgroundColor = "Extra_Int_DialogBackgroundColor";
    private Util_Screen.ScreenOrientationSensor screenOrientationSensor;
    public boolean isCanceledOnTouchOutside, isCanBack, isFullScreen, isLandScreen, isSensorLandScreen;

    private RelativeLayout rl_background;
    private View view;
    private Util_Dialog.ActDialog actDialog;
    private String dialogKey;
    static HashMap<String, Util_Dialog.ActDialog> hashMap = new HashMap<>();

    public static HashMap<String, Util_Dialog.ActDialog> getHashMap() {
        return hashMap;
    }

    int dialogBackgroundColor = Color.argb(150, 50, 50, 50);

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        actDialog = hashMap.get(dialogKey = getIntent().getStringExtra(Extra_String_Dialog));
        dialogBackgroundColor = getIntent().getIntExtra(Extra_Int_DialogBackgroundColor, dialogBackgroundColor);
        isCanBack = getIntent().getBooleanExtra(Extra_Boolean_Is_Can_Back, false);
        isFullScreen = getIntent().getBooleanExtra(Extra_Boolean_Is_Full_Screen, false);
        isLandScreen = getIntent().getBooleanExtra(Extra_Boolean_Is_Land_Screen, false);
        isSensorLandScreen = getIntent().getBooleanExtra(Extra_Boolean_Is_Sensor_Land_Screen, false);
        Util_Screen.screenToFullOrLand(this, isFullScreen, isLandScreen);
        init(R.layout.app_framework_dialog,false);
    }


    @Override
    protected void onResume() {
        actDialog.onDialogStart();
        if (isSensorLandScreen)
            (screenOrientationSensor = Util_Screen.screenOrientationBySensor(getActivity())).open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (isSensorLandScreen)
            screenOrientationSensor.close();
        super.onPause();
    }

    SensorEventListener sensorEventListener = new SensorEventListener() {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[0] >= 7) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (event.values[0] <= -7) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == rl_background.getId())
            if (isCanceledOnTouchOutside)
                dismiss();
        if (actDialog != null)
            actDialog.onClick(view);
    }


    @Override
    public void initView() {
        rl_background = (RelativeLayout) findViewById(R.id.rl_background);
        rl_background.setBackgroundColor(dialogBackgroundColor);
        if (actDialog != null) {
            actDialog.initDialogData();
            rl_background.addView(actDialog.initDialogView(this, view = LayoutInflater.from(getActivity()).inflate(getIntent().getIntExtra(Extra_Int_Layout_Id, 0), rl_background, false)));
            Util_View.viewOnClick(this, view);
        }
    }

    @Override
    public void initViewPager() {

    }

    @Override
    public void initFragment() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, rl_background);
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hashMap.remove(dialogKey);
        if (actDialog != null)
            actDialog.onDismissListener();
    }

    @Override
    public void onBackPressed() {
        if (!isCanBack)
            return;
        super.onBackPressed();
    }

    @Override
    public Framework_Dialog getDialog() {
        return this;
    }

    @Override
    public void dismiss() {
        isCanBack = true;
        onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AndPermission.with(getActivity()).requestCode(requestCode).permission(permissions).callback(Util_Permission.listener).start();
    }

}
