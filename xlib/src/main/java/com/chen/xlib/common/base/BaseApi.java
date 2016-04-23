package com.chen.xlib.common.base;

import com.chen.xlib.common.net.HttpClientManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Chen on 16/2/19.
 */
public class BaseApi {
    protected OkHttpClient client;

    public BaseApi() {
        client = HttpClientManager.getInstance().getOkHttpClient();
    }

    public InputStream downloadFile(String url) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        Response response = client.newCall(request).execute();
        return response.body().byteStream();
    }
}
