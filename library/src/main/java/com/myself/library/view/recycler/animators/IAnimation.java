package com.myself.library.view.recycler.animators;

import android.animation.Animator;
import android.view.View;

/**
 * 动画组接口
 * Created by guchenkai on 2016/1/20.
 */
public interface IAnimation {

    Animator[] getAnimators(View view);
}
