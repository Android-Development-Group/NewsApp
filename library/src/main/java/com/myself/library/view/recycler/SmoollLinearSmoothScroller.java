package com.myself.library.view.recycler;


import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;

/**
 * 慢滚动
 */
public class SmoollLinearSmoothScroller extends LinearSmoothScroller {
    private LinearLayoutManager mLinearLayoutManager;

    public SmoollLinearSmoothScroller(Context context, LinearLayoutManager linearLayoutManager) {
        super(context);
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        return mLinearLayoutManager.computeScrollVectorForPosition(targetPosition);
    }

    @Override
    protected int calculateTimeForScrolling(int dx) {
        return super.calculateTimeForScrolling(dx) * 10;
    }
}
