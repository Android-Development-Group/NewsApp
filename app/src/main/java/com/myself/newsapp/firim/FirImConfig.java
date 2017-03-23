package com.myself.newsapp.firim;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

/**
 * Description: Fir.im获取版本信息
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/3/23 11:46.
 */

public class FirImConfig {
    public static final String TAG = FirImConfig.class.getName();

    private static FirInfoBean mBean = null;

    /**
     * Fir.im获取版本信息测试(FIR)
     */
    public static FirInfoBean getFirAppVersionInfo(final Context context, String firimApiToken) {
        FIR.checkForUpdateInFIR(firimApiToken, new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                Log.i("FIR", "check from fir.im success! " + "\n" + versionJson);
                mBean = new Gson().fromJson(versionJson, FirInfoBean.class);
                Log.d(TAG, "name------->" + mBean.getName() + "\n" +
                        "version----->" + mBean.getVersionShort() + "\n" +
                        "changelog--->" + mBean.getChangelog() + "\n" +
                        "installUrl-->" + mBean.getInstallUrl());
            }

            @Override
            public void onFail(Exception exception) {
                Log.i("FIR", "check fir.im fail! " + "\n" + exception.getMessage());
                Toast.makeText(context, "您的网络不给力，请稍后重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart() {
                Toast.makeText(context, "正在获取", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                if (null != mBean)
                    Toast.makeText(context, "当前版本：" + mBean.getVersionShort(), Toast.LENGTH_SHORT).show();
            }
        });
        return mBean;
    }
}
