package com.myself.newsapp;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
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
    private static final String TAG = "NewsApp_Log";
    public static final String FIR_API_TOKEN = "1b91eb3eaaea5f64ed127882014995dd";
    public static final String LEANCLOUD_APP_ID = "fNshYwdYYVigz6EWN0fbyJXz-gzGzoHsz";
    public static final String LEANCLOUD_APP_KEY = "nGC0m8TuRcnVpHlvNA3N85Mx";

    @Override
    public void onCreate() {
        super.onCreate();

        //Fir-SDk配置
        FIR.init(this);

        //LeanCloud初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, LEANCLOUD_APP_ID, LEANCLOUD_APP_KEY);
        //Debug模式SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(isDebug);
        //Crash上报
        AVAnalytics.enableCrashReport(this, true);

    }

    @Override
    protected void initEnvironment() {
        //初始化Service Api
        BaseApi.init(BaseApi.HOST_TEST);
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

//        app id-ad473298bc
//        app key-664c08fa-2104-4be8-9d1e-0c443ac9e81f
        return "ad473298bc";
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
