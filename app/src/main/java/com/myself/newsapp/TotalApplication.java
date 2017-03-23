package com.myself.newsapp;

import android.content.Context;

import com.myself.library.controller.ActivityManager;
import com.myself.library.controller.BaseApplication;
import com.myself.library.utils.Logger;
import com.myself.library.utils.SDCardUtils;

import java.io.File;

import im.fir.sdk.FIR;


/**
 * Description: BaseApplication
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/3/23 11:00.
 */

public class TotalApplication extends BaseApplication {
    private static final String TAG = "Jni-Test";
    public static final String FIR_API_TOKEN = "1b91eb3eaaea5f64ed127882014995dd";
    public static Context mContext;
    private static boolean isDebug;

    @Override
    public void onCreate() {
        super.onCreate();

        //Fir-SDk配置
        FIR.init(this);

    }

    @Override
    protected void initEnvironment() {
        //初始化Service Api
        BaseApi.init(BaseApi.HOST_FORMAL);
    }

    @Override
    public String appDeviceId() {
        return null;
    }

    @Override
    protected boolean isDebug() {
        //根据需求更改
        return BaseApi.isInnerEnvironment();
    }

    @Override
    protected String getBuglyKey() {
        return "123456789";
    }

    @Override
    public String getPackageName() {
        return "com.myself.newsapp";
    }

    @Override
    protected String getLogTag() {
        return "NewsApp_log";
    }

    @Override
    protected String getSdCardPath() {
        return SDCardUtils.getSDCardPath() + File.separator + getLogTag();
    }

    @Override
    protected String getNetworkCacheDirectoryPath() {
        return sdCardPath + File.separator + "http_cache";
    }

    @Override
    protected int getNetworkCacheSize() {
        return 20 * 1024 * 1024;
    }

    @Override
    protected int getNetworkCacheMaxAgeTime() {
        return 0;
    }

    /**
     * 捕捉到异常就退出App
     *
     * @param ex 异常信息
     */
    @Override
    protected void onCrash(Throwable ex) {
        Logger.e("APP崩溃了,错误信息是" + ex.getMessage());
        ex.printStackTrace();
        ActivityManager.getInstance().finishAllActivity();
    }
}
