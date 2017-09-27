package com.myself.newsapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.myself.library.view.recycler.BasicRecyclerView;
import com.myself.newsapp.R;

public class Test2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);


        BasicRecyclerView brv_view = (BasicRecyclerView) findViewById(R.id.brv_view);
        Test2Adapter adapter = new Test2Adapter(this, null);


        TextView tv_1 = (TextView) findViewById(R.id.tv_0);
        Object tag = tv_1.getTag();
        Log.e("qwe", "onCreate: ");
    }


}
