package com.myself.newsapp.account;

import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.myself.library.utils.PreferenceUtils;
import com.myself.library.utils.StringUtils;
import com.myself.newsapp.user.UserInfoBean;
import com.myself.newsapp.util.Constants;

/**
 * 通行证助手
 * Created by Jusenr on 2017/3/25.
 */

public class AccountHelper {
    public static final String TAG = AccountHelper.class.getSimpleName();

    /**
     * 登录
     */
    public static void login() {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser != null) {
            String uuid = avUser.getUuid();
            Log.e(TAG, "login: uuid--" + uuid);
            if (!StringUtils.isEmpty(uuid))
                PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_UUID, uuid);

            String uid = avUser.getObjectId();
            if (!StringUtils.isEmpty(uid))
                PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_UID, uid);

            String sessionToken = avUser.getSessionToken();
            if (!StringUtils.isEmpty(sessionToken))
                PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_TOKEN, sessionToken);

            String username = avUser.getUsername();
            if (!StringUtils.isEmpty(username))
                PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_USERNAME, username);
        }
    }

    /**
     * 登出
     */
    public static void logout() {
        AVUser.getCurrentUser().logOut();
        PreferenceUtils.remove(Constants.SPKey.PREFERENCE_KEY_UUID);
        PreferenceUtils.remove(Constants.SPKey.PREFERENCE_KEY_UID);
        PreferenceUtils.remove(Constants.SPKey.PREFERENCE_KEY_TOKEN);
        PreferenceUtils.remove(Constants.SPKey.PREFERENCE_KEY_USERNAME);

    }

    /**
     * 是否在登录状态
     *
     * @return
     */
    public static boolean isLogin() {
        return !StringUtils.isEmpty(getCurrentUid()) && !StringUtils.isEmpty(getCurrentToken());
    }

    /**
     * 获取当前登录的Uid
     *
     * @return 当前登录的Uid
     */
    public static String getCurrentUid() {
        return PreferenceUtils.getValue(Constants.SPKey.PREFERENCE_KEY_UID, null);
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
        return PreferenceUtils.getValue(Constants.SPKey.PREFERENCE_KEY_TOKEN, null);
    }

    /**
     * 保存当前Token
     *
     * @param token
     */
    public static void setCurrentToken(String token) {
        PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_TOKEN, token);
    }

    /**
     * 设置当前userInfo
     *
     * @param userInfo
     */
    public static void setUserInfo(UserInfoBean userInfo) {
        if (userInfo != null)
            PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_USER_INFO, userInfo);
    }

    /**
     * 获取当前userInfo
     *
     * @return
     */
    public static UserInfoBean getCurrentUserInfo() {
        return PreferenceUtils.getValue(Constants.SPKey.PREFERENCE_KEY_USER_INFO, null);
    }
}