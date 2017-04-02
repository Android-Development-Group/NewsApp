package com.myself.newsapp.base;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myself.library.controller.BasicPopupWindow;
import com.myself.newsapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择弹框
 * Created by guchenkai on 2015/12/1.
 */
public abstract class SelectPopupWindow extends BasicPopupWindow implements View.OnClickListener {

    @BindView(R.id.ll_first)
    public LinearLayout ll_first;
    @BindView(R.id.ll_second)
    public LinearLayout ll_second;
    @BindView(R.id.tv_first)
    public TextView tv_first;
    @BindView(R.id.tv_second)
    public TextView tv_second;
    @BindView(R.id.tv_cancel)
    public TextView tv_cancel;

    public SelectPopupWindow(Context context) {
        super(context);
    }

    public SelectPopupWindow(Context context, String firstText, int firstTextColor) {
        super(context);
        tv_first.setText(firstText);
        tv_first.setTextColor(firstTextColor);
        ll_second.setVisibility(View.GONE);
    }

    public SelectPopupWindow(Context context, String firstText, int firstTextColor, String secondText, int secondTextColor) {
        super(context);
        tv_first.setText(firstText);
        tv_first.setTextColor(firstTextColor);
        tv_second.setText(secondText);
        tv_second.setTextColor(secondTextColor);
    }

    public SelectPopupWindow(Context context, String firstText, int firstTextColor, String secondText, int secondTextColor, String cancelText, int cancelTextColor) {
        super(context);
        tv_first.setText(firstText);
        tv_first.setTextColor(firstTextColor);
        tv_second.setText(secondText);
        tv_second.setTextColor(secondTextColor);
        tv_cancel.setText(cancelText);
        tv_cancel.setTextColor(cancelTextColor);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.popup_select;
    }

    @OnClick({R.id.ll_first, R.id.ll_second, R.id.ll_cancel})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_first:
                onFirstClick(v);
                break;
            case R.id.ll_second:
                onSecondClick(v);
                break;
        }
        dismiss();
    }

    /**
     * 点击第一行
     *
     * @param v view
     */
    public abstract void onFirstClick(View v);

    /**
     * 点击第二行
     *
     * @param v view
     */
    public abstract void onSecondClick(View v);
}
