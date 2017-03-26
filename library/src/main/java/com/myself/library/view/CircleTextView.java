package com.myself.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.widget.TextView;

import com.myself.library.R;

/**
 * 圆形的TextView
 * Created by guchenkai on 2016/1/14.
 */
public class CircleTextView extends TextView {
    private Paint mBgPaint = new Paint();
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    private int mBgColor;

    public CircleTextView(Context context) {
        this(context, null, 0);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView);
        mBgColor = array.getColor(R.styleable.CircleTextView_background_color, -1);
        array.recycle();

        if (mBgColor != -1)
            mBgPaint.setColor(mBgColor);
        mBgPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();
        int max = Math.max(measureWidth, measureHeight);
        setMeasuredDimension(max, max);
    }

    @Override
    public void setBackgroundColor(int color) {
        // TODO Auto-generated method stub
        mBgPaint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.setDrawFilter(mPaintFlagsDrawFilter);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2, mBgPaint);
        super.draw(canvas);
    }
}
