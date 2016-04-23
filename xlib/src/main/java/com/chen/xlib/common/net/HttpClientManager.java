package com.chen.xlib.common.net;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Chen on 16/2/4.
 */
public class HttpClientManager {

    private static HttpClientManager mInstance;

    private OkHttpClient okHttpClient;

    public HttpClientManager() {
        okHttpClient = new OkHttpClient();
    }

    public static HttpClientManager getInstance() {
        if (mInstance == null) {
            mInstance = new HttpClientManager();
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
