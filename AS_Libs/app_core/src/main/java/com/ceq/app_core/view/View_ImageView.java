package com.ceq.app_core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ceq.app_core.R;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

@SuppressLint("AppCompatCustomView")
public class View_ImageView extends ImageView {
    public View_ImageView(Context context) {
        super(context);
    }

    public View_ImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorFilter(new LightingColorFilter(Color.BLACK,getResources().getColor(R.color.title_fontColor)));
    }

    public View_ImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
