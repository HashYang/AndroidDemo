package demo.chen.com.beauty.picacomic.presenter;

import com.chen.xlib.common.base.Callback;
import com.chen.xlib.common.base.ICommonView;

import java.util.List;

import demo.chen.com.beauty.picacomic.bean.Categories;
import demo.chen.com.beauty.picacomic.model.ComicModel;
import demo.chen.com.beauty.picacomic.model.IComicModel;

/**
 * Created by Chen on 16/1/4.
 */
public class ComicsCatPresenter {
    private ICommonView<List<Categories>> mView;
    private IComicModel mModel;

    public ComicsCatPresenter(ICommonView mView) {
        this.mView = mView;
        mModel = new ComicModel();
    }

    public void loadCatList(){
        mView.showProgress();
        mModel.loadCategories(new Callback<List<Categories>>() {
            @Override
            public void onSuccess(List<Categories> object) {
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
