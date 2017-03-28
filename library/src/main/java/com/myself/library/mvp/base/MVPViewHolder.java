package com.myself.library.mvp.base;

import android.support.v7.widget.RecyclerView;

/**
 * Created by riven_chris on 16/7/1.
 */
public abstract class MVPViewHolder<T> /*extends BasicViewHolder*/ {

//    public MVPViewHolder(View itemView) {
//        super(itemView);
//    }

    public abstract void onBindViewHolder(RecyclerView.Adapter adapter, T data, int position);
}
