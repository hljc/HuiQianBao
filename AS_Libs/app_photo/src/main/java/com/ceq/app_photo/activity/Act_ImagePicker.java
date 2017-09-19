package com.ceq.app_photo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.ceq.app_core.interfaces.Inter_Global;
import com.ceq.app_core.utils.core.Util_File;
import com.ceq.app_core.utils.core.Util_Init;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_photo.R;
import com.ceq.app_core.bean.Bean_ImageOptions;
import com.ceq.app_photo.utils.Util_Image;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.io.File;


/**
 * Created by Administrator on 2016/11/2.
 */

public class Act_ImagePicker extends TakePhotoActivity implements Inter_Global {
    TextView tv_takePhotos, tv_selectPhotos, tv_cancel;
    TakePhoto takePhoto;
    Bean_ImageOptions bean_imageOptions;
    //public static Class<? extends Activity> activityClass;
    public static Util_Image.ImagePickerCallBack imagePickerCallBack;
    public static String Extra_Bean_ImageOptions = "Extra_Bean_ImageOptions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageOption();
        init(R.layout.app_act_main_imagepicker, true, -1, false, null);
        takePhoto = getTakePhoto();
        configCompress();
    }

    private void initImageOption() {
        bean_imageOptions = (Bean_ImageOptions) getIntent().getSerializableExtra(Extra_Bean_ImageOptions);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        Util_Log.logE("图片工具", "takeCancel");
        if (imagePickerCallBack != null)
            imagePickerCallBack.takeCancel();
        finish();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Util_Log.logE("图片工具", "takeFail", result, msg);
        if (imagePickerCallBack != null)
            imagePickerCallBack.takeFail(result, msg);
        finish();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Util_Log.logE("图片工具", "takeSuccess", result, result.getImages().size());

        for (TImage tImage : result.getImages())
            Util_Log.logE("图片工具", tImage.getCompressPath(), tImage.isCompressed(), tImage.isCropped(), tImage.getCompressPath() != null ? Util_File.capacityToString(new File(tImage.getCompressPath()).length()) : "未压缩");
        if (imagePickerCallBack != null)
            imagePickerCallBack.takeSuccess(result);
        finish();

        //startActivity(new Intent(this, activityClass).putExtra(Act_PhotosWall.EXTRA_BEANS_TIMAGE, result.getImages()));
    }


    private void configCompress() {
        if (!bean_imageOptions.isEnableCompress())
            takePhoto.onEnableCompress(null, false);
        else
            takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(bean_imageOptions.getMaxCompressCapacity()).setMaxPixel(bean_imageOptions.getMaxCompressPixel()).enablePixelCompress(true).enableQualityCompress(true).create(), bean_imageOptions.isShowCompressProgress());
    }

    private CropOptions getCropOptions() {
        if (!bean_imageOptions.isEnableCrop())
            return null;
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(bean_imageOptions.getCropWidth()).setAspectY(bean_imageOptions.getCropHeight());
        // builder.setOutputX(cropWidth).setOutputY(cropHeight);
        builder.setWithOwnCrop(!bean_imageOptions.isSystemCrop());
        return builder.create();
    }

    @Override
    public View init(int layoutResID, boolean enableStatusBarSettings, int statusColor, boolean fitsSystemWindows, Util_Screen.Enum_BarFontColor enum_barFontColor) {
        return new Util_Init(this).init(layoutResID, enableStatusBarSettings, statusColor, fitsSystemWindows, enum_barFontColor);
    }

    @Override
    public void initView() {
        tv_takePhotos = (TextView) findViewById(R.id.tv_takePhotos);
        tv_selectPhotos = (TextView) findViewById(R.id.tv_selectPhotos);
        tv_selectPhotos.setVisibility(bean_imageOptions.isOnlyTakePhoto() ? View.GONE : View.VISIBLE);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewPager() {

    }

    @Override
    public void initFragment() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_takePhotos, tv_selectPhotos, tv_cancel);
    }


    @Override
    public void onClick(View v) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        if (v.getId() == R.id.tv_selectPhotos) {
            if (bean_imageOptions.getMaxPhotoCount() > 1) {
                if (bean_imageOptions.isSystemCrop())
                    takePhoto.onPickMultipleWithCrop(bean_imageOptions.getMaxPhotoCount(), getCropOptions());
                else
                    takePhoto.onPickMultiple(bean_imageOptions.getMaxPhotoCount());
                return;
            }
/*                if (isEnableCrop)
                    takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
                else
                    takePhoto.onPickFromDocuments();*/
            if (bean_imageOptions.isEnableCrop()) {
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromGallery();
            }
        } else if (v.getId() == R.id.tv_takePhotos) {
            if (bean_imageOptions.isEnableCrop())
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
            else
                takePhoto.onPickFromCapture(imageUri);
        } else if (v.getId() == R.id.tv_cancel) {
            onBackPressed();
        }
    }


}
