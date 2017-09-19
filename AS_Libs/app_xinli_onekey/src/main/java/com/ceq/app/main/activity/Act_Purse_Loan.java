package com.ceq.app.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.core.activity.Act_Main_Web;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ceq.app_core.framework.Framework_Web.Extra_String_Url;

/**
 * Created by ceq on 2017/5/12.
 */

public class Act_Purse_Loan extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_loan;
    List<Bean_Item> bean_items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_purse_loan);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "贷款超市", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_loan = (RecyclerView) findViewById(R.id.rv_loan);
        rv_loan.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    public void initData() {
        bean_items.add(new Bean_Item("叮当贷", "1.50%", "https://cube.doraemoney.com/newCube/index.html?proId=a1a77c4da15a4f17aaf5ac25a58b145b", R.mipmap.icon_purse_loan_dingdangdai, Arrays.asList("最快60分钟放款到账", "最高5000元", "有信用卡利率省0%")));
        bean_items.add(new Bean_Item("平安i贷", "2%", "https://www.10100000.com/m/iloan/apply1.html?utm_source=wxhnwxxobn--m&utm_medium=cps&utm_campaign=m0018--iln&utm_content=m-XB014&WT.mc_id=CXX-WXHNWXXOBN-XB014-CSM-M0018ILN&", R.mipmap.icon_purse_loan_pinganidai, Arrays.asList("最快1日放款到账", "最高150000元", "有信用卡利率省0.99%")));
        bean_items.add(new Bean_Item("快钱", "1.50%", "https://www.99bill.com/seashell/html/activity/161125_kyhcach_mm/default.html?datasrc=Link-CSD-HH-038", R.mipmap.icon_purse_loan_kuaiqian, Arrays.asList("最快1分钟放款到账", "最高50000元", "有信用卡利率省0.50%")));
        bean_items.add(new Bean_Item("发薪贷", "2%", "https://h5.faxindai.com:8028/fxd-h5/page/faxindai.html?merchant_code=M12_20170509_10003", R.mipmap.icon_purse_loan_faxindai, Arrays.asList("最快1日放款到账", "最高5000元", "有信用卡利率省0.30%")));
        bean_items.add(new Bean_Item("卡卡贷", "1.50%", "https://game.kkcredit.cn/download/wanying04/kakaadv1", R.mipmap.icon_purse_loan_kakadai, Arrays.asList("最快1日放款到账", "最高50000元", "有信用卡利率省0.85%")));
        bean_items.add(new Bean_Item("你我金融", "2%", "https://www.niiwoo.com/h5/project/fast-loan/fast150.html?channelCode=daixiaofei&linkCode=337", R.mipmap.icon_purse_loan_niwojinrong, Arrays.asList("最快60分钟放款到账", "最高5000元", "有信用卡利率省1.08%")));
        bean_items.add(new Bean_Item("拍拍贷", "80元/个", "http://cps.ppdai.com/landinginfo.html?regsourceid=daixiaofeirao", R.mipmap.icon_purse_loan_paipaidai, Arrays.asList("最快1日放款到账", "最高150000元", "有信用卡利率省1.00%")));
        bean_items.add(new Bean_Item("安家趣花", "1%", "http://ajqh.wap.apass.cn/#/downAppReg?channel=dxf", R.mipmap.icon_purse_loan_anjiaquhua, Arrays.asList("最快3分钟放款到账", "最高3000元", "有信用卡利率省0%")));
    }

    @Override
    public void initAdapter() {
        rv_loan.setAdapter(new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_item1, tv_item2, tv_item3;
                ImageView iv_img;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_item1 = (TextView) itemView.findViewById(R.id.tv_item1);
                    tv_item2 = (TextView) itemView.findViewById(R.id.tv_item2);
                    tv_item3 = (TextView) itemView.findViewById(R.id.tv_item3);

                    iv_img = (ImageView) itemView.findViewById(R.id.iv_img);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_rv_purse_loan, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = bean_items.get(position);
                List<String> stringList = (List<String>) bean.getList_value();
                mvh.iv_img.setImageResource(bean.getImgId());
                mvh.tv_item1.setText(stringList.get(0));
                mvh.tv_item2.setText(stringList.get(1));
                mvh.tv_item3.setText(stringList.get(2));
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Act_Main_Web.class).putExtra(Extra_String_Url, bean.getValue()));
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_items.size();
            }
        });
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
