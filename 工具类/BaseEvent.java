package com.risenb.honourfamily.utils.eventbus;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/06/08
 *     desc   : BaseEvent
 *     version: 1.0
 * </pre>
 */
public class BaseEvent<T> {

    T data;

    int eventType;

    public BaseEvent(T data, int eventType) {
        this.data = data;
        this.eventType = eventType;
    }

    public BaseEvent(int eventType) {
        this.eventType = eventType;
    }

    public BaseEvent() {
    }

    public T getData() {
        return data;
    }

    public BaseEvent setData(T data) {
        this.data = data;
        return this;
    }

    public int getEventType() {
        return eventType;
    }

    public BaseEvent setEventType(int eventType) {
        this.eventType = eventType;
        return this;
    }
}
