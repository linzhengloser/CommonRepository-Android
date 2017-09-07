package com.lz.rxjava2demo.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.lz.rxjava2demo.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/09/07
 *     desc   : 缓存管理类
 *     version: 1.0
 * </pre>
 */
public class CacheUtils {

    private static final SharedPreferences CACHE_SHARED_PREFERENCES;

    static {
        CACHE_SHARED_PREFERENCES = MyApplication.getInstance().getSharedPreferences("cache", Context.MODE_PRIVATE);
    }

    public static void put(String key, Object value) {
        SharedPreferences.Editor editor = CACHE_SHARED_PREFERENCES.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.commit();
    }

    public static String getString(String key) {
        return CACHE_SHARED_PREFERENCES.getString(key, "");
    }

    public static boolean getBoolean(String key) {
        return CACHE_SHARED_PREFERENCES.getBoolean(key, false);
    }

    public static int getInteger(String key) {
        return CACHE_SHARED_PREFERENCES.getInt(key, 0);
    }
}
