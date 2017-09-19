package com.ceq.app.main.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app.core.application.Abstract_App;
import com.ceq.app_xinli_onekey.core.modules.beans.Bean_OKModule_UIOptions;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.modules.enums.Enum_OKModule_Feature;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static com.ceq.app.main.methods.Method_Static.setImageLayoutParams;


/**
 * Created by ceq on 2017/5/9.
 */

public class Frag_Purse_Function extends Framework_Fragment {
    RecyclerView rv_function;
    List<Bean_Item> bean_itemList = new ArrayList<>();
    public Bean_OKModule_UIOptions bean_OK_moduleUIOptions = new Bean_OKModule_UIOptions();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initDisableStatusBar(R.layout.fragment_newpurse_1);
    }

    @Override
    public void initView() {
        rv_function = (RecyclerView) findViewById(R.id.rv_function);
    }

    @Override
    public void initData() {
        if (bean_OK_moduleUIOptions != null)
            rv_function.setLayoutManager(new GridLayoutManager(getActivity(), bean_OK_moduleUIOptions.getColumns()==0?2:bean_OK_moduleUIOptions.getColumns()));
        /*switch (bean_oneKey.getAbstract_oneKeyModule_purse().getEnum_pursePlatform()) {
            case 赚道钱包:
                if (bean_OK_moduleUIOptions != null)
                    rv_function.setLayoutManager(new GridLayoutManager(getActivity(), bean_OK_moduleUIOptions.getColumns()));
                break;
            case 和易付:
            case 牛码付:
                if (bean_OK_moduleUIOptions != null)
                    rv_function.setLayoutManager(new GridLayoutManager(getActivity(), bean_OK_moduleUIOptions.getColumns()));
                break;
        }
*/
    }

    @Override
    public void initAdapter() {
        List<Abstract_OkModule_Fragment> fragmentList = Abstract_App.bean_oneKeyBootstrap.getAbstract_okModule_fragmentList();
        Abstract_OkModule_Fragment fragment = null;
        for (int i = 0, size = fragmentList.size(); i < size; i++) {
            if (fragmentList.get(i).getEnum_okModule_feature() == Enum_OKModule_Feature.钱包) {
                fragment = fragmentList.get(i);
                break;
            }
        }
        if (fragment == null)
            return;
        switch (fragment.getEnum_okModule_template()) {
            case 赚道钱包:
            case 赚道钱包2:
                setRecycleView1();
                break;
            case 和易付:
            case 牛码付:
                rv_function.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bean_OK_moduleUIOptions != null)
                            setRecycleView2(getActivity(), rv_function, rv_function.getMeasuredHeight() / bean_OK_moduleUIOptions.getRows(), bean_itemList, true);
                    }
                });
            case 信掌柜:
                rv_function.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bean_OK_moduleUIOptions != null)
                            setRecycleView2(getActivity(), rv_function, rv_function.getMeasuredHeight() / bean_OK_moduleUIOptions.getRows(), bean_itemList, false);
                    }
                });
                break;
        }

    }

    private static void setRecycleView2(final Activity activity, RecyclerView recyclerView, final int item_height, final List<Bean_Item> bean_itemList, final boolean showRightSplit) {
        recyclerView.setAdapter(new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name;
                SimpleDraweeView sdv_img;
                LinearLayout ll_bg;
                int color = activity.getResources().getColor(R.color.text_color_3);
                View v_bottomSplit, v_rightSplit;

                public MyViewHolder(final View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_name.setVisibility(View.VISIBLE);
                    tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    tv_name.setTextColor(color);

                    itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);
                    sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                    setImageLayoutParams(sdv_img, 120, 10);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    ll_bg.setBackgroundResource(R.drawable.bg_keyboard);

                    v_bottomSplit = itemView.findViewById(R.id.v_bottomSplit);
                    v_rightSplit = itemView.findViewById(R.id.v_rightSplit);
                    v_bottomSplit.setBackgroundColor(Color.rgb(225, 225, 225));
                    v_rightSplit.setBackgroundColor(showRightSplit ? Color.rgb(225, 225, 225) : Color.WHITE);

                    v_bottomSplit.post(new Runnable() {
                        @Override
                        public void run() {
                            ViewGroup.LayoutParams lp = ll_bg.getLayoutParams();
                            lp.height = item_height - itemView.findViewById(R.id.v_bottomSplit).getMeasuredHeight();
                            ll_bg.setLayoutParams(lp);

                        }
                    });

                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = bean_itemList.get(position);
                mvh.tv_name.setVisibility(bean == null ? View.GONE : View.VISIBLE);
                if (bean == null) return;
                mvh.tv_name.setText(bean.getName().toString());
                mvh.sdv_img.setImageResource(bean.getImgId());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util_Runnable.Util_ArgsRunnable util_Args_runnable = bean.getUtil_Args_runnable();
                        if (util_Args_runnable != null) util_Args_runnable.run(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_itemList.size();
            }
        });
    }

    private void setRecycleView1() {
        rv_function.setAdapter(new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                TextView tv_name;
                TextView tv_description;
                ImageView iv_img;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                    tv_description = (TextView) itemView.findViewById(R.id.tv_description);

                    iv_img = (ImageView) itemView.findViewById(R.id.iv_img);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_newpurse_function, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = bean_itemList.get(position);
                if(bean==null)return;
                mvh.tv_name.setText(bean.getName().toString());
                mvh.tv_description.setText(bean.getValue());
                mvh.iv_img.setImageResource(bean.getImgId());
                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util_Runnable.Util_ArgsRunnable util_Args_runnable = bean.getUtil_Args_runnable();
                        if (util_Args_runnable != null) util_Args_runnable.run(position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_itemList.size();
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
}
