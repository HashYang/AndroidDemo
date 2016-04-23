package demo.chen.com.beauty;

import android.app.Application;

import com.chen.xlib.util.ImageCacheUtil;

import java.io.File;


/**
 * Created by Chen on 16/1/4.
 */
public class BeatuyApplication extends Application {
    private static final String TAG_FOR_LOGGER = "Beatuy";

    @Override
    public void onCreate() {
        super.onCreate();
        File dir = getExternalCacheDir();
        if (dir == null) {
            dir = getCacheDir();
        }
        ImageCacheUtil.init(this, dir);
    }

}
