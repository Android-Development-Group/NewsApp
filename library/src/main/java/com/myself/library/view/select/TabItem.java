package com.myself.library.view.select;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.myself.library.R;

/**
 * 切换工具条上的按钮,配合TabBar使用
 * Created by guchenkai on 2015/11/24.
 */
public class TabItem extends View {
    private static final String TAG = TabItem.class.getSimpleName();
    private boolean mActive = false;//是否活动状态
    private Drawable mActiveDrawable;
    private Drawable mInActiveDrawable;

    private Drawable mCurrentDrawable;//当前Drawable

    private int mIndicatorRadius = 0;
    private int mNotificationSize = 0;
    private IndicatorDrawable mIndicator;

    public TabItem(Context context) {
        this(context, null, 0);
    }

    public TabItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleable(context, attrs);
        setActive(mActive);
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabItem);
        mIndicatorRadius = array.getInt(R.styleable.TabItem_indicator_radius, 4);
        mActive = array.getBoolean(R.styleable.TabItem_active, false);
        mActiveDrawable = array.getDrawable(R.styleable.TabItem_activeDrawable);
        mInActiveDrawable = array.getDrawable(R.styleable.TabItem_inactiveDrawable);
        array.recycle();
    }

    private void updateIndicator(Context context) {
        if (mNotificationSize == -1)
            mIndicator = new IndicatorDrawable(context, true, mIndicatorRadius);
        if (mNotificationSize != 0 && mIndicator == null)
            mIndicator = new IndicatorDrawable(context);
        if (mIndicator != null)
            mIndicator.setSize(mNotificationSize);
    }

    public void show(int notificationSize) {
        mNotificationSize = notificationSize;
        updateIndicator(getContext());
        postInvalidate();
    }

    public void hide() {
        mNotificationSize = 0;
        mIndicator = null;
        postInvalidate();
    }

    public void setActive(boolean active) {
        mActive = active;
        mCurrentDrawable = active ? mActiveDrawable : mInActiveDrawable;
        postInvalidate();
    }

    public boolean getActive() {
        return mActive;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int drawableWidth = mCurrentDrawable.getIntrinsicWidth();
        final int drawableHeight = mCurrentDrawable.getIntrinsicHeight();
        final int measuredWidth = getMeasuredWidth();
        final int measuredHeight = getMeasuredHeight();

        canvas.save();
        canvas.translate(measuredWidth / 2 - drawableWidth / 2, measuredHeight / 2 - drawableHeight / 2);
        mCurrentDrawable.setBounds(0, 0, mCurrentDrawable.getIntrinsicWidth(), mCurrentDrawable.getIntrinsicHeight());
        mCurrentDrawable.draw(canvas);
        canvas.restore();

        if (mNotificationSize != 0) {
            canvas.save();
            canvas.translate(measuredWidth / 2 + drawableWidth / 2 - mIndicator.getIntrinsicWidth() - 20, 15);
//            mIndicator.setBounds(0, 50, 30/*mIndicator.getIntrinsicWidth()*/, 0/* mIndicator.getIntrinsicHeight()*/);
            mIndicator.draw(canvas);
            canvas.restore();
        }
    }
}
