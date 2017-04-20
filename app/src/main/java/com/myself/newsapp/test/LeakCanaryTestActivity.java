package com.myself.newsapp.test;

import android.os.Bundle;
import android.os.Handler;

import com.myself.library.utils.ToastUtils;
import com.myself.newsapp.R;
import com.myself.newsapp.base.TitleActivity;

/**
 * 添加商品
 * Created by Jusenr on 2017/04/20.
 */
public class LeakCanaryTestActivity extends TitleActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leak_canary_test;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        //模拟内存泄露
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToastShort(LeakCanaryTestActivity.this, "哈哈哈啊哈啊哈哈哈哈");
            }
        }, 10 * 1000);
        finish();
    }
}
