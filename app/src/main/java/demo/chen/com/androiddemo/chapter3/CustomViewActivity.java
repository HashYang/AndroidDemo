package demo.chen.com.androiddemo.chapter3;

import android.os.Bundle;

import demo.chen.com.androiddemo.R;
import demo.chen.com.androiddemo.base.BaseActivity;

/**
 * Created by Chen on 15/11/9.
 */
public class CustomViewActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
    }
}
