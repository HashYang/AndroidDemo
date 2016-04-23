package com.chen.xlib.common.net;

import com.chen.xlib.common.base.BaseApi;
import com.chen.xlib.common.base.Callback;
import com.chen.xlib.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Chen on 16/2/4.
 */
public class CommonModel implements ICommonModel {

    //RxJava方式回调
    private void subscribe(final Callback callback, Observable observable) {
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

    @Override
    public void downloadFile(final String url, final String filePath, Callback<File> callback) {
        subscribe(callback, Observable.create(new Observable.OnSubscribe<InputStream>() {
            @Override
            public void call(Subscriber<? super InputStream> subscriber) {
                try {
                    InputStream inputStream = new BaseApi().downloadFile(url);
                    subscriber.onNext(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).map(new Func1<InputStream, File>() {
                   @Override
                   public File call(InputStream inputStream) {
                       FileUtils.writeFile(filePath, inputStream);
                       return new File(filePath);
                   }
               }
        ));
    }

}
