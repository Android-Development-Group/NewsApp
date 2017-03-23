package com.myself.library.base;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Activity管理工具类,将activity放入栈统一管理
 * Created by guchenkai on 2015/10/22.
 */
public class ActivityManager {
    //activity管理栈
    private volatile Stack<Activity> activityStack;
    //全局单例
    private static volatile ActivityManager instance;

    public ActivityManager() {
        activityStack = new Stack<>();
    }

    /**
     * 单例
     *
     * @return ActivityManager实例
     */
    public static ActivityManager getInstance() {
        if (instance == null)
            instance = new ActivityManager();
        return instance;
    }

    /**
     * 添加activity到管理栈
     *
     * @param activity activity
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 获取管理栈顶的activity
     *
     * @return 栈顶的activity
     */
    public Activity getCurrentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 将指定的activity移出管理堆栈
     */
    public void removeCurrentActivity() {
        Activity activity = getCurrentActivity();
        if (activity != null)
            activityStack.remove(activity);
    }


    /**
     * 结束当前的activity
     */
    public void finishCurrentActivity() {
        getCurrentActivity().finish();
    }

    /**
     * 结束指定的activity
     *
     * @param activity activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的activity(class方式)
     *
     * @param activityClass activityClass
     */
    public void finishActivity(Class<? extends Activity> activityClass) {
        for (Activity activity : activityStack) {
            if (activityClass.equals(activity.getClass()))
                finishActivity(activity);
        }
    }

    /**
     * 是否存在指定的activity(class方式)
     *
     * @param activityClass activityClass
     */
    public boolean hasActivity(Class<? extends Activity> activityClass) {
        for (Activity activity : activityStack) {
            if (activityClass.equals(activity.getClass()))
                return true;
        }
        return false;
    }


    /**
     * 退出栈中其他所有Activity
     *
     * @param cls activityClass
     */
    public void popOtherActivity(Class<? extends Activity>... cls) {
        if (null == cls) return;
        for (Activity activity : activityStack) {
            boolean isActExist = false;
            for (int i = 0; i < cls.length; i++) {
                if (null == activity || activity.getClass().equals(cls[i])) {
                    isActExist = true;
                    break;
                }
            }
            if (!isActExist)
                activity.finish();
        }
    }

    /**
     * 结束所有的activity
     */
    public void finishAllActivity() {
        while (activityStack.size() > 0 && getCurrentActivity() != null) {
            finishActivity(getCurrentActivity());
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void killProcess(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
//            MobclickAgent.onKillProcess(context);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
