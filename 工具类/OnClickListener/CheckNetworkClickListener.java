package com.risenb.honourfamily.views;

import android.view.View;

import com.risenb.honourfamily.utils.NetUtils;
import com.risenb.honourfamily.utils.ToastUtils;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/06/16
 *     desc   : 检查网络 的单击事件
 *     version: 1.0
 * </pre>
 */
public class CheckNetworkClickListener implements View.OnClickListener {

    OnCheckNetworkListener mOnCheckNetworkListener;

    public CheckNetworkClickListener(OnCheckNetworkListener onCheckNetworkListener) {
        mOnCheckNetworkListener = onCheckNetworkListener;
    }

    @Override
    public void onClick(View view) {
        if (NetUtils.isNetworkAvailable(view.getContext())) {
            mOnCheckNetworkListener.networkAvailable(view);
        } else {
            networkUnavailable(view);
        }
    }

    public void networkUnavailable(View view) {
        ToastUtils.showNoNetWorkToast();
    }

}
