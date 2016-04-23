package com.chen.xlib.common.base;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Chen on 16/2/22.
 */
public abstract class BaseModel {

    //RxJava方式回调
    protected void subscribe(final Callback callback, Observable observable) {
        observable.
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object object) {
                callback.onSuccess(object);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }
}
