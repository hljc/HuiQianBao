package com.ceq.app.main.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.HeightAttr;
import com.zhy.autolayout.attr.WidthAttr;

import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.Wechat;
import static com.ceq.app.main.methods.Method_Static.getUserGradeRole;

/**
 * Created by ceq on 2017/4/21.
 */

public class Act_Share_QRCodeShare$ZDQB extends Framework_Activity {
    SimpleDraweeView sdv_qrcode;
    ImageView iv_back;
    LinearLayout ll_save, ll_share;
    LinearLayout ll_shareBg;
    TextView tv_remind3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_share_qrcodeshare_zdqb);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "扫码分享", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        sdv_qrcode = (SimpleDraweeView) findViewById(R.id.sdv_qrcode);
        sdv_qrcode.setImageBitmap(Util_QRCode.getInstance().createQRCode(getActivity(), 500, Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareUrl()));

        String appName = (String) getResources().getText(R.string.app_name);
        util_init.initTextView(R.id.icd_user, R.id.tv_name, null, Abstract_App.bean_userInfo.getPhone(), View.VISIBLE).setTextColor(getResources().getColor(R.color.text_color_3));
        util_init.initTextView(R.id.icd_user, R.id.tv_subName, null, new SpanUtils().append("我是").append(appName).append(getUserGradeRole(Abstract_App.bean_userInfo.getGrade())).setForegroundColor(getResources().getColor(R.color.primaryColorOff)).append(",我为").append(appName).append("代言。").create(), View.VISIBLE).setTextColor(getResources().getColor(R.color.text_color_2));
        util_init.initView(R.id.icd_user, R.id.rl_img).setVisibility(View.VISIBLE);
        SimpleDraweeView sdv_img = (SimpleDraweeView) util_init.initView(R.id.icd_user, R.id.sdv_img);
        sdv_img.setImageResource(R.mipmap.app_logo);
        AutoRelativeLayout.LayoutParams layoutParams = (AutoRelativeLayout.LayoutParams) sdv_img.getLayoutParams();
        AutoLayoutInfo autoLayoutInfo = layoutParams.getAutoLayoutInfo();
        autoLayoutInfo.addAttr(new HeightAttr(120, 0, 0));
        autoLayoutInfo.addAttr(new WidthAttr(120, 0, 0));
        ViewGroup.LayoutParams lp2 = sdv_img.getLayoutParams();
        sdv_img.setLayoutParams(lp2);

        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);

        ll_shareBg = (LinearLayout) findViewById(R.id.ll_shareBg);

        TextView tv_titleShare = (TextView) findViewById(R.id.tv_titleShare);
        tv_titleShare.setText(new SpanUtils().append(appName).setBold().append("，您的自动化赚钱机器").setFontSize(16, true).create());

        tv_remind3 = (TextView) findViewById(R.id.tv_remind3);
        if (appName.contains("钱包"))
            appName = appName.substring(0, appName.indexOf("钱包"));
        tv_remind3.setText(new StringBuilder("滴滴改变出行方式，").append(appName).append("改变赚钱方式；"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, ll_save, ll_share);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back) {
            onBackPressed();
        } else if (v == ll_save) {
            Act_ScanCollection_CollectionCode.generateCollectionCodeToSave(findViewById(R.id.ll_root), "分享二维码.png", true, iv_back);
        } else if (v == ll_share) {
            String shareTitle = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareTitle();
            String shareContent = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareContent();
            String shareUrl = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareUrl();
            Util_Share.getInstance().share(Wechat, shareTitle, shareContent, shareUrl, Act_ScanCollection_CollectionCode.generateCollectionCodeToSave(findViewById(R.id.ll_root), "分享二维码.png", false, iv_back));
        }
    }

}
