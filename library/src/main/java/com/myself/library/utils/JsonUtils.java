package com.myself.library.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * json工具类
 * Created by guchenkai on 2015/10/27.
 */
public final class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    /**
     * 判断json类型
     *
     * @param json json
     * @return json类型
     */
    public static JsonType getJSONType(String json) {
        if (TextUtils.isEmpty(json))
            return JsonType.JSON_TYPE_ERROR;
        final char[] strChar = json.substring(0, 1).toCharArray();
        final char firstChar = strChar[0];
        switch (firstChar) {
            case '{':
                return JsonType.JSON_TYPE_OBJECT;
            case '[':
                return JsonType.JSON_TYPE_ARRAY;
            default:
                return JsonType.JSON_TYPE_ERROR;
        }
    }

    /**
     * json类型枚举
     */
    public enum JsonType {
        JSON_TYPE_OBJECT,//JSONObject
        JSON_TYPE_ARRAY,//JSONArray
        JSON_TYPE_ERROR//不是JSON格式的字符串
    }

    /**
     * 格式化json
     *
     * @param json json
     * @return 格式化后的json
     */
    public static String jsonFormatter(String json) throws NullPointerException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        String formatJson = gson.toJson(je);
        return formatJson;
    }

    /**
     * json格式化
     *
     * @param jsonString json数据源
     * @param paramClass 序列化对象
     * @param <T>
     * @return
     */
    public static <T> T parseData(String jsonString, Class<T> paramClass) {
        return new Gson().fromJson(jsonString, paramClass);
    }
}
