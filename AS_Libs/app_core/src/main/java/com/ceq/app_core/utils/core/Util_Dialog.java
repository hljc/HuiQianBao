package com.ceq.app_core.utils.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ceq.app_core.R;
import com.ceq.app_core.framework.Framework_Dialog;

import me.codeboy.android.aligntextview.CBAlignTextView;

import static com.ceq.app_core.framework.Framework_Dialog.getHashMap;


/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_Dialog {

    public static abstract class OnClickPositive implements DialogInterface.OnClickListener {
        private String positiveText;

        public OnClickPositive(String positiveText) {
            this.positiveText = positiveText;
        }
    }

    public static abstract class OnClickNegative implements DialogInterface.OnClickListener {
        private String negativeText;

        public OnClickNegative(String negativeText) {
            this.negativeText = negativeText;
        }
    }


    @SuppressLint("NewApi")
    public static android.app.Dialog dialogBySystem(Context context, boolean isProgressDialog, String message, OnClickPositive onClickPositive, OnClickNegative onClickNegative, DialogInterface.OnDismissListener onDismissListener) {
        android.app.Dialog dialog = null;
        if (isProgressDialog) {
            ProgressDialog pdb = new ProgressDialog(context);
            pdb.setOnDismissListener(onDismissListener);
            if (!Util_Empty.isEmpty(message))
                pdb.setMessage(message);
            if (!Util_Empty.isEmpty(onClickPositive) || !Util_Empty.isEmpty(onClickNegative))
                pdb.setCancelable(false);
            dialog = pdb;
            Util_Log.logE(context, "dialog", dialog instanceof ProgressDialog);
        } else {
            AlertDialog.Builder adb = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
            if (!Util_Empty.isEmpty(onDismissListener))
                adb.setOnDismissListener(onDismissListener);
            if (!Util_Empty.isEmpty(message))
                adb.setMessage(message);
            if (!Util_Empty.isEmpty(onClickPositive))
                adb.setPositiveButton(onClickPositive.positiveText, onClickPositive);
            if (!Util_Empty.isEmpty(onClickNegative))
                adb.setNegativeButton(onClickNegative.negativeText, onClickNegative);
            if (!Util_Empty.isEmpty(onClickPositive) || !Util_Empty.isEmpty(onClickNegative))
                adb.setCancelable(false);
            dialog = adb.create();
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static void dialogByAct(final View viewToken, int layoutId, int dialogBackgroundColor, boolean isCanBack, boolean isFullScreen, boolean isLandScreen, boolean isSensorLandScreen, ActDialog actDialog) {
        if (Util_Empty.isEmpty(actDialog))
            return;
        Bundle bundle = new Bundle();
        Activity activity = (Activity) viewToken.getContext();
        getHashMap().put(activity.toString(), actDialog);
        bundle.putSerializable(Framework_Dialog.Extra_String_Dialog, activity.toString());
        bundle.putBoolean(Framework_Dialog.Extra_Boolean_Is_Can_Back, isCanBack);
        if (dialogBackgroundColor != Integer.MAX_VALUE)
            bundle.putInt(Framework_Dialog.Extra_Int_DialogBackgroundColor, dialogBackgroundColor);
        bundle.putBoolean(Framework_Dialog.Extra_Boolean_Is_Full_Screen, isFullScreen);
        bundle.putBoolean(Framework_Dialog.Extra_Boolean_Is_Land_Screen, isLandScreen);
        bundle.putBoolean(Framework_Dialog.Extra_Boolean_Is_Sensor_Land_Screen, isSensorLandScreen);
        bundle.putInt(Framework_Dialog.Extra_Int_Layout_Id, layoutId);

        if (viewToken.isEnabled()) {
            activity.startActivity(new Intent(activity, Framework_Dialog.class).putExtras(bundle));
            viewToken.setEnabled(false);
            viewToken.postDelayed(dialogRunnable.setViewToken(viewToken), 500);
        }
    }

    public static void dialogByAct(final View viewToken, int layoutId, boolean isCanBack, boolean isFullScreen, boolean isLandScreen, boolean isSensorLandScreen, ActDialog actDialog) {
        dialogByAct(viewToken, layoutId, Integer.MAX_VALUE, isCanBack, isFullScreen, isLandScreen, isSensorLandScreen, actDialog);
    }

    private static DialogRunnable dialogRunnable = new DialogRunnable();

    private static class DialogRunnable implements Runnable {
        View viewToken;

        public DialogRunnable setViewToken(View viewToken) {
            this.viewToken = viewToken;
            return this;
        }

        @Override
        public void run() {
            viewToken.setEnabled(true);
        }
    }

    public abstract static class ActDialog implements View.OnClickListener {
        public abstract void initDialogData();

        public abstract View initDialogView(DialogContext dialogContext, View view);

        public abstract void onDismissListener();

        public void onDialogStart() {

        }
    }

    public interface DialogContext {
        Framework_Dialog getDialog();

        void dismiss();
    }

    /**
     * 默认Dialog
     */
    public interface DialogListener {
        void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split);

        void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split);

        void onDismissListener();

    }

    public static void showDefaultDialog(final View viewToken, final String contentText, final String confirmText, final String cancelText, final DialogListener dialogListener) {
        Util_Dialog.dialogByAct(viewToken, R.layout.app_default_dialog, false, false, false, false, new Util_Dialog.ActDialog() {
            TextView tv_confirm, tv_cancel;
            CBAlignTextView tv_content;
            Util_Dialog.DialogContext dialogContext;
            View v_split;
            LinearLayout ll_bg;

            @Override
            public void initDialogData() {
            }

            @Override
            public View initDialogView(Util_Dialog.DialogContext dialogContext, View view) {
                this.dialogContext = dialogContext;
                ll_bg = (LinearLayout) view.findViewById(R.id.ll_bg);
                tv_content = (CBAlignTextView) view.findViewById(R.id.tv_content);
                tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
                tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
                v_split = view.findViewById(R.id.v_split);

           /*     tv_content.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_content.setGravity(tv_content.getLineCount() > 1 ? Gravity.LEFT : Gravity.CENTER);
                    }
                },350);*/

                tv_content.setText(contentText);
                tv_confirm.setText(confirmText);
                tv_cancel.setText(cancelText);
                if (confirmText == null) {
                    tv_confirm.setVisibility(View.GONE);
                    v_split.setVisibility(View.GONE);
                } else if (cancelText == null) {
                    tv_cancel.setVisibility(View.GONE);
                    v_split.setVisibility(View.GONE);
                }
                ll_bg.post(new Runnable() {
                    @Override
                    public void run() {
                        int w = (int) (ll_bg.getMeasuredWidth() / 2.5);
                        tv_cancel.setMinWidth(w);
                        tv_confirm.setMinWidth(w);
                    }
                });
                Util_View.viewOnClick(this, tv_cancel, tv_confirm);
                return view;
            }

            @Override
            public void onDismissListener() {
                if (dialogListener != null)
                    dialogListener.onDismissListener();
            }

            @Override
            public void onClick(View v) {
                if (dialogListener != null)
                    if (v.getId() == R.id.tv_cancel) {
                        dialogListener.onCancel(dialogContext.getDialog(), tv_content, tv_confirm, tv_cancel, v_split);
                    } else if (v.getId() == R.id.tv_confirm) {
                        dialogListener.onConfirm(dialogContext.getDialog(), tv_content, tv_confirm, tv_cancel, v_split);
                    }
            }
        });
    }

}
