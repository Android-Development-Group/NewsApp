package com.myself.newsapp.na_me.message;

import android.os.Bundle;

import com.myself.newsapp.R;
import com.myself.newsapp.base.TitleActivity;

public class MessageActivity extends TitleActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();


    }
}
