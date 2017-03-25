package com.myself.newsapp.account;

import com.avos.avoscloud.AVUser;
import com.myself.library.utils.PreferenceUtils;
import com.myself.library.utils.StringUtils;
import com.myself.newsapp.util.Constants;

/**
 * 通行证助手
 * Created by Jusenr on 2017/3/25.
 */

public class AccountHelper {


    /**
     * 登录
     */
    public static void login() {
        AVUser avUser = AVUser.getCurrentUser();
        String uid = avUser.getObjectId();

        if (!StringUtils.isEmpty(uid))
            PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_UID, uid);
    }

    /**
     * 登出
     */
    public static void logout() {
        AVUser.getCurrentUser().logOut();
        PreferenceUtils.remove(Constants.SPKey.PREFERENCE_KEY_UID);

    }

    /**
     * 是否在登录状态
     *
     * @return
     */
    public static boolean isLogin() {
        return !StringUtils.isEmpty(getCurrentUid())/* && !StringUtils.isEmpty(getCurrentToken())*/;
    }

    /**
     * 获取当前登录的Uid
     *
     * @return 当前登录的Uid
     */
    public static String getCurrentUid() {
        return PreferenceUtils.getValue(Constants.SPKey.PREFERENCE_KEY_UID, "");
    }

    /**
     * 保存当前Uid
     *
     * @param uid
     */
    public static void setCurrentUid(String uid) {
        PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_UID, uid);
    }

    /**
     * 获取当前的token
     *
     * @return 当前登录的Uid
     */
    public static String getCurrentToken() {
        return PreferenceUtils.getValue(Constants.SPKey.PREFERENCE_KEY_TOKEN, "");
    }

    /**
     * 保存当前Token
     *
     * @param token
     */
    public static void setCurrentToken(String token) {
        PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_TOKEN, token);
    }
}