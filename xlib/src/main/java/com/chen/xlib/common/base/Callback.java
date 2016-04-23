package com.chen.xlib.common.base;

public interface Callback<T>{
        void onSuccess(T object);
        void onFailure(Throwable e);
    }