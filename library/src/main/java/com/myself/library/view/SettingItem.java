package com.myself.library.view;

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
 * 设置条目按钮
 * Created by guchenkai on 2015/11/26.
 */
public class SettingItem extends LinearLayout {
    private static final int ENUM_NONE = 0;
    private static final int ENUM_TOP = 1;
    private static final int ENUM_BOTTOM = 2;
    private View mRootView;
    private LinearLayout mMainView;
    private ImageView mSettingIconView;
    private TextView mSettingTextView;
    private View mRedDot;

    private Drawable mSettingIcon;
    private String mSettingText;
    private int mSettingDivider;
    private int mSettingDividerColor;
    private Drawable mSettingIndicatorBackground;
    private int mSettingIndicatorColor;

    private BadgeView mIndicator;

    public SettingItem(Context context) {
        this(context, null);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleable(context, attrs);
        initView(context);
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingItem);
        mSettingIcon = array.getDrawable(R.styleable.SettingItem_setting_icon);
        mSettingText = array.getString(R.styleable.SettingItem_setting_text);
        mSettingDivider = array.getInt(R.styleable.SettingItem_setting_divider, -1);
        mSettingDividerColor = array.getColor(R.styleable.SettingItem_setting_divider_color, -1);
        mSettingIndicatorBackground = array.getDrawable(R.styleable.SettingItem_setting_indicator_background);
        mSettingDividerColor = array.getColor(R.styleable.SettingItem_setting_indicator_color, -1);
        mSettingIndicatorColor = array.getColor(R.styleable.SettingItem_setting_indicator_color, -1);
        array.recycle();
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_setting_item, this);
        mMainView = (LinearLayout) mRootView.findViewById(R.id.ll_main);
        mSettingIconView = (ImageView) mRootView.findViewById(R.id.setting_icon);
        mSettingTextView = (TextView) mRootView.findViewById(R.id.setting_text);
        mRedDot = mRootView.findViewById(R.id.red_dot);
        View dividerTop = mRootView.findViewById(R.id.divider_top);
        View dividerBottom = mRootView.findViewById(R.id.divider_bottom);
        mSettingIconView.setImageDrawable(mSettingIcon);
        mSettingTextView.setText(mSettingText);
        if (mSettingDividerColor != -1) {
            dividerTop.setBackgroundColor(mSettingDividerColor);
            dividerBottom.setBackgroundColor(mSettingDividerColor);
        }
        if (mSettingIcon == null)
            mSettingIconView.setVisibility(GONE);
        switch (mSettingDivider) {
            case ENUM_NONE:
                dividerTop.setVisibility(GONE);
                dividerBottom.setVisibility(GONE);
                break;
            case ENUM_TOP:
                dividerBottom.setVisibility(GONE);
                break;
            case ENUM_BOTTOM:
                dividerTop.setVisibility(GONE);
                break;
        }
    }

    /**
     * 显示指示
     */
    public void show() {
        /*mIndicator = new BadgeView(getContext(), mMainView);
        mIndicator.setBadgePosition(BadgeView.POSITION_CENTER_RIGHT);
        mIndicator.setBadgeMargin(100);
        mIndicator.setBackground(mSettingIndicatorBackground);
        mIndicator.setText(String.valueOf(count));
        if (mSettingIndicatorColor != -1)
            mIndicator.setTextColor(mSettingIndicatorColor);
        mIndicator.show();*/

        mRedDot.setVisibility(VISIBLE);
        postInvalidate();

    }

    /**
     * 隐藏指示
     */
    public void hide() {
        /*if (mIndicator != null && mIndicator.isShown())
            mIndicator.hide();*/
        mRedDot.setVisibility(GONE);
    }
}
