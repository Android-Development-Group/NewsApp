package com.myself.library.view.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.myself.library.utils.DensityUtils;


/**
 * 左滑出删除菜单
 * Created by riven_chris on 16/7/11.
 */
public class ItemDeleteLoadMoreRecyclerView extends LoadMoreRecyclerView {
    final String TAG = this.getClass().getSimpleName();

    private VelocityTracker mTracker;
    private int mVelocity;

    private View currentView;
    private View itemView;
    private float mDownX, mDownY, mOffsetX, mOffsetY;
    private int mScrollWidth = 0;
    private boolean shouldScrollBack = false;

    private boolean isHorizontalScroll = false;

    public ItemDeleteLoadMoreRecyclerView(Context context) {
        this(context, null, 0);
    }

    public ItemDeleteLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemDeleteLoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 删除view布局的width(不设置不可滑动)
     *
     * @param width
     */
    public void setMaxScrollWidth(int width) {
        mScrollWidth = width;
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (currentView != null) {
            View nextItem = findChildViewUnder(ev.getX(), ev.getY());
            if (nextItem != null && (getLayoutManager().getPosition(currentView)
                    != getLayoutManager().getPosition(nextItem) ||
                    ev.getX() < (DensityUtils.getScreenW(getContext()) - mScrollWidth))) {
                shouldScrollBack = true;
            }
            if (MotionEvent.ACTION_UP == ev.getAction()) {
                currentView.scrollTo(0, 0);
                currentView = null;
            }
            isHorizontalScroll = false;
        } else {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    shouldScrollBack = false;
                    isHorizontalScroll = false;
                    itemView = findChildViewUnder(ev.getX(), ev.getY());
                    if (itemView == null)
                        break;
                    initVelocityTrackerIsNotExists();
                    mTracker.clear();
                    mTracker.addMovement(ev);
                    mDownX = ev.getX();
                    mDownY = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (itemView == null)
                        break;
                    int scrollX = itemView.getScrollX();
//                Log.d(TAG, "scrollX: " + scrollX);
                    mOffsetX = mDownX - ev.getX();
                    mOffsetY = mDownY - ev.getY();
                    if ((mOffsetX < 0 && scrollX > 0) ||
                            (mOffsetX > 0 && scrollX < mScrollWidth)) {
                        Log.d(TAG, "mOffsetX - mOffsetY: " + (Math.abs(mOffsetX) - Math.abs(mOffsetY)));
                        if (Math.abs(mOffsetX) - Math.abs(mOffsetY) > 2) {
                            isHorizontalScroll = true;
                            int scrollByX = mOffsetX > 0 ? Math.min((int) mOffsetX, mScrollWidth - scrollX) :
                                    Math.max((int) mOffsetX, -scrollX);
//                        Log.d(TAG, "mOffsetX:" + mOffsetX + ", scrollByX: " + scrollByX);
                            itemView.scrollBy(scrollByX, 0);
                        }
                        mDownX = ev.getX();
                        mDownY = ev.getY();
                    }
                    mTracker.addMovement(ev);
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (itemView == null || !isHorizontalScroll || mTracker == null)
                        break;
                    mTracker.computeCurrentVelocity(100);
                    mVelocity = (int) mTracker.getXVelocity();
                    if (mVelocity > 100) {
                        itemView.scrollTo(0, 0);
                        currentView = null;
                    } else if (mVelocity < -100) {
                        itemView.scrollTo(mScrollWidth, 0);
                        currentView = itemView;
                    } else {
                        int lastScrollX = itemView.getScrollX();
                        if (lastScrollX > (mScrollWidth / 2)) {
                            itemView.scrollBy(mScrollWidth - lastScrollX, 0);
                            currentView = itemView;
                        } else {
                            itemView.scrollTo(0, 0);
                            currentView = null;
                        }
                    }
                    itemView = null;
                    recycleTracker();
                    break;
            }
        }
        return (shouldScrollBack || isHorizontalScroll) ? true : super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    private void initVelocityTrackerIsNotExists() {
        if (mTracker == null)
            mTracker = VelocityTracker.obtain();
    }

    public void recycleTracker() {
        if (mTracker != null) {
            mTracker.recycle();
            mTracker = null;
        }
    }

}
