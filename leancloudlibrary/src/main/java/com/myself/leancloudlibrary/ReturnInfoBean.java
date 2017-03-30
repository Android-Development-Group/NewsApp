package com.myself.leancloudlibrary;

/**
 * Description: 返回信息
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/3/30 18:23.
 */

public class ReturnInfoBean {
    public int code;
    public Object msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ReturnInfoBean{" +
                "code=" + code +
                ", msg=" + msg +
                '}';
    }
}
