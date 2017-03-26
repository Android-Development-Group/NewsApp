package com.myself.library.view.recycler.listener;

import java.io.Serializable;

/**
 * item点击事件
 * Created by guchenkai on 2015/11/9.
 */
public interface OnItemClickListener<Item extends Serializable> {

    void onItemClick(Item item, int position);
}
