package com.ceq.app.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.main.bean.Bean_UserAccount;
import com.ceq.app.main.fragment.Frag_MyPurse_NewBalance;
import com.ceq.app.main.fragment.Frag_MyPurse_NewIntegral;
import com.ceq.app.main.fragment.Frag_MyPurse_NewRakeBack;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.activity.Act_MyPurse_PurseDetailed.Extra_Int_MyPurse;
import static com.ceq.app.main.methods.Method_Static.obtainUserPurseBaseInfo;

/**
 * Created by ceq on 2017/5/16.
 */

public class Act_Mine_MyPurse extends Framework_Activity {
    ImageView iv_back;
    int fragmentPosition;
    Frag_MyPurse_NewIntegral frag_myPurse_newIntegral = new Frag_MyPurse_NewIntegral();
    Frag_MyPurse_NewBalance frag_myPurse_newBalance = new Frag_MyPurse_NewBalance();
    Frag_MyPurse_NewRakeBack frag_myPurse_newRakeBack = new Frag_MyPurse_NewRakeBack();
    public List<Fragment> beanFragmentList = new ArrayList<>();
    public List<Bean_Item> bean_fragments = new ArrayList<>();
    View v_split;
    int vpWidth;
    ViewPager vp_myPurse;
    RecyclerView rv_myPurse;
    RecyclerView.Adapter rva_myPurse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null)
            fragmentPosition = getIntent().getIntExtra(Extra_Int_MyPurse, fragmentPosition);
        init(R.layout.act_mine_mypurse);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "我的钱包", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        //功能
        rv_myPurse = (RecyclerView) findViewById(R.id.rv_myPurse);
        rv_myPurse.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        vp_myPurse = (ViewPager) findViewById(R.id.vp_myPurse);

        v_split = findViewById(R.id.v_split);
        ViewGroup.LayoutParams lp = v_split.getLayoutParams();
        lp.width = vpWidth = screenWidth / 3;
        v_split.setLayoutParams(lp);

        obtainUserPurseBaseInfo(getActivity(), null, false, new Util_Runnable.Util_TypeRunnable<Bean_UserAccount>() {
            @Override
            public void run(Bean_UserAccount data) {
                if (frag_myPurse_newIntegral.tv_integral != null)
                    frag_myPurse_newIntegral.tv_integral.setText(data.getCoin());
                if (frag_myPurse_newBalance.tv_balance != null)
                    frag_myPurse_newBalance.tv_balance.setText(new StringBuilder("￥").append(data.getBalance()).append("元").toString());
                if (frag_myPurse_newBalance.tv_balance2 != null)
                    frag_myPurse_newBalance.tv_balance2.setText(new StringBuilder("可提现金额 ").append(data.getBalance()).append("元").toString());
                if (frag_myPurse_newRakeBack.tv_rakeBackMoney != null)
                    frag_myPurse_newRakeBack.tv_rakeBackMoney.setText(new StringBuilder("返佣金额 ").append(data.getRebateBalance()).append("元").toString());

            }
        });
    }

    @Override
    public void initViewPager() {
        super.initViewPager();
        vp_myPurse.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return beanFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return beanFragmentList.size();
            }
        });
        vp_myPurse.setCurrentItem(fragmentPosition);
    }

    @Override
    public void initData() {
        bean_fragments.add(new Bean_Item("积分", false));
        bean_fragments.add(new Bean_Item("余额", false));
        bean_fragments.add(new Bean_Item("分润", false));
        bean_fragments.get(fragmentPosition).setChecked(true);


        beanFragmentList.add(frag_myPurse_newIntegral);
        beanFragmentList.add(frag_myPurse_newBalance);
        beanFragmentList.add(frag_myPurse_newRakeBack);
    }

    @Override
    public void initAdapter() {
        rv_myPurse.setAdapter(rva_myPurse = new RecyclerView.Adapter() {
            int fontSize=screenWidth/26;
            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                    tv_name.getPaint().setFakeBoldText(true);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);

                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.app_adapter_gv, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = bean_fragments.get(position);
                mvh.tv_name.setText((CharSequence) bean.getName());
                mvh.tv_name.setTextColor(bean.isChecked() ? getResources().getColor(R.color.primaryColorOff) : getResources().getColor(R.color.text_color_2));
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp_myPurse.setCurrentItem(position);
                        showItem(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_fragments.size();
            }
        });
        showItem(fragmentPosition);
    }

    void showItem(int position) {
        for (int i = 0, size = bean_fragments.size(); i < size; i++) {
            bean_fragments.get(i).setChecked(false);
        }
        bean_fragments.get(position).setChecked(true);
        if (rva_myPurse != null)
            rva_myPurse.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        vp_myPurse.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                v_split.setX(position * (vpWidth) + (positionOffsetPixels / 3f));
            }

            @Override
            public void onPageSelected(int position) {
                showItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_myPurse.setOffscreenPageLimit(bean_fragments.size());
        Util_View.viewOnClick(this, iv_back);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId())
            onBackPressed();
    }
}
