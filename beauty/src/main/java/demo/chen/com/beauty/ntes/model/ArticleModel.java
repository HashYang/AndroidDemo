package demo.chen.com.beauty.ntes.model;

import com.chen.xlib.common.base.Callback;
import com.chen.xlib.common.net.CommonModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import demo.chen.com.beauty.ntes.NtesApi;
import demo.chen.com.beauty.ntes.bean.Article;
import demo.chen.com.beauty.ntes.bean.ImageListModel;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ArticleModel implements IArticleModel {
    private NtesApi api;
    private Gson mGson;
    private CommonModel commonModel;

    public ArticleModel() {
        commonModel = new CommonModel();
        api = new NtesApi();
        mGson = new Gson();
    }
// Callback实现
//    public void loadArticleList(final Callback<List<Article>> callback) {
//        api.getArticleList(new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                response = response.replace("var allpics = ", "");
//                List<Article> list = mGson.fromJson(response, new TypeToken<List<Article>>() {
//                }.getType());
//                callback.onSuccess(list);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onFailure(error.getCause());
//            }
//        });
//    }

//    @Override
//    public void loadArticleDetail(String article, final Callback<ImageListModel> callback) {
//        if (article == null) {
//            callback.onFailure(null);
//            return;
//        }
//
//        api.getImageList(article, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                parse(response, callback);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onFailure(error.getCause());
//            }
//        });
//    }

//    private void parse(String response, Callback<ImageListModel> callback) {
//        try {
//            int index = response.indexOf("<textarea name=\"gallery-data\" style=\"display:none;\">");
//            if (index == -1) {
//                index = response.indexOf("<textarea id=\"GalleryData\" name=\"gallery-data\" style=\"display:none;\">");
//            }
//            String json = response.substring(index + "<textarea name=\"gallery-data\" style=\"display:none;\">".length(), response.indexOf("</textarea>", index));
//            ImageListModel listModel = mGson.fromJson(json, ImageListModel.class);
//            callback.onSuccess(listModel);
//        } catch (Exception e) {
//            e.printStackTrace();
//            callback.onFailure(e);
//        }
//    }

    private ImageListModel parse(String response) {
        try {
            int index = response.indexOf("<textarea name=\"gallery-data\" style=\"display:none;\">");
            if (index == -1) {
                index = response.indexOf("<textarea id=\"GalleryData\" name=\"gallery-data\" style=\"display:none;\">");
            }
            String json = response.substring(index + "<textarea name=\"gallery-data\" style=\"display:none;\">".length(), response.indexOf("</textarea>", index));
            return mGson.fromJson(json, ImageListModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    //RxJava方式回调
    private void subscribe(final Callback callback, Observable observable) {
        observable.
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object object) {
                callback.onSuccess(object);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    //RxJava实现
    public void loadArticleList(final Callback callback) {
        subscribe(callback, Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = new NtesApi().getArticleList();
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<String, List<Article>>() {
            @Override
            public List<Article> call(String response) {
                response = response.replace("var allpics = ", "");
                return mGson.fromJson(response, new TypeToken<List<Article>>() {
                }.getType());
            }
        }));
    }

    @Override
    public void loadArticleDetail(final String article, final Callback<ImageListModel> callback) {
        if (article == null) {
            callback.onFailure(null);
            return;
        }
        subscribe(callback, Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = new NtesApi().getImageList(article);
                    subscriber.onNext(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<String, ImageListModel>() {
            @Override
            public ImageListModel call(String result) {
                return parse(result);
            }
        }));
    }


    @Override
    public CommonModel getCommonModel() {
        return commonModel;
    }
}

