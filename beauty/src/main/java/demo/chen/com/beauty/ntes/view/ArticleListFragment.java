package demo.chen.com.beauty.ntes.view;

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
import demo.chen.com.beauty.ntes.bean.Article;
import demo.chen.com.beauty.ntes.presenter.ArticleListPresenter;
import demo.chen.com.beauty.ntes.view.adapter.ArticleListAdapter;

/**
 * Created by Chen on 16/1/4.
 */
public class ArticleListFragment extends BaseFragment implements ICommonView<List<Article>>, SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArticleListAdapter mAdapter;
    private ArticleListPresenter mPresenter;
    private AppBarLayout appBarLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ArticleListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appBarLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mPresenter = new ArticleListPresenter(this);
        mPresenter.loadArticleList();
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
    public void bindData(List<Article> object) {
        mAdapter.bind(object);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadArticleList();
    }

}
