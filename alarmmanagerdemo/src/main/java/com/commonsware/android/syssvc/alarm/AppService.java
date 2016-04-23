/***
 * Copyright (c) 2008-2012 CommonsWare, LLC
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 * by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * <p/>
 * From _The Busy Coder's Guide to Advanced Android Development_
 * http://commonsware.com/AdvAndroid
 */

package com.commonsware.android.syssvc.alarm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

public class AppService extends WakefulIntentService {
    public AppService() {
        super("AppService");
    }

    @Override
    protected void doWakefulWork(Intent intent) {
        sendHttpRequest(this);
    }

    public static void sendHttpRequest(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                try {
                    URL url = new URL("https://raw.githubusercontent.com/liaohuqiu/android-ILoveBaidu/master/package-list.txt");
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    String str;
                    while ((str = in.readLine()) != null) {
                        sb.append(str);
                    }
                    in.close();
                    writeLog(context, sb.toString());
                } catch (IOException e) {
                    writeLog(context,"error");
                }
            }
        }).start();
    }

    public static void writeLog(Context context, String str) {
        File log = new File(context.getExternalCacheDir(),
                "AlarmLog.txt");

        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(log.getAbsolutePath(),
                            log.exists()));

            out.write(new Date().toString() + ":" + str);
            out.write("\n");
            out.close();
        } catch (IOException e) {
            Log.e("AppService", "Exception appending to log file", e);
        }
    }
}