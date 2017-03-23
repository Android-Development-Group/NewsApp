package com.myself.library.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logger is a wrapper of {@link Log}
 * But more pretty, simple and powerful
 *
 * @author Orhan Obut
 */
public final class Logger {

    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 4;

    /**
     * In order to prevent readability, max method count is restricted with 5
     */
    private static final int MAX_METHOD_COUNT = 5;

    /**
     * It is used to determine log settings such as method count, thread info visibility
     */
    private static final Settings settings = new Settings();

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    /**
     * TAG is used for the Log, the name is a little different
     * in order to differentiate the logs easily with the filter
     */
    private static String TAG = "PRETTYLOGGER";

    /**
     * logFileName is used for the local save log file name.
     */
    private static String logFileName;

    /**
     * logWriter is used for the local save log file.
     */
    private static FileWriter logWriter;

    //no instance
    private Logger() {
    }

    /**
     * It is used to get the settings object in order to change settings
     *
     * @return the settings object
     */
    public static Settings init() {
        return settings;
    }

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger
     */
    public static Settings init(String tag, String sdCardPath) {
        if (tag == null) {
            throw new NullPointerException("tag may not be null");
        }
        if (tag.trim().length() == 0) {
            throw new IllegalStateException("tag may not be empty");
        }

        logFileName = sdCardPath + File.separator + "log";
        openLogFile();
        Logger.TAG = tag;
        return settings;
    }

    public static void d(String message) {
        d(TAG, message);
    }

    public static void d(String tag, String message) {
        d(tag, message, settings.methodCount);
    }

    public static void dSave(String tag, String message) {
        log(Log.DEBUG, tag, message, settings.methodCount, settings.saveLog);
    }

    public static void d(String message, int methodCount) {
        d(TAG, message, methodCount);
    }

    public static void d(String tag, String message, int methodCount) {
        validateMethodCount(methodCount);
        log(Log.DEBUG, tag, message, methodCount, false);
    }

    public static void e(String message) {
        e(TAG, message);
    }

    public static void e(String tag, String message) {
        e(tag, message, null, settings.methodCount);
    }

    public static void eSave(String tag, String message) {
        log(Log.ERROR, tag, message, settings.methodCount, settings.saveLog);
    }

    public static void e(Exception e) {
        e(TAG, null, e, settings.methodCount);
    }

    public static void e(String tag, Exception e) {
        e(tag, null, e, settings.methodCount);
    }

    public static void e(String message, int methodCount) {
        validateMethodCount(methodCount);
        e(message, null, methodCount);
    }

    public static void e(String tag, String message, int methodCount) {
        validateMethodCount(methodCount);
        e(tag, message, null, methodCount);
    }

    public static void e(String tag, String message, Exception e) {
        e(tag, message, e, settings.methodCount);
    }

    public static void e(String tag, String message, Exception e, int methodCount) {
        validateMethodCount(methodCount);
        if (e != null && message != null) {
            message += " : " + e.toString();
        }
        if (e != null && message == null) {
            message = e.toString();
        }
        if (message == null) {
            message = "No message/exception is set";
        }
        log(Log.ERROR, tag, message, methodCount, false);
    }

    public static void w(String message) {
        w(TAG, message);
    }

    public static void w(String tag, String message) {
        w(tag, message, settings.methodCount);
    }

    public static void wSave(String tag, String message) {
        log(Log.WARN, tag, message, settings.methodCount, settings.saveLog);
    }

    public static void w(String message, int methodCount) {
        w(TAG, message, methodCount);
    }

    public static void w(String tag, String message, int methodCount) {
        validateMethodCount(methodCount);
        log(Log.WARN, tag, message, methodCount, false);
    }

    public static void i(String message) {
        i(TAG, message);
    }

    public static void i(String tag, String message) {
        i(tag, message, settings.methodCount);
    }

    public static void iSave(String tag, String message) {
        log(Log.INFO, tag, message, settings.methodCount, settings.saveLog);
    }

    public static void i(String message, int methodCount) {
        i(TAG, message, methodCount);
    }

    public static void i(String tag, String message, int methodCount) {
        validateMethodCount(methodCount);
        log(Log.INFO, tag, message, methodCount, false);
    }

    public static void v(String message) {
        v(TAG, message);
    }

    public static void v(String tag, String message) {
        v(tag, message, settings.methodCount);
    }

    public static void vSave(String tag, String message) {
        log(Log.VERBOSE, tag, message, settings.methodCount, settings.saveLog);
    }

    public static void v(String message, int methodCount) {
        v(TAG, message, methodCount);
    }

    public static void v(String tag, String message, int methodCount) {
        validateMethodCount(methodCount);
        log(Log.VERBOSE, tag, message, methodCount, false);
    }

    public static void wtf(String message) {
        wtf(TAG, message);
    }

    public static void wtf(String tag, String message) {
        wtf(tag, message, settings.methodCount);
    }

    public static void wtf(String message, int methodCount) {
        wtf(TAG, message, methodCount);
    }

    public static void wtf(String tag, String message, int methodCount) {
        validateMethodCount(methodCount);
        log(Log.ASSERT, tag, message, methodCount, false);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        json(TAG, json);
    }

    public static void json(String tag, String json) {
        json(tag, json, settings.methodCount);
    }

    public static void json(String json, int methodCount) {
        json(TAG, json, methodCount);
    }

    /**
     * Formats the json content and print it
     *
     * @param json        the json content
     * @param methodCount number of the method that will be printed
     */
    public static void json(String tag, String json, int methodCount) {
        validateMethodCount(methodCount);
        if (TextUtils.isEmpty(json)) {
            d(tag, "Empty/Null json content", methodCount);
            return;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(tag, message, methodCount);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(tag, message, methodCount);
            }
        } catch (JSONException e) {
            d(tag, e.getCause().getMessage() + "\n" + json, methodCount);
        }
    }

    private static void log(int logType, String tag, String message, int methodCount, boolean save2File) {
        if (settings.logLevel == LogLevel.NONE) {
            return;
        }
        logTopBorder(logType, tag);
        logHeaderContent(logType, tag, methodCount, save2File);

        //get bytes of message with system's default charset (which is UTF-8 for Android)
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        if (length <= CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(logType, tag);
            }
            logContent(logType, tag, message, save2File);
            logBottomBorder(logType, tag);
            return;
        }
        if (methodCount > 0) {
            logDivider(logType, tag);
        }
        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            //create a new String with system's default charset (which is UTF-8 for Android)
            logContent(logType, tag, new String(bytes, i, count), save2File);
        }
        logBottomBorder(logType, tag);
    }

    private static void logTopBorder(int logType, String tag) {
        logChunk(logType, tag, TOP_BORDER);
    }

    private static void logHeaderContent(int logType, String tag, int methodCount, boolean save2File) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (settings.showThreadInfo) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().getName());
            logDivider(logType, tag);
        }
        String level = "";
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + 5;
            StringBuilder builder = new StringBuilder();
            builder.append("║ ")
                    .append(level)
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            level += "   ";
            logChunk(logType, tag, builder.toString());

            if (save2File) {
                saveLog2File(logType, tag, builder.toString());
            }
        }
    }

    private static void logBottomBorder(int logType, String tag) {
        logChunk(logType, tag, BOTTOM_BORDER);
    }

    private static void logDivider(int logType, String tag) {
        logChunk(logType, tag, MIDDLE_BORDER);
    }

    private static void logContent(int logType, String tag, String chunk, boolean save2File) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
            if (save2File) {
                saveLog2File(logType, tag, line);
            }
        }
    }

    private static void logChunk(int logType, String tag, String chunk) {
        String finalTag = formatTag(tag);
        switch (logType) {
            case Log.ERROR:
                Log.e(finalTag, chunk);
                break;
            case Log.INFO:
                Log.i(finalTag, chunk);
                break;
            case Log.VERBOSE:
                Log.v(finalTag, chunk);
                break;
            case Log.WARN:
                Log.w(finalTag, chunk);
                break;
            case Log.ASSERT:
                Log.wtf(finalTag, chunk);
                break;
            case Log.DEBUG:
                // Fall through, log debug by default
            default:
                Log.d(finalTag, chunk);
                break;
        }
    }

    private static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private static void validateMethodCount(int methodCount) {
        if (methodCount < 0 || methodCount > MAX_METHOD_COUNT) {
            throw new IllegalStateException("methodCount must be > 0 and < 5");
        }
    }

    private static String formatTag(String tag) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.equals(TAG, tag)) {
            return TAG + "-" + tag;
        }
        return TAG;
    }

    public static class Settings {
        int methodCount = 2;
        boolean showThreadInfo = true;
        boolean saveLog = false;

        /**
         * Determines how logs will printed
         */
        LogLevel logLevel = LogLevel.FULL;

        public Settings hideThreadInfo() {
            showThreadInfo = false;
            return this;
        }

        public Settings setSaveLog(boolean saveLog) {
            this.saveLog = saveLog;
            return this;
        }

        public Settings setMethodCount(int methodCount) {
            validateMethodCount(methodCount);
            this.methodCount = methodCount;
            return this;
        }

        public Settings setLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }
    }

    public enum LogLevel {
        FULL, NONE
    }

    public static String getLogFileName() {
        return logFileName;
    }

    private static void saveLog2File(int logType, String tag, String message) {
        if (logWriter == null) {
            openLogFile();
        }
        try {
            logWriter.write(buildFormatLog(logType, tag, message));
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * open local log file
     */
    public static void openLogFile() {
        if (logFileName != null) {
            try {
                File logFile = new File(logFileName);
                boolean isNew = false;
                if (logFile.exists()) {
                    BufferedReader reader = new BufferedReader(new FileReader(logFile));
                    String firstLine = reader.readLine();
                    long createTime = Long.valueOf(firstLine);
                    reader.close();
                    //超过7天，删除log文件
                    if ((System.currentTimeMillis() - createTime) > (7 * 24 * 60 * 60 * 1000)) {
                        logFile.delete();
                        isNew = true;
                    }
                } else {
                    isNew = true;
                }

                logWriter = new FileWriter(logFileName, true);
                if (isNew) {
                    logWriter.write(String.valueOf(System.currentTimeMillis()) + "\n");
                    logWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * close local log file
     */
    public static void closeLogFile() {
        if (logWriter != null) {
            try {
                logWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * format save log
     *
     * @param logType
     * @param tag
     * @param message
     * @return
     */
    public static String buildFormatLog(int logType, String tag, String message) {
        String timeStamp = new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date());
        String type = "";
        switch (logType) {
            case Log.ERROR:
                type = "E";
                break;
            case Log.INFO:
                type = "I";
                break;
            case Log.VERBOSE:
                type = "V";
                break;
            case Log.WARN:
                type = "W";
                break;
            case Log.ASSERT:
                type = "A";
                break;
            case Log.DEBUG:
                // Fall through, log debug by default
            default:
                type = "D";
                break;
        }
        String finalTag = formatTag(tag);
        StringBuilder builder = new StringBuilder();
        builder.append(timeStamp)
                .append(" ")
                .append(type)
                .append("/")
                .append(finalTag)
                .append(": ")
                .append(message)
                .append("\n");
        return builder.toString();
    }
}
