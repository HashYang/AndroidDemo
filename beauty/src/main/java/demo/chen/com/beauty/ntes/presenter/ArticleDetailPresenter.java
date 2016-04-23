package demo.chen.com.beauty.ntes.presenter;

import android.content.Context;

import com.chen.xlib.common.base.Callback;
import com.chen.xlib.common.base.ICommonView;
import com.chen.xlib.util.ImageCacheUtil;

import java.io.File;

import demo.chen.com.beauty.ntes.bean.ImageListModel;
import demo.chen.com.beauty.ntes.bean.ImageModel;
import demo.chen.com.beauty.ntes.model.ArticleModel;
import demo.chen.com.beauty.ntes.model.IArticleModel;
import demo.chen.com.beauty.utils.ImageUtil;

/**
 * Created by Chen on 16/1/4.
 */
public class ArticleDetailPresenter {

    private ICommonView<ImageListModel> mView;
    private IArticleModel mModel;
    private Context mContext;

    public ArticleDetailPresenter(Context context, ICommonView<ImageListModel> mCommonview) {
        this.mView = mCommonview;
        mContext = context;

        mModel = new ArticleModel();
    }

    public void loadArticeDetail(String url) {
        mView.showProgress();
        mModel.loadArticleDetail(url, new Callback<ImageListModel>() {
            @Override
            public void onSuccess(ImageListModel object) {
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

//    public void downloadImage(String url, Response.Listener<File> successListener, Response.ErrorListener errorListener) {
//        String filename = url.substring(url.lastIndexOf("/") + 1);
//        String cacheFile = ImageCacheUtil.getCacheDir() + filename;
//        NetworkClient.addRequest(new FileDownloadRequest(Request.Method.GET, url, cacheFile, successListener, errorListener));
//    }

    public void shareImage(ImageModel imageModel) {
        if (imageModel != null) {
            mView.showProgress();
            //Volley实现
//            downloadImage(imageModel.getImg(), new Response.Listener<File>() {
//                @Override
//                public void onResponse(File response) {
//                    ImageUtil.shareImage(mContext, response.getPath(), "send");
//                    mView.hideProgress();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    mView.hideProgress();
//                }
//            });

            //RxJava
            downloadImage(imageModel.getImg(), new Callback<File>() {
                @Override
                public void onSuccess(File file) {
                    mView.hideProgress();
                    ImageUtil.shareImage(mContext, file.getPath(), "send");
                }

                @Override
                public void onFailure(Throwable e) {
                    mView.hideProgress();
                    mView.showFailMsg();
                }
            });
        }
    }

    public String getCacheFilePath(String url) {
        String filename = url.substring(url.lastIndexOf("/") + 1);
        //下载gif
        String cacheFile = ImageCacheUtil.getCacheDir() + filename;
        return cacheFile;
    }

    public void downloadImage(String url, Callback<File> callback) {
        mModel.getCommonModel().downloadFile(url, getCacheFilePath(url), callback);
    }

}
