package com.ceq.app_core.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.callback.IHeaderCallBack;
import com.andview.refreshview.utils.Utils;
import com.ceq.app_core.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
class View_XRefreshHeader extends LinearLayout implements IHeaderCallBack {
    Context context;
    View view;
    TextView tv_datetime, tv_state;
    ImageView iv_arrow;
    Animation mRotateDownAnim, mRotateUpAnim;
    AVLoadingIndicatorView liv;

    public View_XRefreshHeader(Context context) {
        this(context, null);
        init(context);
    }

    public View_XRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public View_XRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.app_xrefresh_header, this, true);
        tv_datetime = (TextView) view.findViewById(R.id.tv_datetime);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
        iv_arrow.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.text_color_3)));
        liv = (AVLoadingIndicatorView) findViewById(R.id.liv);

        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(200);
        mRotateDownAnim.setFillAfter(true);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(200);
        mRotateUpAnim.setFillAfter(true);
    }

    public void setColor(int color) {
        tv_datetime.setTextColor(color);
        tv_state.setTextColor(color);
        iv_arrow.setColorFilter(new LightingColorFilter(Color.BLACK, color));
        liv.setIndicatorColor(color);
    }

    @Override
    public void onStateNormal() {
        iv_arrow.setVisibility(View.VISIBLE);
        iv_arrow.startAnimation(mRotateDownAnim);
        liv.setVisibility(View.GONE);
        tv_state.setText("下拉刷新");
    }

    @Override
    public void onStateReady() {
        liv.setVisibility(View.GONE);
        iv_arrow.setVisibility(View.VISIBLE);
        iv_arrow.clearAnimation();
        iv_arrow.startAnimation(mRotateUpAnim);
        tv_state.setText("释放刷新数据");
        tv_datetime.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateRefreshing() {
        iv_arrow.clearAnimation();
        iv_arrow.setVisibility(View.GONE);
        liv.setVisibility(View.VISIBLE);
        tv_state.setText("正在刷新数据");
    }

    @Override
    public void onStateFinish(boolean success) {
        iv_arrow.setVisibility(View.GONE);
        tv_datetime.setVisibility(View.VISIBLE);
        liv.setVisibility(View.GONE);
        tv_state.setText(success ? "刷新成功" : "刷新失败");
        tv_datetime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {
    }

    @Override
    public void setRefreshTime(long lastRefreshTime) {
        // 获取当前时间
        Calendar mCalendar = Calendar.getInstance();
        long refreshTime = mCalendar.getTimeInMillis();
        long howLong = refreshTime - lastRefreshTime;
        int minutes = (int) (howLong / 1000 / 60);
        String refreshTimeText;
        Resources resources = getContext().getResources();
        if (minutes < 1) {
            refreshTimeText = resources.getString(R.string.xrefreshview_refresh_justnow);
        } else if (minutes < 60) {
            refreshTimeText = resources.getString(R.string.xrefreshview_refresh_minutes_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes);
        } else if (minutes < 60 * 24) {
            refreshTimeText = resources.getString(R.string.xrefreshview_refresh_hours_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60);
        } else {
            refreshTimeText = resources.getString(R.string.xrefreshview_refresh_days_ago);
            refreshTimeText = Utils.format(refreshTimeText, minutes / 60 / 24);
        }
        tv_datetime.setText(refreshTimeText);
    }

    @Override
    public void hide() {
        setVisibility(GONE);
    }

    @Override
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
