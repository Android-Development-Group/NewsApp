package com.myself.library.view.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * 嵌套RecyClerView使用的ScrollView
 * Created by guchenkai on 2016/1/15.
 */
public class NestScrollView extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;

    public NestScrollView(Context context) {
        this(context, null, 0);
    }

    public NestScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop)
                    return true;
        }
        return super.onInterceptTouchEvent(e);
    }
}
