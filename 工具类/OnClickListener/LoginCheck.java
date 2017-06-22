package com.risenb.honourfamily.views.listener;

import android.view.View;

import com.risenb.honourfamily.MyApplication;
import com.risenb.honourfamily.ui.login.LoginUI;
import com.risenb.honourfamily.utils.SPUtils;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/06/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginCheck implements Check {
    @Override
    public boolean isChechSuccess(View view) {
        return SPUtils.getBoolean(MyApplication.getInstance(),"isLogin");
    }

    @Override
    public void checkFailure(View view) {
        LoginUI.toActivity(view.getContext());
    }
}
