package com.myself.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.myself.newsapp.firim.FirImConfig;
import com.myself.newsapp.firim.FirInfoBean;
import com.myself.newsapp.jninative.NativeLib;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirInfoBean infoBean = FirImConfig.getFirAppVersionInfo(this, TotalApplication.FIR_API_TOKEN);

        NativeLib nativeLib = new NativeLib();
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(nativeLib.stringFromJNI());
    }


}
