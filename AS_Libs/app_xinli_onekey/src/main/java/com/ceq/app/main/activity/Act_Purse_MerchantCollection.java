package com.ceq.app.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SpanUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_MerchantInfo;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.utils.extend.qrcode.abstracts.Util_QRCode;
import com.ceq.app_core.utils.extend.share.abstracts.Util_Share;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.Wechat;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class Act_Purse_MerchantCollection extends Framework_Activity {
    SimpleDraweeView sdv_qrcode;
    ImageView iv_back;
    LinearLayout ll_shareBg, ll_save, ll_share;
    Bean_MerchantInfo bean_merchantInfo;
    TextView tv_merchantName,tv_merchantAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_purse_merchantcollection);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "商家收款码", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        tv_merchantName = (TextView) findViewById(R.id.tv_merchantName);
        tv_merchantAddress = (TextView) findViewById(R.id.tv_merchantAddress);

        sdv_qrcode = (SimpleDraweeView) findViewById(R.id.sdv_qrcode);

        String appName = (String) getResources().getText(R.string.app_name);

        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);

        ll_shareBg = (LinearLayout) findViewById(R.id.ll_shareBg);

        TextView tv_titleShare = (TextView) findViewById(R.id.tv_titleShare);
        tv_titleShare.setText(new SpanUtils().append(appName).setBold().append("，您的一码多付").setBoldItalic().setFontSize(16,true).create());

    }

    @Override
    public void initData() {
        getMerchantData();
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back,  ll_save, ll_share);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back) {
            onBackPressed();
        } else if (v == ll_save) {
            Act_ScanCollection_CollectionCode.generateCollectionCodeToSave(findViewById(R.id.ll_root), "商家二维码.png",true,iv_back);
        } else if (v == ll_share) {
            String shareTitle = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareTitle();
            String shareContent = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareContent();
            Util_Share.getInstance().share(Wechat, shareTitle, shareContent, bean_merchantInfo == null ? "" : bean_merchantInfo.getSrc(), Act_ScanCollection_CollectionCode.generateCollectionCodeToSave(findViewById(R.id.ll_root), "商家二维码.png",false,iv_back));
        }
    }

    private void getMerchantData() {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_MERCHANT_COLLECTION_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("userid", Abstract_App.bean_userInfo.getId());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_request, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        bean_merchantInfo = JSON.parseObject(JSON.parseObject(data).getString(Http_Key_Data), Bean_MerchantInfo.class);
                        tv_merchantName.setText(bean_merchantInfo.getName().trim());
                        tv_merchantAddress.setText(bean_merchantInfo.getShopsaddress().trim());
                        sdv_qrcode.setImageBitmap(Util_QRCode.getInstance().createQRCode(getActivity(), 500, bean_merchantInfo.getSrc()));
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
