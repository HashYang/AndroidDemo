package com.chen.xlib.common.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by Chen on 16/1/4.
 */
public class BaseFragment extends Fragment{
    protected void toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

}
