package com.myself.library.mvp.widgets;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.library.R;

/**
 * Created by riven_chris on 16/6/7.
 */
public class LoadStateView extends ILoadState implements View.OnClickListener {

    private View loadStateView;
    RelativeLayout rlNoData;
    TextView tvEmptyMessage;
    ImageView ivEmptyImage;
    RelativeLayout rlLoadFailed;
    ImageView ivLoadFailed;
    TextView tvFailedMessage;

    public LoadStateView(Context context) {
        this(context, null);
    }

    public LoadStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadStateView = LayoutInflater.from(context).inflate(R.layout.layout_load_states, this, true);
        rlNoData = (RelativeLayout) findViewById(R.id.rl_no_data);
        tvEmptyMessage = (TextView) findViewById(R.id.tv_empty_message);
        ivEmptyImage = (ImageView) findViewById(R.id.img_no_data);
        rlLoadFailed = (RelativeLayout) findViewById(R.id.rl_load_failed);
        ivLoadFailed = (ImageView) findViewById(R.id.iv_load_failed);
        tvFailedMessage = (TextView) findViewById(R.id.tv_load_failed);
        setVisibility(GONE);
    }

    @Override
    public void onLoadFailed() {
        setVisibility(VISIBLE);
        rlNoData.setVisibility(GONE);
        rlLoadFailed.setOnClickListener(this);
        rlLoadFailed.setVisibility(VISIBLE);

    }

    @Override
    public void setFailedMessage(@NonNull String failedMessage) {
        tvFailedMessage.setText(failedMessage);
    }

    @Override
    public void setFailedView(@DrawableRes int drawableId) {
        ivLoadFailed.setImageDrawable(ContextCompat.getDrawable(getContext(), drawableId));
    }

    @Override
    public void onLoadSuccessEmpty() {
        setVisibility(VISIBLE);
        rlLoadFailed.setVisibility(GONE);
        rlNoData.setVisibility(VISIBLE);
    }

    @Override
    public void onLoadSuccess() {
        rlNoData.setVisibility(GONE);
        rlLoadFailed.setVisibility(GONE);
        setVisibility(GONE);
    }

    @Override
    public void setEmptyMessage(@NonNull String emptyMessage) {
        tvEmptyMessage.setText(emptyMessage);
    }

    @Override
    public void setEmptyView(@DrawableRes int drawableId) {
        ivEmptyImage.setImageDrawable(ContextCompat.getDrawable(getContext(), drawableId));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_load_failed) {
            if (mOnRetryListener != null)
                mOnRetryListener.onRetry(v);

        }
    }
}
