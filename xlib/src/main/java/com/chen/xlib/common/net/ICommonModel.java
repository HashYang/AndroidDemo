package com.chen.xlib.common.net;

import com.chen.xlib.common.base.Callback;

import java.io.File;


/**
 * Created by Chen on 16/2/4.
 */
public interface ICommonModel {
    public void downloadFile(final String url, final String filePath, Callback<File> callback);
}
