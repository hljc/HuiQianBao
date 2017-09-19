package com.ceq.app.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_TelephoneFare;
import com.ceq.app.main.methods.Method_Static;
import com.ceq.app_core.bean.Bean_SystemContacts;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.HeightAttr;
import com.zhy.autolayout.attr.MarginBottomAttr;
import com.zhy.autolayout.attr.MarginRightAttr;
import com.zhy.autolayout.attr.TextSizeAttr;
import com.zhy.autolayout.attr.WidthAttr;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app_core.constants.Constants_Common.LENGTH_USER;
import static com.ceq.app_core.constants.Constants_Common.expression_mobile;
import static com.ceq.app_core.utils.core.Util_System.getConatactInfoByTelephoneNumber;
import static com.ceq.app_core.utils.core.Util_System.selectContactInfoFromSystemContactsOnActivityResult;

/**
 * Created by Administrator on 2017/9/10 0010.
 */

public class Act_Purse_RechargeMobile extends Framework_Activity implements TextWatcher {
    RecyclerView rv_price;
    TextView tv_mobileAreaNum, tv_spec, tv_remind;
    EditText et_mobile;
    View_XRefreshLayout xrl;
    ImageView iv_back, iv_contacts;
    RecyclerView.Adapter rva_price;

    List<Bean_TelephoneFare> telephoneFareList = new ArrayList<>();

    int primaryColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        primaryColor = getResources().getColor(R.color.primaryColorOff);
        init(R.layout.act_purse_rechargemobile);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "话费充值", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_price = (RecyclerView) findViewById(R.id.rv_price);
        rv_price.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        iv_contacts.setColorFilter(new LightingColorFilter(Color.BLACK, primaryColor));

        View split = util_init.initView(R.id.icd_mobile, R.id.v_split, View.INVISIBLE);
        ((ViewGroup) split.getParent()).setBackgroundColor(Color.WHITE);
        et_mobile = util_init.initEditText(R.id.icd_mobile, R.id.et_input, "请输入手机号", Abstract_App.bean_userInfo.getPhone(), InputType.TYPE_CLASS_NUMBER, LENGTH_USER, View.VISIBLE);
        et_mobile.setTextColor(getResources().getColor(R.color.text_color_4));
        //tv_mobileAreaNum = util_init.initTextView(R.id.icd_mobile, R.id.tv_left, null, "+86", View.VISIBLE);
//        setTextSize(tv_mobileAreaNum, 80);
        setTextSize(et_mobile, 90);

        tv_spec = (TextView) findViewById(R.id.tv_spec);
        tv_remind = (TextView) findViewById(R.id.tv_remind);
        tv_spec.setText("" +
                "1.充值成功后，话费一般在10分钟内充值到您的手机；\n" +
                "2.充值成功后款项无法退回，请您务必正确填写手机号码；\n" +
                "3.根据国家手机实名制政策，非实名认证的手机号码充值可能无法到账，充值前请确认您将充值的手机号为已实名；\n" +
                "4.充值到账后，您会收到话费充值成功短信。也可拨打移动10086，联通10010，电信10000查询话费余额。");

        xrl = (View_XRefreshLayout) findViewById(R.id.xrl);
    }

    private void setTextSize(TextView textView, int textSize) {
        AutoLinearLayout.LayoutParams lp = (AutoLinearLayout.LayoutParams) textView.getLayoutParams();
        AutoLayoutInfo ali = lp.getAutoLayoutInfo();
        ali.addAttr(new TextSizeAttr(textSize, 0, 0));
        textView.setLayoutParams(lp);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_price.setAdapter(
                rva_price = new RecyclerView.Adapter() {
                    GradientDrawable drawablePrimaryOff = new GradientDrawable();
                    GradientDrawable drawablePrimaryOn = new GradientDrawable();

                    {
                        drawablePrimaryOff.setCornerRadius(20);
                        drawablePrimaryOff.setStroke(3, getResources().getColor(R.color.primaryColorOff));

                        drawablePrimaryOn.setCornerRadius(20);
                        drawablePrimaryOn.setColor(primaryColor);
                    }

                    class MyViewHolder extends RecyclerView.ViewHolder {
                        TextView tv_name, tv_value;
                        LinearLayout ll_bg;

                        public MyViewHolder(View itemView) {
                            super(itemView);
                            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                            tv_name.setVisibility(View.VISIBLE);
                            setTextSize(tv_name, 45);
                            tv_name.setTypeface(Typeface.DEFAULT_BOLD);

                            tv_value = (TextView) itemView.findViewById(R.id.tv_value);
                            tv_value.setVisibility(View.VISIBLE);
                            setTextSize(tv_value, 30);

                            ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                            AutoRelativeLayout.LayoutParams lp = (AutoRelativeLayout.LayoutParams) ll_bg.getLayoutParams();
                            AutoLayoutInfo ali = lp.getAutoLayoutInfo();
                            ali.addAttr(new WidthAttr(350, 0, 0));
                            ali.addAttr(new HeightAttr(200, 0, 0));
                            ali.addAttr(new MarginBottomAttr(20, 0, 0));
                            ali.addAttr(new MarginRightAttr(20, 0, 0));
                            ll_bg.setLayoutParams(lp);

                            itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                        final MyViewHolder mvh = (MyViewHolder) holder;
                        final Bean_TelephoneFare bean = telephoneFareList.get(position);
                        mvh.tv_name.setText(bean.getCardtel().concat("元"));
                        mvh.tv_value.setText(new StringBuilder("售价:").append(bean.getInprice()).append("元"));

                        mvh.tv_name.setTextColor(primaryColor);
                        mvh.tv_value.setTextColor(primaryColor);
                        mvh.ll_bg.setBackgroundDrawable(drawablePrimaryOff);
                        mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Util_Empty.isEmptyAndMatchToToast(et_mobile.getText().toString(), et_mobile.getHint().toString(), expression_mobile, Constant_International.error_expression_mobile)) {
                                    tv_remind.setTextColor(Color.RED);
                                    tv_remind.setText(Constant_International.error_expression_mobile);
                                    return;
                                } else
                                    Method_Static.showInputDialog(getActivity(), "支付密码", "请输入支付密码", "支付密码", InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD, 6, new Util_Runnable.Util_ArgsRunnable() {
                                        @Override
                                        public void run(Object... data) {
                                            final Util_Dialog.DialogContext dialogContext = (Util_Dialog.DialogContext) data[0];
                                            String payPass = (String) data[1];
                                            telephoneFarePayPass(getActivity(), payPass, new Util_Runnable.Util_TypeRunnable<Boolean>() {
                                                @Override
                                                public void run(Boolean data) {
                                                    if (data)
                                                        telephoneFareCommit(dialogContext.getDialog(), et_mobile, bean, new Util_Runnable.Util_ArgsRunnable() {
                                                            @Override
                                                            public void run(Object... data) {
                                                                Util_Toast.toast("充值成功");
                                                                finish();
                                                                dialogContext.dismiss();
                                                            }
                                                        });
                                                    else
                                                        dialogContext.dismiss();
                                                }
                                            });
                                        }
                                    });
                            }
                        });
                        mvh.ll_bg.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        mvh.ll_bg.setBackgroundDrawable(drawablePrimaryOn);
                                        mvh.tv_value.setTextColor(Color.WHITE);
                                        mvh.tv_name.setTextColor(Color.WHITE);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                    case MotionEvent.ACTION_CANCEL:
                                        mvh.ll_bg.setBackgroundDrawable(drawablePrimaryOff);
                                        mvh.tv_value.setTextColor(primaryColor);
                                        mvh.tv_name.setTextColor(primaryColor);
                                        break;
                                }
                                return false;
                            }
                        });
                    }

                    @Override
                    public int getItemCount() {
                        return telephoneFareList.size();
                    }
                }
        );
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, iv_contacts);
        et_mobile.addTextChangedListener(this);
        xrl.setOnRefreshHttpListener(false, false, 0, telephoneFareList, rva_price, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                telephoneAttributionAndFarePriceQuery(getActivity(), view_xRefreshStatusView, et_mobile, beanTelephoneFareRunnable);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v == iv_contacts)
            startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectContactInfoFromSystemContactsOnActivityResult(data, new Util_Runnable.Util_TypeRunnable<Bean_SystemContacts>() {
            @Override
            public void run(Bean_SystemContacts data) {
                et_mobile.setText(data.getPhoneNumber());
                telephoneAttributionAndFarePriceQuery(getActivity(), null, et_mobile, beanTelephoneFareRunnable);
            }
        });
    }

    public static void telephoneAttributionAndFarePriceQuery(Activity activity, View_XRefreshStatusView view_xRefreshStatusView, final EditText et_mobile, final Util_Runnable.Util_TypeRunnable<List<Bean_TelephoneFare>> runnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_TELEPHONE_ATTRIBUTION_AND_FARE_PRICE_QUERY_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", et_mobile.getText().toString());
                httpParams.put("brand_id", Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId());
            }
        }, new Util_Http.HttpDealStringListener(activity, view_xRefreshStatusView, "正在获取号码归属地及话费信息，请稍后！", true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        runnable.run(JSON.parseArray(JSON.parseObject(data).getString(Http_Key_Data), Bean_TelephoneFare.class));
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void telephoneFarePayPass(Activity activity, final String payPass, final Util_Runnable.Util_TypeRunnable<Boolean> runnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_TELEPHONE_FARE_PAY_PASS_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("paypass", payPass);

            }
        }, new Util_Http.HttpDealStringListener(activity, "正在校验支付密码，请稍后！", true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        if (runnable != null)
                            runnable.run(true);
                        break;
                    default:
                        if (runnable != null)
                            runnable.run(false);
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void telephoneFareCommit(Activity activity, final EditText et_telephone, final Bean_TelephoneFare telephoneFare, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_TELEPHONE_FARE_COMMIT_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", Abstract_App.bean_userInfo.getPhone());
                httpParams.put("phonebill", et_telephone.getText().toString());
                httpParams.put("amount", telephoneFare.getCardtel());
                httpParams.put("realamount", telephoneFare.getInprice());
                httpParams.put("order_desc", telephoneFare.getCardname());


            }
        }, new Util_Http.HttpDealStringListener(activity, "正在提交支付请求，请稍后！", true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        util_Args_runnable.run(data);
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (et_mobile.length() == 11) {
            telephoneAttributionAndFarePriceQuery(getActivity(), xrl.getView_xRefreshStatusView(), et_mobile, beanTelephoneFareRunnable);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    Util_Runnable.Util_TypeRunnable<List<Bean_TelephoneFare>> beanTelephoneFareRunnable = new Util_Runnable.Util_TypeRunnable<List<Bean_TelephoneFare>>() {
        @Override
        public void run(List<Bean_TelephoneFare> data) {
            telephoneFareList.clear();
            telephoneFareList.addAll(data);
            if (et_mobile.getText().toString().equals(Abstract_App.bean_userInfo.getPhone())) {
                tv_remind.setTextColor(getResources().getColor(R.color.text_color_3));
                tv_remind.setText(new StringBuilder("默认号码").append("(").append(data.get(0).getGame_area()).append(")"));
            } else {
                Bean_SystemContacts bean = getConatactInfoByTelephoneNumber(et_mobile.getText().toString());
                if (bean == null) {
                    tv_remind.setTextColor(Color.RED);
                    tv_remind.setText(new StringBuilder("不在通讯录").append("(").append(data.get(0).getGame_area()).append(")"));
                } else {
                    tv_remind.setTextColor(getResources().getColor(R.color.text_color_3));
                    tv_remind.setText(new StringBuilder(bean.getName()).append("(").append(data.get(0).getGame_area()).append(")"));
                }

            }
        }

    };
}
