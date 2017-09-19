package com.lnyp.pswkeyboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.ceq.app_core.framework.Framework_Activity;
import com.lnyp.pswkeyboard.widget.PasswordView;
import com.lnyp.pswkeyboard.widget.PopEnterPassword;

public class Act_PayKeyboard extends Framework_Activity {
    public static int Extra_Result_Code = 100;
    public static String Extra_String_Pay_Password = "Extra_String_Pay_Password";

    public interface OnFinishInputListener {
        void OnPasswordInputFinish(PopEnterPassword popEnterPassword, String password);
    }

    public static PopEnterPassword popEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_payment_key_board);
    }

    public static void showPayKeyboard(Activity activity, int tokenViewId, String title, String money, String remark, final OnFinishInputListener onFinishInputListener) {
        if(popEnterPassword!=null)
            return;
        popEnterPassword=new PopEnterPassword(activity);
        PasswordView passwordView = popEnterPassword.getPwdView();
        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                onFinishInputListener.OnPasswordInputFinish(popEnterPassword, password);
            }
        });
        passwordView.setMoney(money);
        passwordView.setTitle(title);
        passwordView.setRemark(remark);

        // 显示窗口
        popEnterPassword.showAtLocation(activity.findViewById(tokenViewId),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
