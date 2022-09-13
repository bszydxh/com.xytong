package com.xytong.utils.http;

public interface HttpListener<T> {
    T onResultBack(String result);
}
