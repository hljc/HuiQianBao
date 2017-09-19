package com.ceq.app.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.utils.extend.qrcode.abstracts.Util_QRCode;
import com.ceq.app_core.utils.extend.share.abstracts.Util_Share;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.Wechat;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class Act_Share_QRCodeShare$JFB extends Framework_Activity {
    SimpleDraweeView sdv_qrcode;
    LinearLayout ll_save, ll_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_share_qrcodeshare_jfb, false);
    }

    @Override
    public void initView() {
        sdv_qrcode = (SimpleDraweeView) findViewById(R.id.sdv_qrcode);
        sdv_qrcode.setImageBitmap(Util_QRCode.getInstance().createQRCode(getActivity(), 500, Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareUrl()));

        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, ll_save, ll_share);
    }

    @Override
    public void onClick(View v) {
        if (v == ll_save) {
            Act_ScanCollection_CollectionCode.generateCollectionCodeToSave(findViewById(R.id.rl_root), "分享二维码.png", true);
        } else if (v == ll_share) {
            String shareTitle = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareTitle();
            String shareContent = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareContent();
            String shareUrl = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareUrl();
            Util_Share.getInstance().share(Wechat, shareTitle, shareContent, shareUrl, Act_ScanCollection_CollectionCode.generateCollectionCodeToSave(findViewById(R.id.rl_root), "分享二维码.png", false));
        }
    }
}
