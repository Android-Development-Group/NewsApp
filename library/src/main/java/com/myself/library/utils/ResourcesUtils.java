package com.myself.library.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Resources资源文件工具
 * Created by guchenkai on 2015/11/3.
 */
public final class ResourcesUtils {

    /**
     * 读取Assets文本资源内容
     *
     * @param resName 文件名
     * @return
     */
    public static String getAssetsTextFile(Context context, String resName) {
        String protocal = null;
        try {
            InputStreamReader reader = new InputStreamReader(getAssets(context).open(resName));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            String Result = "";
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i == 0) Result = line;
                else Result = Result + "\n" + line;
                i++;
            }
            return Result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return protocal;
    }

    /**
     * 获取图片资源的BitmapDrawable
     *
     * @param resId
     * @return
     */
    public static BitmapDrawable getBitmapDrawable(Context context, int resId) {
        return (BitmapDrawable) context.getResources().getDrawable(resId);
    }

    /**
     * 根据字符串资源id获得字符串资源
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 根据字符串数组资源id获得字符串数组资源(Array)
     *
     * @param context
     * @param resId
     * @return
     */
    public static String[] getStringArray(Context context, int resId) {
        return context.getResources().getStringArray(resId);
    }

    /**
     * 根据字符串数组资源id获得字符串数组资源(List)
     *
     * @param context
     * @param resId
     * @return
     */
    public static List<String> getStringList(Context context, int resId) {
        List<String> strings = new ArrayList<String>();
        String[] strs = getStringArray(context, resId);
        for (String string : strs) {
            strings.add(string);
        }
        return strings;
    }

    /**
     * 根据图片资源id获取资源图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }

    /**
     * 根据图片资源id获取资源图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap getBitmap(Context context, int resId) {
        Drawable drawable = getDrawable(context, resId);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        return bitmapDrawable.getBitmap();
    }

    /**
     * 获取Asset资源管理器
     *
     * @param context
     * @return
     */
    public static AssetManager getAssets(Context context) {
        return context.getResources().getAssets();
    }
}
