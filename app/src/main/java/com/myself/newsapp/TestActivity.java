package com.myself.newsapp;

import android.os.Bundle;

import com.myself.newsapp.base.TitleActivity;

public class TestActivity extends TitleActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();


    }
}
