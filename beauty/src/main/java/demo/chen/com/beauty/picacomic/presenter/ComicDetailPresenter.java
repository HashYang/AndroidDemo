package demo.chen.com.beauty.picacomic.presenter;

import com.chen.xlib.common.base.Callback;
import com.chen.xlib.common.base.ICommonView;

import java.util.List;

import demo.chen.com.beauty.picacomic.bean.Ep;
import demo.chen.com.beauty.picacomic.model.ComicModel;
import demo.chen.com.beauty.picacomic.model.IComicModel;

/**
 * Created by Chen on 16/1/4.
 */
public class ComicDetailPresenter {

    private ICommonView<List<Ep>> mView;
    private IComicModel mModel;

    public ComicDetailPresenter(ICommonView<List<Ep>> mCommonview) {
        this.mView = mCommonview;
        mModel = new ComicModel();
    }

    public void loadEp(int comicsId, int ep) {
        mView.showProgress();
        mModel.loadEp(comicsId,ep, new Callback<List<Ep>>() {
            @Override
            public void onSuccess(List<Ep> object) {
                mView.hideProgress();
                mView.bindData(object);
            }

            @Override
            public void onFailure(Throwable e) {
                mView.showFailMsg();
            }
        });
    }
}
