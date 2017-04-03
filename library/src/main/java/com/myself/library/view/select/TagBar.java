package com.myself.library.view.select;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;

import com.myself.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签选择控件
 * Created by guchenkai on 2015/12/4.
 */
public class TagBar extends FlowLayout implements OnClickListener {
    private List<Tag> mTags = new ArrayList<>();

    private Drawable mTagItemBackgroundSel;
    private Drawable mTagItemBackgroundNor;
    private int mTextSelColor;
    private int mTextNorColor;
    private int mTextDisColor;//不可选择的文字颜色

    private TagItem mLastCheckItem = null;
    private SparseArray<TagItem> array;
    private OnTagItemCheckListener mTagItemCheckListener;

    public void setonTagItemCheckListener(OnTagItemCheckListener tagItemCheckListener) {
        this.mTagItemCheckListener = tagItemCheckListener;
    }

    public TagBar(Context context) {
        this(context, null, 0);
    }

    public TagBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initStyleable(context, attrs);
        array = new SparseArray<>();
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TagBar);
        mTagItemBackgroundSel = array.getDrawable(R.styleable.TagBar_tag_background_sel);
        mTagItemBackgroundNor = array.getDrawable(R.styleable.TagBar_tag_background_nor);
        mTextSelColor = array.getColor(R.styleable.TagBar_tag_text_color_sel, -1);
        mTextNorColor = array.getColor(R.styleable.TagBar_tag_text_color_nor, -1);
        mTextDisColor = array.getColor(R.styleable.TagBar_tag_text_color_dis, -1);
        array.recycle();
    }

    private void inflateTagView(Tag tag) {
        TagItem localTagView = (TagItem) View.inflate(getContext(), R.layout.widget_tag, null);
        if (mTagItemBackgroundSel != null)
            localTagView.setTagItemBackgroundSel(mTagItemBackgroundSel);
        if (mTagItemBackgroundNor != null)
            localTagView.setTagItemBackgroundNor(mTagItemBackgroundNor);
        if (mTextSelColor != -1)
            localTagView.setTextSelColor(mTextSelColor);
        if (mTextNorColor != -1)
            localTagView.setTextNorColor(mTextNorColor);
        if (mTextDisColor != -1)
            localTagView.setTextDisColor(mTextDisColor);
        localTagView.setTags(tag);
        addView(localTagView);
    }

    @Override
    public void onClick(View v) {
        checkTagItem((TagItem) v);
    }

    /**
     * 选择TagItem
     *
     * @param item 选中的TagItem
     */
    public void checkTagItem(TagItem item) {
        if (mLastCheckItem == item) {
            mLastCheckItem.setState(true);
            return;
        } else {
            if (mLastCheckItem != null)
                mLastCheckItem.setState(false);
            item.setState(true);
            mLastCheckItem = item;
        }
//        int position = array.get(item.getId());
        if (mTagItemCheckListener != null)
            mTagItemCheckListener.onTagItemCheck(item.getTags(), mTags.indexOf(item.getTags()));
    }

    /**
     * 默认选中项目
     *
     * @param position 项目标号
     */
    private void setCheckItemPosition(int position) {
        mLastCheckItem = array.get(position);
        mLastCheckItem.setState(true);
    }

    private void addTag(Tag tag) {
        mTags.add(tag);
        inflateTagView(tag);
    }

    /**
     * 添加标签组
     *
     * @param tags            标签组
     * @param defaultPosition 默认选中项
     */
    public void addTags(List<Tag> tags, Integer defaultPosition) {
        for (int i = 0; i < tags.size(); i++) {
            addTag(tags.get(i));
        }
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            array.put(i, (TagItem) child);
            if (!(child instanceof TagItem))
                throw new RuntimeException("TagBar's child must be subclass of TagItem!");
            if (((TagItem) child).isState())
                mLastCheckItem = (TagItem) child;
            if (((TagItem) child).getTags().isEnable())
                child.setOnClickListener(this);
        }
        if (defaultPosition != null)
            setCheckItemPosition(defaultPosition);
    }

    /**
     * 替换标签状态
     *
     * @param tags            标签组
     * @param defaultPosition 默认选中项
     */
    public void replaceAllTag(List<Tag> tags, Integer defaultPosition) {
        mTags.clear();
        removeAllViews();
        addTags(tags, defaultPosition);
    }

    public void addTags(List<Tag> tags) {
        addTags(tags, null);
    }

    /**
     * 获得tag
     *
     * @param position 标号
     * @return tag
     */
    public Tag getTag(int position) {
        return mTags.get(position);
    }

    /**
     * TabItem选择监听器
     */
    public interface OnTagItemCheckListener {

        void onTagItemCheck(Tag tag, int position);
    }
}
