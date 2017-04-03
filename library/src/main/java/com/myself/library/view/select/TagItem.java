package com.myself.library.view.select;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 标签类
 * Created by guchenkai on 2015/12/4.
 */
public class TagItem extends TextView {
    private boolean isState;
    private Tag tag;

    private Drawable mTagItemBackgroundSel;
    private Drawable mTagItemBackgroundNor;
    private int mTextSelColor;
    private int mTextNorColor;
    private int mTextDisColor;//不可选择的文字颜色

    public TagItem(Context context) {
        this(context, null, 0);
    }

    public TagItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Tag tag) {
        setText(tag.getText());
        setTextColor(!tag.isEnable() ? mTextDisColor : mTextNorColor);
        if (!tag.isEnable())
            getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        setBackground(mTagItemBackgroundNor);
    }

    public void setTags(Tag tag) {
        this.tag = tag;
        init(tag);
    }

    public Tag getTags() {
        return tag;
    }

    public void setTagItemBackgroundSel(Drawable tagItemBackgroundSel) {
        this.mTagItemBackgroundSel = tagItemBackgroundSel;
    }

    public void setTagItemBackgroundNor(Drawable tagItemBackgroundNor) {
        this.mTagItemBackgroundNor = tagItemBackgroundNor;
    }

    public void setTextSelColor(int textSelColor) {
        this.mTextSelColor = textSelColor;
    }

    public void setTextNorColor(int textNorColor) {
        this.mTextNorColor = textNorColor;
    }

    public void setTextDisColor(int textDisColor) {
        this.mTextDisColor = textDisColor;
    }

    public void setState(boolean isState) {
        this.isState = isState;
        setTextColor(isState ? mTextSelColor : mTextNorColor);
        setBackground(isState ? mTagItemBackgroundSel : mTagItemBackgroundNor);
    }

    public boolean isState() {
        return isState;
    }
}
