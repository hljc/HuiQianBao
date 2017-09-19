package com.ceq.app_core.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.blankj.utilcode.util.NetworkUtils;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class View_XRefreshLayout extends XRefreshView {
    protected List list;
    OnRefreshHttpListener onRefreshHttpListener;
    int firstPagePosition;
    int currentPagePosition;
    RecyclerView.Adapter adapter;
    View_XRefreshStatusView view_xRefreshStatusView;
    boolean enableEmptyView = true;
    View_XRefreshHeader view_xRefreshHeader;
    View_XRefreshFooter view_xRefreshFooter;

    public void setEnableEmptyView(boolean enableEmptyView) {
        this.enableEmptyView = enableEmptyView;
    }

    public boolean isEnableEmptyView() {
        return enableEmptyView;
    }

    public View_XRefreshLayout(Context context) {
        this(context, null);
    }

    public View_XRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public View_XRefreshStatusView getView_xRefreshStatusView() {
        return view_xRefreshStatusView;
    }

    private void init(Context context) {
        setPullLoadEnable(true);
        setDampingRatio(3);
        setPinnedTime(200);
        view_xRefreshStatusView = new View_XRefreshStatusView(context);
        view_xRefreshStatusView.setView_xRefreshLayout(this);
        AutoLinearLayout.LayoutParams layoutParams = new AutoLinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view_xRefreshStatusView.setLayoutParams(layoutParams);
        setEmptyView(view_xRefreshStatusView);
        view_xRefreshHeader = new View_XRefreshHeader(context);
        view_xRefreshFooter = new View_XRefreshFooter(context);
        setCustomHeaderView(view_xRefreshHeader);
        setCustomFooterView(view_xRefreshFooter);
        setXRefreshViewListener(simpleXRefreshListener);
    }

    public void setColor(int color) {
        view_xRefreshHeader.setColor(color);
        view_xRefreshFooter.setColor(color);
    }

    SimpleXRefreshListener simpleXRefreshListener = new XRefreshView.SimpleXRefreshListener() {
        @Override
        public void onLoadMore(boolean isSilence) {
            super.onLoadMore(isSilence);
            onRefreshHttpListener.onHttpStart(++currentPagePosition, view_xRefreshStatusView, list, adapter);
        }

        @Override
        public void onRefresh(boolean isPullDown) {
            super.onRefresh(isPullDown);
            if (NetworkUtils.isConnected())
                list.clear();
            onRefreshHttpListener.onHttpStart(currentPagePosition = firstPagePosition, view_xRefreshStatusView, list, adapter);
        }

        @Override
        public void onRelease(float direction) {
            super.onRelease(direction);
        }
    };

    public static abstract class OnRefreshHttpListener {
        public abstract void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter);
    }

    public void onHttpEnd() {
        if (list != null) {
            enableEmptyView(enableEmptyView && list.isEmpty());
            if (list.isEmpty())
                view_xRefreshStatusView.showHttpStatusView(View_XRefreshStatusView.HttpViewStatus.无数据);
            stopRefresh();
            stopLoadMore();
            if (adapter != null)
                adapter.notifyDataSetChanged();
        }

    }

    public void setOnRefreshHttpListener(int firstPagePosition, List list, RecyclerView.Adapter adapter, OnRefreshHttpListener onRefreshHttpListener) {
        setOnRefreshHttpListener(true,true, firstPagePosition, list, adapter, onRefreshHttpListener);
    }
    public void setOnRefreshHttpListener(boolean enableLoadMore, int firstPagePosition, List list, RecyclerView.Adapter adapter, OnRefreshHttpListener onRefreshHttpListener) {
        setOnRefreshHttpListener(true,enableLoadMore, firstPagePosition, list, adapter, onRefreshHttpListener);
    }

    public void setOnRefreshHttpListener(boolean enableEmptyView,boolean enableLoadMore, int firstPagePosition, List list, RecyclerView.Adapter adapter, OnRefreshHttpListener onRefreshHttpListener) {
        this.firstPagePosition = currentPagePosition = firstPagePosition;
        this.list = list;
        this.adapter = adapter;
        this.onRefreshHttpListener = onRefreshHttpListener;
        setPullLoadEnable(enableLoadMore);
        setEnableEmptyView(enableEmptyView);
        setMoveForHorizontal(true);
        enablePullUpWhenLoadCompleted(enableLoadMore);
        enableReleaseToLoadMore(enableLoadMore);
        enableRecyclerViewPullUp(enableLoadMore);
        enablePullUp(enableLoadMore);
        onRefreshHttpListener.onHttpStart(currentPagePosition, view_xRefreshStatusView, list, adapter);
    }
}
