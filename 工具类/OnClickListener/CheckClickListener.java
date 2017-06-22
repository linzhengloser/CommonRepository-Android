package com.risenb.honourfamily.views.listener;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/06/16
 *     desc   : 用于检测一些状态的 OnClickListener
 *     version: 1.0
 * </pre>
 */
public class CheckClickListener implements View.OnClickListener {

    OnCheckListener mListener;

    public CheckClickListener(OnCheckListener listener) {
        mListener = listener;
    }

    private static List<Check> sCheckList = new ArrayList<>();

    static {
        sCheckList.add(new NetworkCheck());
        sCheckList.add(new LoginCheck());
    }

    @Override
    public void onClick(View view) {
        for (Check check : sCheckList) {
            if(!check.isChechSuccess(view)){
                check.checkFailure(view);
                return;
            }
        }
        mListener.onAllChekSuccess(view);
    }
}
