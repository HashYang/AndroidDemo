package com.chen.xlib.common.base;

/**
 * Created by Chen on 16/1/4.
 */
public interface ICommonView<T> {
    void showProgress();
    void hideProgress();
    void showFailMsg();
    void bindData(T object);
}
