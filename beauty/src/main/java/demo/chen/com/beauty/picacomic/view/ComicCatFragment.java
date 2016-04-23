package demo.chen.com.beauty.picacomic.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.xlib.common.base.ICommonView;

import java.util.ArrayList;
import java.util.List;

import demo.chen.com.beauty.R;
import demo.chen.com.beauty.picacomic.bean.Categories;
import demo.chen.com.beauty.picacomic.presenter.ComicsCatPresenter;

public class ComicCatFragment extends Fragment implements ICommonView<List<Categories>> {

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private ComicsCatPresenter presenter;

    private MyPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic, null);
        mTablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new MyPagerAdapter(getChildFragmentManager());
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(adapter);
        presenter = new ComicsCatPresenter(this);
        mTablayout.setupWithViewPager(mViewPager);
        presenter.loadCatList();
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }

    private void setupViewPager(List<Categories> list) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        for (Categories cat : list
                ) {
            mTablayout.addTab(mTablayout.newTab().setText(cat.getName()));
            adapter.addFragment(ComicListFragment.newInstance(cat.getId()), cat.getName());
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showFailMsg() {

    }

    @Override
    public void bindData(List<Categories> object) {
        setupViewPager(object);

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
