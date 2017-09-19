package com.ceq.app.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class Act_Upgrade_QRCODE extends Framework_Activity {
    ImageView iv_back, iv_price;
    public static final String Extra_Int_Price = "Extra_Int_Price";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_upgrade_qrcode);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "购买产品", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        iv_price = (ImageView) findViewById(R.id.iv_price);

        iv_price.setImageResource(getIntent().getIntExtra(Extra_Int_Price, R.mipmap.zfb_5000_mstb));
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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId())
            onBackPressed();
    }
}
