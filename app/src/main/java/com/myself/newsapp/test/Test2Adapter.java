package com.myself.newsapp.test;

import android.content.Context;
import android.view.View;

import com.myself.library.view.recycler.BasicViewHolder;
import com.myself.library.view.recycler.adapter.BasicAdapter;

import java.text.ParseException;
import java.util.List;

/**
 * Description:
 * Author     : Jusenr
 * Email      : jusenr@163.com
 * Date       : 2017/09/27  22:22.
 */

public class Test2Adapter extends BasicAdapter<String, Test2Adapter.Test2ViewHolder> {

    public Test2Adapter(Context context, List<String> strings) {
        super(context, strings);
    }

    @Override
    public int getLayoutId(int viewType) {
        return 0;
    }

    @Override
    public Test2ViewHolder getViewHolder(View itemView, int viewType) {
        return new Test2ViewHolder(itemView);
    }

    @Override
    public void onBindItem(Test2ViewHolder holder, String s, int position) throws ParseException {

    }

    class Test2ViewHolder extends BasicViewHolder {

        public Test2ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
