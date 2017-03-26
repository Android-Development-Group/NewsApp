package com.myself.library.view.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.myself.library.R;
import com.myself.library.view.recycler.adapter.BasicAdapter;
import com.myself.library.view.recycler.layoutmanager.InnerGridLayoutManager;
import com.myself.library.view.recycler.layoutmanager.InnerLinearLayoutManager;
import com.myself.library.view.recycler.listener.OnItemClickListener;
import com.myself.library.view.recycler.listener.OnItemLongClickListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

/**
 * 提供基本处理的RecyclerView
 * Created by guchenkai on 2015/10/29.
 */
public class BasicRecyclerView extends RecyclerView {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int INVALID_OFFSET = 2;
    public static final int GRID = 3;

    private LayoutManager manager;
    private int layout_mode;//布局方式
    private boolean isInner;//是否是内部使用
    private int column_num;//布局方式为grid时,每行的显示数
    private boolean has_row_divider;//是否显示行间隔线
    private boolean has_rank_divider;//是否显示列间距
    private int divider_width;//列间隔线的宽度
    private int divider_height;//行间隔线的高度
    private int divider_color;//间隔线的颜色
    private int divider_marginLeft, divider_marginRight, divider_marginTop, divider_marginBottom;
    private boolean isProcess;
    private int mProcessBackground;//点击时背景色
    private boolean isStartAnimator;
    private int mDuration;//动画持续时间
    private boolean isFirstOnly;

    public BasicRecyclerView(Context context) {
        this(context, null, 0);
    }

    public BasicRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initStyleable(context, attrs);
        initView(context);
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BasicRecyclerView);
        layout_mode = array.getInt(R.styleable.BasicRecyclerView_layout_mode, VERTICAL);
        column_num = array.getInt(R.styleable.BasicRecyclerView_column_num, 3);
        has_row_divider = array.getBoolean(R.styleable.BasicRecyclerView_has_row_divider, false);
        has_rank_divider = array.getBoolean(R.styleable.BasicRecyclerView_has_rank_divider, false);

        isInner = array.getBoolean(R.styleable.BasicRecyclerView_is_inner, false);
        divider_width = (int) array.getDimension(R.styleable.BasicRecyclerView_divider_width, 1f);
        divider_height = (int) array.getDimension(R.styleable.BasicRecyclerView_divider_height, 1f);
        divider_marginLeft = (int) array.getDimension(R.styleable.BasicRecyclerView_divider_marginLeft, 0f);
        divider_marginRight = (int) array.getDimension(R.styleable.BasicRecyclerView_divider_marginRight, 0f);
        divider_marginTop = (int) array.getDimension(R.styleable.BasicRecyclerView_divider_marginTop, 0f);
        divider_marginBottom = (int) array.getDimension(R.styleable.BasicRecyclerView_divider_marginBottom, 0f);
        divider_color = array.getColor(R.styleable.BasicRecyclerView_divider_color, Color.parseColor("#00000000"));
        isProcess = array.getBoolean(R.styleable.BasicRecyclerView_is_process, false);
        mProcessBackground = array.getInteger(R.styleable.BasicRecyclerView_process_background, R.drawable.recyclerview_background);
        isStartAnimator = array.getBoolean(R.styleable.BasicRecyclerView_is_start_animator, false);
        mDuration = array.getInt(R.styleable.BasicRecyclerView_duration_animator, 300);
        isFirstOnly = array.getBoolean(R.styleable.BasicRecyclerView_is_first_only, false);
        array.recycle();
    }

    /**
     * 初始化view
     */
    private void initView(Context context) {
        setOverScrollMode(OVER_SCROLL_NEVER);
        switch (layout_mode) {
            case BasicRecyclerView.HORIZONTAL:
                if (!isInner)
                    manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                else
                    manager = new InnerLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                break;
            case BasicRecyclerView.VERTICAL:
                if (!isInner)
                    manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                else
                    manager = new InnerLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                break;
            case BasicRecyclerView.INVALID_OFFSET:
                if (!isInner)
                    manager = new LinearLayoutManager(context, LinearLayoutManager.INVALID_OFFSET, false);
                else
                    manager = new InnerLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                break;
            case BasicRecyclerView.GRID:
                if (!isInner) {
                    manager = new GridLayoutManager(context, column_num);
                } else {
                    manager = new InnerGridLayoutManager(context, column_num);
//                    ((GridLayoutManager) manager).setSmoothScrollbarEnabled(true);
                }
                break;
        }
        //排版方式
        setLayoutManager(manager);
        //添加删除的动画
//        setItemAnimator(new BasicItemAnimator());
        //添加间隔线
        if (has_row_divider) {
            HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(context)
                    .color(divider_color)
                    .size(divider_height)
                    .margin(divider_marginLeft, divider_marginRight)
                    .build();
            addItemDecoration(decoration);
        }
        if (has_rank_divider) {
            VerticalDividerItemDecoration decoration = new VerticalDividerItemDecoration.Builder(context)
                    .color(divider_color)
                    .size(divider_width)
                    .margin(divider_marginTop, divider_marginBottom)
                    .build();
            addItemDecoration(decoration);
        }
        setHasFixedSize(true);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        BasicAdapter basicAdapter = getBasicAdapter(adapter);
        if (isProcess) basicAdapter.setProcessDrawable(mProcessBackground);
        basicAdapter.setStartAnimation(isStartAnimator);
        basicAdapter.setDuration(mDuration);
        basicAdapter.setFirstOnly(isFirstOnly);
        super.setAdapter(basicAdapter);
    }

    /**
     * 获得BasicAdapter实例
     *
     * @return BasicAdapter实例
     */
    private BasicAdapter getBasicAdapter(Adapter adapter) {
        if (!(adapter instanceof BasicAdapter))
            throw new RuntimeException("adapter的的类型必须是BasicAdapter");
        return (BasicAdapter) adapter;
    }

    /**
     * 获得BasicAdapter实例
     *
     * @return BasicAdapter实例
     */
    private BasicAdapter getBasicAdapter() {
        return getBasicAdapter(getAdapter());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        getBasicAdapter().setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        getBasicAdapter().setOnItemLongClickListener(onItemLongClickListener);
    }

}
