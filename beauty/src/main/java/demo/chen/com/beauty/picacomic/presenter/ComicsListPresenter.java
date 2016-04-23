package demo.chen.com.beauty.picacomic.presenter;

import com.chen.xlib.common.base.Callback;
import com.chen.xlib.common.base.ICommonView;

import java.util.List;

import demo.chen.com.beauty.picacomic.bean.Comics;
import demo.chen.com.beauty.picacomic.model.ComicModel;
import demo.chen.com.beauty.picacomic.model.IComicModel;

/**
 * Created by Chen on 16/1/4.
 */
public class ComicsListPresenter {
    private ICommonView<List<Comics>> mView;
    private IComicModel mModel;

    public ComicsListPresenter(ICommonView mView) {
        this.mView = mView;
        mModel = new ComicModel();
    }

    public void loadComicsList(int catId,int page){
        mView.showProgress();
        mModel.loadComics(catId,page,new Callback<List<Comics>>() {
            @Override
            public void onSuccess(List<Comics> object) {
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
