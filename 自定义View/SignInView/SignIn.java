package com.lz.rxjava2demo.view;

import android.graphics.Rect;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/10/25
 *     desc   : 签到 Mode
 *     version: 1.0
 * </pre>
 */
public class SignIn {

    //1 已签到 2 未签到 3 今天 4 未来
    private int status;

    private String dayDate;

    private Rect dayRect;

    public SignIn(int status, String dayDate, Rect dayRect) {
        this.status = status;
        this.dayDate = dayDate;
        this.dayRect = dayRect;
    }

    public int getStatus() {
        return status;
    }

    public SignIn setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getDayDate() {
        return dayDate;
    }

    public SignIn setDayDate(String dayDate) {
        this.dayDate = dayDate;
        return this;
    }

    public Rect getDayRect() {
        return dayRect;
    }

    public SignIn setDayRect(Rect dayRect) {
        this.dayRect = dayRect;
        return this;
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "status=" + status +
                ", dayDate='" + dayDate + '\'' +
                ", dayRect=" + dayRect +
                '}';
    }
}
