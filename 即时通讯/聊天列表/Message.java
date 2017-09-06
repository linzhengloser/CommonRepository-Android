package com.lz.rxjava2demo.message;

/**
 * <pre>
 *     author : linzheng
 *     e-mail : 1007687534@qq.com
 *     time   : 2017/08/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Message {

    public Message(String content, Type messageType, Direct messageDirect) {
        this.content = content;
        this.messageType = messageType;
        this.messageDirect = messageDirect;
    }

    public enum Type {
        TXT, IMAGE, VIDEO, LOCATION, VOICE, FILE, CMD
    }

    public enum Direct {
        SEND, RECEIVE
    }

    private String content;

    private Type messageType;

    private Direct messageDirect;

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public Type getMessageType() {
        return messageType;
    }

    public Message setMessageType(Type messageType) {
        this.messageType = messageType;
        return this;
    }

    public Direct getMessageDirect() {
        return messageDirect;
    }

    public Message setMessageDirect(Direct messageDirect) {
        this.messageDirect = messageDirect;
        return this;
    }
}
