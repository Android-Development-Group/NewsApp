package com.myself.newsapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.myself.newsapp.base.TitleActivity;
import com.myself.newsapp.firim.FirImConfig;
import com.myself.newsapp.firim.FirInfoBean;
import com.myself.newsapp.jninative.NativeLib;
import com.myself.newsapp.user.LoginActivity;

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


        if (AVUser.getCurrentUser() == null) {
            startActivity(LoginActivity.class);
            this.finish();
        }

        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("name", "张文");
        testObject.put("gender", "男");
        testObject.put("birthday", "1993-02-05");
        testObject.put("height", "175.5cm");
        testObject.put("weight", "66kg");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("saved", "success!");
                }
            }
        });

        //注销
//        AVUser.getCurrentUser().logOut();
//        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        MainActivity.this.finish();

    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        AVUser.getCurrentUser().logOut();
        startActivity(LoginActivity.class);
        this.finish();
    }
}
