package com.myself.library.view.recycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.myself.library.utils.Logger;
import com.myself.library.view.recycler.adapter.LoadMoreAdapter;

/**
 * 上拉加载更多RecyclerView
 * Created by guchenkai on 2015/11/25.
 */
public class LoadMoreRecyclerView extends BasicRecyclerView {
    private static final String TAG = LoadMoreRecyclerView.class.getSimpleName();
    private LoadingMoreFooter mFootView;//加载布局
    private boolean isLoading = false;//是否正在加载
    private boolean isNoMore = false;//是否是没有更多加载
    private int mPreviousTotal = 0;//前一个布局的position

    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public LoadMoreRecyclerView(Context context) {
        this(context, null, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mFootView = new LoadingMoreFooter(context);
        mFootView.setVisibility(GONE);

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动,即是否向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://当不滚动时
                        //获取最后一个完全显示的ItemPosition
                        int lastVisibleItem = manager.findLastVisibleItemPosition();
                        int totalItemCount = manager.getItemCount();
                        //判断是否滚动到底部
                        if (lastVisibleItem == totalItemCount - 1 && isSlidingToLast && !isLoading && !isNoMore) {
                            Logger.d(TAG, "加载更多");
                            mFootView.setState(LoadingMoreFooter.STATE_LOADING);
                            isLoading = true;
                            if (mOnLoadMoreListener != null)
                                mOnLoadMoreListener.onLoadMore();
                        }
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //dy用来判断纵向滑动
                if (dy > 0)
                    isSlidingToLast = true;
                else
                    isSlidingToLast = false;
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        LoadMoreAdapter loadMoreAdapter = (LoadMoreAdapter) adapter;
        if (!(loadMoreAdapter instanceof LoadMoreAdapter))
            throw new RuntimeException("adapter类型必须是LoadMoreAdapter");
        loadMoreAdapter.addFooter(mFootView);
        super.setAdapter(loadMoreAdapter);
    }

    /**
     * 加载完成
     */
    public void loadMoreComplete() {
        isLoading = false;
        if (mPreviousTotal < getLayoutManager().getItemCount()) {
            mFootView.setState(LoadingMoreFooter.STATE_COMPLETE);
        } else {
            mFootView.setState(LoadingMoreFooter.STATE_NO_MORE);
//            isNoMore = true;
        }
        mPreviousTotal = getLayoutManager().getItemCount();
    }

    /**
     * 没有加载更多
     */
    public void noMoreLoading() {
        isLoading = false;
        isNoMore = true;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
            if (manager.findFirstVisibleItemPosition() <= 0) {
                mFootView.setState(LoadingMoreFooter.STATE_COMPLETE);
            } else {
                mFootView.setState(LoadingMoreFooter.STATE_NO_MORE);
            }
        } else {
            mFootView.setState(LoadingMoreFooter.STATE_NO_MORE);
        }
    }

    /**
     * 重置
     */
    public void reset() {
        isNoMore = false;
    }

    /**
     * 加载更多回调
     */
    public interface OnLoadMoreListener {

        void onLoadMore();
    }

    /**
     * 设置没有更多时文字
     *
     * @param noMoreText 没有更多时文字
     */
    public void setNoMoreText(String noMoreText) {
        mFootView.setNoMoreText(noMoreText);
    }
//    private float startY = -1;
//    private float startX = -1;
//
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startY = ev.getY();
//                startX = ev.getX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                float moveX = ev.getX();
//                float moveY = ev.getY();
//
//                float diffX = moveX - startX;
//                float diffY = moveY - startY;
//
//                if (Math.abs(diffX) > Math.abs(diffY)) {
//                    //水平滑动
//                    return false;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startY = ev.getY();
//                startX = ev.getX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                float moveX = ev.getX();
//                float moveY = ev.getY();
//
//                float diffX = moveX - startX;
//                float diffY = moveY - startY;
//
//                if (Math.abs(diffX) > Math.abs(diffY)) {
//                    //水平滑动
//                    return false;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
}
