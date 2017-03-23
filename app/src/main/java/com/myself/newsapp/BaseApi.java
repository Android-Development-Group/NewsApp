package com.myself.newsapp;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/3/23 15:12.
 */

public abstract class BaseApi {

    public static final int HOST_FORMAL = 1;//正式环境
    public static final int HOST_DEV = 2;//开发环境
    public static final int HOST_TEST = 3;//测试环境

    public static int HOST_NOW;//当前环境

    //e.g.
    public static String WEB_RTC = "";


    /**
     * environment: 1，外网 2，开发内网，3，测试内网
     */
    public static void init(int environment) {
        HOST_NOW = environment;
        switch (environment) {
            case 1:
                WEB_RTC = "";
                break;
            case 2:
                WEB_RTC = "";
                break;
            case 3:
                WEB_RTC = "";
                break;
        }
    }


    public static boolean isInnerEnvironment() {
        return HOST_NOW == HOST_DEV || HOST_NOW == HOST_TEST;
    }


}
