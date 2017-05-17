package com.risenb.honourfamily.utils;

import android.content.Context;
import android.widget.Toast;

import com.risenb.honourfamily.BuildConfig;
import com.risenb.honourfamily.MyApplication;

/**
 * 作者:linzheng
 * 日期:2016/10/24
 * 功能:Toast工具类 防止重复弹出Toast
 */

public class ToastUtils {

    private static Toast sToast;

    /**
     * 显示Toast
     * @param context 不要传 Activity 要传Application
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(msg);

        }
        sToast.show();
    }

    public static void showToast(String msg){
        showToast(MyApplication.getInstance(),msg);
    }


    public static void showDebugToast(Context context, String msg) {
        if (!BuildConfig.DEBUG) return;
        showToast(context, msg);
    }

}

