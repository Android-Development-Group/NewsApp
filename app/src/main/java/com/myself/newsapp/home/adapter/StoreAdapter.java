package com.myself.newsapp.home.adapter;

import android.content.Context;
import android.view.View;

import com.myself.library.view.recycler.BasicViewHolder;
import com.myself.library.view.recycler.adapter.LoadMoreAdapter;
import com.myself.newsapp.model.StoreResource;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Jusenr on 2017/3/31.
 */

public class StoreAdapter extends LoadMoreAdapter<StoreResource, BasicViewHolder> {
    private static final int KEY_BANNER = 0XFD;// 置顶banner
    private static final int KEY_RESOURCE = 0XFC;//文章条目
    private static final int KEY_HOT_TAG = 0XFA;//文章标签


    public StoreAdapter(Context context, List<StoreResource> storeResources) {
        super(context, storeResources);
    }

    @Override
    public int getLayoutId(int viewType) {
        return 0;
    }

    @Override
    public int getMultiItemViewType(int position) {
        if (position == 0) {
            return KEY_BANNER;
        } else if (position == 1) {
            return KEY_HOT_TAG;
        } else {
            return KEY_RESOURCE;
        }
    }

    @Override
    public BasicViewHolder getViewHolder(View itemView, int viewType) {
        if (KEY_HOT_TAG == viewType) {
            return new StoreAdapter.HotTagHolder(itemView);
        } else {
            return new StoreAdapter.ResourceHolder(itemView);
        }
    }

    @Override
    public void onBindItem(BasicViewHolder holder, StoreResource storeResource, int position) throws ParseException {
//        if (position == 1) {
//            setHotTags(holder);//标签列表数据展示
//        } else if (position == 0)
//            setitemData(holder, mTop); //top展示
//        else
//            setitemData(holder, resou);//item展示
    }


    public static class HotTagHolder extends BasicViewHolder {

        public HotTagHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ResourceHolder extends BasicViewHolder {

        public ResourceHolder(View itemView) {
            super(itemView);
        }
    }
}
