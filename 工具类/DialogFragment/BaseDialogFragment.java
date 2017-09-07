package com.lz.rxjava2demo.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lz.rxjava2demo.R;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/09/07
 *     desc   : BaseDialogFragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private int mWidht;

    private int mHeight;

    private int mHorizontalMargin;

    private int mDimAmount;

    private boolean mIsShowBottom = false;

    private boolean mIsCancelbale = true;

    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialogFramgent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutResID(), container, false);
        initViewsAndEvents();
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = mDimAmount == 0 ? 0.5f : mDimAmount;
        lp.gravity = mIsShowBottom ? Gravity.BOTTOM : Gravity.CENTER;
        lp.width = mWidht == 0 ? (getScreenWidth(getContext()) - 2 * mHorizontalMargin) : mWidht;
        lp.height = mHeight == 0 ? WindowManager.LayoutParams.WRAP_CONTENT : mHeight;
        window.setWindowAnimations(R.style.DefaultAnimation);
        window.setAttributes(lp);
        setCancelable(mIsCancelbale);
    }


    protected abstract int getLayoutResID();

    protected abstract void initViewsAndEvents();

    public void show(FragmentTransaction fragmentTransaction) {
        show(fragmentTransaction, String.valueOf(SystemClock.currentThreadTimeMillis()));
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    // ---------------- getter/setter --------------------
    public int getWidht() {
        return mWidht;
    }

    public BaseDialogFragment setWidht(int widht) {
        mWidht = widht;
        return this;
    }

    public int getHeight() {
        return mHeight;
    }

    public BaseDialogFragment setHeight(int height) {
        mHeight = height;
        return this;
    }

    public int getHorizontalMargin() {
        return mHorizontalMargin;
    }

    public BaseDialogFragment setHorizontalMargin(int horizontalMargin) {
        mHorizontalMargin = horizontalMargin;
        return this;
    }

    public int getDimAmount() {
        return mDimAmount;
    }

    public BaseDialogFragment setDimAmount(int dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }

    public boolean isShowBottom() {
        return mIsShowBottom;
    }

    public BaseDialogFragment setShowBottom(boolean showBottom) {
        mIsShowBottom = showBottom;
        return this;
    }

    public boolean isCancelbale() {
        return mIsCancelbale;
    }

    public BaseDialogFragment setCancelbale(boolean cancelbale) {
        mIsCancelbale = cancelbale;
        return this;
    }

}
