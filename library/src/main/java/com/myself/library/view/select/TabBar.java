package com.myself.library.view.select;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;


/**
 * 底部切换工具条
 * Created by guchenkai on 2015/11/24.
 */
public class TabBar extends LinearLayout implements View.OnClickListener {
    private static final String TAG = TabBar.class.getSimpleName();
    private OnTabItemSelectedListener mTabItemSelectedListener;
    private TabItem mLastSelectedItem = null;

    private SparseIntArray array;
    private SparseArray<TabItem> mTabItemArray;

    public TabBar(Context context) {
        this(context, null, 0);
    }

    public TabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        array = new SparseIntArray();
        mTabItemArray = new SparseArray<>();
    }

    public void setOnTabItemSelectedListener(OnTabItemSelectedListener tabItemSelectedListener) {
        mTabItemSelectedListener = tabItemSelectedListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            array.put(child.getId(), i);
            mTabItemArray.put(child.getId(), (TabItem) child);
            if (!(child instanceof TabItem))
                throw new RuntimeException("TabBar's child must be subclass of TabItem!");
            if (((TabItem) child).getActive())
                mLastSelectedItem = (TabItem) child;
            child.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        selectTabItem((TabItem) v);
    }

    /**
     * 选择TabItem
     *
     * @param item 选中的TabItem
     */
    private void selectTabItem(TabItem item) {
        if (mLastSelectedItem == item) {
            mLastSelectedItem.setActive(true);
            return;
        } else {
            if (mLastSelectedItem != null)
                mLastSelectedItem.setActive(false);
            item.setActive(true);
            mLastSelectedItem = item;
        }
        Log.d(TAG, "mTabItemSelectedListener : " + mTabItemSelectedListener);
        int position = array.get(item.getId());
        if (mTabItemSelectedListener != null)
            mTabItemSelectedListener.onTabItemSelected(item, position);
    }

    /**
     * 选择当前选中项
     *
     * @param resId 资源id
     */
    public void setTabItemSelected(int resId) {
        TabItem tabItem = mTabItemArray.get(resId);
        tabItem.setActive(true);
        selectTabItem(tabItem);
    }

    public TabItem getSelectedItem() {
        return mLastSelectedItem;
    }

    public TabItem getCurrentItem() {
        return mLastSelectedItem;
    }

    public int getPosition() {
        return array.size();
    }

    /**
     * TabItem选择监听器
     */
    public interface OnTabItemSelectedListener {

        void onTabItemSelected(TabItem item, int position);
    }
}
