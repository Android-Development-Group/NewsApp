package com.myself.library.controller;

import android.app.Application;
import android.content.Context;

import com.myself.library.utils.AppUtils;
import com.myself.library.utils.CrashHandler;
import com.myself.library.utils.DiskFileCacheHelper;
import com.myself.library.utils.Logger;
import com.myself.library.utils.hawk.Hawk;
import com.myself.library.utils.hawk.LogLevel;
import com.tencent.bugly.Bugly;

import butterknife.ButterKnife;

/**
 * Description: Application
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/12/27 16:05.
 */

public abstract class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    public static final String FIR_API_TOKEN = "1b91eb3eaaea5f64ed127882014995dd";
    private static final String KEY_APP_ID = "app_id";
    private static Context mContext;
    //    private static OkHttpClient mOkHttpClient;//OkHttpClient
    private static int maxAge;//网络缓存最大时间

    private static DiskFileCacheHelper mDiskFileCacheHelper;//磁盘文件缓存器

    public static String app_id;
    public static String app_device_id;
    public static boolean isDebug;
    public static String sdCardPath;//SdCard路径

    @Override
    public void onCreate() {
        super.onCreate();
        initEnvironment();
        isDebug = isDebug();
        mContext = getApplicationContext();
        //sdCard缓存路径
        sdCardPath = getSdCardPath();
        //ButterKnife的Debug模式
        ButterKnife.setDebug(isDebug);
        //偏好设置文件初始化
        Hawk.init(getApplicationContext(), getPackageName(), isDebug ? LogLevel.FULL : LogLevel.FULL);
        //日志输出
        Logger.init(getLogTag(), sdCardPath)
                .hideThreadInfo()
                .setLogLevel(isDebug ? Logger.LogLevel.FULL : Logger.LogLevel.FULL)
                .setSaveLog(true);
        //OkHttp初始化
//        mOkHttpClient = initOkHttpClient();

        //开启bugly
        Bugly.init(getApplicationContext(), getBuglyKey(), isDebug);
        //网络缓存最大时间
        maxAge = getNetworkCacheMaxAgeTime();
        //磁盘文件缓存器
        mDiskFileCacheHelper = DiskFileCacheHelper.get(getApplicationContext(), getLogTag());
        //数据库调试
//        QueryBuilder.LOG_SQL = isDebug;
//        QueryBuilder.LOG_VALUES = isDebug;
        //app_id配置
        app_id = AppUtils.getMetaData(getApplicationContext(), KEY_APP_ID);
        app_device_id = appDeviceId();
        //捕捉系统崩溃异常
        CrashHandler.instance().init(new CrashHandler.OnCrashHandler() {
            @Override
            public void onCrashHandler(Throwable ex) {
                onCrash(ex);
            }
        });
    }

    public static Context getInstance() {
        return mContext;
    }

//    public static OkHttpClient getOkHttpClient() {
//        return mOkHttpClient;
//    }

    public static int getMaxAge() {
        return maxAge;
    }

//    public static DiskFileCacheHelper getDiskFileCacheHelper() {
//        return mDiskFileCacheHelper;
//    }

    /**
     * 设置腾讯bugly的AppKey
     *
     * @return 腾讯bugly的AppKey
     */
    protected abstract String getBuglyKey();

    /**
     * debug模式
     *
     * @return 是否开启
     */
    protected abstract void initEnvironment();

    /**
     * 填写工程包名
     *
     * @return 工程包名
     */
    public abstract String getPackageName();

    /**
     * 设置调试日志标签名
     *
     * @return 调试日志标签名
     */
    protected abstract String getLogTag();

    /**
     * 设置sdCard路径
     *
     * @return sdCard路径
     */
    protected abstract String getSdCardPath();

    /**
     * 网络缓存文件夹路径
     *
     * @return 缓存文件夹路径
     */
    protected abstract String getNetworkCacheDirectoryPath();

    /**
     * 网络缓存文件大小
     *
     * @return 缓存文件大小
     */
    protected abstract int getNetworkCacheSize();

    /**
     * 网络缓存最大时间
     *
     * @return 缓存最大时间
     */
    protected abstract int getNetworkCacheMaxAgeTime();

    /**
     * 异常信息处理
     *
     * @param ex 异常信息
     */
    protected abstract void onCrash(Throwable ex);


//    public abstract OkHttpClient initOkHttpClient();

    /**
     * 生成供应用使用的deviceId
     */
    public abstract String appDeviceId();

    protected abstract boolean isDebug();
}