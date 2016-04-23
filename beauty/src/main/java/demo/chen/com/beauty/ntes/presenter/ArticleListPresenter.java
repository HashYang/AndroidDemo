package demo.chen.com.beauty.ntes.presenter;

import android.support.v7.view.ActionMode;

import com.chen.xlib.common.base.Callback;
import com.chen.xlib.common.base.ICommonView;

import java.util.List;

import demo.chen.com.beauty.ntes.bean.Article;
import demo.chen.com.beauty.ntes.model.ArticleModel;

/**
 * Created by Chen on 16/1/4.
 */
public class ArticleListPresenter {
    private ICommonView<List<Article>> mView;
    private ArticleModel mModel;

    public ArticleListPresenter(ICommonView mView) {
        this.mView = mView;
        mModel = new ArticleModel();
    }

    public void loadArticleList() {
        mView.showProgress();
//回调方式
//        mModel.loadArticleList(new Callback<List<Article>>() {
//            @Override
//            public void onSuccess(List<Article> object) {
//                mView.hideProgress();
//                mView.bindData(object);
//
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//                mView.hideProgress();
//                mView.showFailMsg();
//            }
//        });
        //RxJava
        mModel.loadArticleList(new Callback<List<Article>>() {
            @Override
            public void onSuccess(List<Article> object) {
                mView.hideProgress();
                mView.bindData(object);
            }

            @Override
            public void onFailure(Throwable e) {
                mView.hideProgress();
                mView.showFailMsg();
            }
        });
    }
}
