package com.myself.newsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.myself.library.controller.eventbus.EventBusHelper;
import com.myself.newsapp.base.TitleActivity;
import com.myself.newsapp.na_store.GoodsListActivity;
import com.myself.newsapp.test.LeakCanaryTestActivity;
import com.myself.newsapp.util.Constants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 测试页面
 * Created by Jusenr on 2017/03/25.
 */
public class TestActivity extends TitleActivity {

    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

    }

    @OnClick({R.id.tv_test_0, R.id.tv_test_1, R.id.tv_test_2, R.id.tv_test_3, R.id.tv_test_4, R.id.tv_test_5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_test_0:
                startActivity(GoodsListActivity.class);
                break;
            case R.id.tv_test_1:
                EventBusHelper.post(3, Constants.EventKey.EVENT_REFRESH_ME_TAB);
                break;
            case R.id.tv_test_2:
                mTvTest.setText(0);
                break;
            case R.id.tv_test_3:
                Log.e(TAG, "onClick: LeakCanaryTestActivity" );
                startActivity(LeakCanaryTestActivity.class);
                break;
            case R.id.tv_test_4:
                break;
            case R.id.tv_test_5:
                break;
        }
    }
}
