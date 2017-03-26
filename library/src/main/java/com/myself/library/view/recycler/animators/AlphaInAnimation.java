package com.myself.library.view.recycler.animators;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * 渐变进入动画
 * Created by guchenkai on 2016/1/20.
 */
public class AlphaInAnimation implements IAnimation {
    private final float DEFAULT_ALPHA_FROM = 0.0F;
    private final float mFrom;

    public AlphaInAnimation() {
        mFrom = DEFAULT_ALPHA_FROM;
    }

    public AlphaInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "alpha", mFrom, 1.0F)
        };
    }
}
