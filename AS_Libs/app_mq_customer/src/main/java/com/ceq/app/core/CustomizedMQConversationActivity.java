package com.ceq.app.core;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.ceq.app_core.utils.core.Util_Screen;
import com.meiqia.meiqiasdk.activity.MQConversationActivity;
import com.meiqia.meiqiasdk.model.Agent;

import java.lang.reflect.Field;

import app_mq_customer.R;

/**
 * 集成自 MQConversationActivity，可以动态改变其中的一些方法实现
 */
public class CustomizedMQConversationActivity extends MQConversationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util_Screen.statusBarToTranslucent(this, getResources().getColor(R.color.title_background));
        try {
            Field title_rl = getClass().getSuperclass().getDeclaredField("mTitleRl");
            title_rl.setAccessible(true);
            View view= (View) title_rl.get(this);
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) view.getLayoutParams();
            lp.setMargins(0, BarUtils.getStatusBarHeight(),0,0);
            view.setLayoutParams(lp);

            Field mBackIv = getClass().getSuperclass().getDeclaredField("mBackIv");
            mBackIv.setAccessible(true);
            ((ImageView) mBackIv.get(this)).setColorFilter(new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.title_fontColor)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 这里可以动态添加一些 View 到布局中
        // eg: 右边添加一个按钮
      /*  RelativeLayout titleRL = (RelativeLayout) findViewById(R.id.title_rl);
        Button rightBtn = new Button(this);
        rightBtn.setText("RightBtn");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        titleRL.addView(rightBtn, params);

        // do something
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomizedMQConversationActivity.this, "RightBtn OnClick", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Field field = getClass().getSuperclass().getDeclaredField("mTitleTv");
            field.setAccessible(true);
            TextView tv = (TextView) field.get(this);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLoadDataComplete(MQConversationActivity mqConversationActivity, Agent agent) {
       /* if (agent != null) {
            BaseMessage message = new TextMessage("这是一条自动发送的消息");
            // 在打开对话界面的时候，自动发送一条消息
            mqConversationActivity.sendMessage(message);
        }*/
    }

}
