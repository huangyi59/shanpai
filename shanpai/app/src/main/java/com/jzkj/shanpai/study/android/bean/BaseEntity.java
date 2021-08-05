package com.jzkj.shanpai.study.android.bean;

import java.io.Serializable;

import xxd.base.interfaces.UnProguard;

/**
 * 实现序列化,做GSON混淆过滤
 */
public class BaseEntity<T> implements Serializable,UnProguard{
    private static final long serialVersionUID = 1L;
    private T data;
    private int code = Integer.MIN_VALUE;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return code == 0;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
