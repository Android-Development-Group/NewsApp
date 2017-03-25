package com.myself.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.myself.library.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒计时Button
 * Created by guchenkai on 2015/12/1.
 */
public class TimeButton extends TextView implements View.OnClickListener {
    private int mCountdownTime = 0;//倒计时时长
    private String mTextBefore, mTextAfter;
    private OnClickListener mL;
    private long mTime;
    private Timer mT;
    private TimerTask mTt;

    private OnClickListener mOnclickListener;

    private int mClickBackgroundResId;
    private int mUnClickBackgroundResId;

    private CountDownTimer mTimer;
/*    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TimeButton.this.setText(replace(mTextAfter, (int) (mTime / 1000)));
            mTime -= 1000;
            if (mTime < 0) reset();
        }
    };*/

    private String replace(String str, int time) {
        return str.replace("$", String.valueOf(time));
    }

    public TimeButton(Context context) {
        this(context, null, 0);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleable(context, attrs);
        initView();
    }

    private void initStyleable(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeButton);
        mCountdownTime = array.getInt(R.styleable.TimeButton_countdown_time, 0);
        mTextBefore = array.getString(R.styleable.TimeButton_text_before);
        mTextAfter = array.getString(R.styleable.TimeButton_text_after);
        mClickBackgroundResId = array.getResourceId(R.styleable.TimeButton_click_background, -1);
//        mUnClickBackgroundResId = array.getResourceId(R.styleable.TimeButton_unclick_background, -1);
        array.recycle();
    }

    private void initView() {
        setText(mTextBefore);
        if (mClickBackgroundResId != -1)
            setTextColor(mClickBackgroundResId);
        setOnClickListener(this);
        mTimer = new CountDownTimer(mCountdownTime * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                setTextColor(0xff959595);
                setText(replace(mTextAfter, (int) (millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                setClickable(true);
                setText(mTextBefore);
                setTextColor(mClickBackgroundResId);
            }
        };
    }

    /*private void initTimer() {
        mTime = mCountdownTime * 1000;
        mT = new Timer();
        mTt = new TimerTask() {
            @Override
            public void run() {
                Logger.d("倒计时还剩:" + mTime / 1000 + "");
                mHandler.sendEmptyMessage(0x01);
            }
        };
    }*/

    public void reset() {
        mTimer.onFinish();
        mTimer.cancel();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                setClickable(true);
                *//*clearTimer();
                initView();*//*
            }
        }, 200);*/
    }

 /*   private void clearTimer() {
        if (mTt != null) mTt.cancel();
        mTt = null;
        if (mT != null) mT.cancel();
        mT = null;
    }*/

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton)
            super.setOnClickListener(l);
        else
            mL = l;
    }

    @Override
    public void onClick(View v) {
        setClickable(false);
        mTimer.start();
        if (mL != null) mL.onClick(v);
        /*initTimer();
        setText(replace(mTextAfter, (int) (mTime / 1000)));
        if (mUnClickBackgroundResId != -1)
            setTextColor(mUnClickBackgroundResId);
        setClickable(false);
        mT.schedule(mTt, 0, 1000);
        if (mL != null) mL.onClick(v);*/
    }

  /*  @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == GONE) clearTimer();
    }*/

    /**
     * 设置计时时候显示的文本
     */
    /*public TimeButton setTextAfter(String text_after) {
        mTextAfter = text_after;
        return this;
    }*/

    /**
     * 设置点击之前的文本
     */
  /*  public TimeButton setTextBefore(String text_after) {
        mTextBefore = text_after;
        this.setText(text_after);
        return this;
    }*/

    /**
     * 设置到计时长度
     *
     * @param countdownTime 时间 默认秒
     * @return
     */
    /*public TimeButton setCountdownTime(int countdownTime) {
        mCountdownTime = countdownTime;
        return this;
    }*/
}