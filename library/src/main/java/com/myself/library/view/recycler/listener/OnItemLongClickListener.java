package com.myself.library.view.recycler.listener;

import java.io.Serializable;

/**
 * item长按事件
 * Created by guchenkai on 2015/11/9.
 */
public interface OnItemLongClickListener<Item extends Serializable> {

    void onItemLongClick(Item item, int position);
}
