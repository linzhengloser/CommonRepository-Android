package com.lz.rxjava2demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/10/25
 *     desc   : 签到 View
 *     version: 1.0
 * </pre>
 */
public class SignInView extends View {

    private int mGridWidth, mWidth, mGridPadding, mTodayDate;

    private String mWeekTextArray[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    //日历数据
    private ArrayList<ArrayList<Integer>> mCalendarData = new ArrayList<>();

    //签到数据
    private ArrayList<ArrayList<SignIn>> mSignInList = new ArrayList<>();

    private Rect mWeekTextRectArray[] = new Rect[mWeekTextArray.length];

    private Paint mWeekTextPaint;

    private Paint mLinePaint;

    private Paint mDayPaint;

    private Rect mWeekTextBounds = new Rect();

    private Rect mNotSignInTextBounds = new Rect();

    private int mDayTextSize = 50;

    private OnDateClickListener mOnDateClickListener;

    public SignInView(Context context) {
        super(context);
    }

    public SignInView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SignInView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        initCalendarData();
        mWeekTextPaint = new Paint();
        mWeekTextPaint.setTextSize(40);
        mWeekTextPaint.setColor(Color.parseColor("#ff949a"));
        mWeekTextPaint.setAntiAlias(true);
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor("#E6E6E6"));
        mDayPaint = new Paint();
        mDayPaint.setAntiAlias(true);
        mDayPaint.setTextSize(mDayTextSize);
        mGridPadding = 20;
        mDayPaint.getTextBounds("补", 0, 1, mNotSignInTextBounds);
    }

    /**
     * 初始化日历数据
     */
    private void initCalendarData() {
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse("2017-9-25");
            calendar.setTime(date);
            mTodayDate = calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DATE, 1);
        int sumDays = calendar.getActualMaximum(Calendar.DATE);
        ArrayList<Integer> dayList = new ArrayList<>();
        for (int i = 1; i < calendar.get(Calendar.DAY_OF_WEEK); i++) {
            dayList.add(-1);
        }

        for (int i = 1; i <= sumDays; i++) {
            dayList.add(i);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                //星期六
                mCalendarData.add(dayList);
                dayList = new ArrayList<>();
            }
            calendar.add(Calendar.DATE, 1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            for (ArrayList<SignIn> list : mSignInList) {
                for (SignIn signIn : list) {
                    if (x > signIn.getDayRect().left &&
                            x < signIn.getDayRect().right &&
                            y > signIn.getDayRect().top &&
                            y < signIn.getDayRect().bottom) {
                        if (mOnDateClickListener != null)
                            mOnDateClickListener.onClick(this, signIn);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 初始化 星期 和 签到数据
     */
    private void initWeekAndSignInData() {
        Rect weekRect;
        for (int i = 0; i < mWeekTextArray.length; i++) {
            weekRect = new Rect();
            weekRect.set(i * mGridWidth, 0, (i + 1) * mGridWidth, mGridWidth);
            mWeekTextRectArray[i] = weekRect;
        }

        ArrayList<Integer> lineDayList;
        ArrayList<SignIn> lineSignInList;
        SignIn signIn;
        Rect dayRect;
        for (int i = 0; i < mCalendarData.size(); i++) {
            lineDayList = mCalendarData.get(i);
            lineSignInList = new ArrayList<>();
            for (int i1 = 0; i1 < lineDayList.size(); i1++) {
                dayRect = new Rect();
                signIn = new SignIn(1, String.valueOf(lineDayList.get(i1)), dayRect);
                if (lineDayList.get(i1) != -1) {
                    int left = i1 * mGridWidth;
                    int top = mGridWidth + i * mGridWidth;
                    int right = (i1 + 1) * mGridWidth;
                    int bottom = mGridWidth + (i + 1) * mGridWidth;
                    dayRect.set(left, top, right, bottom);
                }
                int dayDateNumber = Integer.valueOf(signIn.getDayDate());
                if (dayDateNumber == mTodayDate) {
                    signIn.setStatus(3);
                } else if (dayDateNumber > mTodayDate) {
                    signIn.setStatus(4);
                } else if (dayDateNumber == 5 || dayDateNumber == 6 || dayDateNumber == 10) {
                    signIn.setStatus(2);
                }
                lineSignInList.add(signIn);
            }
            mSignInList.add(lineSignInList);
        }
    }

    private boolean isInitial = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mGridWidth = mWidth / mWeekTextArray.length;
        if (!isInitial) {
            isInitial = !isInitial;
            initWeekAndSignInData();
        }
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                ((mSignInList.size() + 1) * mGridWidth));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawWeekText(canvas);
        canvas.drawLine(0, mGridWidth, mWidth, mGridWidth + 1, mLinePaint);
        drawDay(canvas);
    }

    /**
     * 绘制周
     * @param canvas
     */
    private void drawWeekText(Canvas canvas) {
        for (int i = 0; i < mWeekTextArray.length; i++) {
            mWeekTextPaint.getTextBounds(mWeekTextArray[i], 0, mWeekTextArray[i].length(), mWeekTextBounds);
            Point weekPoint = getFontCenterPointFromRect(mWeekTextRectArray[i], mWeekTextBounds);
            canvas.drawText(mWeekTextArray[i], weekPoint.x, weekPoint.y, mWeekTextPaint);
        }
    }

    /**
     * 绘制日期
     */
    private void drawDay(Canvas canvas) {
        ArrayList<SignIn> rowSignInList;
        Rect dayTextBounds = new Rect();
        SignIn signIn;
        for (int i = 0; i < mSignInList.size(); i++) {
            rowSignInList = mSignInList.get(i);
            for (int i1 = 0; i1 < rowSignInList.size(); i1++) {
                signIn = rowSignInList.get(i1);
                if (signIn.getDayRect().isEmpty()) continue;
                //计算文字Rect
                mDayPaint.getTextBounds(signIn.getDayDate(), 0, signIn.getDayDate().length(), dayTextBounds);
                //根据不同状态绘制
                if (signIn.getStatus() == 1)
                    drawAlreadySignInDay(signIn, dayTextBounds, canvas);
                else if (signIn.getStatus() == 2)
                    drawNotSignInDay(signIn, dayTextBounds, canvas);
                else if (signIn.getStatus() == 3)
                    drawToday(signIn, dayTextBounds, canvas);
                else
                    drawFutureDay(signIn, dayTextBounds, canvas);

            }
        }
    }


    /**
     * 绘制已签到的日期
     */
    private void drawAlreadySignInDay(SignIn signIn, Rect textBounds, Canvas canvas) {
        mDayPaint.setColor(Color.parseColor("#ED161B"));
        mDayPaint.setStyle(Paint.Style.FILL);
        drawDayBg(signIn, canvas);
        mDayPaint.setTextSize(mDayTextSize);
        mDayPaint.setColor(Color.WHITE);
        drawDayText(signIn, textBounds, canvas);
    }

    /**
     * 绘制未签到的日期
     */
    private void drawNotSignInDay(SignIn signIn, Rect textBounds, Canvas canvas) {
        mDayPaint.setColor(Color.parseColor("#999999"));
        mDayPaint.setStyle(Paint.Style.STROKE);
        mDayPaint.setStrokeWidth(2);
        drawDayBg(signIn, canvas);
        mDayPaint.setTextSize(mDayTextSize);
        mDayPaint.setStyle(Paint.Style.FILL);
        drawDayText(signIn, textBounds, canvas);
    }

    /**
     * 绘制今天
     */
    private void drawToday(SignIn signIn, Rect textBounds, Canvas canvas) {
        mDayPaint.setColor(Color.parseColor("#ea1c27"));
        mDayPaint.setStyle(Paint.Style.STROKE);
        mDayPaint.setStrokeWidth(2);
        drawDayBg(signIn, canvas);
        mDayPaint.setTextSize(mDayTextSize);
        mDayPaint.setStyle(Paint.Style.FILL);
        drawDayText(signIn, textBounds, canvas);
    }

    /**
     * 绘制未来的日期
     */
    private void drawFutureDay(SignIn signIn, Rect textBounds, Canvas canvas) {
        mDayPaint.setTextSize(mDayTextSize);
        mDayPaint.setStyle(Paint.Style.FILL);
        mDayPaint.setColor(Color.parseColor("#333333"));
        drawDayText(signIn, textBounds, canvas);
    }

    /**
     * 绘制日期文字
     */
    private void drawDayText(SignIn signIn, Rect textBounds, Canvas canvas) {
        Point dayTextPoint;
        //status = 2 表示补签
        if (signIn.getStatus() == 2) {
            dayTextPoint = getFontCenterPointFromRect(signIn.getDayRect(), mNotSignInTextBounds);
            canvas.drawText("补", dayTextPoint.x, dayTextPoint.y, mDayPaint);
        } else {
            dayTextPoint = getFontCenterPointFromRect(signIn.getDayRect(), textBounds);
            canvas.drawText(signIn.getDayDate(), dayTextPoint.x, dayTextPoint.y, mDayPaint);
        }
    }

    /**
     * 绘制日期背景
     */
    private void drawDayBg(SignIn signIn, Canvas canvas) {
        Point dayCirclePoint = getCircleCenterPointFromRect(signIn.getDayRect());
        canvas.drawCircle(dayCirclePoint.x, dayCirclePoint.y, mGridWidth / 2 - mGridPadding, mDayPaint);
    }


    /**
     * 获取一个文字在一个 Rect 中居中绘制的 X 与 Y
     */
    private Point getFontCenterPointFromRect(Rect rect, Rect textBounds) {
        int x = rect.left + rect.width() / 2 - textBounds.width() / 2;
        int y = rect.top + rect.height() / 2 + textBounds.height() / 2;
        return new Point(x, y);
    }

    /**
     * 获取一个圆心在一个 Rect 中绘制的 X 与 Y
     */
    private Point getCircleCenterPointFromRect(Rect rect) {
        int x = rect.left + rect.width() / 2;
        int y = rect.top + rect.height() / 2;
        return new Point(x, y);
    }

    /**
     * 设置日期点击事件
     */
    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.mOnDateClickListener = onDateClickListener;
    }

    public interface OnDateClickListener {
        void onClick(View view, SignIn signIn);
    }

}
