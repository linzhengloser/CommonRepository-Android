package com.lz.eventbus3demo;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;

/**
 * 作者:linzheng
 * 日期:2017/3/7
 * 功能:实现在RecyclerView中滑动的动画效果
 */

public class SlideFrameLayout extends FrameLayout {

    ViewGroup mCheckBox;

    ViewGroup mContentLayout;

    int mOffset;


    public SlideFrameLayout(Context context) {
        super(context);
    }

    public SlideFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCheckBox = (ViewGroup) getChildAt(0);
        mContentLayout = (ViewGroup) getChildAt(1);
        setOffset(35);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void openAnimation() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, 1);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            float fraction = valueAnimator1.getAnimatedFraction();
            int endX = (int) (-mOffset * fraction);
            doAnimationSet(endX, fraction);
        });
        valueAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void closeAnimation() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, 1);
        valueAnimator.setDuration(150);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            float fraction = valueAnimator1.getAnimatedFraction();
            int endX = (int) (-mOffset * (1 - fraction));
            doAnimationSet(endX, (1 - fraction));
        });
        valueAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void doAnimationSet(int dx, float fraction) {
        mContentLayout.scrollTo(dx, 0);
        mCheckBox.setScaleX(fraction);
        mCheckBox.setScaleY(fraction);
        mCheckBox.setAlpha(fraction * 255);
    }

    public void open() {
        mContentLayout.scrollTo(-mOffset, 0);
    }

    public void close() {
        mContentLayout.scrollTo(0, 0);
    }

    public void setOffset(int offset) {
        mOffset = (int) (getContext().getResources().getDisplayMetrics().density * offset + 0.5f);
    }

    public CheckBox getCheckBox(){
        return (CheckBox) mCheckBox.getChildAt(0);
    }

}
