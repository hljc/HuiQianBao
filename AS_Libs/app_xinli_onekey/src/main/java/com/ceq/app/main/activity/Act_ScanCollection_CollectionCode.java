package com.ceq.app.main.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_Collection;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_File;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.utils.extend.qrcode.abstracts.Util_QRCode;
import com.ceq.app_core.utils.extend.share.abstracts.Util_Share;
import com.ceq.app_photo.utils.Util_Image;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.ceq.app.main.activity.Act_Home_SelectChannel.Extra_Bean_Collection;
import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.Wechat;

/**
 * Created by ceq on 2017/4/15.
 */

public class Act_ScanCollection_CollectionCode extends Framework_Activity {
    ImageView iv_back, iv_warning, iv_share;
    SimpleDraweeView sdv_collectionCode;
    TextView tv_title;
    Bean_Collection bean_collection;
    LinearLayout ll_save;
    CardView cv_bg;
    ImageView iv_bottom;
    TextView tv_scanPlatform, tv_scanRemind1, tv_scanRemind2, tv_collectionMoney, tv_remark;
    public static final String Extra_Bean_Channel = "Extra_Bean_Channel";
    Bean_Channel bean_channel=new Bean_Channel();
    SimpleDraweeView sdv_scanPlatform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_scancollection_collectioncode);
    }

    @Override
    public void initView() {
        bean_channel = (Bean_Channel) getIntent().getSerializableExtra(Extra_Bean_Channel);
        bean_collection = (Bean_Collection) getIntent().getSerializableExtra(Extra_Bean_Collection);

        int color = getResources().getColor(R.color.primaryColorOn);
        //标题栏
        tv_title = util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "收款码", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        iv_share = util_init.initImageView(R.id.icd_title, R.id.iv_titleRight, R.mipmap.icon_collectioncode_share, View.VISIBLE);

        sdv_collectionCode = (SimpleDraweeView) findViewById(R.id.sdv_collectionCode);

        //描述
        sdv_scanPlatform = (SimpleDraweeView) findViewById(R.id.sdv_scanPlatform);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);

        tv_scanPlatform = (TextView) findViewById(R.id.tv_scanPlatform);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
        tv_scanRemind1 = (TextView) findViewById(R.id.tv_scanRemind1);
        tv_scanRemind2 = (TextView) findViewById(R.id.tv_scanRemind2);
        tv_collectionMoney = (TextView) findViewById(R.id.tv_collectionMoney);
        iv_warning = (ImageView) findViewById(R.id.iv_warning);
        iv_warning.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.primaryColorOff)));

        setView(color);

        ll_save = (LinearLayout) findViewById(R.id.ll_save);

        cv_bg = (CardView) findViewById(R.id.cv_bg);

        tv_remark.setText(Util_Empty.isEmpty(bean_channel) || Util_Empty.isEmpty(bean_channel.getRemarks()) ? "" : bean_channel.getRemarks());
    }

    private void setView(int color) {
        if (bean_channel == null)
            return;
        sdv_collectionCode.setImageBitmap(Util_QRCode.getInstance().createQRCode(getActivity(), 500, bean_collection.getQrCodeUrl()));
        String money = bean_collection.getMoney();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        money = decimalFormat.format(new BigDecimal(money));
        sdv_scanPlatform.setImageURI(bean_channel.getLog());
        tv_collectionMoney.setText("￥".concat(money));
        tv_title.setText(bean_channel.getSubName().concat("收款码"));
        tv_scanPlatform.setText(bean_channel.getSubName().concat("收款").concat(bean_channel.getChannelParams()));
        //tv_balanceWay.setText("【结算方式".concat("】"));
        tv_scanRemind2.setText(new StringBuilder("请使用").append(bean_channel.getSubName()).append("扫描下方二维码"));
        tv_scanRemind1.setText(new SpanUtils().append("商家")
                .append(bean_collection.getRealName()).setForegroundColor(color)
                .append("正在向您发起一笔金额为")
                .append(money).setForegroundColor(color)
                .append("元的")
                .append(bean_channel.getSubName())
                .append("收款").create());
        iv_bottom.setColorFilter(new LightingColorFilter(Color.argb(5, 10, 10, 10), color));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, ll_save, iv_share);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back)
            onBackPressed();
        else if (v == ll_save) {
            if (Util_Empty.isEmptyToToast(bean_channel, "未获取通道信息"))
                return;
            generateCollectionCodeToSave(cv_bg, new StringBuilder(bean_channel.getSubName()).append(bean_channel.getChannelParams()).append("_").append(bean_collection.getMoney()).append("元.png").toString(), true, iv_back);
        } else if (iv_share == v) {
            if (Util_Empty.isEmptyToToast(bean_channel, "未获取通道信息"))
                return;
            String shareTitle = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareTitle();
            String shareContent = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareContent();
            String shareUrl = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareUrl();
            Util_Share.getInstance().share(Wechat, shareTitle, shareContent, shareUrl, generateCollectionCodeToSave(cv_bg, new StringBuilder(bean_channel.getSubName()).append(bean_channel.getChannelParams()).append("_").append(bean_collection.getMoney()).append("元.png").toString(), false, iv_back));
        }
    }

    public static String generateCollectionCodeToSave(View view, String filename, boolean isToast, View... hide) {
        if (hide != null)
            for (int i = 0, size = hide.length; i < size; i++) {
                hide[i].setVisibility(View.GONE);
            }
        final File file = new File(Util_File.fileToAppDir(), filename);
        ImageUtils.save(Util_Image.createViewBitmap(view), file, Bitmap.CompressFormat.PNG);
        Util_Image.scanPhoto(file);
        if (isToast)
            Util_Toast.toast("已保存在".concat(file.toString().substring(Util_File.fileToAppDir().toString().lastIndexOf("/"))));
        if (hide != null)
            for (int i = 0, size = hide.length; i < size; i++) {
                hide[i].setVisibility(View.VISIBLE);
            }
        return file.getPath();
    }
}
