package com.ceq.app.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.bean.Bean_PushMessage;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.MarginBottomAttr;
import com.zhy.autolayout.attr.MarginTopAttr;
import com.zhy.autolayout.attr.MinHeightAttr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app_core.constants.Constants_Common.Page_Size_20;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class Frag_PushMessage extends Framework_Fragment {
    RecyclerView rv_message;
    RecyclerView.Adapter rva_message;

    Enum_MessageCenter enum_messageCenter;

    List<Bean_PushMessage> bean_pushMessageList = new ArrayList<>();

    public static final String Extra_Enum_MessageCenter = "Extra_Enum_MessageCenter";

    public enum Enum_MessageCenter implements Serializable {
        Platform, Personal
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        enum_messageCenter = (Enum_MessageCenter) getArguments().getSerializable(Extra_Enum_MessageCenter);
        return init(R.layout.frag_pushmessage);
    }

    @Override
    public void initView() {
        rv_message = (RecyclerView) findViewById(R.id.rv_message);
        rv_message.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_message.setAdapter(rva_message = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_subName, tv_right;
                RelativeLayout rl_bg;
                RecyclerView rv_child;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    tv_name.setTextColor(getResources().getColor(R.color.primaryColorOff));

                    tv_subName = (TextView) itemView.findViewById(R.id.tv_subName);
                    tv_subName.setVisibility(View.VISIBLE);
                    tv_subName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    tv_subName.setTextColor(getResources().getColor(R.color.text_color_3));
                    AutoLinearLayout.LayoutParams layoutParams3 = (AutoLinearLayout.LayoutParams) tv_subName.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo3 = layoutParams3.getAutoLayoutInfo();
                    autoLayoutInfo3.addAttr(new MinHeightAttr(0, 0, 0));
                    autoLayoutInfo3.addAttr(new MarginTopAttr(10, 0, 0));
                    ViewGroup.LayoutParams lp3 = tv_subName.getLayoutParams();
                    tv_subName.setLayoutParams(lp3);

                    rv_child = (RecyclerView) itemView.findViewById(R.id.rv_child);
                    rv_child.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_child.setVisibility(View.VISIBLE);

                    AutoRelativeLayout rl_bg = (AutoRelativeLayout) itemView.findViewById(R.id.rl_bg);
                    AutoLinearLayout.LayoutParams lp = (AutoLinearLayout.LayoutParams) rl_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo = lp.getAutoLayoutInfo();
                    autoLayoutInfo.addAttr(new MinHeightAttr(0, 0, 0));
                    autoLayoutInfo.addAttr(new MarginTopAttr(20, 0, 0));
                    ViewGroup.LayoutParams lp2 = rl_bg.getLayoutParams();
                    rl_bg.setLayoutParams(lp2);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
            }


            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                Bean_PushMessage bean = bean_pushMessageList.get(position);
                mvh.tv_name.setText(bean.getTitle());
                mvh.tv_subName.setText(bean.getContent());

                setChildAdapter(mvh.rv_child, bean);
            }

            @Override
            public int getItemCount() {
                return bean_pushMessageList.size();
            }
        });

        final View_XRefreshLayout xrv = (View_XRefreshLayout) findViewById(R.id.xrv);
        xrv.setOnRefreshHttpListener(0, bean_pushMessageList, rva_message, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                getPushMessageData(currentPagePosition, view_xRefreshStatusView);
            }
        });

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onClick(View v) {

    }

    private void setChildAdapter(RecyclerView rv_child, final Bean_PushMessage bean_pushMessage) {
        rv_child.setAdapter(new RecyclerView.Adapter() {
            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_right;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                    tv_right.setVisibility(View.VISIBLE);
                    tv_right.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    tv_right.setTextColor(getResources().getColor(R.color.text_color_2));

                    AutoRelativeLayout rl_bg = (AutoRelativeLayout) itemView.findViewById(R.id.rl_bg);
                    AutoLinearLayout.LayoutParams lp = (AutoLinearLayout.LayoutParams) rl_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo = lp.getAutoLayoutInfo();
                    autoLayoutInfo.addAttr(new MinHeightAttr(0, 0, 0));
                    autoLayoutInfo.addAttr(new MarginBottomAttr(20, 0, 0));
                    ViewGroup.LayoutParams lp2 = rl_bg.getLayoutParams();
                    rl_bg.setLayoutParams(lp2);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                mvh.tv_right.setText(bean_pushMessage.getCreateTime());
            }

            @Override
            public int getItemCount() {
                return 1;
            }
        });
    }

    int count;

    private void getPushMessageData(final int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView) {
        Util_Http.httpToRequest(getActivity(), (enum_messageCenter == Enum_MessageCenter.Platform ? Constant_Api.URL_PUSH_MESSAGE_PLATFORM_GET : Constant_Api.URL_PUSH_MESSAGE_PERSONAL_GET).concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("page", currentPagePosition);
                httpParams.put("size", Page_Size_20);

            }
        }, new Util_Http.HttpDealStringListener(getActivity(), view_xRefreshStatusView, count >= 1 ? null : Constant_International.message_net_request, ++count == 1, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        bean_pushMessageList.addAll(JSON.parseArray(JSON.parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_PushMessage.class));
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }

}
