package com.myself.newsapp.jninative;

/**
 * Description: nativelib-lib
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/3/23 10:44.
 */

public class NativeLib {
    public static final String TAG = NativeLib.class.getSimpleName();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    // Example of a call to a nativelib method

    /**
     * A nativelib method that is implemented by the 'native-lib' nativelib library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
