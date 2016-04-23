package demo.chen.com.beauty.picacomic.model;

import com.chen.xlib.common.base.Callback;

import java.util.List;

import demo.chen.com.beauty.picacomic.bean.Categories;
import demo.chen.com.beauty.picacomic.bean.Comics;
import demo.chen.com.beauty.picacomic.bean.ComicsDetail;
import demo.chen.com.beauty.picacomic.bean.Ep;

/**
 * Created by Chen on 16/1/5.
 */
public interface IComicModel {
    void loadCategories(Callback<List<Categories>> callback);

    void loadComicsDetail(int id, Callback<ComicsDetail> callback);

    void loadComics(int catId, int page, Callback<List<Comics>> callback);

    void loadEp(int comicsId, int ep, Callback<List<Ep>> callback);
}
