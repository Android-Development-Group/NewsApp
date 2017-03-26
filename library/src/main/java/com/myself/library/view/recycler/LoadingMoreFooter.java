package com.myself.library.view.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.library.R;
import com.myself.library.utils.StringUtils;

/**
 * 加载更多尾部
 * Created by guchenkai on 2015/12/3.
 */
class LoadingMoreFooter extends RelativeLayout {
    public final static int STATE_LOADING = 0;//加载中
    public final static int STATE_COMPLETE = 1;//加载完成
    public final static int STATE_NO_MORE = 2;//没有更多

    private View mRootView;

    private ImageView mIvLoading;
    private TextView mTvLoading;

    private String mLoadingText;//加载中文字
    private String mNoMoreText;//没有更多时文字

    private RotateAnimation mRotateAnimation;//旋转动画

    public LoadingMoreFooter(Context context) {
        this(context, null, 0);
    }

    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleable(context, attrs);
        initView(context);
        initAnimation();
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadMoreRecyclerView);
        mLoadingText = array.getString(R.styleable.LoadMoreRecyclerView_loading_text);
        if (StringUtils.isEmpty(mLoadingText))
            mLoadingText = context.getResources().getString(R.string.loadMore_loadingText);
        mNoMoreText = array.getString(R.styleable.LoadMoreRecyclerView_no_more_text);
        if (StringUtils.isEmpty(mNoMoreText))
            mNoMoreText = context.getResources().getString(R.string.loadMore_noMoreText);
        array.recycle();
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_loading_footer, null);
        mIvLoading = (ImageView) mRootView.findViewById(R.id.iv_loading);
        mTvLoading = (TextView) mRootView.findViewById(R.id.tv_loading);
        addView(mRootView);
    }

    /**
     * 初始化旋转动画
     */
    private void initAnimation() {
        mRotateAnimation = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    /**
     * 设置加载中文字
     *
     * @param loadingText 加载中文字
     */
    public void setLoadingText(String loadingText) {
        mLoadingText = loadingText;
    }

    /**
     * 设置没有更多时文字
     *
     * @param noMoreText 没有更多时文字
     */
    public void setNoMoreText(String noMoreText) {
        mNoMoreText = noMoreText;
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING://加载中
                mIvLoading.setVisibility(VISIBLE);
                mIvLoading.startAnimation(mRotateAnimation);
                mTvLoading.setText(mLoadingText);
                setVisibility(VISIBLE);
                break;
            case STATE_COMPLETE://加载完成
                setVisibility(View.GONE);
                break;
            case STATE_NO_MORE://没有更多
                mIvLoading.setVisibility(GONE);
                mIvLoading.clearAnimation();
                mTvLoading.setText(mNoMoreText);
                setVisibility(VISIBLE);
                break;
        }
    }
}
