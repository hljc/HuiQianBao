package com.ceq.app_core.framework;

import android.app.Activity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Toast;


/**
 * Created by Administrator on 201/1/14.
 */

public class JI_Web {
    Activity activity;
    TextView tv_title;
    ImageView iv_back;
    LinearLayout ll_title;
    WebView webView;

    public JI_Web(WebView webView, LinearLayout ll_title, TextView tv_title, ImageView iv_back) {
        this.activity = (Activity) webView.getContext();
        this.webView = webView;
        this.tv_title = tv_title;
        this.iv_back = iv_back;
        this.ll_title = ll_title;
    }

    /**
     * 跳转界面
     *
     * @param actId  界面ID
     * @param params 参数数组，可传可不传
     */
    @JavascriptInterface
    public void skipToAct(int actId, String... params) {
        switch (actId) {
            case 0://跳转到网页
                break;
            case 1://登录
                break;
            case 2://注册
                break;
            case 3://忘记密码
                break;
            case 4://不懂就问
                break;
            case 5://打电话 参数：String mobile
                break;
            case 6://上传图片 参数：int type
                break;
        }
    }

    /**
     * 调用原生方法
     *
     * @param methodId 方法ID
     * @param params   参数数组，可传可不传
     */
    @JavascriptInterface
    public void invokeMethod(final int methodId, final String... params) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (methodId) {
                    case 0://设置标题栏 参数：String titleName、boolean isShowBackArrow

                        ll_title.setVisibility(View.VISIBLE);
                        if (tv_title != null)
                            tv_title.setText(params[0]);
                        if (iv_back != null) {
                            iv_back.setVisibility(Boolean.parseBoolean(params[1]) ? View.VISIBLE : View.GONE);
                        }

                        break;
                    case 1://如果webView可以返回上一层则返回，否则返回到原生 参数：boolean skipToNative
                        boolean skipToNative = Boolean.parseBoolean(params[0]);
                        if (!skipToNative && webView.canGoBack())
                            webView.goBack();
                        else
                            activity.finish();
                        break;
                    case 2://显示对话框 参数：String contentText、String confirmText、String cancelText
                        String contentText=params[0];
                        String confirmText=params[1];
                        final String confirmFunction=params[2];
                        String cancelText=params[3];
                        final String cancelFunction=params[4];
                        Util_Dialog.showDefaultDialog(webView, contentText, confirmText, cancelText, new Util_Dialog.DialogListener() {
                            @Override
                            public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                                webView.loadUrl(new StringBuilder("javascript:").append(confirmFunction).append("()").toString());
                                framework_dialog.dismiss();
                            }

                            @Override
                            public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                                webView.loadUrl(new StringBuilder("javascript:").append(cancelFunction).append("()").toString());
                                framework_dialog.dismiss();
                            }

                            @Override
                            public void onDismissListener() {

                            }
                        });
                        break;
                    case 3://吐司(用于提示用户) 参数：String toastContent
                        Util_Toast.toast(params[0]);
                        break;
                    case 4://获取用户信息 参数：String methodName
                       // webView.loadUrl(new StringBuilder("javascript:").append(params[0]).append("('").append(JSON.toJSON(Abstract_App.bean_userInfo)).append("')").toString());
                        break;
                    case 5:
                        break;
                    case 6:
                        break;

                }
            }
        });
    }
}
