package com.lz.rxjava2demo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lz.rxjava2demo.R;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/09/08
 *     desc   : 学习 PathMeasure
 *     version: 1.0
 * </pre>
 */
public class PathMeasureView extends View implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    Paint mPaint;

    int mWidth;

    int mHeight;

    float mDistance;

    Path mInnerPath = new Path();

    Path mOutPath = new Path();

    Path mTriangle1 = new Path();

    Path mTriangle2 = new Path();

    Path mDrawPath = new Path();

    PathMeasure mPathMeasure = new PathMeasure();

    ValueAnimator mValueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);

    {
        RectF innerRect = new RectF(-220, -220, 220, 220);
        RectF outerRect = new RectF(-280, -280, 280, 280);
        mInnerPath.addArc(innerRect, 150, -359.9f);
        mOutPath.addArc(outerRect, 60, -359.9f);

        mPathMeasure.setPath(mInnerPath, false);

        float pos[] = new float[2];
        mPathMeasure.getPosTan(0, pos, null);
        mTriangle1.moveTo(pos[0], pos[1]);
        mPathMeasure.getPosTan((1f / 3f) * mPathMeasure.getLength(), pos, null);
        mTriangle1.lineTo(pos[0], pos[1]);
        mPathMeasure.getPosTan((2f / 3f) * mPathMeasure.getLength(), pos, null);
        mTriangle1.lineTo(pos[0], pos[1]);
        mTriangle1.close();

        mPathMeasure.getPosTan((2f / 3f) * mPathMeasure.getLength(), pos, null);
        Matrix matrix = new Matrix();
        matrix.postRotate(-180);
        mTriangle1.transform(matrix, mTriangle2);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        mPaint.setShadowLayer(15, 0, 0, Color.WHITE);

        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);

        setTag("circle");
        mValueAnimator.start();
    }

    public PathMeasureView(Context context) {
        super(context);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        mDrawPath.reset();
        if (getTag().equals("circle")) {
            mPathMeasure.setPath(mInnerPath, false);
            mPathMeasure.getSegment(0, mDistance * mPathMeasure.getLength(), mDrawPath, true);
            canvas.drawPath(mDrawPath, mPaint);
            mPathMeasure.setPath(mOutPath, false);
            mPathMeasure.getSegment(0, mDistance * mPathMeasure.getLength(), mDrawPath, true);
            canvas.drawPath(mDrawPath, mPaint);
        } else if (getTag().equals("trangle")) {
            canvas.drawPath(mInnerPath, mPaint);
            canvas.drawPath(mOutPath, mPaint);
            mPathMeasure.setPath(mTriangle1, false);
            float stopD = mDistance * mPathMeasure.getLength();
            float startD = stopD - (0.5f - Math.abs(0.5f - mDistance)) * 200;
            mPathMeasure.getSegment(startD, stopD, mDrawPath, true);
            canvas.drawPath(mDrawPath, mPaint);
            mDrawPath.reset();
            mPathMeasure.setPath(mTriangle2, false);
            mPathMeasure.getSegment(startD, stopD, mDrawPath, true);
            canvas.drawPath(mDrawPath, mPaint);
        } else if (getTag().equals("finish")) {
            canvas.drawPath(mInnerPath, mPaint);
            canvas.drawPath(mOutPath, mPaint);
            mPathMeasure.setPath(mTriangle1, false);
            mPathMeasure.getSegment(0, mDistance * mPathMeasure.getLength(), mDrawPath, true);
            canvas.drawPath(mDrawPath, mPaint);
            mDrawPath.reset();
            mPathMeasure.setPath(mTriangle2, false);
            mPathMeasure.getSegment(0, mDistance * mPathMeasure.getLength(), mDrawPath, true);
            canvas.drawPath(mDrawPath, mPaint);
        }

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mDistance = (float) animation.getAnimatedValue();
        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mHandler.sendEmptyMessage(1);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (getTag().equals("circle")) {
                mValueAnimator.setDuration(2000);
                setTag("trangle");
                mValueAnimator.start();
            } else if (getTag().equals("trangle")) {
                mValueAnimator.setDuration(1000);
                setTag("finish");
                mValueAnimator.start();
            }
        }
    };

}
