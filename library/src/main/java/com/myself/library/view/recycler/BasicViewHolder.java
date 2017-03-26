package com.myself.library.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * 重新封装ViewHolder
 * Created by guchenkai on 2015/11/9.
 */
public class BasicViewHolder extends RecyclerView.ViewHolder {

    public BasicViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
