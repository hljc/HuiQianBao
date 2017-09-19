package com.ceq.app.main.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SpanUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.utils.extend.qrcode.abstracts.Util_QRCode;
import com.ceq.app_core.utils.extend.share.abstracts.Util_Share;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.Wechat;
import static com.ceq.app.main.methods.Method_Static.getUserGradeRole;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class Act_Share_QRCodeShare$SKJF extends Framework_Activity {
    SimpleDraweeView sdv_qrcode;
    LinearLayout ll_save, ll_share;
    TextView tv_description, tv_mobile,tv_remind,tv_name,tv_brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_share_qrcodeshare_skjf, false);
    }

    @Override
    public void initView() {
        sdv_qrcode = (SimpleDraweeView) findViewById(R.id.sdv_qrcode);
        sdv_qrcode.setImageBitmap(Util_QRCode.getInstance().createQRCode(getActivity(), 500, Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareUrl()));

        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);

        String brandName=getText(R.string.app_name).toString();

        tv_brand = (TextView) findViewById(R.id.tv_brand);
        tv_brand.setShadowLayer(2, 2, 3, Color.parseColor("#50000000"));
        tv_brand.setText(brandName);

        tv_remind = (TextView) findViewById(R.id.tv_remind);
        tv_remind.setShadowLayer(2, 0, 3, Color.parseColor("#50dddddd"));

        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_mobile.setText(Abstract_App.bean_userInfo.getPhone());

        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_description.setText(new SpanUtils().append("我是").append(brandName).append(getUserGradeRole(Abstract_App.bean_userInfo.getGrade())).setBold().append(",我为").setItalic().append(brandName).setItalic().append("代言!").setItalic().create());
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
