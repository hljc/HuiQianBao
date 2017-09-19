package com.ceq.app.main.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_photo.utils.Util_Image;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.constant.RegexConstants.REGEX_ID_CARD18;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;

/**
 * Created by ceq on 2017/5/6.
 */

public class Act_Mine_Certification extends Framework_Activity {
    TextView tv_commit;
    ImageView iv_back;
    SimpleDraweeView sdv_certification1, sdv_certification2, sdv_certification3;
    List<String> pathList = new ArrayList<>();
    List<String> srcList = new ArrayList<>();
    EditText et_bankUserName, et_bankIdentify;
    static int seconds = 0;
    ImageView iv1, iv2, iv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_settings_certification);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tv_commit.setEnabled(seconds == 0);
            if (seconds == 0) {
                tv_commit.setText("提交");
                tv_commit.removeCallbacks(this);
            } else {
                tv_commit.setText(new StringBuilder("提交(").append(seconds).append("s)"));
                tv_commit.postDelayed(this, 1000);
                --seconds;
            }
        }
    };

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "实名认证", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        sdv_certification1 = (SimpleDraweeView) findViewById(R.id.sdv_certification1);
        sdv_certification2 = (SimpleDraweeView) findViewById(R.id.sdv_certification2);
        sdv_certification3 = (SimpleDraweeView) findViewById(R.id.sdv_certification3);

        //银行用户名
        et_bankUserName = util_init.initEditText(R.id.icd_bankUserName, R.id.et_input, "请输入真实姓名", null, InputType.TYPE_CLASS_TEXT, LENGTH_USER, View.VISIBLE);
        util_init.initTextView(R.id.icd_bankUserName, R.id.tv_left, null, "用户名", View.VISIBLE);
        util_init.initView(R.id.icd_bankUserName, R.id.v_split, View.VISIBLE);
        //身份证
        et_bankIdentify = util_init.initEditText(R.id.icd_bankIdentify, R.id.et_input, "请输入身份证", null, InputType.TYPE_CLASS_TEXT, 18, View.VISIBLE);
        util_init.initTextView(R.id.icd_bankIdentify, R.id.tv_left, null, "身份证", View.VISIBLE);
        util_init.initView(R.id.icd_bankIdentify, R.id.v_split, View.VISIBLE);

        //提交按钮
        tv_commit = util_init.initTextView(R.id.icd_commit, R.id.tv_button, null, "提交", View.VISIBLE);
        tv_commit.post(runnable);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
    }


    @Override
    public void initData() {
        pathList.add(null);
        pathList.add(null);
        pathList.add(null);
        srcList.add(new Uri.Builder().scheme("res").path(String.valueOf(R.mipmap.bg_certification_foreground)).build().toString());
        srcList.add(new Uri.Builder().scheme("res").path(String.valueOf(R.mipmap.bg_certification_background)).build().toString());
        srcList.add(new Uri.Builder().scheme("res").path(String.valueOf(R.mipmap.bg_certification_hold)).build().toString());
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_commit, iv_back);
        Util_View.viewOnClick(this, iv1, iv2, iv3);
        Util_View.viewOnClick(this, sdv_certification1, sdv_certification2, sdv_certification3);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == tv_commit.getId()) {
            if (Util_Empty.isEmptyToToast(et_bankUserName.getText().toString(), et_bankUserName.getHint().toString()))
                return;
            if (Util_Empty.isEmptyAndMatchToToast(et_bankIdentify.getText().toString(), et_bankIdentify.getHint().toString(), REGEX_ID_CARD18, Constant_International.error_identify_num))
                return;
            if (Abstract_App.bean_userInfo.getRealnameStatus().equals("1")) {
                Util_Toast.toast("您已实名审核通过！");
                return;
            }
            if (pathList.contains(null)) {
                Util_Toast.toast("必须上传3张照片");
                return;
            }
            seconds = 100;
            tv_commit.post(runnable);
            verificationInfo(et_bankUserName.getText().toString(), et_bankIdentify.getText().toString());
        } else if (v.getId() == iv_back.getId()) {
            onBackPressed();
        }
        if (v == sdv_certification1) setCertificationImage(0, sdv_certification1);
        if (v == sdv_certification2) setCertificationImage(1, sdv_certification2);
        if (v == sdv_certification3) setCertificationImage(2, sdv_certification3);

        if (v == iv1) Util_Image.imageToWatchDetail(getActivity(), srcList, 0);
        if (v == iv2) Util_Image.imageToWatchDetail(getActivity(), srcList, 1);
        if (v == iv3) Util_Image.imageToWatchDetail(getActivity(), srcList, 2);
    }

    public void setCertificationImage(final int id, final SimpleDraweeView simpleDraweeView) {
        Util_Image.imageToIntentActivityByCustom(getActivity(), true, false, false, 1, new Util_Image.ImagePickerCallBack() {
            @Override
            public void takeCancel() {

            }

            @Override
            public void takeFail(TResult result, String msg) {

            }

            @Override
            public void takeSuccess(TResult result) {
                String path = result.getImage().getOriginalPath();
                if (pathList.contains(path)) {
                    Util_Toast.toast("已经存在该照片");
                    return;
                }
                pathList.remove(id);
                pathList.add(id, path);
                Util_Image.imageToShowByFresco(simpleDraweeView, Uri.parse("file://".concat(path)), 50, 50);
            }
        });
    }

    private void verificationInfo(final String realname, final String idcard) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_CERTIFICATION_VERIFICATION_INFO_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("realname", realname);
                httpParams.put("idcard", idcard);

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_verification, false, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                String result = null;
                JSONObject jsonObject = JSON.parseObject(data).getJSONObject("result");
                if (jsonObject != null)
                    result = jsonObject.getString("result");
                String resp_message = JSON.parseObject(data).getString("resp_message");
                if (result != null)
                    switch (result) {
                        case "1":
                            uploadCertificationImage();
                            break;
                        default:
                            Util_Http.dismiss();
                            Util_Toast.toast("实名信息有误!");
                            break;
                    }
                else if (resp_message != null) {
                    Util_Http.dismiss();
                    Util_Toast.toast(resp_message);
                }
            }
        });
    }

    void uploadCertificationImage() {
        Util_Http.httpToFile(getActivity(), Constant_Api.URL_CERTIFICATION_UPLOAD_IMAGE_POST, new Util_Http.HttpHeadersAndFile() {
            @Override
            public PostRequest addFile(PostRequest postRequest) {
                List<File> files = new ArrayList<File>();
                for (int i = 0; i < pathList.size(); i++) {
                    files.add(new File(pathList.get(i)));
                }
                postRequest.addFileParams("image", files);
                return postRequest;
            }

            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", Abstract_App.bean_userInfo.getPhone());

            }
        }, new Util_Http.HttpDealFileListener(getActivity(), Constant_International.message_net_uploading, null, null, true, true) {

            @Override
            public void onDealResult(String data, File file, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast("上传成功");
                        finish();
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

            @Override
            public void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

            }
        });
    }
}
