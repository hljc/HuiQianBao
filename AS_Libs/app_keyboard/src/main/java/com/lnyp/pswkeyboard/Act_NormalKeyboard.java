package com.lnyp.pswkeyboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.utils.core.Util_View;
import com.lnyp.pswkeyboard.widget.VirtualKeyboardView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;


public class Act_NormalKeyboard extends Framework_Activity {

    private VirtualKeyboardView virtualKeyboardView;

    private GridView gridView;

    private ArrayList<Map<String, String>> valueList;


    private Animation enterAnim;

    private Animation exitAnim;
    EditText et_collection;
    TextView tv_collection;
    public static final int Result_Code = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_normal_key_board);
        valueList = virtualKeyboardView.getValueList();
    }

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
    }


    @Override
    public void initView() {
        et_collection = util_init.initEditText(R.id.icd_collection, R.id.et_input, "请输入收款价格", null, InputType.TYPE_CLASS_TEXT, 11, View.VISIBLE);
        tv_collection = (TextView) findViewById(R.id.tv_collection);

        initAnim();

        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            et_collection.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(et_collection, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        virtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);
        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
            }
        });

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);

        et_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                virtualKeyboardView.setFocusable(true);
                virtualKeyboardView.setFocusableInTouchMode(true);

                virtualKeyboardView.startAnimation(enterAnim);
                virtualKeyboardView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {
        Util_View.viewOnClick(this, tv_collection);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮

                String amount = et_collection.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                et_collection.setText(amount);

                Editable ea = et_collection.getText();
                et_collection.setSelection(ea.length());
            } else {

                if (position == 9) {      //点击退格键
                    String amount = et_collection.getText().toString().trim();
                    if (!amount.contains(".")) {
                        amount = amount + valueList.get(position).get("name");
                        et_collection.setText(amount);

                        Editable ea = et_collection.getText();
                        et_collection.setSelection(ea.length());
                    }
                }

                if (position == 11) {      //点击退格键
                    String amount = et_collection.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        et_collection.setText(amount);

                        Editable ea = et_collection.getText();
                        et_collection.setSelection(ea.length());
                    }
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
    }
}
