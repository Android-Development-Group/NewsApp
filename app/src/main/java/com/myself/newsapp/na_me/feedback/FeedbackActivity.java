package com.myself.newsapp.na_me.feedback;

import android.os.Bundle;

import com.myself.newsapp.R;
import com.myself.newsapp.base.TitleActivity;


/**
 * 意见反馈
 * Created by Jusenr on 2017/03/26.
 */
public class FeedbackActivity extends TitleActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

    }

    @Override
    public void onRightAction() {
        super.onRightAction();
    }
}
