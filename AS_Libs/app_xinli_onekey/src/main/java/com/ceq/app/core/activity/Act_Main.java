package com.ceq.app.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.bean.Bean_ApkInfo;
import com.ceq.app.core.bean.Bean_WelcomeOptions;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app_core.constants.Constants_International;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_Dialog;
import com.ceq.app_core.utils.core.Util_Apk;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Permission;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_System;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.youth.banner.Banner;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.MarginBottomAttr;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.methods.Method_Static.setBanner;


public class Act_Main extends Framework_Activity {
    ImageView iv_bg, iv_welcome;
    TextView tv_name, tv_description, tv_load;
    Banner banner;
    RelativeLayout rl_guide, rl_entry;
    TextView tv_entry;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        //startActivity(new Intent(getActivity(),Act_Main_Web.class));
        init(R.layout.app_act_main, false);
    }

    @Override
    public void initView() {
        final Bean_WelcomeOptions bean_welcomeOptions = Abstract_App.bean_oneKeyBootstrap.getBean_welcomeOptions();
        int entryColor = bean_welcomeOptions.getEntryColor();
        int marginBottom = bean_welcomeOptions.getEntryMarginBottom();

        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        iv_welcome = (ImageView) findViewById(R.id.iv_welcome);

        banner = (Banner) findViewById(R.id.banner);
        rl_guide = (RelativeLayout) findViewById(R.id.rl_guide);
        rl_entry = (RelativeLayout) findViewById(R.id.rl_entry);
        tv_entry = (TextView) findViewById(R.id.tv_entry);

        if (entryColor != Integer.MAX_VALUE) {
            tv_entry.setTextColor(entryColor);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setStroke(SizeUtils.dp2px(2), entryColor);
            drawable.setCornerRadius(SizeUtils.dp2px(10));
            tv_entry.setBackgroundDrawable(drawable);
        }
        if (marginBottom != Integer.MAX_VALUE) {
            AutoRelativeLayout.LayoutParams alp = (AutoRelativeLayout.LayoutParams) tv_entry.getLayoutParams();
            AutoLayoutInfo autoLayoutInfo = alp.getAutoLayoutInfo();
            autoLayoutInfo.addAttr(new MarginBottomAttr(marginBottom, 0, 0));
            ViewGroup.LayoutParams lp = tv_entry.getLayoutParams();
            tv_entry.setLayoutParams(lp);
        }

        (tv_name = (TextView) findViewById(R.id.tv_name)).setText(getString(R.string.app_name));
        tv_load = (TextView) findViewById(R.id.tv_load);
        tv_description = (TextView) findViewById(R.id.tv_description);
        iv_bg.post(new Runnable() {
            @Override
            public void run() {
                if (Abstract_App.getInstance().getBean_BaseUserInfo() != null || Abstract_App.bean_oneKeyBootstrap.getBean_welcomeList().isEmpty()) {
                    openPermission();
                } else {
                    iv_welcome.setVisibility(View.GONE);
                    rl_guide.setVisibility(View.VISIBLE);
                    banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        int size = Abstract_App.bean_oneKeyBootstrap.getBean_welcomeList().size();
                        int currentPosition;

                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            if (position < currentPosition)
                                rl_entry.setX((screenWidth - positionOffsetPixels));
                            else
                                rl_entry.setX(-positionOffsetPixels);
                        }

                        @Override
                        public void onPageSelected(int position) {
                            this.currentPosition = position;
                            rl_entry.setVisibility(position+1 == size ? View.VISIBLE : View.GONE);

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    setBanner(getActivity(), banner, true);
                }

            }
        });
     /*   Util_Animation.animationToAlpha(tv_load, 1000, 1, 0, -1, null);
        Util_Animation.animationToScale(iv_bg, 5000, 1, 1.2f, 1, 1.2F, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Util_Animation.animationToScale(tv_name, 3000, 1.5f, 1, 1, 1, null);
                Util_Animation.animationToAlpha(tv_description, 5000, 0, 1.2f, null);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateApk();
                //login();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });*/
    }

    private void openPermission() {
        Util_Permission.openPermission(getActivity(), new Util_Permission.PermissionCallBack() {
            @Override
            public void success() {
                updateApk(getRootView(), false, new Util_Runnable.Util_ArgsRunnable() {

                    @Override
                    public void run(Object... data) {
                        skipToAct((Boolean) data[0]);
                    }

                });
            }

            @Override
            public void fail() {
                Util_Toast.toast("请先开启权限");
                openPermission();
            }
        }, Util_Permission.permissions_base);
    }

   /* private void login() {
        final QueryBuilder queryBuilder = Util_Database.getDaoSession().getBean_UserInfoDao().queryBuilder().orderDesc(Bean_UserInfoDao.Properties.LoginDate);
        if (queryBuilder.count() == 0) {
            startActivity(new Intent(getActivity(), Act_Main_Login.class));
            finish();
        } else {
            Framework_App.bean_userInfo = (Bean_PersonalInfo) queryBuilder.limit(1).list().get(0);
            Util_Log.logE("用户对象", Framework_App.bean_userInfo);
            if(Framework_App.bean_userInfo.getPassword()==null){
                startActivity(new Intent(getActivity(), Act_Main_Login.class));
                finish();
                return;
            }
            if (!Util_Net.isConnecting()) {
                Util_Toast.toast(Constants_International.error_net_disconnect);
                startActivity(new Intent(getActivity(), Act_Main_Login.class));
                return;
            }
            Util_Customer.IM_YW.loginIM(Framework_App.bean_userInfo.getId(), Framework_App.bean_userInfo.getPassword(), new IWxCallback() {
                @Override
                public void onSuccess(Object... objects) {
                    startActivity(new Intent(getActivity(), Act_Main_Module.class));

                }

                @Override
                public void onError(int i, String s) {
                    Util_Toast.toast("账号或密码错误");
                    startActivity(new Intent(getActivity(), Act_Main_Login.class));
                }

                @Override
                public void onProgress(int i) {
                }
            });
        }
    }*/

    @Override
    public void initData() {
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_entry);
    }


    @Override
    public void onClick(View view) {
        if (view == tv_entry)
            openPermission();
    }

    @Override
    public void onBackPressed() {
        Process.killProcess(Process.myPid());
        super.onBackPressed();
    }

    public static void updateApk(final View viewToken, final boolean isToastRecent, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        final Activity activity = (Activity) viewToken.getContext();
        Util_Http.httpToRequest(activity, Constant_Api.URL_UPDATE_APK_GET, Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("brand_id", Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId());

            }
        }, new Util_Http.HttpDealStringListener(activity, null, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        final Bean_ApkInfo bean_apkInfo = JSON.parseObject(parseObject(data).getJSONObject(Http_Key_Data).getString("android"), Bean_ApkInfo.class);
                        final int serverVersion = Integer.parseInt(bean_apkInfo.getVersion().replace(".", ""));
                        int localVersion = Integer.parseInt(AppUtils.getAppVersionName(AppUtils.getAppPackageName()).replace(".", ""));
                        final int sdVersion = Integer.parseInt(Util_Apk.getVersionNameFromApk(Util_Apk.getApkFile()).replace(".", ""));
                        Util_Log.logTest(serverVersion, localVersion, sdVersion, serverVersion == sdVersion);
                        if (serverVersion - localVersion > 0)
                            Util_Dialog.showDefaultDialog(viewToken, serverVersion == sdVersion ? "检测到新版本已经下载成功，更新后可体验更多功能！" : "检测到有新版本更新，更新后可体验更多功能！", serverVersion == sdVersion ? "立马安装" : "立马更新", "退出程序", new Util_Dialog.DialogListener() {

                                @Override
                                public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                                    Util_System.systemToExitAndToast(Constants_International.keyboard_back_exit, 1);
                                }

                                @Override
                                public void onConfirm(Framework_Dialog framework_dialog, final TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                                    if (serverVersion == sdVersion) {
                                        Util_Apk.installApk(activity);
                                    } else {
                                        tv_confirm.setVisibility(View.GONE);
                                        v_split.setVisibility(View.GONE);
                                        Util_Apk.apkToDownloadByCustom(activity, bean_apkInfo.getUrl(), Util_Apk.foreignDownloadApk(activity, tv_content), Util_Apk.foreignDownloadApkError(activity, tv_content, bean_apkInfo.getUrl()));
                                    }
                                }

                                @Override
                                public void onDismissListener() {
                                   /* if (util_Args_runnable != null)
                                        util_Args_runnable.run(true);*/
                                }
                            });
                        else if (isToastRecent)
                            Util_Toast.toast("当前已是最新版本");
                        else if (util_Args_runnable != null)
                            util_Args_runnable.run(true);
                        break;
                    default:
                        if (util_Args_runnable != null)
                            util_Args_runnable.run(false);
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                if (util_Args_runnable != null)
                    util_Args_runnable.run(false);
            }
        });
    }


    private void skipToAct(boolean isHttpOk) {
        if (Abstract_App.bean_userInfo != null) {
            if (!NetworkUtils.isConnected() || !isHttpOk || Abstract_App.bean_userInfo.getPassword() == null)
                startActivity(new Intent(getActivity(), Act_Main_Login.class));
            else
                startActivity(new Intent(getActivity(), Act_Main_Module.class));
        } else
            startActivity(new Intent(getActivity(), Act_Main_Login.class));
        finish();
    }

}
