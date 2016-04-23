package demo.chen.com.androiddemo.chapter1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import demo.chen.com.androiddemo.R;
import demo.chen.com.androiddemo.base.BaseActivity;
import demo.chen.com.androiddemo.chapter1.aidl.AidlCallbackActivity;
import demo.chen.com.androiddemo.chapter1.messager.MessengerActivity;

/**
 * Created by Chen on 15/10/29.
 */
public class Chapter1Acitivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.messager:
                startActivity(new Intent(mContext, MessengerActivity.class));
                break;
            case R.id.aidl:
                startActivity(new Intent(mContext, AidlCallbackActivity.class));
                break;
        }
    }
}
