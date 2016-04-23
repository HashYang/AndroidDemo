package com.chen.xlib.util;

import android.content.Context;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Chen on 15/11/23.
 */
public class ImageCacheUtil {

    public interface Callback {
        void onSuccess();

        void onFail();
    }

    private static File mImageCacheDir;
    private static Picasso picasso;

    public static void init(Context context, File imageCacheDir) {
        mImageCacheDir = imageCacheDir;
        picasso = new Picasso.Builder(context).downloader(
                new OkHttpDownloader(imageCacheDir)).build();
        Fresco.initialize(context);
    }

    public static void loadImageUrl(ImageView imageView, String url) {
        picasso.load(url).into(imageView);
    }

    public static void loadImageUrl(ImageView imageView, String url, final Callback callback) {
        picasso.load(url).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {
                callback.onFail();
            }
        });
    }

    public static String getCacheDir() {
        return mImageCacheDir + "/";
    }
}

