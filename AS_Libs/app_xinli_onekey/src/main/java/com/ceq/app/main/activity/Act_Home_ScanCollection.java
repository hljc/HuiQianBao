package com.ceq.app.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_Collection;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.Result_Code_Scan_Collection;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.skipToPayPage;

/**
 * Created by ceq on 2017/4/15.
 */

public class Act_Home_ScanCollection extends Framework_Activity {
    ImageView iv_back, iv_T1;
    LinearLayout ll_d0, ll_t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_home_scancollection);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "扫码收款", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        ll_d0 = (LinearLayout) findViewById(R.id.ll_d0);
        ll_t1 = (LinearLayout) findViewById(R.id.ll_t1);

        iv_T1 = (ImageView) findViewById(R.id.iv_T1);
        iv_T1.setColorFilter(new LightingColorFilter(Color.BLACK, Color.rgb(100, 200, 100)));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, iv_T1);
        Util_View.viewOnClick(this, ll_d0, ll_t1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v.getId() == ll_d0.getId()) {
            startActivity(new Intent(getActivity(), Act_Home_SetCollectionMoney.class).putExtra(Act_Home_SelectChannel.Extra_Int_Function_Code, Result_Code_Scan_Collection));
        } else if (v.getId() == ll_t1.getId()) {
            startActivity(new Intent(getActivity(), Act_Home_SetCollectionMoney.class).putExtra(Act_Home_SelectChannel.Extra_Int_Function_Code, Result_Code_Scan_Collection));
        }
    }

    public static void getCollectionCodeData(final Activity activity, final Bean_Collection bean_collection) {
        Util_Http.httpToRequest(activity,Constant_Api.URL_FAST_COLLECTION_AND_QRCODE__POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("order_desc", bean_collection.getRemark());
                httpParams.put("phone", Abstract_App.bean_userInfo.getPhone());
                httpParams.put("amount", bean_collection.getMoney().replace(".",""));
                httpParams.put("channe_tag", bean_collection.getChannelTag().toString());

            }
        }, new Util_Http.HttpDealStringListener(activity, Constant_International.message_net_generate_order, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        skipToPayPage( activity,data, bean_collection,null);
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }
}
