package com.myself.library.view.sticky;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.myself.library.R;

/**
 * 粘性头部
 * Created by guchenkai on 2015/11/6.
 */
public class StickyHeaderLayout extends ScrollableLayout {
    private View mHeaderView;//头部
    private View mStickyView;//粘滞头部
    private View mScrollableView;//底部滚动视图

    private View mMinusView;

    private int maxScrollY;

    public StickyHeaderLayout(Context context) {
        this(context, null, 0);
    }

    public StickyHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMinusView(View minusView) {
        mMinusView = minusView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = findViewById(R.id.stickyHeaderLayout_header);
        mStickyView = findViewById(R.id.stickyHeaderLayout_sticky);
        mScrollableView = findViewById(R.id.stickyHeaderLayout_scrollable);

        setDraggableView(mStickyView);
        setFriction(0.1f);

        setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {
                float stickyTranslationY = 0f;
                if (y >= maxY)
                    stickyTranslationY = y - maxY;
                mStickyView.setTranslationY(stickyTranslationY);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mMinusView != null)
            maxScrollY = mHeaderView.getMeasuredHeight() - mMinusView.getMeasuredHeight();
        else
            maxScrollY = mHeaderView.getMeasuredHeight();
        setMaxScrollY(maxScrollY);
    }

    public void canScrollView() {
        setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return mScrollableView != null && mScrollableView.canScrollVertically(direction);
            }
        });
    }

    public View getStickyView() {
        return mStickyView;
    }
}
