package com.ceq.app_core.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.ceq.app_core.bean.Bean_LuckPan;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Runnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23 0023.
 */

class View_LuckyPan extends View {
    List<Bean_LuckPan> data = new ArrayList<>();
    Paint p_part;
    Paint p_innerText;
    Paint p_middle1, p_middle2, p_strokeInner, p_strokeOuter, p_stroke2, p_strokeOuterLight;
    Paint p_lightOn, p_lightOff;
    Paint paint5;
    Paint paint6;
    int partCount;
    int strokeOuterCount = 24;
    int pointsCount = 12;

    public View_LuckyPan(Context context) {
        this(context, null);
    }

    public View_LuckyPan(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View_LuckyPan(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    void setData(List<Bean_LuckPan> data) {
        this.data.clear();
        this.data.addAll(data);
        partCount = data.size();
    }

    void startRoll(final View viewToken, final float rollDegree, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        RotateAnimation mRotateDownAnim = new RotateAnimation(0, rollDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(5000);
        mRotateDownAnim.setFillAfter(true);
        mRotateDownAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mRotateDownAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewToken.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                float singleDegree = 360f / data.size();
                float realRollDegree = rollDegree > 360 ? rollDegree % 360f : rollDegree;
                int index = (int) ((int) (realRollDegree + singleDegree / 2f) / singleDegree);
                Bean_LuckPan bean_luckPan = data.get(index == data.size() ? 0 : index);
                if (util_Args_runnable != null)
                    util_Args_runnable.run(bean_luckPan);
                viewToken.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(mRotateDownAnim);
    }

    int r_partInner, r_middle1, r_middle1Text, r_middle2, r_strokeOuter, r_partInnerStroke, r_partInnerPointStroke;
    int w, h, w_half, h_half;
    int strokeOuterLightWidth = 10;
    int strokeOuterWidth = 20;
    int singleDegree;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Util_Log.logTest("qqqqq", "onSizeChanged");
        this.w = w;
        this.h = h;
        w_half = w / 2;
        h_half = h / 2;
        singleDegree = (int) (360f / partCount);
        System.err.println("==============" + w + "/" + h);
        r_partInner = w / 3;
        r_partInnerStroke = (int) (w / 2.95);
        r_partInnerPointStroke = (int) (w / 2.85);
        r_strokeOuter = (int) (w / 2.1);
        r_middle1 = (int) (w / 2.5);
        r_middle1Text = (int) (w / 4);
        r_middle2 = (int) (w / 2.1);

        p_middle1 = new Paint();
        p_middle1.setColor(Color.parseColor("#c31900"));
        p_middle1.setDither(true);
        p_middle1.setAntiAlias(true);
        float[] direction = new float[]{20, 20, 20};
        float ambient = 0.5f;
        float specular = 5;
        float blurRadius = 5;
        p_middle1.setMaskFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));

        p_middle2 = new Paint();
        p_middle2.setColor(Color.parseColor("#6f0d0c"));
        p_middle2.setDither(true);
        p_middle2.setAntiAlias(true);
        direction = new float[]{20, 20, 20};
        ambient = 0.5f;
        specular = 5;
        blurRadius = 5;
        p_middle2.setMaskFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));


        p_strokeOuterLight = new Paint();
        p_strokeOuterLight.setDither(true);
        p_strokeOuterLight.setAntiAlias(true);
        p_strokeOuterLight.setStrokeWidth(strokeOuterLightWidth);
        p_strokeOuterLight.setStyle(Paint.Style.STROKE);
        p_strokeOuterLight.setColor(Color.parseColor("#10ffffff"));
        p_strokeOuterLight.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL));

        p_strokeOuter = new Paint();
        p_strokeOuter.setDither(true);
        p_strokeOuter.setAntiAlias(true);
        p_strokeOuter.setStrokeWidth(strokeOuterWidth);
        p_strokeOuter.setStyle(Paint.Style.STROKE);
        direction = new float[]{10, 10, 10};
        ambient = 0.5f;
        specular = 5;
        blurRadius = 1;
        p_strokeOuter.setMaskFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));

        p_part = new Paint();
        p_part.setDither(true);
        p_part.setAntiAlias(true);

        p_strokeInner = new Paint();
        p_strokeInner.setColor(Color.parseColor("#aac31900"));
        p_strokeInner.setDither(true);
        p_strokeInner.setAntiAlias(true);
        p_strokeInner.setShadowLayer(strokeOuterWidth, 0, 0, Color.parseColor("#000000"));

        p_stroke2 = new Paint();
        p_stroke2.setColor(Color.parseColor("#6f0d0c"));
        p_stroke2.setDither(true);
        p_stroke2.setAntiAlias(true);

        p_lightOn = new Paint();
        p_lightOn.setDither(true);
        p_lightOn.setAntiAlias(true);
        p_lightOn.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        //p_lightOn.setMaskFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));
        p_lightOn.setShader(new RadialGradient(0, 0, 5, new int[]{
                Color.WHITE, Color.parseColor("#fbf5b9"), Color.parseColor("#fbeda2"), Color.parseColor("#fcf2bc")}, null,
                Shader.TileMode.CLAMP));

        p_lightOff = new Paint();
        p_lightOff.setDither(true);
        p_lightOff.setAntiAlias(true);
        direction = new float[]{10, 10, 10};
        ambient = 0.7f;
        specular = 5;
        blurRadius = 1;
        p_lightOff.setMaskFilter(new EmbossMaskFilter(direction, ambient, specular, blurRadius));
        p_lightOff.setColor(Color.parseColor("#feab03"));
        //p_lightOff.setShadowLayer(5, 0, 0, Color.BLACK);
        //p_lightOff.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        p_lightOff.setShader(new RadialGradient(50, 50, 50, new int[]{Color.parseColor("#feab03"), Color.parseColor("#f7a100"), Color.parseColor("#b41600"), Color.parseColor("#feab03")}, new float[]{0, 0.1F, 0.7F, 0.8F}, Shader.TileMode.CLAMP));


        p_innerText = new Paint();
        p_innerText.setColor(Color.parseColor("#720e0c"));
        p_innerText.setTextSize(w / 20);
        p_innerText.setTextAlign(Paint.Align.CENTER);
        p_innerText.setDither(true);
        p_innerText.setAntiAlias(true);
        p_innerText.setShadowLayer(5, 0, 0, Color.WHITE);

        paint5 = new Paint();
        paint5.setColor(Color.YELLOW);
        paint5.setDither(true);
        paint5.setAntiAlias(true);

        paint6 = new Paint();
        paint6.setColor(Color.WHITE);
        paint6.setDither(true);
        paint6.setAntiAlias(true);
        paint6.setTextSize(w / 15);
        paint6.setTextAlign(Paint.Align.CENTER);

        rectF_partInner = new RectF(w_half - r_partInner, h_half - r_partInner, w_half + r_partInner, h_half + r_partInner);
        rectF_partInnerStroke = new RectF(w_half - r_partInnerStroke, h_half - r_partInnerStroke, w_half + r_partInnerStroke, h_half + r_partInnerStroke);
        rectF_partInnerPoint = new RectF(w_half - r_partInnerPointStroke, h_half - r_partInnerPointStroke, w_half + r_partInnerPointStroke, h_half + r_partInnerPointStroke);
        rectF_partInnerText = new RectF(w_half - r_middle1Text, h_half - r_middle1Text, w_half + r_middle1Text, h_half + r_middle1Text);
        rectF_partInnerImage = new RectF(w_half - r_middle1Text + 50, h_half - r_middle1Text + 50, w_half + r_middle1Text + 50, h_half + r_middle1Text + 50);
        rectF_strokeOuter = new RectF(w_half - r_strokeOuter, h_half - r_strokeOuter, w_half + r_strokeOuter, h_half + r_strokeOuter);
    }

    RectF rectF_partInner, rectF_partInnerText, rectF_partInnerImage, rectF_partInnerStroke, rectF_strokeOuter, rectF_partInnerPoint;


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawCircle(w_half, h_half, r_middle2, p_middle2);
        canvas.drawCircle(w_half, h_half, r_middle1, p_middle1);
        canvas.drawCircle(w_half, h_half, r_partInnerStroke, p_strokeInner);
        canvas.rotate(-2 * singleDegree, w_half, h_half);
        for (int i = 0; i < partCount; i++) {
            p_part.setColor(i % 2 == 0 ? Color.parseColor("#ffcf01") : Color.parseColor("#f0652c"));
            canvas.drawArc(rectF_partInner, -singleDegree * i, singleDegree, true, p_part);
            Path path = new Path();
            path.addArc(rectF_partInnerText, -singleDegree * i/*singleDegree * i*/,/* singleDegree*/singleDegree);
            canvas.drawTextOnPath(data.get(i).getName(), path, 0, 0, p_innerText);
        }
        for (int i = 0; i < strokeOuterCount; i++) {
            p_strokeOuter.setColor(i % 2 == 0 ? Color.parseColor("#fdb0a0") : Color.parseColor("#b62b24"));
            canvas.drawArc(rectF_strokeOuter, 360f / strokeOuterCount * i, 360f / strokeOuterCount, false, p_strokeOuter);
        }
        for (int i = 0; i < pointsCount; i++) {
            canvas.rotate(360 / pointsCount, w_half, h_half);
            canvas.drawCircle(w / 4.2f, h / 4.2f, w / 80, i % 2 == 0 ? p_lightOn : p_lightOff);
        }
        canvas.drawCircle(w_half, h_half, r_strokeOuter + (strokeOuterWidth - strokeOuterLightWidth), p_strokeOuterLight);
        // canvas.save();
        canvas.restore();
        for (int i = 0; i < data.size(); i++) {
            int imgId = data.get(i).getImg();
            if (imgId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgId);
                canvas.drawBitmap(bitmap, w_half - bitmap.getWidth() / 2, rectF_partInnerText.top + bitmap.getWidth() / 2, null);
            }
            canvas.rotate(-singleDegree, w_half, h_half);
        }
    }
}
