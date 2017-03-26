package com.myself.newsapp.na_me;

import android.os.Bundle;
import android.view.View;

import com.myself.library.controller.BaseActivity;
import com.myself.newsapp.R;
import com.myself.newsapp.account.AccountHelper;
import com.myself.newsapp.guidance.GuidanceActivity;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {

    }


    @OnClick(R.id.tv_text)
    public void onClick(View view) {
        AccountHelper.logout();
        startActivity(GuidanceActivity.class);
        finish();
    }
}
