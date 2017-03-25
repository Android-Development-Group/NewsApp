package com.myself.newsapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.myself.library.controller.BaseActivity;
import com.myself.newsapp.account.AccountHelper;
import com.myself.newsapp.guidance.GuidanceActivity;
import com.myself.newsapp.jninative.NativeLib;
import com.myself.newsapp.user.LoginActivity;

import butterknife.BindView;

/**
 * 主页
 * Created by Jusenr on 2017/03/25.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.sample_text)
    TextView mSampleText;
//    @BindView(R.id.tv_text)
//    TextView mTvText;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
//        addNavigation();

//        FirInfoBean infoBean = FirImConfig.getFirAppVersionInfo(this, TotalApplication.FIR_API_TOKEN);

        NativeLib nativeLib = new NativeLib();
        mSampleText.setText(nativeLib.stringFromJNI());


        if (!AccountHelper.isLogin()) {
            startActivity(LoginActivity.class);
            this.finish();
        }

//        testLeanCloudSDK();
    }

//    @Override
//    public void onLeftAction() {
//        startActivity(TestActivity.class);
//    }
//
//    @Override
//    public void onRightAction() {
//        super.onRightAction();
//        logOut();
//    }

//    @OnClick({R.id.tv_text})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_text:
//
//                break;
//        }
//    }

    /**
     * 注销
     */
    private void logOut() {
        AccountHelper.logout();
        startActivity(GuidanceActivity.class);
        MainActivity.this.finish();
    }

    /**
     * 测试SDK是否正常工作
     */
    private void testLeanCloudSDK() {
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
    }
}
