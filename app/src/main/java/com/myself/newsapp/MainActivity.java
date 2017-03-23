package com.myself.newsapp;

import android.os.Bundle;
import android.widget.TextView;

import com.myself.newsapp.base.TitleActivity;
import com.myself.newsapp.firim.FirImConfig;
import com.myself.newsapp.firim.FirInfoBean;
import com.myself.newsapp.jninative.NativeLib;

public class MainActivity extends TitleActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();

        FirInfoBean infoBean = FirImConfig.getFirAppVersionInfo(this, TotalApplication.FIR_API_TOKEN);

        NativeLib nativeLib = new NativeLib();
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(nativeLib.stringFromJNI());
    }
}
