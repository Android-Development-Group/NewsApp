package com.myself.library.utils;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * Created by sunnybear on 15/3/13.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = CrashHandler.class.getSimpleName();

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    //自定义异常处理回调
    private OnCrashHandler mOnCrashHandler;
    //用户名
//    private static String username;
    //格式化日期,作为日志文件名的一部分
//    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     *
     * @return CrashHandler实例
     */
    public static CrashHandler instance() {
        return INSTANCE;
    }

    /**
     * 初始化
     */
    public void init(/*String username,*/ OnCrashHandler onCrashHandler) {
//        CrashHandler.username = username;
        mOnCrashHandler = onCrashHandler;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (handlerException(ex) && mDefaultHandler != null)
            mDefaultHandler.uncaughtException(thread, ex);//如果用户没有处理则让系统默认的异常处理器来处理
//        String phoneInfo = getPhoneInfo(mContext);//获取设备信息
//        File log = saveCrashInfo2File(phoneInfo, ex);
        if (mOnCrashHandler != null)
            mOnCrashHandler.onCrashHandler(/*log, phoneInfo,*/ ex);
    }

//    /**
//     * 获取设备信息
//     *
//     * @param context context
//     * @return 设备信息
//     */
//    private String getPhoneInfo(Context context) {
//        StringBuffer buffer = new StringBuffer();
//        Properties phoneInfo = PhoneManager.collectDeviceInfo(context);
//        Enumeration en = phoneInfo.propertyNames();
//        while (en.hasMoreElements()) {
//            String key = en.nextElement().toString();
//            String value = phoneInfo.getProperty(key);
//            buffer.append(key).append("=").append(value).append("\n");
//        }
//        int len = buffer.length();
//        return buffer.delete(len - 1, len).toString();
//    }
//
//    /**
//     * 保存错误信息到文件中
//     *
//     * @param phoneInfo 设备信息
//     * @param ex        异常
//     * @return log文件
//     */
//    private static File saveCrashInfo2File(String phoneInfo, Throwable ex) {
//        StringBuffer sb = new StringBuffer("设备信息:\n").append(phoneInfo).append("\n").append("异常信息:\n");
//        Writer writer = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(writer);
//        ex.printStackTrace(printWriter);
//        Throwable cause = ex.getCause();
//        while (cause != null) {
//            cause.printStackTrace(printWriter);
//            cause = cause.getCause();
//        }
//        printWriter.close();
//        String result = writer.toString();
//        sb.append(result);
//        try {
//            //保存文件
//            String path = "";
//            String fileName = "crash-" + username + "-"
//                    + format.format(Calendar.getInstance().getTime()) + ".log";
//            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//                path = Environment.getExternalStorageDirectory() + "/crash_log/";
//                File dir = new File(path);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                FileOutputStream fos = new FileOutputStream(path + fileName);
//                fos.write(sb.toString().getBytes());
//                fos.close();
//            }
//            return new File(path + fileName);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 异常
     * @return 如果处理了该异常信息;否则返回true.
     */
    private boolean handlerException(Throwable ex) {
        return false;
    }

    /**
     * 自定义异常处理回调
     */
    public interface OnCrashHandler {

        void onCrashHandler(/*File log, String phoneInfo,*/ Throwable ex);
    }
}
