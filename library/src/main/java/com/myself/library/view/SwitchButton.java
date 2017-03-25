package com.myself.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.myself.library.R;

/**
 * 开关按钮
 * Created by guchenkai on 2015/11/5.
 */
public class SwitchButton extends Button implements View.OnClickListener {
    private boolean isOpen;
    private Drawable mOpenDrawable, mCloseDrawable;
    private OnSwitchClickListener mOnSwitchClickListener;

    public SwitchButton(Context context) {
        this(context, null, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleable(context, attrs);
        init();
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        isOpen = a.getBoolean(R.styleable.SwitchButton_isSelect, false);
        mOpenDrawable = a.getDrawable(R.styleable.SwitchButton_selDrawable);
        mCloseDrawable = a.getDrawable(R.styleable.SwitchButton_norDrawable);
        a.recycle();
    }

    private void init() {
        setState(isOpen);
        setOnClickListener(this);
    }

    public void setCloseDrawable(Drawable closeDrawable) {
        mCloseDrawable = closeDrawable;
    }

    public void setOpenDrawable(Drawable OpenDrawable) {
        mOpenDrawable = OpenDrawable;
    }

    /**
     * 设置是否打开
     *
     * @param isOpen
     */
    public void setState(boolean isOpen) {
        if (isOpen)
            setBackground(mOpenDrawable);
        else
            setBackground(mCloseDrawable);
    }

    public void setOnSwitchClickListener(OnSwitchClickListener onSwitchClickListener) {
        this.mOnSwitchClickListener = onSwitchClickListener;
    }

    @Override
    public void onClick(View v) {
        isOpen = isOpen ? false : true;
        setState(isOpen);
        if (mOnSwitchClickListener != null)
            mOnSwitchClickListener.onSwitchClick(v, isOpen);
    }

    public boolean getState() {
        return isOpen;
    }

    /**
     *
     */
    public interface OnSwitchClickListener {

        void onSwitchClick(View v, boolean isSelect);
    }
}
