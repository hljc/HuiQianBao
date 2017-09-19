package com.ceq.app.main.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;


/**
 * Created by Administrator on 2017/6/4.
 */

@SuppressLint("AppCompatCustomView")
public class KeyboardLinearLayout extends LinearLayout {
    Bean_OKModule_UIOptions uiOptions;

    public KeyboardLinearLayout(Context context) {
        this(context, null);
    }

    public KeyboardLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Bean_OKModule_UIOptions getUiOptions() {
        return uiOptions;
    }

    public void setUiOptions(Bean_OKModule_UIOptions uiOptions) {
        this.uiOptions = uiOptions;
        onKeyUp();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (uiOptions.getKeyboardPressOnBackgroundResource() != 0)
                    setBackgroundResource((uiOptions.getKeyboardPressOnBackgroundResource() == 0 ? R.mipmap.kb_on_zdqb : uiOptions.getKeyboardPressOnBackgroundResource()));
                else
                    setBackgroundColor(uiOptions.getKeyboardPressOnBackgroundColor() == 0 ? getResources().getColor(R.color.text_color_0) : uiOptions.getKeyboardPressOnBackgroundColor());
                break;
            case MotionEvent.ACTION_UP:
                onKeyUp();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void onKeyUp() {
        if (uiOptions.getKeyboardPressOffBackgroundResource() != 0)
            setBackgroundResource((uiOptions.getKeyboardPressOffBackgroundResource() == 0 ? R.mipmap.kb_off_zdqb : uiOptions.getKeyboardPressOffBackgroundResource()));
        else
            setBackgroundColor(uiOptions.getKeyboardPressOffBackgroundColor() == 0 ? getResources().getColor(R.color.text_color_1) : uiOptions.getKeyboardPressOffBackgroundColor());
    }
}
