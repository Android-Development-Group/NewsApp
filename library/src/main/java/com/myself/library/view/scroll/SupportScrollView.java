package com.myself.library.view.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author xiaanming
 */
public class SupportScrollView extends ScrollView {
    public OnScrollListener onScrollListener;

    public SupportScrollView(Context context) {
        this(context, null);
    }

    public SupportScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SupportScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置滚动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }

    /**
     * 滚动的回调接口
     *
     * @author xiaanming
     */
    public interface OnScrollListener {
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         *
         * @param scrollY 、
         */
        public void onScroll(int scrollY);
    }
}