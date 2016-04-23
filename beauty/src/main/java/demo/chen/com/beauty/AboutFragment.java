package demo.chen.com.beauty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.xlib.common.base.BaseFragment;

/**
 * Created by Chen on 16/1/7.
 */
public class AboutFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_about, null);
        return view;
    }
}
