package com.ceq.app.main.fragment;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.core.bean.Bean_PushData;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceq on 2017/5/11.
 */

public class Frag_Module_Income$MGZB extends Abstract_OkModule_Fragment {
    TextView tv_bill, tv_income;
    RecyclerView rv_money;
    RecyclerView.Adapter rva_money;
    LinearLayout ll_rakeBack;
    ImageView iv_bill;
    View_XRefreshLayout xrv;

    int dark, white;

    List<Bean_Item> moneyList = new ArrayList<>();
    List<Bean_Item> merchantList = new ArrayList<>();

    public Frag_Module_Income$MGZB() {
    }

    public Frag_Module_Income$MGZB(String tabBarTitle, int tabBarImgId ) {
        super(tabBarTitle, tabBarImgId );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dark = getResources().getColor(dark);
        white = getResources().getColor(R.color.text_color_1);
        return init(R.layout.frag_module_income_mgqb);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null,getTabBarTitle(), View.VISIBLE).setTextColor(white);
        findViewById(R.id.icd_title).setBackgroundColor(dark);
        tv_bill = util_init.initTextView(R.id.icd_title, R.id.tv_titleLeft, null, "账单", View.VISIBLE);
        iv_bill = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.icon_share_bill, View.VISIBLE);
        iv_bill.setColorFilter(new LightingColorFilter(Color.BLACK, white));
        tv_income = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "收益分析", View.VISIBLE);

        tv_bill.setTextColor(white);
        tv_income.setTextColor(white);

        //功能
        rv_money = (RecyclerView) findViewById(R.id.rv_money);
        rv_money.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        ll_rakeBack = (LinearLayout) findViewById(R.id.ll_rakeBack);

        xrv = (View_XRefreshLayout) findViewById(R.id.xrv);
    }

    @Override
    public void initData() {
        moneyList.add(new Bean_Item("本月流水", "0", false));
        moneyList.add(new Bean_Item("交易笔数", "0", true));
        moneyList.add(new Bean_Item("新增用户", "0", false));
    }

    @Override
    public void initAdapter() {
        rv_money.setAdapter(rva_money = new RecyclerView.Adapter() {
            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_value;
                LinearLayout ll_bg;


                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    tv_name.setTextColor(white);

                    tv_value = (TextView) itemView.findViewById(R.id.tv_value);
                    tv_value.setVisibility(View.VISIBLE);
                    tv_value.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    tv_value.setTextColor(white);


                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = moneyList.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_value.setText(bean.getSubName());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return moneyList.size();
            }
        });
        xrv.setOnRefreshHttpListener(0, merchantList, null, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                xrv.onHttpEnd();
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, ll_rakeBack);
    }

    @Override
    public void onSelected() {
        Util_Screen.statusBarToTranslucent(getActivity(), getResources().getColor(dark), Util_Screen.Enum_BarFontColor.White);
    }

    @Override
    public void onClick(View v) {
   /*     if (v == ll_rakeBack)
            startActivity(new Intent(getActivity(), Act_Home_RakeBack.class));*/
    }

    @Override
    public void run(Object data) {

    }

    @Override
    public void updateViewByPushMessage(Bean_PushData bean_pushData, Bean_Push bean_push) {

    }
}
