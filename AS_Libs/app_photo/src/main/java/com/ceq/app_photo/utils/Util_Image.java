package com.ceq.app_photo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.ceq.app_core.bean.Bean_ImageOptions;
import com.ceq.app_core.framework.Framework_App;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_photo.R;
import com.ceq.app_photo.activity.Act_ImagePicker;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jph.takephoto.model.TResult;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/6/10.
 */
public class Util_Image {
    private static boolean isInit = false;

    public interface ImagePickerCallBack {
        void takeCancel();

        void takeFail(TResult result, String msg);

        void takeSuccess(TResult result);
    }

    public static void imageToIntentActivityByCustom(final Activity activity, boolean onlyTakePhoto, boolean enableCompress, boolean enableCrop, int maxPhotoCount, ImagePickerCallBack imagePickerCallBack) {
        Act_ImagePicker.imagePickerCallBack = imagePickerCallBack;
        Bean_ImageOptions bean_imageOptions = new Bean_ImageOptions();
        bean_imageOptions.setEnableCrop(enableCrop);
        bean_imageOptions.setEnableCompress(enableCompress);
        bean_imageOptions.setMaxPhotoCount(maxPhotoCount);
        bean_imageOptions.setOnlyTakePhoto(onlyTakePhoto);
        activity.startActivity(new Intent(activity, Act_ImagePicker.class).putExtra(Act_ImagePicker.Extra_Bean_ImageOptions, bean_imageOptions));
    }

    public static void imageToIntentActivityByCustom(final Activity activity, Bean_ImageOptions bean_imageOptions, ImagePickerCallBack imagePickerCallBack) {
        Act_ImagePicker.imagePickerCallBack = imagePickerCallBack;
        activity.startActivity(new Intent(activity, Act_ImagePicker.class).putExtra(Act_ImagePicker.Extra_Bean_ImageOptions, bean_imageOptions));
    }


    public static void imageToShowByFresco(SimpleDraweeView simpleDraweeView, Uri uri, int resizeWidthDp, int resizeHeightDp) {
        if (Util_Empty.isEmpty(uri) || Util_Empty.isEmpty(simpleDraweeView))
            return;
        initialize(simpleDraweeView.getContext());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(SizeUtils.dp2px(resizeWidthDp), SizeUtils.dp2px(resizeHeightDp)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        simpleDraweeView.setController(controller);
    }


    public static void imageToWatchDetail(final Context context, final List<String> al_photoPath, int startPosition) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_detail_photos, null);
        final TextView tv_position = (TextView) view.findViewById(R.id.tv_position);
        new ImageViewer.Builder(context, al_photoPath).setImageChangeListener(new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                tv_position.setText(String.valueOf(position + 1).concat("/").concat(String.valueOf(al_photoPath.size())));
            }
        }).setOverlayView(view).setStartPosition(startPosition).show();
    }


    private static void initialize(Context context) {
        if (isInit)
            return;
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context).setDownsampleEnabled(true).build();
        Fresco.initialize(context, config);
        isInit = true;
    }

    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),  Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    public static void scanPhoto(File imgFileName) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(imgFileName);
        mediaScanIntent.setData(contentUri);
        Framework_App.getInstance().sendBroadcast(mediaScanIntent);
    }

}
