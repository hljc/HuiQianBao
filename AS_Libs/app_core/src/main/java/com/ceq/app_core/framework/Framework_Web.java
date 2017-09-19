package com.ceq.app_core.framework;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceq.app_core.R;
import com.ceq.app_core.utils.core.Util_Animation;
import com.ceq.app_core.utils.core.Util_Input;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_View;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class Framework_Web extends Framework_Fragment {
    public static final String Extra_String_Url = "Extra_String_Url";
    public static final String Extra_String_Title = "Extra_String_Title";
    public WebView webView;
    WebSettings webSettings;
    public ImageView iv_left;
    float progress = 0;
    float realProgress;
    TextView tv_progress, tv_title;
    AutoLinearLayout ll_title;
    AVLoadingIndicatorView liv;
    AutoRelativeLayout rl_loadProgress;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    String title, url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        title = bundle.getString(Extra_String_Title);
        url = bundle.getString(Extra_String_Url);
        View view = init(R.layout.app_framework_web);
        Util_Input.AndroidBug5497Workaround.assistActivity(getActivity());
        return view;
    }

    @Override
    public void initView() {
        //标题栏
        tv_title = util_init.initTextView(R.id.icd_title, R.id.tv_title, null, title, View.VISIBLE);
        iv_left = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        rl_loadProgress = (AutoRelativeLayout) findViewById(R.id.rl_loadProgress);
        ll_title = (AutoLinearLayout) findViewById(R.id.icd_title);
        ll_title.setVisibility(title == null ? View.GONE : View.VISIBLE);

        liv = (AVLoadingIndicatorView) findViewById(R.id.liv);
        tv_progress = (TextView) findViewById(R.id.tv_progress);

        webView = (WebView) findViewById(R.id.webView);
      /*  AutoRelativeLayout.LayoutParams lp= (AutoRelativeLayout.LayoutParams) webView.getLayoutParams();
        AutoLayoutInfo autoLayoutInfo=lp.getAutoLayoutInfo();
        autoLayoutInfo.addAttr(new MarginBottomAttr(BarUtils.getStatusBarHeight(this)+Util_Input.getNavigationBarHeight(this),0,0));
        webView.setLayoutParams(webView.getLayoutParams());*/

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                realProgress = (int) (progress = 0);
                tv_progress.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_progress.setText("进度" + (int) Math.floor(realProgress != 100 ? realProgress != 100 && (progress += .5) >= 100 ? 99 : progress : realProgress) + "%");
                        tv_progress.postDelayed(this, 500);
                    }
                });
                webSettings.setBlockNetworkImage(true);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webSettings.setBlockNetworkImage(false);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Util_Log.logWeb(errorCode, description, failingUrl);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebView.HitTestResult hitTestResult = view.getHitTestResult();
                //hitTestResult==null解决重定向问题
                if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, final int newProgress) {
                if (newProgress != 100) {
                    realProgress = newProgress;
                    if (progress < realProgress)
                        progress = realProgress;
                    rl_loadProgress.setVisibility(View.VISIBLE);
                } else
                    Util_Animation.animationToAlpha(tv_progress, 300, 1, 0, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            rl_loadProgress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Util_Log.logWeb(consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                getActivity().startActivityForResult(
                        Intent.createChooser(i, "File Chooser"),
                        FILECHOOSER_RESULTCODE);

            }

            public void openFileChooser(ValueCallback uploadMsg,
                                        String acceptType) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                getActivity().startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
            }

            // For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                getActivity().startActivityForResult(
                        Intent.createChooser(i, "File Chooser"),
                        FILECHOOSER_RESULTCODE);

            }

            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                openFileChooserImplForAndroid5(filePathCallback);
                return true;//重要，没有这个会报错
            }
        });

        webView.addJavascriptInterface(new JI_Web(webView, ll_title, tv_title, iv_left), "android");
        webView.loadUrl(url);
        //webView.loadUrl("file:///android_asset/1.html?json=".concat(JSON.toJSONString(Abstract_App.bean_userInfo)));

    }

    @Override
    public void initData() {
    }

    @Override
    public void initAdapter() {

    }

    public void initListener() {
        Util_View.viewOnClick(this, iv_left);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_left)
            getActivity().onBackPressed();
    }


    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        startActivityForResult(chooserIntent,
                FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            if (result == null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
                return;
            }

            Bitmap bm = null;
            //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
            ContentResolver resolver = getActivity().getContentResolver();
            try {
                Uri originalUri = intent.getData(); // 获得图片的uri
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                // 这里开始的第二部分，获取图片的路径：
                String[] proj = {MediaStore.Images.Media.DATA};
                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = getActivity().managedQuery(originalUri, proj, null, null, null);
                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                String path = cursor.getString(column_index);
                Uri uri = Uri.fromFile(new File(path));
                mUploadMessage.onReceiveValue(uri);
            } catch (IOException e) {
                Log.e("TAG-->Error", e.toString());
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }

    @Override
    public void onSelected() {
        Util_Screen.statusBarToTranslucent(getActivity(), getResources().getColor(R.color.title_background));
    }
}
