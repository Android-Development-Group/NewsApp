package com.myself.library.view.select;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * 指示小红点
 * Created by guchenkai on 2015/12/1.
 */
public class IndicatorDrawable extends Drawable {
    private static final String TAG = IndicatorDrawable.class.getSimpleName();
    private int mSize;
    private int mRadius = 0;
    private Rect mTextBounds;
    private float mDensityDpi;
    private Paint mPaint;
    private boolean isShowSize = false;

    private int dp(float dp) {
        return (int) (mDensityDpi * dp);
    }

    public IndicatorDrawable(Context context) {
        mTextBounds = new Rect();
        mDensityDpi = context.getResources().getDisplayMetrics().density;
        mRadius = dp(8);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(dp(8));
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public IndicatorDrawable(Context context, boolean isShowSize, float radius) {
        this(context);
        mRadius = dp(radius);
        this.isShowSize = isShowSize;
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(0xffed5564);
        mPaint.setStrokeWidth(1);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        if (!isShowSize) {
            String text = String.valueOf(mSize);
            mPaint.getTextBounds(text, 0, text.length(), mTextBounds);
//            Logger.d(TAG, "text bounds : " + mTextBounds.width() + ",  " + mTextBounds.height() + "rect : " + mTextBounds);
            mPaint.setColor(0xffffffff);
            canvas.drawText(text, mRadius, mRadius + mTextBounds.height() / 2, mPaint);
        }
    }

    @Override
    public int getIntrinsicWidth() {
        return mRadius * 2;
    }

    @Override
    public int getIntrinsicHeight() {
        return mRadius * 2;
    }


    public void setSize(int size) {
        mSize = size;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }
}
