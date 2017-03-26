package com.myself.library.view.recycler;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.myself.library.R;

/**
 * 为RecyclerView添加头部
 * Created by guchenkai on 2015/11/27.
 */
public class RecyclerViewHeader extends RelativeLayout {
    public static final String TAG = "RecyclerViewHeader";
    private RecyclerView mRecyclerView;

    private int mDownScroll;
    private int mCurrentScroll;
    private boolean mReversed;
    private boolean mAlreadyAligned;
    private boolean mRecyclerWantsTouchEvent;
    private boolean isAdd = false;

    /**
     * 读取额外的布局添加头部
     *
     * @param context   context
     * @param layoutRes 布局id
     * @return RecyclerViewHeader实例
     */
    public static RecyclerViewHeader fromXml(Context context, @LayoutRes int layoutRes) {
        RecyclerViewHeader header = new RecyclerViewHeader(context);
        View.inflate(context, layoutRes, header);
        return header;
    }

    public RecyclerViewHeader(Context context) {
        super(context);
    }

    public RecyclerViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void attachTo(RecyclerView recyclerView) {
        attachTo(recyclerView, false);
    }

    public void attachTo(RecyclerView recyclerView, boolean headerAlreadyAligned) {
        validateRecyclerView(recyclerView, headerAlreadyAligned);
        mRecyclerView = recyclerView;
        mAlreadyAligned = headerAlreadyAligned;
        mReversed = isLayoutManagerReversed(recyclerView);

        setupAlignment(recyclerView);
        setupHeader(recyclerView);

        isAdd = true;
    }

    public boolean isAdd() {
        return isAdd;
    }

    private boolean isLayoutManagerReversed(RecyclerView recyclerView) {
        boolean reversed = false;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager)
            reversed = ((LinearLayoutManager) layoutManager).getReverseLayout();
        else if (layoutManager instanceof StaggeredGridLayoutManager)
            reversed = ((StaggeredGridLayoutManager) layoutManager).getReverseLayout();
        return reversed;
    }

    private void setupAlignment(RecyclerView recyclerView) {
        if (!mAlreadyAligned) {
            ViewGroup.LayoutParams currentParams = getLayoutParams();
            FrameLayout.LayoutParams newHeaderParams = null;
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            int gravity = (mReversed ? Gravity.BOTTOM : Gravity.TOP) | Gravity.CENTER_HORIZONTAL;
            if (currentParams != null) {
                newHeaderParams = new FrameLayout.LayoutParams(getLayoutParams());
                newHeaderParams.width = width;
                newHeaderParams.height = height;
                newHeaderParams.gravity = gravity;
            } else {
                newHeaderParams = new FrameLayout.LayoutParams(width, height, gravity);
            }
            setLayoutParams(newHeaderParams);

            FrameLayout newRootParent = new FrameLayout(recyclerView.getContext());
            ViewParent currentParent = recyclerView.getParent();
            if (currentParent instanceof ViewGroup) {
                int indexWithinParent = ((ViewGroup) currentParent).indexOfChild(recyclerView);
                ((ViewGroup) currentParent).removeViewAt(indexWithinParent);
                recyclerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                newRootParent.addView(recyclerView);
                newRootParent.addView(this);
                ((ViewGroup) currentParent).addView(newRootParent, indexWithinParent);
            }
        }
    }

    private void setupHeader(final RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentScroll += dy;
                RecyclerViewHeader.this.setTranslationY(-mCurrentScroll);
            }
        });
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                int height = RecyclerViewHeader.this.getHeight();
                int height = getRealHeight();
                Log.i(TAG, "头部真实高度=" + height);
                if (height > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        RecyclerViewHeader.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    else
                        RecyclerViewHeader.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    if (mAlreadyAligned) {
                        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
                        height += params.topMargin;
                        height += params.bottomMargin;
                    }
                    recyclerView.addItemDecoration(new HeaderItemDecoration(recyclerView.getLayoutManager(), height), 0);
                }
            }
        });
    }

    /**
     * 计算真实高度
     *
     * @return 真实高度
     */
    private int getRealHeight() {
        int height = 0;
        View view = getChildAt(0);
        if (view instanceof ScrollView)
            for (int i = 0; i < ((ScrollView) view).getChildCount(); i++) {
                View child = ((ScrollView) view).getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout || child instanceof FrameLayout) {
                    child.setBackgroundResource(R.color.transparent);
                    height += child.getHeight();
                }
            }
        else if (view instanceof LinearLayout)
            for (int i = 0; i < ((LinearLayout) view).getChildCount(); i++) {
                View child = ((LinearLayout) view).getChildAt(i);
                if (child instanceof WebView)
                    height += ((WebView) child).getContentHeight();
                else
                    height += child.getHeight();
            }
        else
            height = this.getHeight();
        return height;
    }

    /**
     * 验证RecyclerView
     *
     * @param recyclerView         recyclerView
     * @param headerAlreadyAligned headerAlreadyAligned
     */
    private void validateRecyclerView(RecyclerView recyclerView, boolean headerAlreadyAligned) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null)
            throw new IllegalStateException("Be sure to call RecyclerViewHeader constructor after setting your RecyclerView's LayoutManager.");
        else if (layoutManager.getClass() != LinearLayoutManager.class    //not using instanceof on purpose
                && layoutManager.getClass() != GridLayoutManager.class
                && !(layoutManager instanceof StaggeredGridLayoutManager))
            throw new IllegalArgumentException("Currently RecyclerViewHeader supports only LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager.");
        if (layoutManager instanceof LinearLayoutManager)
            if (((LinearLayoutManager) layoutManager).getOrientation() != LinearLayoutManager.VERTICAL)
                throw new IllegalArgumentException("Currently RecyclerViewHeader supports only VERTICAL orientation LayoutManagers.");
            else if (layoutManager instanceof StaggeredGridLayoutManager)
                if (((StaggeredGridLayoutManager) layoutManager).getOrientation() != StaggeredGridLayoutManager.VERTICAL)
                    throw new IllegalArgumentException("Currently RecyclerViewHeader supports only VERTICAL orientation StaggeredGridLayoutManagers.");

        if (!headerAlreadyAligned) {
            ViewParent parent = recyclerView.getParent();
            if (parent != null &&
                    !(parent instanceof LinearLayout) &&
                    !(parent instanceof RelativeLayout) &&
                    !(parent instanceof FrameLayout))
                throw new IllegalStateException("Currently, NOT already aligned RecyclerViewHeader " +
                        "can only be used for RecyclerView with a parent of one of types: LinearLayout, FrameLayout, RelativeLayout.");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mRecyclerWantsTouchEvent = mRecyclerView.onInterceptTouchEvent(ev);
        if (mRecyclerWantsTouchEvent && ev.getAction() == MotionEvent.ACTION_DOWN)
            mDownScroll = mCurrentScroll;
        return mRecyclerWantsTouchEvent || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mRecyclerWantsTouchEvent) {
            int scrollDiff = mCurrentScroll - mDownScroll;
            MotionEvent recyclerEvent = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(),
                    event.getX(), event.getY() - scrollDiff, event.getMetaState());
            mRecyclerView.onTouchEvent(recyclerEvent);
            return false;
        }
        return super.onTouchEvent(event);
    }

    /**
     *
     */
    private class HeaderItemDecoration extends RecyclerView.ItemDecoration {
        private int mHeaderHeight;
        private int mNumberOfChildren;

        public HeaderItemDecoration(RecyclerView.LayoutManager layoutManager, int height) {
            if (layoutManager instanceof LinearLayoutManager)
                mNumberOfChildren = 1;
            else if (layoutManager instanceof GridLayoutManager)
                mNumberOfChildren = ((GridLayoutManager) layoutManager).getSpanCount();
            else if (layoutManager instanceof StaggeredGridLayoutManager)
                mNumberOfChildren = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            mHeaderHeight = height;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int value = (parent.getChildLayoutPosition(view) < mNumberOfChildren) ? mHeaderHeight : 0;
            if (mReversed)
                outRect.bottom = value;
            else
                outRect.top = value;
        }
    }
}
