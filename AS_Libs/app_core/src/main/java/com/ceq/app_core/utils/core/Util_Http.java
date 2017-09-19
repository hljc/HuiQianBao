package com.ceq.app_core.utils.core;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.NetworkUtils;
import com.ceq.app_core.framework.Framework_App;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

import okhttp3.Call;
import okhttp3.OkHttpClient;

import static com.ceq.app_core.constants.Constants_International.error_net_disconnect;
import static com.ceq.app_core.constants.Constants_International.error_net_maintenance_timeout;
import static com.ceq.app_core.constants.Constants_International.error_net_not_stable;
import static com.ceq.app_core.constants.Constants_International.error_net_request_exception;
import static com.ceq.app_core.constants.Constants_International.error_net_request_timeout;
import static com.ceq.app_core.view.View_XRefreshStatusView.HttpViewStatus.服务器异常;
import static com.ceq.app_core.view.View_XRefreshStatusView.HttpViewStatus.服务器超时;
import static com.ceq.app_core.view.View_XRefreshStatusView.HttpViewStatus.网络不稳;
import static com.ceq.app_core.view.View_XRefreshStatusView.HttpViewStatus.网络超时;

/**
 * Created by Administrator on 2016/12/29.
 */

public class Util_Http {
    private static final List<Context> contextList = new ArrayList<>();

    private static final int httpTimeout = 5000;
    private static ProgressDialog progressDialog;
    private static HttpDealFilterListener httpDealFilterListener;
    private static OkGo okGo;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (Util_Apk.isApkDebug()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo日志");
            //log打印级别，决定了log显示的详细程度
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
            //log颜色级别，决定了log在控制台显示的颜色
            loggingInterceptor.setColorLevel(Level.INFO);
            builder.addInterceptor(loggingInterceptor);
            //第三方的开源库，使用通知显示当前请求的log
            // builder.addInterceptor(new ChuckInterceptor(this));
        }

        //全局的读取超时时间
        builder.readTimeout(httpTimeout, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        //builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(httpTimeout, TimeUnit.MILLISECONDS);

        //使用sp保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(Framework_App.getInstance())));
        //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new DBCookieStore(Framework_App.getInstance())));
        //使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));


        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
        //HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
        //builder.hostnameVerifier(new SafeHostnameVerifier());


        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        //-------------------------------------------------------------------------------------//
        okGo = OkGo.getInstance().init(Framework_App.getInstance())                       //必须调用初始化
                .setOkHttpClient(builder.build())               //必须设置OkHttpClient
                .setCacheMode(CacheMode.DEFAULT)
                //.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数

    }

    public static void init(HttpDealFilterListener httpDealFilterListener) {
        Util_Http.httpDealFilterListener = httpDealFilterListener;
    }


    public enum RequestMethod {
        POST, GET
    }

    public static OkGo getOkGo() {
        return okGo;
    }

    public interface HttpHeadersAndParams {
        void addHeaders(HttpHeaders httpHeaders);

        void addParams(HttpParams httpParams);
    }

    public interface HttpDealFilterListener {
        boolean beforeHttpDealResult(Activity activity, String data, Response response);
    }

    public interface HttpHeadersAndFile extends HttpHeadersAndParams {
        PostRequest addFile(PostRequest postRequest);
    }


    public interface HttpDealString {
        void onDealResult(String data, Response response);
    }

    public interface HttpDealFile {
        void onDealResult(String data, File file, Response response);

        void onProgress(long currentSize, long totalSize, float progress, long networkSpeed);
    }

    public static abstract class HttpDealStringListener extends StringCallback implements HttpDealString {
        Context context;
        String toastContent;
        boolean isAutoDismiss;
        boolean F_Toast$T_Dialog;
        View_XRefreshStatusView view_xRefreshStatusView;

        public HttpDealStringListener(final Context context, String toastContent, boolean isAutoDismiss, boolean F_Toast$T_Dialog) {
            this.toastContent = toastContent;
            this.context = context;
            this.isAutoDismiss = isAutoDismiss;
            this.F_Toast$T_Dialog = F_Toast$T_Dialog;
        }

        public HttpDealStringListener(final Context context, View_XRefreshStatusView view_xRefreshStatusView, String toastContent, boolean isAutoDismiss, boolean F_Toast$T_Dialog) {
            this(context, toastContent, isAutoDismiss, F_Toast$T_Dialog);
            this.view_xRefreshStatusView = view_xRefreshStatusView;
        }

        @Override
        public void onStart(Request<String, ? extends Request> request) {
            super.onStart(request);
            httpBefore(request, view_xRefreshStatusView, false, context, toastContent, F_Toast$T_Dialog);
        }

        @Override
        public void onSuccess(Response<String> response) {
            try {
                String data = response.body();
                printReturnParams(response, data);
                if (httpDealFilterListener.beforeHttpDealResult((Activity) context, data, response))
                    onDealResult(data, response);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (view_xRefreshStatusView != null)
                    view_xRefreshStatusView.getView_xRefreshLayout().onHttpEnd();
            }
        }

        @Override
        public void onError(Response<String> response) {
            super.onError(response);
            dealError(view_xRefreshStatusView, response.getRawCall(), response, response.getException());
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (progressDialog != null && isAutoDismiss) {
                progressDialog.dismiss();
            }
        }
    }

    private static void dealError(View_XRefreshStatusView view_xRefreshStatusView, Call call, Response response, Throwable throwable) {
        Util_Http.dismiss();
        printHttpException(call, response, throwable);
        sendException(view_xRefreshStatusView, response, throwable);
    }

    private static void sendException(View_XRefreshStatusView view_xRefreshStatusView, Response response, Throwable throwable) {
        Util_Log.logTest("www", view_xRefreshStatusView);
        dismiss();
        if (!NetworkUtils.isConnected()) {
            Util_Toast.toast(error_net_disconnect);
            stopXRefresh(view_xRefreshStatusView, View_XRefreshStatusView.HttpViewStatus.网络断开);
            return;
        }
        if (throwable instanceof TimeoutException) {
            Util_Toast.toast(error_net_request_timeout);
            stopXRefresh(view_xRefreshStatusView, 网络超时);
        } else if (throwable instanceof SocketTimeoutException) {
            Util_Toast.toast(error_net_maintenance_timeout);
            stopXRefresh(view_xRefreshStatusView, 服务器超时);
        } else if (throwable instanceof SocketException) {
            Util_Toast.toast(error_net_not_stable);
            stopXRefresh(view_xRefreshStatusView, 网络不稳);
        } else {
            Util_Toast.toast(error_net_request_exception);
            stopXRefresh(view_xRefreshStatusView, 服务器异常);
        }

    }

    private static void stopXRefresh(View_XRefreshStatusView view_xRefreshStatusView, View_XRefreshStatusView.HttpViewStatus httpViewStatus) {
        if (view_xRefreshStatusView != null) {
            view_xRefreshStatusView.showHttpStatusView(httpViewStatus);
            view_xRefreshStatusView.getView_xRefreshLayout().stopRefresh();
            view_xRefreshStatusView.getView_xRefreshLayout().stopLoadMore();
        }
    }

    public static void httpToRequestSync(Context context, String requestUrl, RequestMethod requestMethod, HttpHeadersAndParams httpHeadersAndParams, HttpDealString httpDealString) {
        httpToRequestCore(context, requestUrl, requestMethod, httpHeadersAndParams, httpDealString);
    }

    public static void httpToRequest(Context context, String requestUrl, RequestMethod requestMethod, HttpHeadersAndParams httpHeadersAndParams, HttpDealStringListener httpDealStringListener) {
        httpToRequestCore(context, requestUrl, requestMethod, httpHeadersAndParams, httpDealStringListener);
    }

    private static void httpToRequestCore(Context context, String requestUrl, RequestMethod requestMethod, HttpHeadersAndParams httpHeadersAndParams, final HttpDealString httpDealString) {
        if (requestMethod == null || requestUrl == null)
            return;
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpParams httpParams = new HttpParams();
        httpHeadersAndParams.addHeaders(httpHeaders);
        httpHeadersAndParams.addParams(httpParams);
        httpHeaders.put(httpHeaders);
        httpParams.put(httpParams);
        Request request = null;
        switch (requestMethod) {
            case GET:
                request = okGo.get(requestUrl);
                break;
            case POST:
                request = okGo.post(requestUrl);
                break;
        }
        request.headers(httpHeaders).params(httpParams).tag(context);
        if (httpDealString instanceof HttpDealStringListener)
            request.execute((HttpDealStringListener) httpDealString);
        else {
            Response response = null;
            try {
                response = request.converter(new StringConvert()).adapt().execute();
                String data = response.body().toString();
                if (httpDealFilterListener.beforeHttpDealResult((Activity) context, data, response))
                    httpDealString.onDealResult(data, response);
            } catch (Exception e) {
                e.printStackTrace();
                sendException(null, response, e);
            }
        }

    }

    public static void httpToFile(Context context, String requestUrl, HttpHeadersAndFile httpHeadersAndFile, final HttpDealFileListener httpDealFileListener) {
        if (requestUrl == null)
            return;
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpParams httpParams = new HttpParams();
        httpHeadersAndFile.addHeaders(httpHeaders);
        httpHeadersAndFile.addParams(httpParams);
        httpHeaders.put(httpHeaders);
        httpParams.put(httpParams);
        Request request = httpHeadersAndFile.addFile(okGo.post(requestUrl));
        if (request == null) {
            request = OkGo.get(requestUrl);
            request.headers(httpHeaders).params(httpParams).tag(context).execute(httpDealFileListener);
        } else {
            ((PostRequest) request).isMultipart(true);
            request.headers(httpHeaders).params(httpParams).tag(context).execute(httpDealFileListener);
        }
    }

    public static abstract class HttpDealFileListener extends FileCallback implements HttpDealFile {
        Context context;
        String toastContent;
        boolean isAutoDismiss;
        boolean F_Toast$T_Dialog;
        String downloadDir, downloadName;
        private FileConvert convert;    //文件转换类

        @Override
        public File convertResponse(okhttp3.Response response) throws Throwable {
            File file = convert.convertResponse(response);
            response.close();
            return file;
        }


        public HttpDealFileListener(final Context context, String toastContent, boolean isAutoDismiss, boolean F_Toast$T_Dialog) {
            this.context = context;
            this.toastContent = toastContent;
            this.isAutoDismiss = isAutoDismiss;
            this.F_Toast$T_Dialog = F_Toast$T_Dialog;
        }

        public HttpDealFileListener(final Context context, String toastContent, String downloadDir, String downloadName, boolean isAutoDismiss, boolean F_Toast$T_Dialog) {
            this(context, toastContent, isAutoDismiss, F_Toast$T_Dialog);
            this.downloadDir = new File(Util_File.fileToAppDir(), (String) Util_Empty.isEmptyToReplace(downloadDir, "")).getPath();
            this.downloadName = downloadName;
            convert = new FileConvert(this.downloadDir, downloadName);
            convert.setCallback(this);
        }

        @Override
        public void onStart(Request<File, ? extends Request> request) {
            super.onStart(request);
            httpBefore(request, null, true, context, toastContent, F_Toast$T_Dialog);
        }

        @Override
        public void onSuccess(Response<File> response) {
            File file = response.body();
            if (downloadName == null) {
                BufferedReader br = null;
                String line = "";
                try {
                    line = (br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"))).readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    printReturnParams(response, line);
                    onDealResult(line, file, response);
                    try {
                        if (br != null)
                            br.close();
                        file.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                printReturnParams(response, file);
                onDealResult(null, file, response);
            }
        }

        @Override
        public void onError(Response<File> response) {
            super.onError(response);
            dealError(null, response.getRawCall(), response, response.getException());
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (progressDialog != null && isAutoDismiss)
                progressDialog.dismiss();
        }

        public void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            onProgress(currentSize, totalSize, progress, networkSpeed);
        }

        @Override
        public void uploadProgress(Progress progress) {
            super.uploadProgress(progress);
            onProgress(progress.currentSize, progress.totalSize, progress.fraction, progress.speed);
        }

        @Override
        public void downloadProgress(Progress progress) {
            super.downloadProgress(progress);
            onProgress(progress.currentSize, progress.totalSize, progress.fraction, progress.speed);
        }


    }

    private static void printHttpException(Call call, Response response, Throwable throwable) {
        //  Util_Log.logNet("请求异常", call.request(), response, e);
    }

    private static void printReturnParams(Response response, Object result) {
        //  Util_Log.logNet("服务器返回", response.request().url(), result);
    }

    private static void printCommitParams(boolean isFileHttp, Request request, String method) {
        //  String params = request.getParams().toString();
        //  Util_Log.logNet(method.concat(isFileHttp ? "文件参数" : "提交参数"), request.getCall().request().url(), method.equals("POST") ? params.substring(0, params.length() - 1).replace("=[", "=").replace("]&", "&") : "");
    }


    public static void dismiss() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    public static void setProgressBarMessage(String message, DialogInterface.OnDismissListener onDismissListener) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
            progressDialog.show();
            progressDialog.setOnDismissListener(onDismissListener);
        }
    }

    private static void httpBefore(Request request, final View_XRefreshStatusView view_xRefreshStatusView, boolean isFileHttp, final Context context, String toastContent, boolean F_Toast$T_Dialog) {
        HttpMethod method = request.getMethod();
        printCommitParams(isFileHttp, request, method.name());
        if (Util_Empty.isEmpty(toastContent))
            return;
        if (!F_Toast$T_Dialog) {
            Util_Toast.toast(toastContent);
            return;
        }
        if (context != null && !Util_Empty.isEmpty(toastContent) && F_Toast$T_Dialog) {
            if (!contextList.contains(context)) {
                contextList.add(context);
                progressDialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            }

        }
        if (progressDialog == null)
            return;
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        setProgressBarMessage(toastContent, null);
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                contextList.remove(context);
                okGo.cancelTag(context);
                if (view_xRefreshStatusView != null)
                    view_xRefreshStatusView.getView_xRefreshLayout().onHttpEnd();
            }
        });
    }
}
