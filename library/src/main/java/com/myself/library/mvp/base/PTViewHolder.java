package com.myself.library.mvp.base;

import android.support.v7.widget.RecyclerView;

/**
 * Created by riven_chris on 16/7/1.
 */
public abstract class PTViewHolder<T> /*extends BasicViewHolder*/ {

//    public PTViewHolder(View itemView) {
//        super(itemView);
//    }

    public abstract void onBindViewHolder(RecyclerView.Adapter adapter, T data, int position);
}
