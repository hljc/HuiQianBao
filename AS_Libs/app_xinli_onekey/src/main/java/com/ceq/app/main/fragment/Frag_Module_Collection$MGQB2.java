package com.ceq.app.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceq.app.core.bean.Bean_PushData;
import com.ceq.app.main.activity.Act_Collection_Detailed;
import com.ceq.app.main.activity.Act_Mine_Rate;
import com.ceq.app.main.activity.Act_Mine_TransactionDetailed;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_ChannelExtraInfo;
import com.ceq.app.main.view.KeyboardLinearLayout;
import com.ceq.app.main.view.KeyboardTextView;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Screen;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;
import com.ceq.app_xinli_onekey.core.modules.interfaces.Inter_OKModule_CollectionUI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.attr.PaddingTopAttr;
import com.zhy.autolayout.attr.TextSizeAttr;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.ceq.app.main.methods.Method_Static.appendNumByKeyboard;
import static com.ceq.app.main.methods.Method_Static.delMoney;
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;
import static com.ceq.app.main.methods.Method_Static.skipToHelp;
import static com.ceq.app_core.framework.Framework_Activity.screenWidth;

/**
 * Created by ceq on 2017/5/11.
 */

public class Frag_Module_Collection$MGQB2 extends Abstract_OkModule_Fragment<Inter_OKModule_CollectionUI.Inter_Collection$MGQB2> {
    TextView tv_bill, tv_help;
    RecyclerView rv_platform;
    RecyclerView.Adapter rva_platform;
    SimpleDraweeView sdv_platform;
    KeyboardTextView tv_kb1, tv_kb2, tv_kb3, tv_kb4, tv_kb5, tv_kb6, tv_kb7, tv_kb8, tv_kb9, tv_kb0, tv_kbClear, tv_kb_point;
    KeyboardLinearLayout ll_kb_del;
    LinearLayout ll_submit,ll_keyboard;
    TextView tv_money;
    View_XRefreshLayout xrv;
    GridLayoutManager gridLayoutManager;

    int dark, white;

    List<Bean_Item> platformList = new ArrayList<>();
    Bean_ChannelExtraInfo bean_channelExtraInfo = new Bean_ChannelExtraInfo();
    Bean_OKModule_UIOptions keyboardUiOptions=new Bean_OKModule_UIOptions();
    public Frag_Module_Collection$MGQB2() {
    }

    public Frag_Module_Collection$MGQB2(String tabBarTitle, int tabBarImgId ) {
        super(tabBarTitle, tabBarImgId );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dark = getResources().getColor(R.color.mgqb2_dark);
        white = getResources().getColor(R.color.text_color_1);
        return init(R.layout.frag_module_collection_mgqb2);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, getTabBarTitle(), View.VISIBLE).setTextColor(white);
        findViewById(R.id.icd_title).setBackgroundColor(dark);
        tv_bill = util_init.initTextView(R.id.icd_title, R.id.tv_titleLeft, null, "账单", View.VISIBLE);
        tv_bill.setTextColor(white);

        tv_help = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "使用教程", View.VISIBLE);
        tv_help.setTextColor(white);

        //功能
        rv_platform = (RecyclerView) findViewById(R.id.rv_platform);
        gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        rv_platform.setLayoutManager(gridLayoutManager);

        sdv_platform = (SimpleDraweeView) findViewById(R.id.sdv_platform);

        ll_keyboard = (LinearLayout) findViewById(R.id.ll_keyboard);
        ll_submit = (LinearLayout) findViewById(R.id.ll_submit);
        tv_money = (TextView) findViewById(R.id.tv_money);

        tv_kbClear = (KeyboardTextView) findViewById(R.id.tv_kbClear);
        tv_kb0 = (KeyboardTextView) findViewById(R.id.tv_kb0);
        tv_kb1 = (KeyboardTextView) findViewById(R.id.tv_kb1);
        tv_kb2 = (KeyboardTextView) findViewById(R.id.tv_kb2);
        tv_kb3 = (KeyboardTextView) findViewById(R.id.tv_kb3);
        tv_kb4 = (KeyboardTextView) findViewById(R.id.tv_kb4);
        tv_kb5 = (KeyboardTextView) findViewById(R.id.tv_kb5);
        tv_kb6 = (KeyboardTextView) findViewById(R.id.tv_kb6);
        tv_kb7 = (KeyboardTextView) findViewById(R.id.tv_kb7);
        tv_kb8 = (KeyboardTextView) findViewById(R.id.tv_kb8);
        tv_kb9 = (KeyboardTextView) findViewById(R.id.tv_kb9);
        tv_kb_point = (KeyboardTextView) findViewById(R.id.tv_kb_point);
        ll_kb_del = (KeyboardLinearLayout) findViewById(R.id.ll_kb_del);

        xrv = (View_XRefreshLayout) findViewById(R.id.xrv);

        tv_kbClear.setUiOptions(keyboardUiOptions);
        tv_kb0.setUiOptions(keyboardUiOptions);
        tv_kb1.setUiOptions(keyboardUiOptions);
        tv_kb2.setUiOptions(keyboardUiOptions);
        tv_kb3.setUiOptions(keyboardUiOptions);
        tv_kb4.setUiOptions(keyboardUiOptions);
        tv_kb5.setUiOptions(keyboardUiOptions);
        tv_kb6.setUiOptions(keyboardUiOptions);
        tv_kb7.setUiOptions(keyboardUiOptions);
        tv_kb8.setUiOptions(keyboardUiOptions);
        tv_kb9.setUiOptions(keyboardUiOptions);
        tv_kb_point.setUiOptions(keyboardUiOptions);
        ll_kb_del.setUiOptions(keyboardUiOptions);if (keyboardUiOptions.getViewBackgroundColor() != 0)
        ll_keyboard.setBackgroundColor(keyboardUiOptions.getViewBackgroundColor());
    }

    @Override
    public void initData() {
    }

    @Override
    public void initAdapter() {
        rv_platform.setAdapter(rva_platform = new RecyclerView.Adapter() {
            int itemWidth = (int) (screenWidth / gridLayoutManager.getSpanCount());

            class MyViewHolder extends RecyclerView.ViewHolder {
                SimpleDraweeView sdv_img;
                TextView tv_name, tv_value;
                LinearLayout ll_bg;
                //View v_mark;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    setImageLayoutParams(sdv_img, 150, 30);

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_2));
                    AutoLinearLayout.LayoutParams layoutParams1 = (AutoLinearLayout.LayoutParams) tv_name.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo1 = layoutParams1.getAutoLayoutInfo();
                    autoLayoutInfo1.addAttr(new PaddingTopAttr(8, 0, 0));
                    autoLayoutInfo1.addAttr(new TextSizeAttr(30, 0, 0));
                    ViewGroup.LayoutParams lp1 = tv_name.getLayoutParams();
                    tv_name.setLayoutParams(lp1);

              /*      tv_value = (TextView) itemView.findViewById(R.id.tv_value);
                    tv_value.setVisibility(View.VISIBLE);
                    tv_value.setTextColor(Color.parseColor("#eeeeee"));
                    AutoLinearLayout.LayoutParams layoutParams2 = (AutoLinearLayout.LayoutParams) tv_value.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo2 = layoutParams2.getAutoLayoutInfo();
                    autoLayoutInfo2.addAttr(new TextSizeAttr(30, 0, 0));
                    ViewGroup.LayoutParams lp2 = tv_value.getLayoutParams();
                    tv_value.setLayoutParams(lp2);*/

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);

                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);

                    RelativeLayout rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);
                    ViewGroup.LayoutParams lp3 = rl_bg.getLayoutParams();
                    lp3.width = itemWidth;
                    lp3.height = rv_platform.getMeasuredHeight() / 2;
                    rl_bg.setLayoutParams(lp3);

           /*         v_mark = itemView.findViewById(R.id.v_mark);
                    setImageLayoutParams(v_mark, 80, 0);*/
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean_item = platformList.get(position);
                final Bean_Channel bean_channel = (Bean_Channel) bean_item.getObject();
                mvh.sdv_img.setImageURI(bean_channel.getLog());
                mvh.tv_name.setText(bean_channel.getSubName());
                // mvh.tv_value.setText(new StringBuilder(Util_Empty.isEmpty(bean_channel.getRate()) ? "-" : new BigDecimal(bean_channel.getRate()).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString().concat("%")).append(" ").append(bean_channel.getChannelParams()));
                mvh.sdv_img.setBackgroundResource(bean_item.isChecked() ? R.drawable.bg_stroke_mgqb2_select_on : R.drawable.bg_stroke_mgqb2_select_off);
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0, size = platformList.size(); i < size; i++)
                            platformList.get(i).setChecked(false);
                        bean_item.setChecked(true);
                        sdv_platform.setImageURI(bean_channel.getLog());
                        bean_channelExtraInfo.setChannelPlatform(bean_channel.getSubName());
                        notifyDataSetChanged();
                    }
                });
                //setImageMark(mvh.v_mark, bean_channel);
            }

            @Override
            public int getItemCount() {
                return platformList.size();
            }
        });

        xrv.setOnRefreshHttpListener(false, 0, platformList, rva_platform, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, final List list, RecyclerView.Adapter adapter) {
                Act_Mine_Rate.getRateData(getActivity(), view_xRefreshStatusView, true,new Util_Runnable.Util_ArgsRunnable() {
                    @Override
                    public void run(Object... data) {
                        Map<String, Bean_Channel> hashMap = new LinkedHashMap<String, Bean_Channel>();
                        List<Bean_Channel> beans = (List<Bean_Channel>) data[0];
                        for (int i = 0, size = beans.size(); i < size; i++) {
                            Bean_Channel bean_channel = beans.get(i);
                            hashMap.put(bean_channel.getSubName(), bean_channel);
                        }
                        for (Map.Entry<String, Bean_Channel> e : hashMap.entrySet()) {
                            Bean_Item bean_item = new Bean_Item();
                            bean_item.setObject(e.getValue());
                            list.add(bean_item);
                        }
                        Bean_Item bean_item = (Bean_Item) list.get(0);
                        Bean_Channel bean_channel = (Bean_Channel) bean_item.getObject();
                        bean_channelExtraInfo.setChannelPlatform(bean_channel.getSubName());
                        bean_channelExtraInfo.setPayChannel(bean_channel.getChannelTag());
                        bean_channelExtraInfo.setOrderDescription(bean_channel.getName());
                        bean_item.setChecked(true);
                        sdv_platform.setImageURI(bean_channel.getLog());
                    }
                });
            }
        });
     /*   bga.setRefreshListener(0, platformList, false, new View_BGAStatusRefreshLayout.OnRefreshListener() {

            @Override
            public void onHttpStart(int page, final List list, final View_BGAStatusRefreshLayout.OnRefreshListener onRefreshListener) {
                Act_Mine_Rate.getRateData(getActivity(), true, new Util_Runnable.Util_ArgsRunnable() {
                    @Override
                    public void run(Object... data) {
                        List<Bean_Channel> beans = (List<Bean_Channel>) data[0];
                        for (int i = 0, size = beans.size(); i < size; i++) {
                            Bean_Channel bean_channel = beans.get(i);
                            channelDataMaps.put(bean_channel.getChannelTag(), bean_channel);
                            Bean_Item bean_item = new Bean_Item();
                            bean_item.setObject(bean_channel);
                            list.add(bean_item);
                        }
                        onRefreshListener.onHttpEnd(bga, rva_platform, list);
                        Bean_Item bean_item = (Bean_Item) list.get(0);
                        Bean_Channel bean_channel = (Bean_Channel) bean_item.getObject();
                        setRemark(bean_channel);
                        bean_item.setChecked(true);
                        tv_limit.setText(new StringBuilder("【单笔限额:").append(Util_Empty.isEmptyToReplace(bean_channel.getSingleMinLimit(), "0")).append("-").append(Util_Empty.isEmptyToReplace(bean_channel.getSingleMaxLimit(), "0")).append("】"));
                        payChannel = bean_channel.getChannelTag();
                        sdv_platform.setImageURI(bean_channel.getLog());
                        rv_platform.removeCallbacks(runnable);
                        rv_platform.postDelayed(runnable, 500);
                    }
                });
            }
        });*/
    }


    @Override
    public void onSelected() {
        Util_Screen.statusBarToTranslucent(getActivity(), getResources().getColor(R.color.mgqb2_dark), Util_Screen.Enum_BarFontColor.White);
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_kb1, tv_kb2, tv_kb3, tv_kb4, tv_kb5, tv_kb6, tv_kb7, tv_kb8, tv_kb9, tv_kb0, tv_kbClear, tv_kb_point);
        Util_View.viewOnClick(this, ll_kb_del);
        Util_View.viewOnClick(this, ll_submit);
        Util_View.viewOnClick(this, tv_help, tv_bill);
    }


    @Override
    public void onClick(View v) {
        if (v == tv_help) {
            skipToHelp(getActivity(), tv_help.getText().toString(), R.mipmap.bg_collection_help);
        } else if (v.getId() == tv_bill.getId()) {
            startActivity(new Intent(getActivity(), Act_Mine_TransactionDetailed.class));
        } else if (v == ll_submit) {
            final String money = tv_money.getText().toString();
            if (Util_Empty.isEmptyToToast(money, "请输入金额"))
                return;
            if (Float.parseFloat(money) == 0) {
                Util_Toast.toast("金额不能为0");
                return;
            }
            bean_channelExtraInfo.setMoney(money);
            startActivity(new Intent(getActivity(), Act_Collection_Detailed.class).putExtra(Act_Collection_Detailed.Extra_Bean_ChannelExtraInfo, bean_channelExtraInfo));
            tv_money.setText("");
            //submitToCollection(ll_submit, tv_money, null);
        } else if (v == tv_kbClear) tv_money.setText("");
        else if (v == tv_kb0) appendNumByKeyboard(tv_money, "0");
        else if (v == tv_kb1) appendNumByKeyboard(tv_money, "1");
        else if (v == tv_kb2) appendNumByKeyboard(tv_money, "2");
        else if (v == tv_kb3) appendNumByKeyboard(tv_money, "3");
        else if (v == tv_kb4) appendNumByKeyboard(tv_money, "4");
        else if (v == tv_kb5) appendNumByKeyboard(tv_money, "5");
        else if (v == tv_kb6) appendNumByKeyboard(tv_money, "6");
        else if (v == tv_kb7) appendNumByKeyboard(tv_money, "7");
        else if (v == tv_kb8) appendNumByKeyboard(tv_money, "8");
        else if (v == tv_kb9) appendNumByKeyboard(tv_money, "9");
        else if (v == tv_kb_point) {
            if (tv_money.getText().toString().length() == 0)
                tv_money.setText("0.");
            else if (!tv_money.getText().toString().contains("."))
                appendNumByKeyboard(tv_money, ".");
        } else if (v == ll_kb_del)
            delMoney(tv_money);
    }



    @Override
    public void updateViewByPushMessage(Bean_PushData bean_pushData, Bean_Push bean_push) {

    }

    @Override
    public void run(Inter_OKModule_CollectionUI.Inter_Collection$MGQB2 data) {
        data.initKeyboardStyle(this, keyboardUiOptions);
    }
}
