package com.myself.newsapp.home;

import android.os.Bundle;
import android.view.View;

import com.myself.library.controller.BaseFragment;
import com.myself.newsapp.R;
import com.myself.newsapp.account.AccountHelper;
import com.myself.newsapp.guidance.GuidanceActivity;

import butterknife.OnClick;

/**
 * 附近
 * Created by Jusenr on 2017/3/25.
 */

public class NearbyFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    @OnClick(R.id.tv_text)
    public void onClick(View view) {
        AccountHelper.logout();
        startActivity(GuidanceActivity.class);
        mActivity.finish();
    }
}
