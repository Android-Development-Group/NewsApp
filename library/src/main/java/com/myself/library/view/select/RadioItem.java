package com.myself.library.view.select;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myself.library.R;

/**
 * 单选布局Item
 * Created by guchenkai on 2015/12/10.
 */
public class RadioItem extends LinearLayout {
    private Drawable mSelectDrawable;
    private String mText;
    private int mTextSize;
    private int mTextColor;
    private boolean isSelect;

    private View mRootView;
    private TextView mTextView;
    private ImageView mImageView;
    private View divider;

    public RadioItem(Context context) {
        this(context, null, 0);
    }

    public RadioItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleable(context, attrs);
        initView(context);
        init();
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RadioItem);
        mSelectDrawable = array.getDrawable(R.styleable.RadioItem_select_drawable);
        mText = array.getString(R.styleable.RadioItem_select_text);
        mTextSize = array.getDimensionPixelSize(R.styleable.RadioItem_select_text_size, -1);
        mTextColor = array.getColor(R.styleable.RadioItem_select_text_color, -1);
        isSelect = array.getBoolean(R.styleable.RadioItem_is_select, false);
        array.recycle();
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_radio_item, this);
        mTextView = (TextView) mRootView.findViewById(R.id.tv_name);
        mImageView = (ImageView) mRootView.findViewById(R.id.iv_select_icon);
        divider = mRootView.findViewById(R.id.divider);
    }

    private void init() {
        mImageView.setImageDrawable(mSelectDrawable);
        mTextView.setText(mText);
        if (mTextColor != -1)
            mTextView.setTextColor(mTextColor);
        if (mTextSize != -1)
            mTextView.setTextSize(mTextSize);
        setSelect(isSelect);
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
        mImageView.setVisibility(isSelect ? VISIBLE : GONE);
    }

    public String getText() {
        return mText;
    }
}
