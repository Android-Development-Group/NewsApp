package com.myself.library.view.scroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * RecyclerView内部嵌套的ScrollView
 * Created by guchenkai on 2016/1/15.
 */
public class InnerScrollView extends ScrollView {
    private RecyclerView mParentRecyclerView;
    private int lastScrollDelta = 0;
    int mTop = 10;

    public InnerScrollView(Context context) {
        super(context, null, 0);
    }

    public InnerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public InnerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void resume() {
        overScrollBy(0, -lastScrollDelta, 0, getScrollY(), 0, getScrollRange(), 0, 0, true);
        lastScrollDelta = 0;
    }

    public void scrollTo(View targetView) {
        int oldScrollY = getScrollY();
        int top = targetView.getTop() - mTop;
        int delatY = top - oldScrollY;
        lastScrollDelta = delatY;
        overScrollBy(0, delatY, 0, getScrollY(), 0, getScrollRange(), 0, 0, true);
    }

    private int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            scrollRange = Math.max(0, child.getHeight() - (getHeight()));
        }
        return scrollRange;
    }

    int currentY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mParentRecyclerView == null) {
            return super.onInterceptTouchEvent(ev);
        } else {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                // 将父scrollview的滚动事件拦截
                currentY = (int) ev.getY();
                setParentScrollAble(false);
                return super.onInterceptTouchEvent(ev);
            } else if (ev.getAction() == MotionEvent.ACTION_UP) {
                // 把滚动事件恢复给父Scrollview
                setParentScrollAble(true);
            } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            }
        }
        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        View child = getChildAt(0);
        if (mParentRecyclerView != null) {
            if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                int height = child.getMeasuredHeight();
                height = height - getMeasuredHeight();

                // System.out.println("height=" + height);
                int scrollY = getScrollY();
                // System.out.println("scrollY" + scrollY);
                int y = (int) ev.getY();

                // 手指向下滑动
                if (currentY < y) {
                    if (scrollY <= 0) {
                        // 如果向下滑动到头，就把滚动交给父Scrollview
                        setParentScrollAble(true);
                        return false;
                    } else {
                        setParentScrollAble(false);
                    }
                } else if (currentY > y) {
                    if (scrollY >= height) {
                        // 如果向上滑动到头，就把滚动交给父Scrollview
                        setParentScrollAble(true);
                        return false;
                    } else {
                        setParentScrollAble(false);
                    }
                }
                currentY = y;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void setParentScrollAble(boolean flag) {

        mParentRecyclerView.requestDisallowInterceptTouchEvent(!flag);
    }
}
