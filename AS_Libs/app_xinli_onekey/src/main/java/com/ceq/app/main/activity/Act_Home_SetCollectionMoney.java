package com.ceq.app.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_BankCardInfo;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_Collection;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.blankj.utilcode.util.ScreenUtils.getScreenWidth;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.Extra_Bean_Collection;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.Extra_Int_Function_Code;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.Result_Code_Fast_Collection;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.Result_Code_Scan_Collection;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.skipToPayPage;

/**
 * Created by ceq on 2017/4/18.
 * 过时
 */

public class Act_Home_SetCollectionMoney extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_keyboard1, rv_keyboard2;
    List<Bean_Item> bean_itemList1 = new ArrayList<>();
    List<Bean_Item> bean_itemList2 = new ArrayList<>();
    TextView tv_num;
    String title;
    Intent intent;
    LinearLayout kb_collection;
    int functionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_home_fastcollection);
    }

    @Override
    public void initView() {
        intent = getIntent();
        switch (functionCode = intent.getIntExtra(Extra_Int_Function_Code, 0)) {
            case Result_Code_Fast_Collection:
                title = "快捷收款";
                break;
            case Result_Code_Scan_Collection:
                title = "扫码收款";
                break;
       /*     case Result_Code_Buy_Production:
                title = "购买产品";
                break;*/
        }
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, title, View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        tv_num = (TextView) findViewById(R.id.tv_num);

        rv_keyboard1 = (RecyclerView) findViewById(R.id.rv_keyboard1);
        rv_keyboard1.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        rv_keyboard2 = (RecyclerView) findViewById(R.id.rv_keyboard2);
        rv_keyboard2.setLayoutManager(new LinearLayoutManager(getActivity()));

        kb_collection = (LinearLayout) findViewById(R.id.kb_collection);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) kb_collection.getLayoutParams();
        lp.height = screenWidth / 2;
        kb_collection.setLayoutParams(lp);
        updateKeyboard();

    }

    @Override
    public void initData() {
        bean_itemList1.add(new Bean_Item("1", 0, false));
        bean_itemList1.add(new Bean_Item("2", 0, false));
        bean_itemList1.add(new Bean_Item("3", 0, false));

        bean_itemList1.add(new Bean_Item("4", 0, false));
        bean_itemList1.add(new Bean_Item("5", 0, false));
        bean_itemList1.add(new Bean_Item("6", 0, false));

        bean_itemList1.add(new Bean_Item("7", 0, false));
        bean_itemList1.add(new Bean_Item("8", 0, false));
        bean_itemList1.add(new Bean_Item("9", 0, false));

        bean_itemList1.add(new Bean_Item(".", 0, false));
        bean_itemList1.add(new Bean_Item("0", 0, false));
        bean_itemList1.add(new Bean_Item("00", 0, false));


        bean_itemList2.add(new Bean_Item(null, R.mipmap.icon_kb_del, false));
        bean_itemList2.add(new Bean_Item(null, R.mipmap.icon_kb_ac, false));

    }


    @Override
    public void initAdapter() {
        rv_keyboard1.setAdapter(new RecyclerView.Adapter() {
            int w = getScreenWidth() / 4;

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name;
                SimpleDraweeView sdv_img;
                LinearLayout ll_bg;
                int color = getResources().getColor(R.color.text_color_4);
                View v_bottomSplit, v_rightSplit;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextColor(color);

                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    sdv_img.setVisibility(View.VISIBLE);
                    sdv_img.setColorFilter(new LightingColorFilter(Color.BLACK, color));
                    ViewGroup.LayoutParams layoutParams = sdv_img.getLayoutParams();
                    layoutParams.width = layoutParams.height = w / 3;
                    sdv_img.setLayoutParams(layoutParams);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    ll_bg.setBackgroundResource(R.drawable.bg_keyboard);
                    ViewGroup.LayoutParams lp = ll_bg.getLayoutParams();
                    lp.width = lp.height = w;
                    ll_bg.setLayoutParams(lp);
                    ll_bg.setPadding(0, 50, 0, 50);

                    v_bottomSplit = itemView.findViewById(R.id.v_bottomSplit);
                    v_rightSplit = itemView.findViewById(R.id.v_rightSplit);
                    v_bottomSplit.setBackgroundColor(Color.parseColor("#eeeeee"));
                    v_rightSplit.setBackgroundColor(Color.parseColor("#eeeeee"));
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                final MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = bean_itemList1.get(position);
                if (bean.getName() != null) {
                    mvh.tv_name.setVisibility(View.VISIBLE);
                    mvh.sdv_img.setVisibility(View.GONE);
                    mvh.tv_name.setText(bean.getName().toString());
                } else {
                    mvh.tv_name.setVisibility(View.GONE);
                    mvh.sdv_img.setVisibility(View.VISIBLE);
                    mvh.sdv_img.setImageResource(bean.getImgId());
                }
                mvh.tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {/*
                            case 0:
                                appendNumByKeyboard("1");
                                break;
                            case 1:
                                appendNumByKeyboard("2");
                                break;
                            case 2:
                                appendNumByKeyboard("3");
                                break;
                            case 3:
                                appendNumByKeyboard("4");
                                break;
                            case 4:
                                appendNumByKeyboard("5");
                                break;
                            case 5:
                                appendNumByKeyboard("6");
                                break;
                            case 6:
                                appendNumByKeyboard("7");
                                break;
                            case 7:
                                appendNumByKeyboard("8");
                                break;
                            case 8:
                                appendNumByKeyboard("9");
                                break;
                            case 9:
                                if (tv_num.getText().toString().length() == 0)
                                    tv_num.setText("0.");
                                else if (!tv_num.getText().toString().contains("."))
                                    appendNumByKeyboard(".");
                                break;
                            case 10:
                                appendNumByKeyboard("0");
                                break;
                            case 11:
                                appendNumByKeyboard("00");
                                break;*/
                        }
                        updateKeyboard();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_itemList1.size();
            }
        });

        rv_keyboard2.setAdapter(new RecyclerView.Adapter() {
            int w = getScreenWidth() / 4;

            class MyViewHolder extends RecyclerView.ViewHolder {
                SimpleDraweeView sdv_img;
                LinearLayout ll_bg;
                View v_bottomSplit, v_rightSplit;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    sdv_img.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams layoutParams = sdv_img.getLayoutParams();
                    layoutParams.width = layoutParams.height = w / 2;
                    sdv_img.setLayoutParams(layoutParams);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    ViewGroup.LayoutParams lp = ll_bg.getLayoutParams();
                    lp.width = lp.height = w;
                    ll_bg.setLayoutParams(lp);
                    ll_bg.setPadding(0, 50, 0, 50);
                    ll_bg.setBackgroundResource(R.drawable.bg_keyboard);

                    v_bottomSplit = itemView.findViewById(R.id.v_bottomSplit);
                    v_rightSplit = itemView.findViewById(R.id.v_rightSplit);
                    v_bottomSplit.setBackgroundColor(Color.parseColor("#eeeeee"));
                    v_rightSplit.setBackgroundColor(Color.parseColor("#eeeeee"));
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                final MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = bean_itemList2.get(position);
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                String num = tv_num.getText().toString();
                                if (num.length() > 0)
                                    tv_num.setText(num.substring(0, num.length() - 1));
                                break;
                            case 1:
                                tv_num.setText("");
                                break;
                        }
                        updateKeyboard();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_itemList2.size();
            }
        });

    }

    public void updateKeyboard() {
        String value = tv_num.getText().toString();
        // kb_collection.setBackgroundResource(value.length() == 0 || Float.parseFloat(value) == 0 ? R.mipmap.zaixianshoukuanhui : R.mipmap.zaixianshoukuan);
    }


    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, kb_collection);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId())
            onBackPressed();
        else if (v.getId() == kb_collection.getId()) {
            String money = tv_num.getText().toString();
            if (Util_Empty.isEmptyToToast(money, "请输入金额"))
                return;
            if (Float.parseFloat(money) == 0) {
                Util_Toast.toast("金额不为0");
                return;
            }
            Bean_Collection bean_collection = new Bean_Collection();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
            bean_collection.setMoney(decimalFormat.format(new BigDecimal(money)));
            switch (functionCode) {
                case Result_Code_Fast_Collection:
                    startActivity(new Intent(getActivity(), Act_Home_SelectChannel.class).putExtra(Extra_Int_Function_Code, functionCode).putExtra(Extra_Bean_Collection, bean_collection));
                    break;
                case Result_Code_Scan_Collection:
                    startActivity(new Intent(getActivity(), Act_Home_SelectChannel.class).putExtra(Extra_Int_Function_Code, functionCode).putExtra(Extra_Bean_Collection, bean_collection));
                    break;
            }
        }
    }

    public static void getCollectionURL(final Activity activity, final Bean_Collection bean_collection, final Bean_Channel bean_channel, final Bean_BankCardInfo bean_bankCardInfo) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_FAST_COLLECTION_AND_QRCODE__POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("order_desc", bean_collection.getRemark());
                httpParams.put("phone", Abstract_App.bean_userInfo.getPhone());
                httpParams.put("amount", bean_collection.getMoney().substring(0, bean_collection.getMoney().indexOf(".")));
                httpParams.put("channe_tag", bean_collection.getChannelTag().toString());
                if (bean_bankCardInfo != null)
                    httpParams.put("bank_card", bean_bankCardInfo.getCardNo());

            }
        }, new Util_Http.HttpDealStringListener(activity, Constant_International.message_net_generate_order, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        skipToPayPage(activity, data, bean_collection, bean_channel);
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void selectRechargeCardToSkip(final Activity activity, View_XRefreshStatusView view_xRefreshStatusView, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_GET_BANK_CARD_INFO_GET.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                //httpParams.put("type", 0);

            }
        }, new Util_Http.HttpDealStringListener(activity, view_xRefreshStatusView, null, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        List<Bean_BankCardInfo> bean_bankCardInfoList = new ArrayList<>();
                        List<Bean_BankCardInfo> list = JSON.parseArray(JSON.parseObject(data).getString(Http_Key_Data), Bean_BankCardInfo.class);
                        for (int i = 0, size = list.size(); i < size; i++) {
                            if (list.get(i).getType().equals("0"))
                                bean_bankCardInfoList.add(list.get(i));
                        }
                        util_Args_runnable.run(bean_bankCardInfoList);
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
