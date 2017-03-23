package com.myself.library.mvp.widgets;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by riven_chris on 16/7/1.
 */
public abstract class ILoadState extends FrameLayout {

    public interface OnRetryListener {
        void onRetry(View v);
    }

    protected OnRetryListener mOnRetryListener;

    public ILoadState(Context context) {
        super(context);
    }

    public ILoadState(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ILoadState(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void onLoadFailed();

    public abstract void onLoadSuccess();

    public abstract void onLoadSuccessEmpty();

    public abstract void setEmptyMessage(String emptyMessage);

    public abstract void setEmptyView(@DrawableRes int drawableId);

    public abstract void setFailedMessage(String failedMessage);

    public abstract void setFailedView(@DrawableRes int drawableId);

    public void setOnRetryListener(OnRetryListener mOnRetryListener) {
        this.mOnRetryListener = mOnRetryListener;
    }
}
