package com.ceq.app_core.utils.core;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;


/**
 * Created by Administrator on 2016/4/23.
 */
public class Util_Animation {
    public enum AnimationTranslateOrientation {
        ANIMATION_TRANSLATE_HORIZONTAL, ANIMATION_TRANSLATE_VERTICAL
    }

    public static void animationToTranslate(View view, AnimationTranslateOrientation animationTranslateOrientation, long durationMillis, float beginValue, float endValue, Animation.AnimationListener listener) {
        TranslateAnimation ta = null;
        switch (animationTranslateOrientation) {
            case ANIMATION_TRANSLATE_HORIZONTAL:
                ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, beginValue, Animation.RELATIVE_TO_SELF, endValue, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                break;
            case ANIMATION_TRANSLATE_VERTICAL:
                ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, beginValue, Animation.RELATIVE_TO_SELF, endValue);
                break;
        }
        initAnimation(view, ta, durationMillis, listener, 0);
    }

    public static void animationToScale(View view, long durationMillis, float fromX, float toX, float fromY, float toY, Animation.AnimationListener listener) {
        initAnimation(view, new ScaleAnimation(fromX, toX, fromY, toY, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f), durationMillis, listener, 0);

    }

    public static void animationToAlpha(View view, long durationMillis, float beginAlpha, float endAlpha, Animation.AnimationListener listener) {
        animationToAlpha(view, durationMillis, beginAlpha, endAlpha, 0, listener);
    }

    public static void animationToAlpha(View view, long durationMillis, float beginAlpha, float endAlpha, int repeatCount, Animation.AnimationListener listener) {
        initAnimation(view, new AlphaAnimation(beginAlpha, endAlpha), durationMillis, listener, repeatCount);
    }

    public static void animationToRotate(View view, long durationMillis, float beginDegrees, float endDegrees, int repeatCount, Animation.AnimationListener listener) {
        initAnimation(view, new RotateAnimation(beginDegrees, endDegrees, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f), durationMillis, listener, repeatCount);
    }

    private static void initAnimation(View view, Animation animation, long durationMillis, Animation.AnimationListener animationListener, int repeatCount) {
        if (view == null)
            return;
        animation.setFillAfter(false);
        animation.setDuration(durationMillis);
        animation.setAnimationListener(animationListener);
        animation.setRepeatCount(repeatCount);
        view.startAnimation(animation);
    }
}
