package com.ceq.app.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;

/**
 * Created by ceq on 2017/5/21.
 */

public class Act_Upgrade_Remit$ZDQB extends Framework_Activity {
    ImageView iv_back;
    TextView tv_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_upgrade_remit);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "转账汇款", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        tv_value = (TextView) findViewById(R.id.tv_value);
        tv_value.setText("公司账户：\n" +
                "户名：广西赚道网络科技有限公司\n" +
                "账号：150271561\n" +
                "开户行：中国民生银行股份有限公司南宁丽原天际支行\n" +
                " \n" +
                "法人账户：\n" +
                "户名：陈冠\n" +
                "账号：6226 2255 0296 9981\n" +
                "开户行：中国民生银行股份有限公司南宁丽原天际支行\n" +
                " \n" +
                "温馨提示：\n" +
                "1、汇款完成后，请及时联系客服人员或拨打400-998-5238完成升级的流程。\n" +
                "2、如支付过程中遇到困难，请联系客服人员或拨打400-998-5238获得帮助。\"");
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
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        }
    }
}
