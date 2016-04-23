package demo.chen.com.beauty.ntes.model;

import com.chen.xlib.common.base.Callback;
import com.chen.xlib.common.net.CommonModel;

import java.util.List;

import demo.chen.com.beauty.ntes.bean.Article;
import demo.chen.com.beauty.ntes.bean.ImageListModel;

/**
 * Created by Chen on 16/1/4.
 */
public interface IArticleModel {
    void loadArticleList(Callback<List<Article>> callback);

    void loadArticleDetail(String article, Callback<ImageListModel> callback);

    CommonModel getCommonModel();

}
