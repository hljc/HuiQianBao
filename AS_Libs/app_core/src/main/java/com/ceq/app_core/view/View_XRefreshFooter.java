package com.ceq.app_core.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IFooterCallBack;
import com.andview.refreshview.utils.Utils;
import com.ceq.app_core.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
class View_XRefreshFooter extends LinearLayout implements IFooterCallBack {
    Context context;
    View view;
    TextView tv_state;
    AVLoadingIndicatorView liv;
    private boolean showing = true;

    public View_XRefreshFooter(Context context) {
        super(context);
        init(context);
    }

    public View_XRefreshFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public View_XRefreshFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.app_xrefresh_footer, this, true);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        liv = (AVLoadingIndicatorView) findViewById(R.id.liv);
    }
    public void setColor(int color) {
        tv_state.setTextColor(color);
        liv.setIndicatorColor(color);
    }
    @Override
    public void callWhenNotAutoLoadMore(XRefreshView xRefreshView) {
        View childView = xRefreshView.getChildAt(1);
        if (childView != null && childView instanceof RecyclerView) {
            show(Utils.isRecyclerViewFullscreen((RecyclerView) childView));
            xRefreshView.enableReleaseToLoadMore(Utils.isRecyclerViewFullscreen((RecyclerView) childView));
        }
    }

    @Override
    public void onStateReady() {
        tv_state.setVisibility(View.GONE);
        liv.setVisibility(View.GONE);
    }

    @Override
    public void onStateRefreshing() {
        tv_state.setText("正在刷新中");
        tv_state.setVisibility(View.VISIBLE);
        liv.setVisibility(View.VISIBLE);
        show(true);
    }

    @Override
    public void onReleaseToLoadMore() {
        tv_state.setText("释放加载更多");
        tv_state.setVisibility(View.VISIBLE);
        liv.setVisibility(View.GONE);
    }

    @Override
    public void onStateFinish(boolean hideFooter) {
        if (hideFooter) {
            tv_state.setText("加载完成");
        } else {
            //处理数据加载失败时ui显示的逻辑，也可以不处理，看自己的需求
            tv_state.setText("没有更多数据");
        }
        tv_state.setVisibility(View.VISIBLE);
        liv.setVisibility(View.GONE);
    }

    @Override
    public void onStateComplete() {
        tv_state.setText("已无更多数据");
        tv_state.setVisibility(View.VISIBLE);
        liv.setVisibility(View.GONE);
    }

    @Override
    public void show(boolean show) {
        if (show == showing)
            return;
        showing = show;
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        lp.height = show ? LayoutParams.WRAP_CONTENT : 0;
        view.setLayoutParams(lp);
    }

    @Override
    public boolean isShowing() {
        return false;
    }

    @Override
    public int getFooterHeight() {
        return getMeasuredHeight();
    }
}
