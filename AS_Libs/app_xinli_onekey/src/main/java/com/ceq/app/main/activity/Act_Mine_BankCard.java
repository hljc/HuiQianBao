package com.ceq.app.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.fragment.Frag_MyPurse_NewBalance.request_code_default_bank;

/**
 * Created by ceq on 2017/4/15.
 */

public class Act_Mine_BankCard extends Framework_Activity {
    ImageView iv_back;
    List<Bean_BankCardInfo> bean_bankCardInfoList = new ArrayList<>();
    RecyclerView rv_bankCardList;
    RecyclerView.Adapter rva_bankCardList;
    TextView tv_addBankCard;
    View_XRefreshStatusView view_xRefreshStatusView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_bankcard);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "银行卡", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        tv_addBankCard = util_init.initTextView(R.id.icd_title, R.id.tv_titleRight, null, "添加", View.VISIBLE);

        rv_bankCardList = (RecyclerView) findViewById(R.id.rv_bankCardList);
        rv_bankCardList.setLayoutManager(new LinearLayoutManager(getActivity()));

        xrv = (View_XRefreshLayout) findViewById(R.id.xrv);
    }

    View_XRefreshLayout xrv;

    @Override
    public void initData() {
        view_xRefreshStatusView = xrv.getView_xRefreshStatusView();
        xrv.setOnRefreshHttpListener(false, 0, bean_bankCardInfoList, rva_bankCardList, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                getBankListInfo();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getBankListInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(request_code_default_bank);
    }

    @Override
    public void initAdapter() {
        rv_bankCardList.setAdapter(rva_bankCardList = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_bankCarCompany, tv_bankCardType, tv_bankCardNum, tv_bankType;
                LinearLayout ll_default, ll_delete, ll_bg;
                SimpleDraweeView sdv_img;
                CardView cv_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_bankCarCompany = (TextView) itemView.findViewById(R.id.tv_bankCarCompany);
                    tv_bankCardType = (TextView) itemView.findViewById(R.id.tv_bankCardType);
                    tv_bankCardNum = (TextView) itemView.findViewById(R.id.tv_bankCardNum);
                    tv_bankType = (TextView) itemView.findViewById(R.id.tv_bankType);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);

                    ll_default = (LinearLayout) itemView.findViewById(R.id.ll_default);
                    ll_delete = (LinearLayout) itemView.findViewById(R.id.ll_delete);


                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);

                    cv_bg = (CardView) itemView.findViewById(R.id.cv_bg);
                /*    cv_bg.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });*/
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_rv_authourity, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_BankCardInfo bean = bean_bankCardInfoList.get(position);
                mvh.tv_bankCardNum.setText(new StringBuilder("**** **** **** ").append(bean.getCardNo().substring(bean.getCardNo().length() - 4)));
                mvh.tv_bankCardType.setText(bean.getCardType());
                mvh.sdv_img.setImageURI(bean.getLogo());
                switch (bean.getType()) {
                    case "0":
                        mvh.ll_default.setVisibility(View.GONE);
                        mvh.tv_bankType.setText("充值卡");
                        mvh.tv_bankCarCompany.setText(bean.getBankName());
                        break;
                    case "2":
                        mvh.ll_default.setVisibility(View.VISIBLE);
                        mvh.tv_bankType.setText("提现卡");
                        mvh.tv_bankCarCompany.setText(bean.getBankName().concat(bean.getIdDef().equals("1") ? "   [默认]" : ""));
                        break;
                }
                mvh.ll_default.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        defaultBankListInfo(bean);
                    }
                });
                mvh.ll_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delBankListInfo(bean);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_bankCardInfoList.size();
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back, tv_addBankCard);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        } else if (v.getId() == tv_addBankCard.getId()) {
            startActivity(new Intent(getActivity(), Act_BankCard_Add.class));
        }
    }

    public void getBankListInfo() {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_GET_BANK_CARD_INFO_GET.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), view_xRefreshStatusView, null, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        bean_bankCardInfoList.clear();
                        bean_bankCardInfoList.addAll(JSON.parseArray(JSON.parseObject(data).getString(Http_Key_Data), Bean_BankCardInfo.class));
                        if (rva_bankCardList != null) rva_bankCardList.notifyDataSetChanged();
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public void delBankListInfo(final Bean_BankCardInfo bean) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_DEL_BANK_CARD_INFO_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("cardno", bean.getCardNo());
                httpParams.put("type", bean.getType());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_request, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        bean_bankCardInfoList.remove(bean);
                        rva_bankCardList.notifyDataSetChanged();
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public void defaultBankListInfo(final Bean_BankCardInfo bean) {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_SET_DEFAULT_BANK_CARD_INFO_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("cardno", bean.getCardNo());

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), Constant_International.message_net_request, false, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        getBankListInfo();
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }
}
