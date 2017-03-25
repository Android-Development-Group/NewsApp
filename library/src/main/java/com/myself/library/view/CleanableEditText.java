package com.myself.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AutoCompleteTextView;

import com.myself.library.R;
import com.myself.library.utils.StringUtils;

/**
 * 在焦点变化时和输入内容发生变化时均要判断是否显示右边clean图标
 * Created by guchenkai on 2015/11/3.
 */
public class CleanableEditText extends AutoCompleteTextView implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mRightDrawable;
    private boolean isHasFocus;
    private int maxLength;

    public CleanableEditText(Context context) {
        super(context);
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStyteable(context, attrs);
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyteable(context, attrs);
        init();
    }

    private void initStyteable(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CleanableEditText);
        maxLength = a.getInteger(R.styleable.CleanableEditText_maxLength, Integer.MAX_VALUE);
        a.recycle();
    }

    private void init() {
        Drawable[] drawables = getCompoundDrawables();
        mRightDrawable = drawables[2];
        if (mRightDrawable == null)
            if (isInEditMode())
                mRightDrawable = getResources().getDrawable(android.R.drawable.presence_offline);
            else
                mRightDrawable = getResources().getDrawableForDensity(android.R.drawable.presence_offline,
                        getResources().getDisplayMetrics().densityDpi);
        setOnFocusChangeListener(this);//设置焦点变化的监听
        addTextChangedListener(this);//设置EditText文字变化的监听
        setClearDrawableVisible(false);//初始化时让右边clean图标不可见
    }

    public void setMaxLenght(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isClear = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getY() < (getWidth() - getPaddingRight()));
                if (isClear) setText("");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        isHasFocus = hasFocus;
        if (isHasFocus) {
            boolean isVisible = getText().length() >= 1;
            setClearDrawableVisible(isVisible);
        } else {
            setClearDrawableVisible(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        Editable editable = getText();
        int len = editable.length();
        if (len > maxLength) {
            int selEndIndex = Selection.getSelectionEnd(editable);
            String str = editable.toString();
            //截取字符串
            String newStr = str.substring(0, maxLength);
            setText(newStr);
            editable = getText();
            //新字符串的长度
            int newLen = editable.length();
            if (selEndIndex > newLen)//旧光标位置超过字符串长度
                selEndIndex = editable.length();
            Selection.setSelection(editable, selEndIndex);
            setSelection(newLen);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean isVisible = getText().length() >= 1;
        setClearDrawableVisible(isVisible);
    }

    /**
     * 隐藏或者显示右边clean的图标
     *
     * @param isVisible 是否显示
     */
    private void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible)
            rightDrawable = mRightDrawable;
        else
            rightDrawable = null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], rightDrawable, getCompoundDrawables()[3]);
    }

    /**
     * 显示一个动画,以提示用户输入
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    /**
     * CycleTimes动画重复的次数putao
     *
     * @param CycleTimes 重复的次数
     * @return 动画实例
     */
    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public static class EmojiInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            return StringUtils.filterEmoji(source);
        }
    }

    public static class SpaceInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String trimSource = source.toString().trim();
            return TextUtils.isEmpty(trimSource) ? "" : trimSource;
        }
    }
}
