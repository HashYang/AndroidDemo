package demo.chen.com.beauty.picacomic.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.xlib.common.base.BaseFragment;
import com.chen.xlib.common.base.ICommonView;

import java.util.List;

import demo.chen.com.beauty.R;
import demo.chen.com.beauty.picacomic.bean.Comics;
import demo.chen.com.beauty.picacomic.presenter.ComicsListPresenter;
import demo.chen.com.beauty.picacomic.view.adapter.ComicsListAdapter;

/**
 * Created by Chen on 16/1/4.
 */
public class ComicListFragment extends BaseFragment implements ICommonView<List<Comics>>, SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ComicsListAdapter mAdapter;
    private ComicsListPresenter mPresenter;
    private AppBarLayout appBarLayout;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    private int catId = 1;

    public static ComicListFragment newInstance(int catId) {
        Bundle args = new Bundle();
        ComicListFragment fragment = new ComicListFragment();
        args.putInt("catId", catId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catId = getArguments().getInt("catId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ComicsListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appBarLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mPresenter = new ComicsListPresenter(this);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        onRefresh();
    }


    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showFailMsg() {
    }

    @Override
    public void bindData(List<Comics> object) {
        mAdapter.add(object);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mAdapter.clear();
        mPresenter.loadComicsList(catId, page);
    }


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    && mAdapter.isShowFooter()) {
                page++;
                mPresenter.loadComicsList(catId, page);
            }
        }
    };
}
