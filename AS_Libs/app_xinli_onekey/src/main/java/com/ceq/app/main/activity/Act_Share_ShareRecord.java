package com.ceq.app.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.NetworkUtils;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.bean.Bean_Vip;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.HeightAttr;
import com.zhy.autolayout.attr.MarginBottomAttr;
import com.zhy.autolayout.attr.MarginTopAttr;
import com.zhy.autolayout.attr.PaddingBottomAttr;
import com.zhy.autolayout.attr.PaddingTopAttr;
import com.zhy.autolayout.attr.TextSizeAttr;
import com.zhy.autolayout.attr.WidthAttr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app_core.constants.Constants_Common.Page_Size_20;
import static com.ceq.app.main.methods.Method_Static.callTelephone;
import static com.ceq.app.main.methods.Method_Static.getUserGradeRole;
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;
import static com.ceq.app_core.framework.Framework_Web.Extra_String_Title;

/**
 * Created by ceq on 2017/4/16.
 */

public class Act_Share_ShareRecord extends Framework_Activity {
    ImageView iv_back;
    RecyclerView rv_watchVip;
    List<Bean_Vip> bean_vipList = new ArrayList<>();
    RecyclerView.Adapter rva_watchVip;
    String grade;
    String title;
    public static final String Extra_String_Grade="Extra_String_Grade";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        title=intent.getStringExtra(Extra_String_Title);
        grade=intent.getStringExtra(Extra_String_Grade);
        init(R.layout.act_upgrade_watchvip);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, title, View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_watchVip = (RecyclerView) findViewById(R.id.rv_watchVip);
        rv_watchVip.setLayoutManager(new LinearLayoutManager(getActivity()));

        count = 0;

    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_watchVip.setAdapter(rva_watchVip = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_subName, tv_right, tv_bottom;
                RelativeLayout rl_bg;
                SimpleDraweeView sdv_img;
                ImageView iv_forward;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);
                    AutoLinearLayout.LayoutParams layoutParams0 = (AutoLinearLayout.LayoutParams) rl_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo0 = layoutParams0.getAutoLayoutInfo();
                    autoLayoutInfo0.addAttr(new PaddingTopAttr(20, 0, 0));
                    autoLayoutInfo0.addAttr(new PaddingBottomAttr(20, 0, 0));
                    ViewGroup.LayoutParams lp0 = rl_bg.getLayoutParams();
                    rl_bg.setLayoutParams(lp0);

                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    setImageLayoutParams(sdv_img, 60, 0);

                    AutoRelativeLayout.LayoutParams layoutParams = (AutoRelativeLayout.LayoutParams) sdv_img.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo = layoutParams.getAutoLayoutInfo();
                    autoLayoutInfo.addAttr(new HeightAttr(120, 0, 0));
                    autoLayoutInfo.addAttr(new WidthAttr(120, 0, 0));
                    autoLayoutInfo.addAttr(new MarginBottomAttr(20, 0, 0));
                    autoLayoutInfo.addAttr(new MarginTopAttr(20, 0, 0));
                    ViewGroup.LayoutParams lp = sdv_img.getLayoutParams();
                    sdv_img.setLayoutParams(lp);

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    AutoLinearLayout.LayoutParams layoutParams1 = (AutoLinearLayout.LayoutParams) tv_name.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo1 = layoutParams1.getAutoLayoutInfo();
                    autoLayoutInfo1.addAttr(new TextSizeAttr(45, 0, 0));
                    ViewGroup.LayoutParams lp1 = tv_name.getLayoutParams();
                    tv_name.setLayoutParams(lp1);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_3));
                    tv_name.setHintTextColor(getResources().getColor(R.color.text_color_2));
                    tv_name.setHint("暂未实名");


                    tv_subName = (TextView) itemView.findViewById(R.id.tv_subName);
                    tv_subName.setVisibility(View.VISIBLE);
                    tv_subName.setTextColor(getResources().getColor(R.color.text_color_3));
                    AutoLinearLayout.LayoutParams lp5 = (AutoLinearLayout.LayoutParams) tv_subName.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo5 = lp5.getAutoLayoutInfo();
                    autoLayoutInfo5.addAttr(new MarginTopAttr(0, 0, 0));
                    tv_subName.setLayoutParams(tv_subName.getLayoutParams());

                    tv_bottom = (TextView) itemView.findViewById(R.id.tv_bottom);
                    tv_bottom.setVisibility(View.VISIBLE);
                    tv_bottom.setTextColor(getResources().getColor(R.color.text_color_2));
                    tv_bottom.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    AutoLinearLayout.LayoutParams lp4 = (AutoLinearLayout.LayoutParams) tv_bottom.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo4 = lp4.getAutoLayoutInfo();
                    autoLayoutInfo4.addAttr(new MarginTopAttr(10, 0, 0));
                    autoLayoutInfo4.addAttr(new TextSizeAttr(30, 0, 0));
                    tv_bottom.setLayoutParams(tv_bottom.getLayoutParams());

                    tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                    tv_right.setVisibility(View.VISIBLE);
                    tv_right.setTextColor(getResources().getColor(R.color.primaryColorOff));
                    AutoLinearLayout.LayoutParams layoutParams2 = (AutoLinearLayout.LayoutParams) tv_right.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo2 = layoutParams2.getAutoLayoutInfo();
                    autoLayoutInfo2.addAttr(new TextSizeAttr(35, 0, 0));
                    ViewGroup.LayoutParams lp2 = tv_right.getLayoutParams();
                    tv_right.setLayoutParams(lp2);

                    iv_forward = (ImageView) itemView.findViewById(R.id.iv_forward);
                    iv_forward.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.primaryColorOff)));
                    iv_forward.setVisibility(View.VISIBLE);
                    iv_forward.setImageResource(R.mipmap.icon_aboutus_hotline);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Vip bean = bean_vipList.get(position);
                String name = (String) Util_Empty.isEmptyToReplace(bean.getRealname(), "");
                StringBuilder sb = new StringBuilder();
                if (name.length() > 0) {
                    sb.append(name.charAt(0));
                    for (int i = 0, size = name.length() - 1; i < size; i++) {
                        sb.append("*");
                    }
                }
                mvh.tv_name.setText(sb.toString());
                mvh.tv_subName.setText((String) Util_Empty.isEmptyToReplace(bean.getPhone(), ""));
                mvh.tv_right.setText((String) Util_Empty.isEmptyToReplace(getUserGradeRole(bean.getGrade()), "-"));
                mvh.sdv_img.getHierarchy().setPlaceholderImage(R.mipmap.app_logo);
                mvh.tv_bottom.setText("注册日期：".concat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.parseLong(bean.getCreateTime()))));

                mvh.iv_forward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callTelephone(getActivity(), bean.getPhone());
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_vipList.size();
            }
        });
        final View_XRefreshLayout xrv = (View_XRefreshLayout) findViewById(R.id.xrv);
        xrv.setOnRefreshHttpListener(0, bean_vipList, rva_watchVip, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                getVipMemberData(getActivity(), view_xRefreshStatusView, currentPagePosition, list, null,grade);
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

    static int count = NetworkUtils.isConnected() ? 0 : 1;

    public static void getVipMemberData(Activity activity, View_XRefreshStatusView view_xRefreshStatusView, final int page, final List list, final TextView tv_member, final String grade) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_GET_APPOINT_VIP_MEMBER_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("size", Page_Size_20);
                httpParams.put("page", page);
                httpParams.put("grade", grade);
                httpParams.put("lower_level", 1);

            }
        }, new Util_Http.HttpDealStringListener(activity, view_xRefreshStatusView, count >= 1 ? null : Constant_International.message_net_request, ++count == 1, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        List bean_vips = JSON.parseArray(parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_Vip.class);
                        list.addAll(bean_vips);
                        if (tv_member != null)
                            tv_member.setText(parseObject(data).getJSONObject(Http_Key_Data).getString("totalElememts").concat("人"));
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }
}
