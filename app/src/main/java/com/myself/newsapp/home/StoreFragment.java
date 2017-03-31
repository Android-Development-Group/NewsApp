package com.myself.newsapp.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myself.library.controller.BaseFragment;
import com.myself.library.view.recycler.BasicRecyclerView;
import com.myself.library.view.scroll.SupportScrollView;
import com.myself.library.view.viewpager.banner.ConvenientBanner;
import com.myself.newsapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商城
 * Created by Jusenr on 2017/03/26.
 */

public class StoreFragment extends BaseFragment {

    @BindView(R.id.tv_scan)
    TextView mTvScan;
    @BindView(R.id.rl_search)
    RelativeLayout mRlSearch;
    @BindView(R.id.tabTitle)
    TabLayout mTabTitle;
    @BindView(R.id.ll_tab_bar)
    LinearLayout mLlTabBar;
    @BindView(R.id.cb_banner)
    ConvenientBanner mCbBanner;
    @BindView(R.id.tv_integral)
    TextView mTvIntegral;
    @BindView(R.id.iv_integral)
    ImageView mIvIntegral;
    @BindView(R.id.tv_daily_specials)
    TextView mTvDailySpecials;
    @BindView(R.id.rv_daily_specials)
    BasicRecyclerView mRvDailySpecials;
    @BindView(R.id.tv_hot_single)
    TextView mTvHotSingle;
    @BindView(R.id.rv_hotSell)
    BasicRecyclerView mRvHotSell;
    @BindView(R.id.lrv_main)
    SupportScrollView mLrvMain;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {

    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tv_scan, R.id.rl_search, R.id.tv_message, R.id.tabTitle, R.id.tv_integral, R.id.iv_integral})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_scan:
                break;
            case R.id.rl_search:
                break;
            case R.id.tv_message:
                break;
            case R.id.tabTitle:
                break;
            case R.id.tv_integral:
                break;
            case R.id.iv_integral:
                break;
        }
    }
}
