package com.yindwe.tmdata.dto;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author ydw
 */
public class R<T> implements Serializable {
    private int code = 0;
    private String message = "";
    private T data;

    public int getCode() {
        return this.code;
    }

    public R setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public R setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}


