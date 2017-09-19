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

import com.ceq.app.main.fragment.Frag_PushMessage;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_xinli_onekey.R;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.fragment.Frag_PushMessage.Extra_Enum_MessageCenter;

/**
 * Created by ceq on 2017/5/16.
 */

public class Act_Home_PushMessage extends Framework_Activity {
    ImageView iv_back;

    public List<Bean_Item> bean_items = new ArrayList<>();
    public List<Fragment> beanFragmentList = new ArrayList<>();
    public List<Bean_Item> bean_fragments = new ArrayList<>();
    RecyclerView.Adapter rva_fragment;
    RecyclerView rv_fragment;
    View v_split;
    ViewPager vp_share;
    int vpWidth;
    Frag_PushMessage frag_pushMessage1 = new Frag_PushMessage();
    Frag_PushMessage frag_pushMessage2 = new Frag_PushMessage();
    {
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(Extra_Enum_MessageCenter, Frag_PushMessage.Enum_MessageCenter.Platform);
        frag_pushMessage1.setArguments(bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable(Extra_Enum_MessageCenter, Frag_PushMessage.Enum_MessageCenter.Personal);
        frag_pushMessage2.setArguments(bundle2);
    }
    int spanCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.act_home_pushmessage);
    }

    @Override
    public void initView() {
        //标题栏
        util_init.initTextView(R.id.icd_title, R.id.tv_title, null, "消息中心", View.VISIBLE);
        iv_back = util_init.initImageView(R.id.icd_title, R.id.iv_titleLeft, R.mipmap.app_arrow_left, View.VISIBLE);

        rv_fragment = (RecyclerView) findViewById(R.id.rv_fragment);
        rv_fragment.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));

        v_split = findViewById(R.id.v_split);
        ViewGroup.LayoutParams lp = v_split.getLayoutParams();
        lp.width = vpWidth = Framework_Activity.screenWidth / spanCount;
        v_split.setLayoutParams(lp);

        vp_share = (ViewPager) findViewById(R.id.vp_share);
    }

    @Override
    public void initData() {

        beanFragmentList.add(frag_pushMessage1);
        beanFragmentList.add(frag_pushMessage2);

        bean_fragments.add(new Bean_Item("系统消息", true));
        bean_fragments.add(new Bean_Item("个人消息", false));
    }

    @Override
    public void initAdapter() {
        vp_share.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return beanFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return beanFragmentList.size();
            }
        });


        rv_fragment.setAdapter(rva_fragment = new RecyclerView.Adapter() {
            int fontSize = (int) (Framework_Activity.screenWidth / 27);

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                    tv_name.setTextColor(getResources().getColor(R.color.text_color_4));
                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
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
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0:
                                vp_share.setCurrentItem(0);
                                break;
                            case 1:
                                vp_share.setCurrentItem(1);
                                break;
                            case 2:
                                vp_share.setCurrentItem(2);
                                break;
                        }
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_fragments.size();
            }
        });
    }


    @Override
    public void initListener() {
        Util_View.viewOnClick(this, iv_back);

        vp_share.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                v_split.setX(position * vpWidth + (positionOffsetPixels / spanCount));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_share.setOffscreenPageLimit(bean_fragments.size());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == iv_back.getId()) {
            onBackPressed();
        }
    }
}
