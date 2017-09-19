package com.ceq.app_core.utils.core;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.ceq.app_core.framework.Framework_Dialog;
import com.ceq.app_core.interfaces.Inter_Global_Init;


/**
 * Created by Administrator on 2016/12/21.
 */

public class Util_Init {
    private Activity activity;
    private View view;
    private Inter_Global_Init inter_global_init;

    public Util_Init(Inter_Global_Init inter_global_init) {
        this.inter_global_init = inter_global_init;
        //  Util_Log.logI("初始化所属实例", inter_global_init instanceof Activity, inter_global_init instanceof Fragment);
        if (inter_global_init instanceof Activity)
            activity = (Activity) inter_global_init;
        else if (inter_global_init instanceof Fragment)
            activity = ((Fragment) inter_global_init).getActivity();
    }

    public View init(int layoutResID, boolean enableStatusBarSettings, int statusColor, boolean fitsSystemWindows, Util_Screen.Enum_BarFontColor enum_barFontColor) {
        view = LayoutInflater.from(activity).inflate(layoutResID, (ViewGroup) activity.getWindow().getDecorView(), false);
        if (inter_global_init instanceof Activity)
            activity.setContentView(view);
        else if (inter_global_init instanceof Fragment)
            inter_global_init.setContentView(view);
        view.setFitsSystemWindows(fitsSystemWindows);
        if (enableStatusBarSettings) {
            if (!fitsSystemWindows) {
                if (activity instanceof Framework_Dialog)
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                else
                    BarUtils.setStatusBarColor(activity, Color.TRANSPARENT, 0);
            } else
                Util_Screen.statusBarToTranslucent(activity, statusColor, enum_barFontColor);
        }
        inter_global_init.initView();
        inter_global_init.initData();
        inter_global_init.initViewPager();
        inter_global_init.initFragment();
        inter_global_init.initAdapter();
        inter_global_init.initListener();
        return view;
    }

    //View
    public View initView(int layoutId, int viewId, int visibility) {
        View v = ((layoutId == 0 ? view : view.findViewById(layoutId)).findViewById(viewId));
        v.setVisibility(visibility);
        return v;
    }

    //View
    public View initView(View parent, int layoutId, int viewId, int visibility) {
        View v = parent.findViewById(layoutId == 0 ? viewId : layoutId);
        if (layoutId != 0)
            v = v.findViewById(viewId);
        v.setVisibility(visibility);
        return v;
    }

    public View initView(int layoutId, int viewId) {
        return initView(layoutId, viewId, View.VISIBLE);
    }

    //ImageView
    public ImageView initImageView(int layoutId, int viewId, int imgId, int visibility) {
        ImageView imageView = (ImageView) initView(layoutId, viewId, visibility);
        imageView.setImageResource(imgId);
        return imageView;
    }

    public ImageView initImageView(View parent, int layoutId, int viewId, int imgId, int visibility) {
        ImageView imageView = (ImageView) initView(parent, layoutId, viewId, visibility);
        imageView.setImageResource(imgId);
        return imageView;
    }

    public ImageView initImageView(int viewId, int imgId) {
        return initImageView(0, viewId, imgId, View.VISIBLE);
    }

    //TextView
    public TextView initTextView(int layoutId, int viewId, String hint, CharSequence text, int visibility) {
        TextView textView = (TextView) initView(layoutId, viewId, visibility);
        textView.setHint(hint == null ? "" : hint);
        textView.setText(text == null ? "" : text);
        return textView;
    }

    public TextView initTextView(View parent, int layoutId, int viewId, String hint, CharSequence text, int visibility) {
        TextView textView = (TextView) initView(parent, layoutId, viewId, visibility);
        textView.setHint(hint == null ? "" : hint);
        textView.setText(text == null ? "" : text);
        return textView;
    }

    public TextView initTextView(int viewId, CharSequence text) {
        return initTextView(0, viewId, "", text, View.VISIBLE);
    }

    //EditView
    public EditText initEditText(int layoutId, int viewId, String hint, String text, int inputType, int maxLength, int visibility) {
        EditText editText = (EditText) initView(layoutId, viewId, visibility);
        editText.setHint(hint == null ? "" : hint);
        editText.setText(text == null ? "" : text);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        editText.setInputType(inputType);
        return editText;
    }

    public EditText initEditText(View parent, int layoutId, int viewId, String hint, String text, int inputType, int maxLength, int visibility) {
        EditText editText = (EditText) initView(parent, layoutId, viewId, visibility);
        editText.setHint(hint == null ? "" : hint);
        editText.setText(text == null ? "" : text);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        editText.setInputType(inputType);
        return editText;
    }

    public EditText initEditText(int viewId, String hint, String text) {
        return initEditText(0, viewId, hint, text, InputType.TYPE_CLASS_TEXT, 255, View.VISIBLE);
    }

}
