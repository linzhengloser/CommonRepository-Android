package com.risenb.honourfamily.views.listener;

import android.view.View;

import com.risenb.honourfamily.MyApplication;
import com.risenb.honourfamily.utils.NetUtils;
import com.risenb.honourfamily.utils.ToastUtils;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/06/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class NetworkCheck implements Check{

    @Override
    public boolean isChechSuccess(View view) {
        return NetUtils.isNetworkAvailable(MyApplication.getInstance());
    }

    @Override
    public void checkFailure(View view) {
        ToastUtils.showNoNetWorkToast();
    }
}
