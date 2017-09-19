package com.ceq.app.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.activity.Act_MyPurse_PurseDetailed;
import com.ceq.app.main.bean.Bean_UserAccount;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.activity.Act_MyPurse_PurseDetailed.Extra_Int_MyPurse;
import static com.ceq.app_core.framework.Framework_Activity.screenWidth;
import static com.ceq.app.main.methods.Method_Static.getUserGradeRole;
import static com.ceq.app.main.methods.Method_Static.obtainUserPurseBaseInfo;
import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;

/**
 * Created by ceq on 2017/5/16.
 */

public class Frag_MyPurse_NewIntegral extends Framework_Fragment {
    RecyclerView rv_integral;
    LinearLayout ll_integral, ll_ranking;
    TextView tv_ranking, tv_name, tv_identify, tv_mobile;
    public TextView tv_integral, tv_remind, tv_remind0;

    List<Bean_Item> bean_items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return init(R.layout.frag_mypurse_newintegral);
    }

    @Override
    public void initView() {
        rv_integral = (RecyclerView) findViewById(R.id.rv_integral);
        rv_integral.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        ll_integral = (LinearLayout) findViewById(R.id.ll_integral);
        ll_ranking = (LinearLayout) findViewById(R.id.ll_ranking);

        tv_ranking = (TextView) findViewById(R.id.tv_ranking);
        tv_integral = (TextView) findViewById(R.id.tv_integral);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_identify = (TextView) findViewById(R.id.tv_identify);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);

        tv_name.setText(Abstract_App.bean_userInfo.getNickName());
        tv_mobile.setText(Abstract_App.bean_userInfo.getPhone());
        tv_identify.setText(getUserGradeRole(Abstract_App.bean_userInfo.getGrade()));

        tv_remind = (TextView) findViewById(R.id.tv_remind);
        tv_remind0 = (TextView) findViewById(R.id.tv_remind0);
        tv_remind0.setText(new StringBuilder("【分享好友获积分】分享").append(getResources().getString(R.string.app_name)).append("，共享财富商机！"));
        tv_remind.setText(new StringBuilder("【积分兑换").append(getUserGradeRole("2")).append("】积分兑换").append(getUserGradeRole("2")).append("、").append(getUserGradeRole("3")).append("资格！"));

        obtainUserPurseBaseInfo(getActivity(), null, false, new Util_Runnable.Util_TypeRunnable<Bean_UserAccount>() {
            @Override
            public void run(Bean_UserAccount data) {
                tv_integral.setText(data.getCoin());
            }
        });

        int color = getResources().getColor(R.color.primaryColorOn);
        ((ImageView) findViewById(R.id.iv_bg_top)).setColorFilter(new LightingColorFilter(color, Color.argb(0, 0, 0, 0)));
    }

    @Override
    public void initData() {
        bean_items.add(new Bean_Item("兑换", R.mipmap.icon_integral_replace, true));
        bean_items.add(new Bean_Item("规则", R.mipmap.icon_integral_regular, false));
        bean_items.add(new Bean_Item("明细", R.mipmap.icon_integral_detailed, false));

    }

    @Override
    public void initAdapter() {
        rv_integral.setAdapter(new RecyclerView.Adapter() {
            int bgW = screenWidth / 3;
            int imgW = screenWidth / 9;

            class MyViewHolder extends RecyclerView.ViewHolder {
                SimpleDraweeView sdv_img;
                TextView tv_name;
                LinearLayout ll_bg;
                View v_bottomSplit, v_rightSplit;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    setImageLayoutParams(sdv_img, 120, 5);

                    (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    ViewGroup.LayoutParams lp = ll_bg.getLayoutParams();
                    lp.width = lp.height = bgW;
                    ll_bg.setLayoutParams(lp);
                    ll_bg.setBackgroundResource(R.drawable.app_btn_white);

                    v_bottomSplit = itemView.findViewById(R.id.v_bottomSplit);
                    v_rightSplit = itemView.findViewById(R.id.v_rightSplit);
                    v_bottomSplit.setBackgroundColor(Color.rgb(225, 225, 225));
                    v_rightSplit.setBackgroundColor(Color.rgb(225, 225, 225));

                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = bean_items.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                Util_Toast.toast(Constant_International.framework_function_no_open);
                                break;
                            case 1:
                                Util_Toast.toast(Constant_International.framework_function_no_open);
                                break;
                            case 2:
                                startActivity(new Intent(getActivity(), Act_MyPurse_PurseDetailed.class).putExtra(Extra_Int_MyPurse, 0));
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_items.size();
            }
        });
    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, ll_integral, ll_ranking);
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onClick(View v) {

    }
}
