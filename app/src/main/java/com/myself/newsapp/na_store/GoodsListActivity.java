package com.myself.newsapp.na_store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.myself.newsapp.R;
import com.myself.newsapp.base.TitleActivity;
import com.myself.newsapp.home.adapter.StoreAdapter;
import com.myself.newsapp.test.AddGoodsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品列表
 * Created by Jusenr on 2017/03/25.
 */
public class GoodsListActivity extends TitleActivity {

    @BindView(R.id.rv_goodslist)
    RecyclerView mRvGoodslist;

    private StoreAdapter mStoreAdapter;
    private List<AVObject> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_list;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        mRvGoodslist.setHasFixedSize(true);
        mRvGoodslist.setLayoutManager(new LinearLayoutManager(this));
        mStoreAdapter = new StoreAdapter(this, mList);
        mRvGoodslist.setAdapter(mStoreAdapter);
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
                }
            }
        });
    }

    @OnClick({R.id.fab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startActivity(AddGoodsActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
