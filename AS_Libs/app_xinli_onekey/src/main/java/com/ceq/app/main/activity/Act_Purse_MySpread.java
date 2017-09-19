package com.ceq.app.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.main.bean.Bean_MyMerchantInfo;
import com.ceq.app.main.bean.Bean_ProductionPeopleInfo;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Screen;
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
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;
import static com.ceq.app_core.framework.Framework_Web.Extra_String_Title;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class Act_Purse_MySpread extends Framework_Activity {
    ImageView iv_back;
    List<Bean_ProductionPeopleInfo> bean_productionPeopleInfoList = new ArrayList<>();
    Bean_MyMerchantInfo bean_myMerchantInfo;
    RecyclerView rv_productionCategory;
    RecyclerView.Adapter rva_productionCategory;
    TextView tv_peoples, tv_realName, tv_noRealName;
    int primaryColorOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        primaryColorOff = getResources().getColor(R.color.primaryColorOff);
        init(R.layout.act_purse_merchantmanage,primaryColorOff, Util_Screen.Enum_BarFontColor.White);
    }

    @Override
    public void initView() {
        //标题栏
        (util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "我的推广", View.VISIBLE)).setTextColor(Color.WHITE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);
        findViewById(R.id.icd_title).setBackgroundColor(primaryColorOff);
        iv_back.setColorFilter(new LightingColorFilter(Color.BLACK,Color.WHITE));

        rv_productionCategory = (RecyclerView) findViewById(R.id.rv_productionCategory);
        rv_productionCategory.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_realName = (TextView) findViewById(R.id.tv_realName);
        tv_noRealName = (TextView) findViewById(R.id.tv_noRealName);
        tv_peoples = (TextView) findViewById(R.id.tv_peoples);


        //((ImageView)findViewById(R.id.iv_bg)).setColorFilter(new LightingColorFilter(Color.argb(5, 10, 10, 10), getResources().getColor(R.color.primaryColorOff)));

    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        rv_productionCategory.setAdapter(rva_productionCategory = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name, tv_subName;
                RelativeLayout rl_bg;
                ImageView iv_forward;
                SimpleDraweeView sdv_img;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    setImageLayoutParams(sdv_img, 150, 20);

                    iv_forward = (ImageView) itemView.findViewById(R.id.iv_forward);
                    iv_forward.setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.text_color_2)));
                    iv_forward.setVisibility(View.VISIBLE);

                    rl_bg = (RelativeLayout) itemView.findViewById(R.id.rl_bg);

                    tv_subName = (TextView) itemView.findViewById(R.id.tv_subName);
                    tv_subName.setVisibility(View.VISIBLE);
                    tv_subName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    tv_subName.setTextColor(getResources().getColor(R.color.text_color_3));

                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    tv_name.setTextColor(getResources().getColor(R.color.primaryColorOff));
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_ProductionPeopleInfo bean = bean_productionPeopleInfoList.get(position);
                mvh.tv_name.setText(bean.getName());
                switch (bean.getGrade()) {
                    case "0":
                        mvh.sdv_img.setImageResource(R.mipmap.scale0);
                        break;
                    case "1":
                        mvh.sdv_img.setImageResource(R.mipmap.scale1);
                        break;
                    case "2":
                        mvh.sdv_img.setImageResource(R.mipmap.scale2);
                        break;
                    case "3":
                        mvh.sdv_img.setImageResource(R.mipmap.scale3);
                        break;
                    case "4":
                        mvh.sdv_img.setImageResource(R.mipmap.scale4);
                        break;
                    case "5":
                        mvh.sdv_img.setImageResource(R.mipmap.scale5);
                        break;
                    case "6":
                        mvh.sdv_img.setImageResource(R.mipmap.scale6);
                        break;
                    case "7":
                        mvh.sdv_img.setImageResource(R.mipmap.scale7);
                        break;
                    case "8":
                        mvh.sdv_img.setImageResource(R.mipmap.scale8);
                        break;
                    case "9":
                        mvh.sdv_img.setImageResource(R.mipmap.scale9);
                        break;
                    case "10":
                        mvh.sdv_img.setImageResource(R.mipmap.scale10);
                        break;
                }
                mvh.tv_subName.setText(new StringBuilder("直推人数:").append(bean.getGradesonIds()).append("  间推人数:").append(bean.getGradepreuids()));
                mvh.rl_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Act_Share_ShareRecord.class).putExtra(Extra_String_Title,bean.getName()).putExtra(Act_Share_ShareRecord.Extra_String_Grade,bean.getGrade()));
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_productionPeopleInfoList.size();
            }
        });
        final View_XRefreshLayout xrv = (View_XRefreshLayout) findViewById(R.id.xrv);
        xrv.setOnRefreshHttpListener(false, 0, bean_productionPeopleInfoList, rva_productionCategory, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                getMyMerchantInfo(getActivity(), view_xRefreshStatusView, new Util_Runnable.Util_ArgsRunnable() {
                    @Override
                    public void run(Object... data) {
                        Bean_MyMerchantInfo bean_myMerchantInfo = JSON.parseObject(JSON.parseObject((String) data[0]).getString(Http_Key_Data), Bean_MyMerchantInfo.class);
                        bean_productionPeopleInfoList.clear();
                        bean_productionPeopleInfoList.addAll(bean_myMerchantInfo.getThirdLevelDistribution());
                        tv_peoples.setText(String.valueOf(bean_myMerchantInfo.getFalsenum() + bean_myMerchantInfo.getTruenum()));
                        tv_noRealName.setText(String.valueOf(bean_myMerchantInfo.getFalsenum()));
                        tv_realName.setText(String.valueOf(bean_myMerchantInfo.getTruenum()));
                    }
                });
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back)
            onBackPressed();

    }

    public static void getMyMerchantInfo(Activity activity, View_XRefreshStatusView view_xRefreshStatusView, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_MERCHANT_INFO_POST.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealStringListener(activity, view_xRefreshStatusView, "正在获取推广信息，请稍后！", true, true) {
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
}
