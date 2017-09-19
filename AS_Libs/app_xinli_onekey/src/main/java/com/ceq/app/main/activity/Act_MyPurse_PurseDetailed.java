package com.ceq.app.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_BankCardInfo;
import com.ceq.app.main.bean.Bean_Purse_Balance;
import com.ceq.app.main.bean.Bean_Purse_Integral;
import com.ceq.app.main.bean.Bean_Purse_Share;
import com.ceq.app.main.fragment.Frag_MyPurse_Balance;
import com.ceq.app.main.fragment.Frag_MyPurse_Integral;
import com.ceq.app.main.fragment.Frag_MyPurse_Share;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_Dialog;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Fragment;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.activity.Act_MyPurse_Withdrawals.Extra_Bean_MyPurse_Withdrawals;
import static com.ceq.app_core.constants.Constants_Common.Page_Size_20;

/**
 * Created by ceq on 2017/4/15.
 */

public class Act_MyPurse_PurseDetailed extends Framework_Activity {
    ImageView iv_back;
    Util_Fragment util_fragment = new Util_Fragment();
    int fragmentPosition;
    RecyclerView rv_fragment;
    Frag_MyPurse_Integral frag_myPurse_integral = new Frag_MyPurse_Integral();
    Frag_MyPurse_Balance frag_myPurse_balance = new Frag_MyPurse_Balance();
    Frag_MyPurse_Share frag_myPurse_share = new Frag_MyPurse_Share();
    List<Bean_Item> functionList = new ArrayList<>();
    TextView tv_withdrawals;
    public static String Extra_Int_MyPurse = "Extra_Int_MyPurse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_home_mypurse);
    }

    @Override
    public void initFragment() {
        super.initFragment();
        util_fragment.fragmentToInit((FragmentActivity) getActivity(), R.id.rl_myPurseFragment, new Util_Fragment.InitFragment() {
            @Override
            public void initFragment(TreeMap<Integer, Fragment> treeMap) {
                treeMap.put(0, frag_myPurse_integral);
                treeMap.put(1, frag_myPurse_balance);
                treeMap.put(2, frag_myPurse_share);
            }
        }, fragmentPosition);
    }

    @Override
    public void initView() {
        if (getIntent() != null)
            fragmentPosition = getIntent().getIntExtra(Extra_Int_MyPurse, fragmentPosition);
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "钱包明细", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        tv_withdrawals = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "提现", View.GONE);

        //功能
        rv_fragment = (RecyclerView) findViewById(R.id.rv_fragment);
        rv_fragment.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    public void initData() {
        functionList.add(new Bean_Item("积分", false));
        functionList.add(new Bean_Item("余额", false));
        functionList.add(new Bean_Item("分润", false));
        functionList.get(fragmentPosition).setChecked(true);
    }

    @Override
    public void initAdapter() {
        rv_fragment.setAdapter(new RecyclerView.Adapter() {
            int width =screenWidth/9*7;
            int padding = SizeUtils.dp2px(3);

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
                    tv_name.setWidth(width);
                    tv_name.setPadding(tv_name.getPaddingLeft(), padding, tv_name.getPaddingRight(), padding);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = functionList.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_name.setTextColor(bean.isChecked() ? getResources().getColor(R.color.text_color_4) : getResources().getColor(R.color.text_color_2));
                if (bean.isChecked())
                    mvh.tv_name.setBackgroundColor(getResources().getColor(R.color.content_background));
                else
                    mvh.tv_name.setBackgroundColor(Color.argb(0, 0, 0, 0));
                mvh.tv_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0, size = functionList.size(); i < size; i++)
                            functionList.get(i).setChecked(false);
                        bean.setChecked(true);
                        notifyDataSetChanged();
                        util_fragment.fragmentToShow(fragmentPosition = position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return functionList.size();
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);
        Util_View.viewOnClick(this, tv_withdrawals);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId())
            onBackPressed();
        if (v.getId() == tv_withdrawals.getId()) {
            getDefaultBankCardInfo(tv_withdrawals,getActivity());
        }
    }


    int count = NetworkUtils.isConnected()? 0 : 3;

    public void getScoreBalanceShareMoneyData(final View_XRefreshStatusView view_xRefreshStatusView, final String url, final int page, final List list, final int type) {
        Util_Http.httpToRequest(getActivity(), url.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("page", page);
                httpParams.put("size", Page_Size_20);
      /*          httpParams.put("order",order);
                httpParams.put("sort",sort);*/

            }
        }, new Util_Http.HttpDealStringListener(getActivity(),view_xRefreshStatusView, count >= 3 ? null : Constant_International.message_net_request, false, true) {

            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        switch (type) {
                            case 0:
                                list.addAll(JSON.parseArray(JSON.parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_Purse_Integral.class));
                                break;
                            case 1:
                                list.addAll(JSON.parseArray(JSON.parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_Purse_Balance.class));
                                break;
                            case 2:
                                list.addAll(JSON.parseArray(JSON.parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_Purse_Share.class));
                                break;
                        }
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (++count == 3)
                    Util_Http.dismiss();
            }
        });
    }

    public static void getDefaultBankCardInfo(final View viewToken, final Activity activity) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_GET_DEFAULT_BANK_CARD_INFO_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("user_id", Abstract_App.bean_userInfo.getId());

            }
        }, new Util_Http.HttpDealStringListener(activity, Constant_International.message_net_query, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Bean_BankCardInfo bean_bankCardInfo = JSONObject.parseObject(JSON.parseObject(data).getString(Http_Key_Data), Bean_BankCardInfo.class);
                        if (bean_bankCardInfo == null) {
                            Util_Dialog.showDefaultDialog(viewToken, "你还未绑定银行卡，是否前往绑定？", "确定", "取消", new Util_Dialog.DialogListener() {

                                @Override
                                public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel,View v_split) {
                                    if (activity instanceof Act_MyPurse_Withdrawals)
                                        activity.onBackPressed();
                                    framework_dialog.dismiss();
                                }

                                @Override
                                public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                                    framework_dialog.startActivity(new Intent(framework_dialog, Act_BankCard_Add.class));
                                    framework_dialog.dismiss();
                                }

                                @Override
                                public void onDismissListener() {

                                }
                            });
                        } else
                            activity.startActivity(new Intent(activity, Act_MyPurse_Withdrawals.class).putExtra(Extra_Bean_MyPurse_Withdrawals, bean_bankCardInfo));
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
