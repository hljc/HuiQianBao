package com.ceq.app_core.utils.core;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.lang.reflect.Method;

/**
 * Created by ceq on 2017/5/5.
 */

public class Util_Input {
    /**
     * 输入框小数的位数
     */
    private final int DECIMAL_DIGITS = 2;
    private EditText editText;

    public Util_Input(EditText editText) {
        this.editText = editText;
        editText.setFilters(new InputFilter[]{moneyFilter});
    }

    public static EditText bindMoneyFilter(EditText editText) {
        new Util_Input(editText);
        return editText;
    }

    private InputFilter moneyFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // source:当前输入的字符
            // start:输入字符的开始位置
            // end:输入字符的结束位置
            // dest：当前已显示的内容
            // dstart:当前光标开始位置
            // dent:当前光标结束位置
            Util_Log.logTest("", "source=" + source + ",start=" + start + ",end=" + end
                    + ",dest=" + dest.toString() + ",dstart=" + dstart
                    + ",dend=" + dend);
            if (editText == null)
                return null;
            String num = editText.getText().toString();
            if (num.length() == 0 && source.equals("."))
                return "0.";
            if (num.equals("0"))
                if (source.equals("0"))
                    return "";
           /*     else{
                    //editText.setText("2");
                    return "";
                }*/
            if (num.contains(".")) {
                String[] values = num.split("\\.");
                if (values.length > 1) {
                    String suffix = values[1];
                    Util_Log.logTest(num, suffix);
                    if (suffix.length() >= DECIMAL_DIGITS) {
                        // Util_Toast.toast("小数位最多2位");
                        return "";
                    }
                }
            } else {
                if (!source.equals(".") && num.length() >= 10) {
                    //Util_Toast.toast("整数位最多10位");
                    return "";
                }
            }
            return null;
        }
    };

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);

        void keyBoardHide(int height);
    }

    public static class SoftKeyBoardListener {
        private View rootView;//activity的根视图
        int rootViewVisibleHeight;//纪录根视图的显示高度
        private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

        public SoftKeyBoardListener(Activity activity) {
            //获取activity的根视图
            rootView = activity.getWindow().getDecorView();

            //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //获取当前根视图在屏幕上显示的大小
                    Rect r = new Rect();
                    rootView.getWindowVisibleDisplayFrame(r);
                    int visibleHeight = r.height();
                    if (rootViewVisibleHeight == 0) {
                        rootViewVisibleHeight = visibleHeight;
                        return;
                    }

                    //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                    if (rootViewVisibleHeight == visibleHeight) {
                        return;
                    }

                    //根视图显示高度变小超过200，可以看作软键盘显示了
                    if (rootViewVisibleHeight - visibleHeight > 200) {
                        if (onSoftKeyBoardChangeListener != null) {
                            onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                        }
                        rootViewVisibleHeight = visibleHeight;
                        return;
                    }

                    //根视图显示高度变大超过200，可以看作软键盘隐藏了
                    if (visibleHeight - rootViewVisibleHeight > 200) {
                        if (onSoftKeyBoardChangeListener != null) {
                            onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                        }
                        rootViewVisibleHeight = visibleHeight;
                        return;
                    }

                }
            });
        }

        private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
            this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
        }


        public static void setListener(Activity activity, OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
            SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(activity);
            softKeyBoardListener.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
        }
    }


    //H5键盘防遮挡
    public static void showH5Input(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private static View mChildOfContent;
    private static int usableHeightPrevious;
    private static FrameLayout.LayoutParams frameLayoutParams;


    private static void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private static int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);// 全屏模式下： return r.bottom
    }

    public static class SeparatorTextWatcher implements TextWatcher {
        EditText editText;
        char separator;
        int offset;
        String splitChar;
        int maxLength;

        public SeparatorTextWatcher(EditText editText, char separator, int offset, int maxLength) {
            this.editText = editText;
            this.separator = separator;
            this.offset = offset;
            this.splitChar = String.valueOf(separator);
            this.maxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            String result = s.toString();
            if (count == 1 && result.charAt(result.length() - 1) == separator) {
                StringBuilder sb = new StringBuilder();
                sb.append(result.substring(0, result.lastIndexOf(splitChar) - 1));
                editText.setText(sb.toString());
                editText.setSelection(sb.length());
            } else if (start != 0 && (start + count) % offset == 0 && result.length() > start) {
                result = s.toString();
                StringBuilder sb1 = new StringBuilder();
                if (start != 0) {
                    int position = start - (result.charAt(start) == separator ? 1 : 0);
                    String prefix = result.substring(0, position);
                    String suffix = result.substring(start);
                    sb1.append(prefix).append(suffix);
                }
                StringBuilder sb = new StringBuilder();
                String bankNum = sb1.toString();
                if (bankNum.contains(splitChar))
                    bankNum = bankNum.replace(splitChar, "");
                for (int i = 0, size = bankNum.length(); i < size; i++) {
                    sb.append(bankNum.charAt(i));
                    if ((sb.length() + 1) % offset == 0)
                        sb.append(splitChar);
                }
                editText.setText(sb);
                editText.setSelection(start - (result.charAt(start) == separator ? 1 : 0));
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (before == 0 && count != 0) {
                String result = s.toString();
                StringBuilder sb = new StringBuilder();
                if (result.contains(splitChar))
                    result = result.replace(splitChar, "");
                for (int i = 0, size = result.length(); i < size; i++) {
                    sb.append(result.charAt(i));
                    if ((sb.length() + 1) % offset == 0)
                        sb.append(splitChar);
                }
                String after = sb.toString();
                editText.setText(after);
                int index = start + (after.length() > start + 1 && after.charAt(start + 1) == separator ? 2 : 1);
                editText.setSelection(index > maxLength ? index - 1 : index);
            } else if (before == 1 && count == 0) {
                String result = s.toString();
                StringBuilder sb = new StringBuilder();
                if (result.contains(splitChar))
                    result = result.replace(splitChar, "");
                for (int i = 0, size = result.length(); i < size; i++) {
                    sb.append(result.charAt(i));
                    if ((sb.length() + 1) % offset == 0)
                        sb.append(splitChar);
                }
                String after = sb.toString();
                if (start > 0 && start < after.length() && after.charAt(start) == separator) {
                    int position = start - (after.charAt(start) == separator ? 1 : 0);
                    String prefix = after.substring(0, position);
                    String suffix = after.substring(start + 1);
                    String results = prefix.concat(suffix);

                    StringBuilder sb2 = new StringBuilder();
                    if (results.contains(splitChar))
                        results = results.replace(splitChar, "");
                    for (int i = 0, size = results.length(); i < size; i++) {
                        sb2.append(results.charAt(i));
                        if ((sb2.length() + 1) % offset == 0)
                            sb2.append(splitChar);
                    }
                    editText.setText(sb2);
                    editText.setSelection(position);
                } else {
                    editText.setText(after);
                    editText.setSelection(start);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    //获取虚拟按键的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    public static class AndroidBug5497Workaround {
        public static void assistActivity(Activity activity) {
            new AndroidBug5497Workaround(activity);
        }

        private View mChildOfContent;
        private int usableHeightPrevious;
        private FrameLayout.LayoutParams frameLayoutParams;
        private int contentHeight;
        private boolean isfirst = true;
        private Activity activity;
        private int statusBarHeight;

        private AndroidBug5497Workaround(Activity activity) {
            //获取状态栏的高度
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
            this.activity = activity;
            FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
            mChildOfContent = content.getChildAt(0);

            //界面出现变动都会调用这个监听事件
            mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    if (isfirst) {
                        contentHeight = mChildOfContent.getHeight();//兼容华为等机型
                        isfirst = false;
                    }
                    possiblyResizeChildOfContent();
                }
            });

            frameLayoutParams = (FrameLayout.LayoutParams)
                    mChildOfContent.getLayoutParams();
        }

        //重新调整跟布局的高度
        private void possiblyResizeChildOfContent() {

            int usableHeightNow = computeUsableHeight();

            //当前可见高度和上一次可见高度不一致 布局变动
            if (usableHeightNow != usableHeightPrevious) {
                //int usableHeightSansKeyboard2 = mChildOfContent.getHeight();//兼容华为等机型
                int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
                int heightDifference = usableHeightSansKeyboard - usableHeightNow;
                if (heightDifference > (usableHeightSansKeyboard / 4)) {
                    // keyboard probably just became visible
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        //frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                        frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarHeight;
                    } else {
                        frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                    }
                } else {
                    frameLayoutParams.height = contentHeight;
                }

                mChildOfContent.requestLayout();
                usableHeightPrevious = usableHeightNow;
            }
        }

        /**
         * 计算mChildOfContent可见高度     ** @return
         */
        private int computeUsableHeight() {
            Rect r = new Rect();
            mChildOfContent.getWindowVisibleDisplayFrame(r);
            return (r.bottom - r.top);
        }

    }
}


