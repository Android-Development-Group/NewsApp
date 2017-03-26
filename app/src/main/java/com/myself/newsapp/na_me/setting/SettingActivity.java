package com.myself.newsapp.na_me.setting;

import android.os.Bundle;
import android.view.View;

import com.myself.newsapp.R;
import com.myself.newsapp.TestActivity;
import com.myself.newsapp.account.AccountHelper;
import com.myself.newsapp.base.TitleActivity;
import com.myself.newsapp.guidance.GuidanceActivity;

import butterknife.OnClick;

public class SettingActivity extends TitleActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        startActivity(TestActivity.class);
    }

    @OnClick(R.id.btn_loginout)
    public void onClick(View view) {
        AccountHelper.logout();
        startActivity(GuidanceActivity.class);
        finish();
    }
}
