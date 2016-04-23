package com.chen.xlib.common.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Chen on 15/11/23.
 */
public class BaseActivity extends AppCompatActivity {


    protected ProgressDialog mDialog;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected void showDialog(String title, String message) {
        mDialog = ProgressDialog.show(this, title, message);
    }

    protected void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog();
    }

    protected void toast(String str) {
        Toast.makeText(BaseActivity.this, str, Toast.LENGTH_SHORT).show();
    }

}
