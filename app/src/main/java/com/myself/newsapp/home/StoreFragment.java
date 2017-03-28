package com.myself.newsapp.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.myself.library.controller.BaseFragment;
import com.myself.library.mvp.widgets.LoadStateView;
import com.myself.newsapp.R;
import com.myself.newsapp.home.adapter.StoreAdapter;
import com.myself.newsapp.test.AddGoodsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商城
 * Created by Jusenr on 2017/03/26.
 */

public class StoreFragment extends BaseFragment {

    @BindView(R.id.main_title)
    TextView mMainTitle;
    @BindView(R.id.rv_goodslist)
    RecyclerView mRvGoodslist;
    @BindView(R.id.load_state_view)
    LoadStateView mLoadStateView;

    private List<AVObject> mList = new ArrayList<>();
    private StoreAdapter mStoreAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    public void onViewCreatedFinish(Bundle savedInstanceState) {
        mMainTitle.setText("商城");
        mLoadStateView.setEmptyMessage(getString(R.string.empty_data));
        mLoadStateView.setOnRetryListener(onRetryListener);

        mRvGoodslist.setHasFixedSize(true);
        mRvGoodslist.setLayoutManager(new LinearLayoutManager(mActivity));
        mStoreAdapter = new StoreAdapter(mActivity, mList);
        mRvGoodslist.setAdapter(mStoreAdapter);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }


    private void initData() {
        mList.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("Product");
        avQuery.orderByDescending("createdAt");
        avQuery.include("owner");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mList.addAll(list);
                    mStoreAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                    showFailedView();
                }
            }
        });
    }

    private LoadStateView.OnRetryListener onRetryListener = new LoadStateView.OnRetryListener() {
        @Override
        public void onRetry(View v) {
            initData();
        }
    };

    @OnClick({R.id.fab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startActivity(AddGoodsActivity.class);
                break;
        }
    }

    private void showFailedView() {
        mLoadStateView.onLoadFailed();
        mLoadStateView.setFailedMessage(getString(R.string.no_network_tips));
        mLoadStateView.setFailedView(R.drawable.ani_loading);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
