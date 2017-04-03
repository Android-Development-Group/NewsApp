package com.myself.library.view.select;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 单选布局
 * Created by guchenkai on 2015/12/10.
 */
public class RadioBar extends LinearLayout implements View.OnClickListener {
    private OnRadioItemSelectListener mOnRadioItemSelectListener;
    private RadioItem mLastSelectedItem = null;

    private SparseIntArray array;

    public void setOnRadioItemSelectListener(OnRadioItemSelectListener onRadioItemSelectListener) {
        mOnRadioItemSelectListener = onRadioItemSelectListener;
    }

    public RadioBar(Context context) {
        this(context, null, 0);
    }

    public RadioBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        array = new SparseIntArray();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int index = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
//            array.put(child.getId(), i);
//            if (!(child instanceof RadioItem))
//                throw new RuntimeException("RadioBar's child must be subclass of RadioItem!");
//            if (((RadioItem) child).isSelect())
//                mLastSelectedItem = (RadioItem) child;
//            child.setOnClickListener(this);
            if (child instanceof RadioItem) {
                array.put(child.getId(), index);
                if (((RadioItem) child).isSelect())
                    mLastSelectedItem = (RadioItem) child;
                child.setOnClickListener(this);
                index++;
            }
        }
    }

    @Override
    public void onClick(View v) {
        selectRadioItem((RadioItem) v);
    }

    /**
     * 选择RadioItem
     *
     * @param item 选中的RadioItem
     */
    public void selectRadioItem(RadioItem item) {
        if (mLastSelectedItem == item) {
            mLastSelectedItem.setSelect(true);
            return;
        } else {
            if (mLastSelectedItem != null)
                mLastSelectedItem.setSelect(false);
            item.setSelect(true);
            mLastSelectedItem = item;
        }
        int position = array.get(item.getId());
        if (mOnRadioItemSelectListener != null)
            mOnRadioItemSelectListener.onRadioItemSelect(item, position);
    }

    /**
     * RadioItem选择监听器
     */
    public interface OnRadioItemSelectListener {

        void onRadioItemSelect(RadioItem item, int position);
    }
}
