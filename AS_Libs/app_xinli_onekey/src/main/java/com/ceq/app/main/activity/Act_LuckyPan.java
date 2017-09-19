package com.ceq.app.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app_core.bean.Bean_LuckPan;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_Dialog;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.view.View_LuckyPanLayout;
import com.ceq.app_xinli_onekey.R;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class Act_LuckyPan extends Framework_Activity {
    List<Bean_LuckPan> bean_luckPen = new ArrayList<>();
    View_LuckyPanLayout lpl_lucky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_luckypan,false);
    }

    @Override
    public void initView() {
        lpl_lucky = (View_LuckyPanLayout) findViewById(R.id.lpl_lucky);
    }

    @Override
    public void initData() {
        bean_luckPen.add(new Bean_LuckPan("天佑红包",R.mipmap.icon_luckypan_redpackage, null));
        bean_luckPen.add(new Bean_LuckPan("福气红包",R.mipmap.icon_luckypan_redpackage, null));
        bean_luckPen.add(new Bean_LuckPan("健康红包",R.mipmap.icon_luckypan_redpackage, null));
        bean_luckPen.add(new Bean_LuckPan("友谊红包",R.mipmap.icon_luckypan_redpackage, null));
        bean_luckPen.add(new Bean_LuckPan("状元红包",R.mipmap.icon_luckypan_redpackage, null));
        bean_luckPen.add(new Bean_LuckPan("利事红包",R.mipmap.icon_luckypan_redpackage, null));

        lpl_lucky.setData(bean_luckPen, R.mipmap.icon_luckypan_pointer, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
               /* Bean_LuckPan bean_luckPan = (Bean_LuckPan) data[0];
                Util_Toast.toast(bean_luckPan.getName());*/
                getLuckyData();
            }
        });
    }

    void getLuckyData() {
        Util_Http.httpToRequest(getActivity(), Constant_Api.URL_GET_ENCOURAGE_MONEY_GET.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealStringListener(getActivity(),null, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Dialog.showDefaultDialog(lpl_lucky, new StringBuilder("您获得").append(parseObject(data).getString(Http_Key_Data)).append("元鼓励金").toString(), "确定", null, new Util_Dialog.DialogListener() {
                            @Override
                            public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {


                            }

                            @Override
                            public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                                framework_dialog.dismiss();
                            }

                            @Override
                            public void onDismissListener() {

                            }
                        });
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        //Util_View.viewOnClick(this, iv_lucky);

    }

    @Override
    public void onClick(View v) {

    }
}
