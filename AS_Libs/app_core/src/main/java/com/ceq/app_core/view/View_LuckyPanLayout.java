package com.ceq.app_core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.ceq.app_core.R;
import com.ceq.app_core.bean.Bean_LuckPan;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class View_LuckyPanLayout extends AutoRelativeLayout implements View.OnClickListener {
    View_LuckyPan view_luckyPan;
    ImageView iv_luckyPan;
    Util_Runnable.Util_ArgsRunnable util_Args_runnable;
    int pointerSize;
    Context context;
    AttributeSet attrs;

    public View_LuckyPanLayout(Context context) {
        this(context, null);
    }

    public View_LuckyPanLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View_LuckyPanLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;

        view_luckyPan = new View_LuckyPan(context);
        addView(view_luckyPan);

        iv_luckyPan = new ImageView(context);
        int w=ScreenUtils.getScreenWidth();
        AutoRelativeLayout.LayoutParams autoRelativeLayout = new AutoRelativeLayout.LayoutParams(w/ 7, w / 7);
        autoRelativeLayout.addRule(AutoRelativeLayout.CENTER_IN_PARENT);
        iv_luckyPan.setImageResource(R.mipmap.app_logo);
        addView(iv_luckyPan, autoRelativeLayout);
        iv_luckyPan.setOnClickListener(this);
        requestLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setData(List<Bean_LuckPan> bean_luckPen, int luckyPanPointerImgId, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        view_luckyPan.setData(bean_luckPen);
        iv_luckyPan.setImageResource(luckyPanPointerImgId);
        this.util_Args_runnable = util_Args_runnable;
    }

    @Override
    public void onClick(View v) {
        view_luckyPan.startRoll(iv_luckyPan, (float) (Math.random() * 355 + 1854), util_Args_runnable);
    }

}
