package com.ceq.app_core.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceq.app_core.R;
import com.zhy.autolayout.AutoLinearLayout;

import static com.ceq.app_core.constants.Constants_International.error_net_disconnect;
import static com.ceq.app_core.constants.Constants_International.error_net_maintenance_timeout;
import static com.ceq.app_core.constants.Constants_International.error_net_not_data;
import static com.ceq.app_core.constants.Constants_International.error_net_not_stable;
import static com.ceq.app_core.constants.Constants_International.error_net_request_exception;
import static com.ceq.app_core.constants.Constants_International.error_net_request_timeout;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class View_XRefreshStatusView extends AutoLinearLayout {
    ImageView iv_status;
    TextView tv_status;
    View_XRefreshLayout view_xRefreshLayout;

    public enum HttpViewStatus {
        网络断开, 网络不稳, 网络超时, 无数据, 有数据, 服务器异常, 服务器超时
    }

    public View_XRefreshStatusView(Context context) {
        this(context, null);
        init(context);
    }

    public View_XRefreshStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public View_XRefreshStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_xrefresh_status_view, this, true);
        iv_status = (ImageView) view.findViewById(R.id.iv_status);
        tv_status = (TextView) view.findViewById(R.id.tv_status);
        int color=getResources().getColor(R.color.text_color_3);
        iv_status.setColorFilter(new LightingColorFilter(Color.BLACK,color));
        tv_status.setTextColor(color);
    }

    public void showHttpStatusView(HttpViewStatus httpViewStatus) {
        if (getView_xRefreshLayout().list != null && !getView_xRefreshLayout().list.isEmpty())
            return;
        View_XRefreshLayout view_xRefreshLayout = getView_xRefreshLayout();
        view_xRefreshLayout.enableEmptyView(view_xRefreshLayout.isEnableEmptyView());
        switch (httpViewStatus) {
            case 网络断开:
                iv_status.setImageResource(R.mipmap.app_no_net);
                tv_status.setText(error_net_disconnect);
                break;
            case 网络不稳:
                iv_status.setImageResource(R.mipmap.app_net_error);
                tv_status.setText(error_net_not_stable);
                break;
            case 网络超时:
                iv_status.setImageResource(R.mipmap.app_net_error);
                tv_status.setText(error_net_request_timeout);
                break;
            case 服务器超时:
                iv_status.setImageResource(R.mipmap.app_net_error);
                tv_status.setText(error_net_maintenance_timeout);
                break;
            case 服务器异常:
                iv_status.setImageResource(R.mipmap.app_net_exception);
                tv_status.setText(error_net_request_exception);
                break;
            case 无数据:
                iv_status.setImageResource(R.mipmap.app_no_data);
                tv_status.setText(error_net_not_data);
                break;
        }
    }

    public View_XRefreshLayout getView_xRefreshLayout() {
        return view_xRefreshLayout;
    }

    public void setView_xRefreshLayout(View_XRefreshLayout view_xRefreshLayout) {
        this.view_xRefreshLayout = view_xRefreshLayout;
    }
}
