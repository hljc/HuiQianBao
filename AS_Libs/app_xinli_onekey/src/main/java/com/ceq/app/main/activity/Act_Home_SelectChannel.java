package com.ceq.app.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.activity.Act_Main_Web;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_Collection;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.activity.Act_ScanCollection_CollectionCode.Extra_Bean_Channel;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;
import static com.ceq.app_core.framework.Framework_Web.Extra_String_Url;

/**
 * Created by ceq on 2017/4/14.
 */

public class Act_Home_SelectChannel extends Framework_Activity {
    List<Bean_Item> bean_items = new ArrayList<>();
    List<Bean_Channel> bean_channelList = new ArrayList<>();

    ImageView iv_back;
    TextView tv_commit;
    EditText et_collectionSpec;
    TextView tv_channelName, tv_channelWay, tv_channelRate;
    RelativeLayout rl_channelBg;
    public static final int Result_Code_Fast_Collection = 100;
    public static final int Result_Code_Scan_Collection = 101;
    public static final int Result_Code_Buy_Production = 102;
    public static final int Result_Code_Withdrawals = 103;
    public static final String Extra_Int_Function_Code = "Extra_Int_Function_Code";
    public static final String Extra_Bean_Collection = "Extra_Bean_Collection";
    Bean_Collection bean_collection;
    String title;
    Intent intent;
    int functionCode;
    Bean_Channel bean_channel=new Bean_Channel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_quickcollection_collection);
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
            case Result_Code_Buy_Production:
                title = "购买产品";
                break;
        }
        bean_collection = (Bean_Collection) getIntent().getSerializableExtra(Extra_Bean_Collection);

        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, title, View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        //收款金额
        util_init.initTextView(R.id.icd_collectionMoney, R.id.tv_name, null, "收款金额", View.VISIBLE);
        util_init.initTextView(R.id.icd_collectionMoney, R.id.tv_right, null, "￥".concat(bean_collection.getMoney()), View.VISIBLE);
        //收款说明
        et_collectionSpec = util_init.initEditText(R.id.icd_collectionSpec, R.id.et_input, "收款说明", null, InputType.TYPE_CLASS_TEXT, LENGTH_USER, View.VISIBLE);
        util_init.initImageView(R.id.icd_collectionSpec, R.id.iv_leftImg, R.mipmap.icon_desc, View.VISIBLE);


        //快捷通道
        tv_channelName = (TextView) findViewById(R.id.tv_channelName);
        tv_channelWay = (TextView) findViewById(R.id.tv_channelWay);
        tv_channelRate = (TextView) findViewById(R.id.tv_channelRate);

        rl_channelBg = (RelativeLayout) findViewById(R.id.rl_channelBg);
        //提交
        tv_commit = util_init.initTextView(R.id.icd_commit, R.id.tv_button, null, "提交", View.VISIBLE);
    }

    @Override
    public void initData() {
        bean_items.add(new Bean_Item("微信支付", R.mipmap.app_logo, true));
        bean_items.add(new Bean_Item("支付宝支付", R.mipmap.app_logo, false));
        /*getChannelData(getActivity(), false, bean_channelList, functionCode, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                setChannelData(bean_channelList.get(0));
            }

        });*/
    }

    void setChannelData(Bean_Channel bean_channel) {
        if (bean_channel != null) {
            this.bean_channel = bean_channel;
            tv_channelWay.setText("T+0");
            tv_channelName.setText(bean_channel.getChannelTag().toString());
            tv_channelRate.setText(Util_Empty.isEmpty(bean_channel.getRate()) ? "-" :new BigDecimal(bean_channel.getRate()).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString().concat("%"));
        }
    }

    @Override
    public void initAdapter() {
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, rl_channelBg, tv_commit);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId())
            onBackPressed();
        else if (v.getId() == rl_channelBg.getId()) {
            //showChannelDataDialog(getActivity(), bean_channelList, functionCode, null);
        } else if (v.getId() == tv_commit.getId()) {/*
            if (!Abstract_App.bean_userInfo.getRealnameStatus().equals("1")) {
                Act_Main_Module.showCertificationDialog(getActivity());
                return;
            }
            if (Util_Empty.isEmptyToToast(et_collectionSpec.getText().toString(), "请输入收款说明"))
                return;
            if (Util_Empty.isEmptyToToast(tv_channelName.getText().toString(), "选择快捷通道"))
                return;
            if (bean_collection != null && bean_channel != null) {
                BigDecimal payMoney = new BigDecimal(bean_collection.getMoney());
                BigDecimal poundage = payMoney.multiply(new BigDecimal(bean_channel.getRate()));
                switch (functionCode) {
                    case Result_Code_Fast_Collection:
                    case Result_Code_Scan_Collection:
                    case Result_Code_Buy_Production:
                        String maxMoney = bean_channel.getSingleMaxLimit();
                        if (maxMoney != null) {
                            BigDecimal maxLimit = new BigDecimal(maxMoney);
                            if (payMoney.compareTo(maxLimit) > 0) {
                                Util_Toast.toast(new StringBuilder("该通道单笔").append(title).append("金额不得超过").append(maxLimit.stripTrailingZeros().toPlainString()).append("元"));
                                return;
                            }
                        }
                        break;
                    case Result_Code_Withdrawals:
                        String minMoney = bean_channel.getSingleMinLimit();
                        if (minMoney != null) {
                            if (payMoney.compareTo(new BigDecimal(minMoney)) < 0) {
                                Util_Toast.toast(new StringBuilder("该通道单笔提现金额不得低于").append(minMoney).append("元"));
                                return;
                            }
                        }
                        break;
                }

                switch (tv_channelName.getText().toString()) {
                    case Channel_YiLian:
                        break;
                }
                KeyboardUtils.hideSoftInput(getActivity());
                Act_PayKeyboard.showPayKeyboard(getActivity(), tv_commit.getId(), title, bean_collection.getMoney(), new StringBuilder("手续费").append(poundage.stripTrailingZeros().toPlainString()).append("元/笔").toString(), new Act_PayKeyboard.OnFinishInputListener() {
                    @Override
                    public void OnPasswordInputFinish(PopEnterPassword popEnterPassword, String password) {
                        popEnterPassword.dismiss();
                        if (verificationPayCode(getActivity(), password)) {
                            bean_collection.setRemark(et_collectionSpec.getText().toString());
                            // bean_collection.setChannelTag(tv_channelName.getText().toString());
                            switch (intent.getIntExtra(Extra_Int_Function_Code, 0)) {
                                case Result_Code_Fast_Collection:
                                    Act_Home_SetCollectionMoney.getCollectionURL(getActivity(), bean_collection);
                                    break;
                                case Result_Code_Scan_Collection:
                                    Act_Home_ScanCollection.getCollectionCodeData(getActivity(), bean_collection);
                                    break;
                                case Result_Code_Buy_Production:
                                    //buyProduction(getActivity(), bean_collection);
                                    break;
                            }
                        }
                    }
                });
            }*/
        }
    }

    public static void skipToPayPage(final Activity activity, String data, final Bean_Collection bean_collection, final Bean_Channel bean_channel) {
        String url = parseObject(data).getString(Http_Key_Data);
        if (bean_channel != null)
            switch (bean_channel.getChannelNo()) {
                case "1":
                    bean_collection.setQrCodeUrl(url);
                    break;
                case "2":
                    bean_collection.setWebUrl(url);
                    break;
                case "3":
                    break;
            }
        if (bean_collection.getWebUrl() != null) {
            activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Extra_String_Url, bean_collection.getWebUrl()));
        } else if (bean_collection.getQrCodeUrl() != null) {
            Util_Http.dismiss();
            Intent intent = new Intent(activity, Act_ScanCollection_CollectionCode.class).putExtra(Extra_Bean_Collection, bean_collection);
            if (bean_channel != null)
                intent.putExtra(Extra_Bean_Channel, bean_channel);
            activity.startActivity(intent);
        }
    }


   /* public static void showChannelDataDialog(final Activity activity, final List<Bean_Channel> bean_channelList, final int functionCode, final Util_Runnable.Util_ArgsRunnable util_runnable) {
        Util_Dialog.dialogByAct(activity, R.layout.collection_channel, true, true, false, false, new Util_Dialog.ActDialog() {
            RecyclerView rv_channel;
            TextView tv_cancel;
            Util_Dialog.DialogContext dialogContext;
            TextView tv_channelName, tv_channelWay, tv_channelRate, tv_channelLimit;

            @Override
            public void initDialogData() {

            }

            @Override
            public View initDialogView(Util_Dialog.DialogContext dialogContext, View view) {
                this.dialogContext = dialogContext;
                rv_channel = (RecyclerView) view.findViewById(R.id.rv_channel);
                rv_channel.setLayoutManager(new LinearLayoutManager(activity));
                tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

                tv_channelName = (TextView) view.findViewById(R.id.tv_channelName);
                tv_channelWay = (TextView) view.findViewById(R.id.tv_channelWay);
                tv_channelRate = (TextView) view.findViewById(R.id.tv_channelRate);
                tv_channelLimit = (TextView) view.findViewById(R.id.tv_channelLimit);
                tv_channelName.setText("通道名称");
                tv_channelWay.setText("结算方式");
                tv_channelRate.setText(functionCode == Result_Code_Withdrawals ? "手续费" : "费率");
                tv_channelLimit.setText("单笔限额");

                setChannelAdapter(activity, dialogContext, rv_channel, bean_channelList, functionCode, util_runnable);
                Util_View.viewOnClick(this, tv_cancel);
                return view;
            }

            @Override
            public void onDismissListener() {

            }

            @Override
            public void onClick(View v) {
                if (v == tv_cancel)
                    dialogContext.dismiss();
            }
        });
    }*/


    private static void setChannelAdapter(final Activity activity, final Util_Dialog.DialogContext dialogContext, RecyclerView recyclerView, final List<Bean_Channel> bean_channelList, final int functionCode, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_channelName, tv_channelWay, tv_channelRate, tv_channelLimit;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_channelName = (TextView) itemView.findViewById(R.id.tv_channelName);
                    tv_channelWay = (TextView) itemView.findViewById(R.id.tv_channelWay);
                    tv_channelRate = (TextView) itemView.findViewById(R.id.tv_channelRate);
                    tv_channelLimit = (TextView) itemView.findViewById(R.id.tv_channelLimit);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_rv_channel, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Channel bean = bean_channelList.get(position);
                mvh.tv_channelName.setText((String) Util_Empty.isEmptyToReplace(bean.getChannelTag(), "-"));
                mvh.tv_channelWay.setText("T+0");
                BigDecimal maxLimit = new BigDecimal(bean.getSingleMaxLimit());
                BigDecimal minLimit = new BigDecimal(bean.getSingleMinLimit());
                switch (functionCode) {
                    case Result_Code_Fast_Collection:
                    case Result_Code_Scan_Collection:
                    case Result_Code_Buy_Production:
                        mvh.tv_channelRate.setText(Util_Empty.isEmpty(bean.getRate()) ? "-" :new BigDecimal(bean.getRate()).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString().concat("%"));
                        String maxMoney = (String) Util_Empty.isEmptyToReplace(maxLimit.stripTrailingZeros().toPlainString(), "-");
                        mvh.tv_channelLimit.setText(maxMoney.equals("-") ? maxMoney : new StringBuilder().append("≤").append(maxMoney));
                        break;
                    case Result_Code_Withdrawals:
                 /*       mvh.tv_channelRate.setText(withdrawalsRate);
                        bean.setRate(withdrawalsRate);*/
                        String minMoney = (String) Util_Empty.isEmptyToReplace(minLimit.stripTrailingZeros().toPlainString(), "-");
                        mvh.tv_channelLimit.setText(minMoney.equals("-") ? minMoney : new StringBuilder().append("≥").append(minMoney));
                        break;
                }
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (util_Args_runnable != null)
                            util_Args_runnable.run(bean);
                        if (activity instanceof Act_Home_SelectChannel)
                            ((Act_Home_SelectChannel) activity).setChannelData(bean);
                        else if (activity instanceof Act_MyPurse_Withdrawals)
                            ((Act_MyPurse_Withdrawals) activity).setChannelData(bean);
                        dialogContext.dismiss();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_channelList.size();
            }
        });
    }

    public static boolean verificationPayCode(Activity activity, final String paypass) {
        final boolean[] success = new boolean[1];
        Util_Http.httpToRequestSync(activity, Constant_Api.URL_VERIFICATION_PAY_CODE_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("paypass", paypass);

            }
        }, new Util_Http.HttpDealString() {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        success[0] = true;
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
        return success[0];
    }

   /* public static void getChannelData(Activity activity, boolean isAutoDismiss, final List<Bean_Channel> bean_channelList, final int functionCode, final Util_Runnable.Util_ArgsRunnable runnable*//*final TextView tv_channelName, final TextView tv_channelWay, final TextView tv_channelRate*//*) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_RATE_INFO_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
                return null;
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("user_id", Abstract_App.bean_userInfo.getId());

            }
        }, new Util_Http.HttpDealStringListener(activity, Constant_International.message_net_request, isAutoDismiss, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        bean_channelList.addAll(JSONArray.parseArray(parseObject(data).getString(Http_Key_Data), Bean_Channel.class));
                        if (functionCode != -1) {
                            List list = new ArrayList();
                            switch (functionCode) {
                                case Result_Code_Fast_Collection:
                                case Result_Code_Buy_Production:
                                    for (int i = 0, size = bean_channelList.size(); i < size; i++) {
                                        Bean_Channel bean_channel = bean_channelList.get(i);
                                        String tag = bean_channel.getChannelTag().toString();
                                        if (tag != null)
                                            if (!tag.contains(Channel_YiLian)) {
                                                list.add(bean_channel);
                                            }
                                    }
                                    bean_channelList.removeAll(list);
                                    break;
                                case Result_Code_Withdrawals:
                                    for (int i = 0, size = bean_channelList.size(); i < size; i++) {
                                        Bean_Channel bean_channel = bean_channelList.get(i);
                                        String tag = bean_channel.getChannelTag().toString();
                                        if (tag != null) {
                                            if (tag.contains(Channel_YiLian)) {
                                                list.add(bean_channel);
                                            }
                                        }
                                    }
                                    bean_channelList.clear();
                                    bean_channelList.addAll(list);
                                    break;
                                case Result_Code_Scan_Collection:
                                    for (int i = 0, size = bean_channelList.size(); i < size; i++) {
                                        Bean_Channel bean_channel = bean_channelList.get(i);
                                        String tag = bean_channel.getChannelTag().toString();
                                        if (tag != null)
                                            if (!tag.contains(Channel_ZHONGMAO_PAY)) {
                                                list.add(bean_channel);
                                            }
                                    }
                                    bean_channelList.removeAll(list);
                                    break;
                                default:

                                    break;
                            }
                        }
                        if (runnable != null)
                            runnable.run();
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }
        });
    }*/
}
