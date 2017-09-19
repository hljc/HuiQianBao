package com.ceq.app.main.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.utils.extend.picker.abstracts.Util_Picker;
import com.ceq.app_core.utils.extend.picker.interfaces.callback.Inter_Picker_Callback;
import com.ceq.app_photo.utils.Util_Image;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;

/**
 * Created by ceq on 2017/5/13.
 */

public class Act_Purse_MerchantCertification$ZDQB extends Framework_Activity {
    ImageView iv_back;
    TextView tv_area, tv_managementForm;
    EditText et_merchantName, et_merchantAddress;
    TextView tv_commit;
    SimpleDraweeView sdv_certification1, sdv_certification2, sdv_certification3, sdv_certification4, sdv_certification5;
    List<String> pathList = new ArrayList<>();
    TextView tv_remind1, tv_remind2, tv_remind3, tv_remind4, tv_remind5;
    LinearLayout all_managementForm;

    public enum ManagementForm {
        个人(0), 个体工商户(1), 企业(2);
        int index;

        ManagementForm(int index) {
            this.index = index;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_purse_merchantcertification_zdqb);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "商户登记", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        tv_area = (TextView) findViewById(R.id.tv_area);
        tv_managementForm = (TextView) findViewById(R.id.tv_managementForm);

        et_merchantName = (EditText) findViewById(R.id.et_merchantName);
        et_merchantAddress = (EditText) findViewById(R.id.et_merchantAddress);
        et_merchantAddress = (EditText) findViewById(R.id.et_merchantAddress);

        sdv_certification1 = (SimpleDraweeView) findViewById(R.id.sdv_certification1);
        sdv_certification2 = (SimpleDraweeView) findViewById(R.id.sdv_certification2);
        sdv_certification3 = (SimpleDraweeView) findViewById(R.id.sdv_certification3);
        sdv_certification4 = (SimpleDraweeView) findViewById(R.id.sdv_certification4);
        sdv_certification5 = (SimpleDraweeView) findViewById(R.id.sdv_certification5);

        tv_remind1 = (TextView) findViewById(R.id.tv_remind1);
        tv_remind2 = (TextView) findViewById(R.id.tv_remind2);
        tv_remind3 = (TextView) findViewById(R.id.tv_remind3);
        tv_remind4 = (TextView) findViewById(R.id.tv_remind4);
        tv_remind5 = (TextView) findViewById(R.id.tv_remind5);

        //完成
        tv_commit = util_init.initTextView(R.id.icd_commit, R.id.tv_button, null, "完成", View.VISIBLE);

        tv_remind1.setText("1、身份证复印件(原件或复印件签字+手印)");
        tv_remind2.setText("2、银行卡复印件(原件或复印件签字+手印)");
        tv_remind3.setText("3、经营场所租赁合同(原件扫描件)");
        tv_remind4.setText("4、经营场所照片");
        tv_remind5.setVisibility(View.GONE);

        LinearLayout all_remind = (LinearLayout) findViewById(R.id.all_remind);
        all_managementForm = (LinearLayout) findViewById(R.id.all_managementForm);
        TextView tv_uploadTitle = (TextView) findViewById(R.id.tv_uploadTitle);
        all_remind.setVisibility(View.VISIBLE);
        all_managementForm.setVisibility(View.VISIBLE);
        tv_uploadTitle.setText("上传照片(注意:不同经营形式上传的照片不同)");

    }

    public  void setSimpleDraweeView(ManagementForm managementForm) {
        pathList.clear();
        switch (managementForm) {
            case 个人:
                pathList.add(null);
                pathList.add(null);
                pathList.add(null);
                pathList.add(null);
                sdv_certification1.setImageResource(R.mipmap.geren1);
                sdv_certification2.setImageResource(R.mipmap.geren2);
                sdv_certification3.setImageResource(R.mipmap.geren3);
                sdv_certification4.setImageResource(R.mipmap.geren4);
                sdv_certification1.setVisibility(View.VISIBLE);
                sdv_certification2.setVisibility(View.VISIBLE);
                sdv_certification3.setVisibility(View.VISIBLE);
                sdv_certification4.setVisibility(View.VISIBLE);
                sdv_certification5.setVisibility(View.INVISIBLE);
                sdv_certification5.setOnClickListener(null);
                tv_remind1.setText("1、身份证复印件(原件或复印件签字+手印)");
                tv_remind2.setText("2、银行卡复印件(原件或复印件签字+手印)");
                tv_remind3.setText("3、经营场所租赁合同(原件扫描件)");
                tv_remind4.setText("4、经营场所照片");
                tv_remind5.setVisibility(View.GONE);
                break;
            case 个体工商户:
                pathList.add(null);
                pathList.add(null);
                pathList.add(null);
                pathList.add(null);
                pathList.add(null);
                sdv_certification1.setImageResource(R.mipmap.getigongshanghu1);
                sdv_certification2.setImageResource(R.mipmap.getigongshanghu2);
                sdv_certification3.setImageResource(R.mipmap.getigongshanghu3);
                sdv_certification4.setImageResource(R.mipmap.getigongshanghu4);
                sdv_certification5.setImageResource(R.mipmap.getigongshanghu5);
                sdv_certification1.setVisibility(View.VISIBLE);
                sdv_certification2.setVisibility(View.VISIBLE);
                sdv_certification3.setVisibility(View.VISIBLE);
                sdv_certification4.setVisibility(View.VISIBLE);
                sdv_certification5.setVisibility(View.VISIBLE);
                tv_remind1.setText("1、营业执照(原件或复印件加盖公章)");
                tv_remind2.setText("2、法人身份证(原件或复印件加盖公章)");
                tv_remind3.setText("3、法人银行卡复印件(正反面)");
                tv_remind4.setText("4、可授权第三方收款(授权书盖章扫描件)");
                tv_remind5.setText("5、个体户没有公章的可法人签字+手印");
                tv_remind5.setVisibility(View.VISIBLE);
                break;
            case 企业:
                pathList.add(null);
                pathList.add(null);
                pathList.add(null);
                pathList.add(null);
                pathList.add(null);
                sdv_certification1.setImageResource(R.mipmap.qiye1);
                sdv_certification2.setImageResource(R.mipmap.qiye2);
                sdv_certification3.setImageResource(R.mipmap.qiye3);
                sdv_certification4.setImageResource(R.mipmap.qiye4);
                sdv_certification5.setImageResource(R.mipmap.qiye5);
                sdv_certification1.setVisibility(View.VISIBLE);
                sdv_certification2.setVisibility(View.VISIBLE);
                sdv_certification3.setVisibility(View.VISIBLE);
                sdv_certification4.setVisibility(View.VISIBLE);
                sdv_certification5.setVisibility(View.VISIBLE);
                tv_remind1.setText("1、营业执照(原件或复印件加盖公章)");
                tv_remind2.setText("2、税务登记证(原件或复印件加盖公章)");
                tv_remind3.setText("3、组织机构代码证(原件或复印件加盖公章)");
                tv_remind4.setText("4、法人身份证(原件或复印件加盖公章)");
                tv_remind5.setText("5、可授权第三方收款(授权书盖章扫描件)");
                tv_remind5.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void initData() {
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);
        Util_View.viewOnClick(this, tv_commit, tv_area, tv_managementForm);
        Util_View.viewOnClick(this, sdv_certification1, sdv_certification2, sdv_certification3, sdv_certification4, sdv_certification5);
    }

    @Override
    public void onClick(View v) {
        if (v == sdv_certification1) {
            if (Util_Empty.isEmptyToToast(tv_managementForm.getText().toString(), tv_managementForm.getHint().toString()))
                return;
            setCertificationImage(0, sdv_certification1, pathList);
        }

        if (v == sdv_certification2) {
            if (Util_Empty.isEmptyToToast(tv_managementForm.getText().toString(), tv_managementForm.getHint().toString()))
                return;
            setCertificationImage(1, sdv_certification2, pathList);
        }
        if (v == sdv_certification3) {
            if (Util_Empty.isEmptyToToast(tv_managementForm.getText().toString(), tv_managementForm.getHint().toString()))
                return;
            setCertificationImage(2, sdv_certification3, pathList);
        }
        if (v == sdv_certification4) {
            if (Util_Empty.isEmptyToToast(tv_managementForm.getText().toString(), tv_managementForm.getHint().toString()))
                return;
            setCertificationImage(3, sdv_certification4, pathList);
        }
        if (v == sdv_certification5) {
            if (Util_Empty.isEmptyToToast(tv_managementForm.getText().toString(), tv_managementForm.getHint().toString()))
                return;
            setCertificationImage(4, sdv_certification5, pathList);
        }
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v.getId() == tv_area.getId()) {
            Util_Picker.getInstance().useCityPicker(getActivity(), tv_area.getHint().toString(), null, null, null, false, false, new Inter_Picker_Callback.OnAddressPickListener() {
                @Override
                public void onAddressPicked(String province, String city, String county) {
                    tv_area.setText(new StringBuilder().append(province).append("-").append(city).append("-").append(county));
                }
            });
        } else if (v.getId() == tv_managementForm.getId()) {
            String[] results = new String[ManagementForm.values().length];
            for (int i = 0, size = ManagementForm.values().length; i < size; i++) {
                results[i] = ManagementForm.values()[i].name();
            }
            String form = tv_managementForm.getText().toString();
            Util_Picker.getInstance().useOptionPicker(getActivity(), tv_managementForm.getHint().toString(), form.length() == 0 ? 0 : ManagementForm.valueOf(form).index, results, new Inter_Picker_Callback.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    tv_managementForm.setText(item);
                    setSimpleDraweeView(ManagementForm.valueOf(item));
                }
            });
        } else if (v.getId() == tv_commit.getId()) {
            if (Util_Empty.isEmptyToToast(et_merchantName.getText().toString(), et_merchantName.getHint().toString()))
                return;
            if (all_managementForm.getVisibility() == View.VISIBLE && Util_Empty.isEmptyToToast(tv_managementForm.getText().toString(), tv_managementForm.getHint().toString()))
                return;
            if (Util_Empty.isEmptyToToast(tv_area.getText().toString(), tv_area.getHint().toString()))
                return;
            if (Util_Empty.isEmptyToToast(et_merchantAddress.getText().toString(), et_merchantAddress.getHint().toString()))
                return;
            if (pathList.contains(null)) {
                Util_Toast.toast(new StringBuilder("必须上传").append(pathList.size()).append("张照片"));
                return;
            }
            uploadCertificationImage(getActivity(), et_merchantName.getText().toString(), tv_area.getText().toString(), et_merchantAddress.getText().toString(), tv_managementForm.getText().toString(),pathList);
        }
    }

    public static void uploadCertificationImage(final Activity activity, final String name, final String address, final String shopsaddress, final String management_form, final List<String> pathList) {
        Util_Http.httpToFile(activity, Constant_Api.URL_MERCHANT_CERTIFICATION_POST.concat(Abstract_App.bean_userInfo.getUserToken()), new Util_Http.HttpHeadersAndFile() {
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
                try {
                    httpParams.put("name", URLEncoder.encode(name, "utf-8"));
                    httpParams.put("address", URLEncoder.encode(address, "utf-8"));
                    httpParams.put("shopsaddress", URLEncoder.encode(shopsaddress, "utf-8"));
                    httpParams.put("management_form", URLEncoder.encode(management_form, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }, new Util_Http.HttpDealFileListener(activity, Constant_International.message_net_uploading, null, null, true, true) {

            @Override
            public void onDealResult(String data, File file, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast("上传成功");
                        activity.finish();
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

    public static void setCertificationImage(final int id, final SimpleDraweeView simpleDraweeView, final List<String> pathList) {
        Activity activity = (Activity) simpleDraweeView.getContext();
        Util_Image.imageToIntentActivityByCustom(activity, true, false, false, 1, new Util_Image.ImagePickerCallBack() {
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
}
