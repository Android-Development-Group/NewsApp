package com.myself.leancloudlibrary;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Description: ErrorCode操作提示
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/3/7 13:17.
 */

public class TipsUtils {
    public static final String TAG = TipsUtils.class.getSimpleName();

    public static void getPrompt(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void getPrompt(Context context, int code) {
        String description = null;
        if (code != -1) {
            switch (code) {
                case 216:
                    description = context.getString(R.string.errcode_216);
                    break;
                case 1109002:
                    description = context.getString(R.string.errcode_216);
                    break;

                default:
                    description = context.getString(R.string.errcode_70000);
            }
            Log.d(TAG, "####-getPrompt: code-->" + code + "--description-->" + description);
            Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
        }
    }
}
