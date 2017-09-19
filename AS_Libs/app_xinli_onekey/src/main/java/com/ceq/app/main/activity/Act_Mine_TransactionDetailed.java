package com.ceq.app.main.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Integral;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Pay;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Recharge;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Share;
import com.ceq.app.main.bean.Bean_TransactionDetailed_Withdrawals;
import com.ceq.app.main.fragment.Frag_TransactionDetailed_Integral;
import com.ceq.app.main.fragment.Frag_TransactionDetailed_Pay;
import com.ceq.app.main.fragment.Frag_TransactionDetailed_Recharge;
import com.ceq.app.main.fragment.Frag_TransactionDetailed_Share;
import com.ceq.app.main.fragment.Frag_TransactionDetailed_Withdrawals;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Fragment;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.utils.extend.picker.abstracts.Util_Picker;
import com.ceq.app_core.utils.extend.picker.interfaces.callback.Inter_Picker_Callback;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Oem;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app_core.constants.Constants_Common.Page_Size_20;
import static com.ceq.app_xinli_onekey.core.props.beans.Bean_OKProp_Oem.Enum_OrderStatusFilter.显示全部;

/**
 * Created by ceq on 2017/4/15.
 */

public class Act_Mine_TransactionDetailed extends Framework_Activity {
    ImageView iv_back;
    Util_Fragment util_fragment = new Util_Fragment();
    int fragmentPosition;
    RecyclerView rv_fragment;
    public Bean_OKProp_Oem.Enum_OrderStatusFilter defaultOrderStatus;

    {
        defaultOrderStatus = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getOrderStatusFilter();
    }

    Frag_TransactionDetailed_Recharge frag_transactionDetailed_recharge = new Frag_TransactionDetailed_Recharge();
    Frag_TransactionDetailed_Pay frag_transactionDetailed_pay = new Frag_TransactionDetailed_Pay();
    Frag_TransactionDetailed_Withdrawals frag_transactionDetailed_withdrawals = new Frag_TransactionDetailed_Withdrawals();
    Frag_TransactionDetailed_Share frag_transactionDetailed_share = new Frag_TransactionDetailed_Share();
    Frag_TransactionDetailed_Integral frag_transactionDetailed_integral = new Frag_TransactionDetailed_Integral();
    List<Bean_Item> functionList = new ArrayList<>();
    ImageView iv_filter;
    Calendar calendar;
    String startTime, endTime;
    TextView tv_subTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        init(R.layout.act_withdrawals_detailed);
    }

    @Override
    public void initFragment() {
        super.initFragment();
        util_fragment.fragmentToInit((FragmentActivity) getActivity(), R.id.rl_Fragment, new Util_Fragment.InitFragment() {
            @Override
            public void initFragment(TreeMap<Integer, Fragment> treeMap) {
                treeMap.put(0, frag_transactionDetailed_recharge);
                treeMap.put(1, frag_transactionDetailed_pay);
                treeMap.put(2, frag_transactionDetailed_withdrawals);
                treeMap.put(3, frag_transactionDetailed_share);
                treeMap.put(4, frag_transactionDetailed_integral);
            }
        }, fragmentPosition);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "交易明细", View.VISIBLE);
        tv_subTitle = util_init.initTextView(R.id.icd_title, R.id.tv_subTitle, null, "", View.VISIBLE);
        setFilterOrderStatus(defaultOrderStatus);

        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        iv_filter = util_init.initImageView(R.id.icd_title, R.id.iv_titleRight, R.mipmap.icon_transaction_category, View.VISIBLE);
        //功能
        rv_fragment = (RecyclerView) findViewById(R.id.rv_fragment);
        rv_fragment.setLayoutManager(new GridLayoutManager(getActivity(), 5));
    }

    @NonNull
    private void setFilterOrderStatus(Bean_OKProp_Oem.Enum_OrderStatusFilter enum_orderStatus) {
        if (enum_orderStatus == null) {
            tv_subTitle.setVisibility(View.GONE);
            return;
        }
        tv_subTitle.setVisibility(View.VISIBLE);
        tv_subTitle.setText(enum_orderStatus.toString());
        switch (enum_orderStatus) {
            case 显示全部:
                break;
            case 只显示待完成:
                break;
            case 只显示已成功:
                break;
            case 只显示已取消:
                break;
            case 只显示待处理:
                break;
            case 只显示待结算:
                break;
            case 不显示待完成:

                break;
        }

    }

    @Override
    public void initData() {
        functionList.add(new Bean_Item("充值", true));
        functionList.add(new Bean_Item("支付", false));
        functionList.add(new Bean_Item("提现", false));
        functionList.add(new Bean_Item("分润", false));
        functionList.add(new Bean_Item("积分", false));
    }

    @Override
    public void initAdapter() {
        rv_fragment.setAdapter(new RecyclerView.Adapter() {
            int width = SizeUtils.dp2px(80);
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
                        switch (position) {
                            case 0:
                                setFilterOrderStatus(frag_transactionDetailed_recharge.enum_orderStatus);
                                iv_filter.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                setFilterOrderStatus(frag_transactionDetailed_pay.enum_orderStatus);
                                iv_filter.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                setFilterOrderStatus(frag_transactionDetailed_withdrawals.enum_orderStatus);
                                iv_filter.setVisibility(View.VISIBLE);
                                break;
                            default:
                                setFilterOrderStatus(null);
                                iv_filter.setVisibility(View.GONE);
                                break;
                        }
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
        Util_View.viewOnClick(this, iv_filter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId())
            onBackPressed();
        else if (iv_filter == v) {
            showFilterPicker(fragmentPosition);
        }
       /* else if (v.getId() == tv_filter.getId())
            Util_Dialog.dialogByAct(getActivity(), R.layout.dialog_transactiondetailed_filter, true, false, false, false, new Util_Dialog.ActDialog() {
                EditText et_bankMobile;
                TextView tv_startTime, tv_query, tv_endTime;
                Util_Dialog.DialogContext dialogContext;

                @Override
                public void initDialogData() {

                }

                @Override
                public View initDialogView(Util_Dialog.DialogContext dialogContext, View view) {
                    this.dialogContext = dialogContext;
                    //预留手机号
                    util_init.initView(view, R.id.icd_mobile, R.id.v_split, View.VISIBLE);
                    util_init.initTextView(view, R.id.icd_mobile, R.id.tv_left, null, "+86", View.VISIBLE);
                    et_bankMobile = util_init.initEditText(view, R.id.icd_mobile, R.id.et_input, "请输入预留手机号", "18961713143", InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
                    tv_startTime = (TextView) view.findViewById(R.id.tv_startTime);
                    tv_endTime = (TextView) view.findViewById(R.id.tv_endTime);
                    //查询按钮
                    tv_query = util_init.initTextView(view, R.id.icd_query, R.id.tv_button, null, "查 询", View.VISIBLE);

                    tv_startTime.setText("开始时间:".concat(startTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
                    tv_endTime.setText("结束时间:".concat(endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

                    Util_View.viewOnClick(this, tv_startTime, tv_query, tv_endTime);
                    return view;
                }

                @Override
                public void onDismissListener() {

                }

                @Override
                public void onClick(View v) {
                    if (v.getId() == tv_startTime.getId()) {
                        new DatePickerDialog(dialogContext.getDialog(), android.R.style.Theme_Material_Light_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                StringBuilder sb = new StringBuilder("开始时间:");
                                sb.append(year).append("-").append(new DecimalFormat("00").format(monthOfYear + 1)).append("-").append(new DecimalFormat("00").format(dayOfMonth));
                                tv_startTime.setText(sb.toString());
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                    } else if (v.getId() == tv_query.getId()) {
                        dialogContext.dismiss();
                    } else if (v.getId() == tv_endTime.getId()) {
                        new DatePickerDialog(dialogContext.getDialog(), android.R.style.Theme_Material_Light_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                StringBuilder sb = new StringBuilder(" 结束时间:");
                                sb.append(year).append("-").append(new DecimalFormat("00").format(monthOfYear + 1)).append("-").append(new DecimalFormat("00").format(dayOfMonth));
                                tv_startTime.setText(sb.toString());
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                }
            });*/
    }

    private void showFilterPicker(final int fragmentPosition) {
        int index = 0;
        switch (fragmentPosition) {
            case 0:
                for (int i = 0, size = Bean_OKProp_Oem.Enum_OrderStatusFilter.values().length; i < size; i++) {
                    if (Bean_OKProp_Oem.Enum_OrderStatusFilter.values()[i] == frag_transactionDetailed_recharge.enum_orderStatus) {
                        index = i;
                        break;
                    }
                }
                break;
            case 1:
                for (int i = 0, size = Bean_OKProp_Oem.Enum_OrderStatusFilter.values().length; i < size; i++) {
                    if (Bean_OKProp_Oem.Enum_OrderStatusFilter.values()[i] == frag_transactionDetailed_pay.enum_orderStatus) {
                        index = i;
                        break;
                    }
                }
                break;
            case 2:
                for (int i = 0, size = Bean_OKProp_Oem.Enum_OrderStatusFilter.values().length; i < size; i++) {
                    if (Bean_OKProp_Oem.Enum_OrderStatusFilter.values()[i] == frag_transactionDetailed_withdrawals.enum_orderStatus) {
                        index = i;
                        break;
                    }
                }
                break;
        }
        String[] orderStatus = new String[Bean_OKProp_Oem.Enum_OrderStatusFilter.values().length];
        for (int i = 0, size = Bean_OKProp_Oem.Enum_OrderStatusFilter.values().length; i < size; i++) {
            orderStatus[i] = Bean_OKProp_Oem.Enum_OrderStatusFilter.values()[i].name();
        }
        Util_Picker.getInstance().useOptionPicker(getActivity(), "筛选订单状态", index, orderStatus, new Inter_Picker_Callback.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                switch (fragmentPosition) {
                    case 0:
                        frag_transactionDetailed_recharge.enum_orderStatus = Bean_OKProp_Oem.Enum_OrderStatusFilter.valueOf(item);
                        frag_transactionDetailed_recharge.xrv.startRefresh();
                        break;
                    case 1:
                        frag_transactionDetailed_pay.enum_orderStatus = Bean_OKProp_Oem.Enum_OrderStatusFilter.valueOf(item);
                        frag_transactionDetailed_pay.xrv.startRefresh();
                        break;
                    case 2:
                        frag_transactionDetailed_withdrawals.enum_orderStatus = Bean_OKProp_Oem.Enum_OrderStatusFilter.valueOf(item);
                        frag_transactionDetailed_withdrawals.xrv.startRefresh();
                        break;
                }
                setFilterOrderStatus(Bean_OKProp_Oem.Enum_OrderStatusFilter.valueOf(item));
            }
        });
    }


    int count = NetworkUtils.isConnected() ? 0 : 5;

    public void getTransactionDetailedData(View_XRefreshStatusView xRefreshStatusView, String url, final int page, final List list, final int type) {
        Util_Http.httpToRequest(getActivity(), url.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("page", page);
                httpParams.put("size", Page_Size_20);
                if (type != -1)
                    httpParams.put("phone", Abstract_App.bean_userInfo.getPhone());
                if (type > -1) {
                    httpParams.put("type", type);//0充值 1支付 2提现 3退款
                    httpParams.put("userid", Abstract_App.bean_userInfo.getId());
                    //0待完成 1支付成功 2已取消 3待处理 4待结算
                    switch (type) {
                        case 0:
                            if (frag_transactionDetailed_recharge.enum_orderStatus != 显示全部)
                                httpParams.put("status", frag_transactionDetailed_recharge.enum_orderStatus.orderStatus);
                            break;
                        case 1:
                            if (frag_transactionDetailed_pay.enum_orderStatus != 显示全部)
                                httpParams.put("status", frag_transactionDetailed_pay.enum_orderStatus.orderStatus);
                            break;
                        case 2:
                            if (frag_transactionDetailed_withdrawals.enum_orderStatus != 显示全部)
                                httpParams.put("status", frag_transactionDetailed_withdrawals.enum_orderStatus.orderStatus);
                            break;
                    }
                }
                httpParams.put("start_time", startTime);
                httpParams.put("end_time", endTime);
      /*          httpParams.put("order",order);
                httpParams.put("sort",sort);*/

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), xRefreshStatusView, count >= 5 ? null : Constant_International.message_net_request, false, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        switch (type) {
                            case 0:
                                list.addAll(JSON.parseArray(parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_TransactionDetailed_Recharge.class));
                                break;
                            case 1:
                                list.addAll(JSON.parseArray(parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_TransactionDetailed_Pay.class));
                                break;
                            case 2:
                                list.addAll(JSON.parseArray(parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_TransactionDetailed_Withdrawals.class));
                                break;
                            case -1:
                                list.addAll(JSON.parseArray(parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_TransactionDetailed_Share.class));
                                break;
                            case -2:
                                list.addAll(JSON.parseArray(parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_TransactionDetailed_Integral.class));
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
                if (++count == 5)
                    Util_Http.dismiss();
            }
        });
    }
}
