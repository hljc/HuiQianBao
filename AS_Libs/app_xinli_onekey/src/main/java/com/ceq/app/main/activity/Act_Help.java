package com.ceq.app.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;

import static com.ceq.app_core.framework.Framework_Web.Extra_String_Title;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class Act_Help extends Framework_Activity {
    ImageView iv_back, iv_help;
    public static final String Extra_Int_Img = "Extra_Int_Img";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_collection_help);
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, intent.getStringExtra(Extra_String_Title), View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        iv_help = (ImageView) findViewById(R.id.iv_help);
        iv_help.setImageResource(intent.getIntExtra(Extra_Int_Img, 0));
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
        if (v == iv_back) {
            onBackPressed();
        }
    }
}
