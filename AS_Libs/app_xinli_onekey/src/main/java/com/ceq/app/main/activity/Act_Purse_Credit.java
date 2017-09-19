package com.ceq.app.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class Act_Purse_Credit extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_credit;
    List<Bean_Item> bean_items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_purse_credit);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "信用卡申请", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_credit = (RecyclerView) findViewById(R.id.rv_credit);
        rv_credit.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        bean_items.add(new Bean_Item("兴业信用卡","80元/张","https://ccshop.cib.com.cn:8010/application/cardapp/cappl/ApplyCard/toSelectCard?id=6c339a20b0a04a77a7afef484952841d",R.mipmap.icon_purse_credit_xingye, Arrays.asList("现金分期5倍积分","坐享酒店机票优惠","至尊租车礼遇")));
        bean_items.add(new Bean_Item("交通信用卡","90元/张","https://creditcardapp.bankcomm.com/applynew/front/apply/track/record.html?trackCode=A030219306518",R.mipmap.icon_purse_credit_jiaotong, Arrays.asList("超市、加油全年返5%","免息还款期最长达56天","最快当天领卡")));
        bean_items.add(new Bean_Item("玖富万卡","1.50%","https://onecard.9fbank.com/wkCubeNew/#/register?proId=dxf19d0cfcab071385bea1f9e7a5b6e11e8f",R.mipmap.icon_purse_credit_jiufu, Arrays.asList("商城分期消费","信用额度可循环使用","年轻人专属的信用账户")));
        bean_items.add(new Bean_Item("光大信用卡","60元/张","https://xyk.cebbank.com/cebmms/apply/ps/card-index.htm?req_card_id=3341&pro_code=FHTG067633SJ41ZZSH",R.mipmap.icon_purse_credit_guangda, Arrays.asList("开卡免首年年费","刷三笔免次年年费","交易更安全")));
        bean_items.add(new Bean_Item("民生信用卡","60元/张","https://creditcard.cmbc.com.cn/wsonline/home/homeHZ.jhtml?recommendInfo=x9fs/pH1hQJRDaSG0JpQfBIS8D8ONASSxjEQvoViW1LVbTtJqq1S8JVc7yCNA5ddwg8cXvl1KECJHAv4yqPDrsGtfEIP8GZ5WR5YCPSnDpcSA/MNpwjFBTduvSo9oc0WXocLzBZ1mbaJp/WSm/nuY44SYLyw9dgSLpjKbcrmgY9qee4BouC+JdTIhM+m5wPyDrfOfAPt0wOeLfKlpHwofpPLSUs2BWzMPl+pJb6yfrxBpCgCSwgiCXrINJRlAvooDGBAhimQgniScZMn5B5z3Z4xHpj5fPIR9vj715AyCSN4id9gbodD+/DsYTEiDCWQ6ZGrCZA+mm7ffWEQil56dw==&time=1498559290346&time=1498559309453",R.mipmap.icon_purse_credit_guangda, Arrays.asList("开卡免首年年费","刷三笔免次年年费","交易更安全")));
    }

    @Override
    public void initAdapter() {
        rv_credit.setAdapter(new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name,tv_item1,tv_item2,tv_item3,tv_apply,tv_other;
                ImageView iv_card;
                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name= (TextView) itemView.findViewById(R.id.tv_name);
                    tv_item1= (TextView) itemView.findViewById(R.id.tv_item1);
                    tv_item2= (TextView) itemView.findViewById(R.id.tv_item2);
                    tv_item3= (TextView) itemView.findViewById(R.id.tv_item3);
                    tv_apply= (TextView) itemView.findViewById(R.id.tv_apply);
                    tv_other= (TextView) itemView.findViewById(R.id.tv_other);

                    iv_card= (ImageView) itemView.findViewById(R.id.iv_card);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_rv_purse_credit, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = bean_items.get(position);
                List<String> stringList= (List<String>) bean.getList_value();
                mvh.tv_name.setText(bean.getName().toString());
                mvh.iv_card.setImageResource(bean.getImgId());
                mvh.tv_item1.setText(stringList.get(0));
                mvh.tv_item2.setText(stringList.get(1));
                mvh.tv_item3.setText(stringList.get(2));
                mvh.tv_other.setText("申请有好礼");
                mvh.tv_apply.setOnClickListener(new View.OnClickListener() {
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
