package demo.chen.com.beauty.picacomic.model;

import com.chen.xlib.common.base.Callback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import demo.chen.com.beauty.picacomic.PicacomicApi;
import demo.chen.com.beauty.picacomic.bean.Categories;
import demo.chen.com.beauty.picacomic.bean.Comics;
import demo.chen.com.beauty.picacomic.bean.ComicsDetail;
import demo.chen.com.beauty.picacomic.bean.Ep;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Chen on 16/1/5.
 */
public class ComicModel implements IComicModel {
    private PicacomicApi mApi;
    private Gson mGson;

    public ComicModel() {
        mApi = new PicacomicApi();
        mGson = new Gson();
    }

//    Callback实现
//    @Override
//    public void loadCategories(final Callback<List<Categories>> callback) {
//        mApi.getCategoriesList(new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                List<Categories> list = mGson.fromJson(response, new TypeToken<List<Categories>>() {
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
//
//    @Override
//    public void loadComicsDetail(int id, final Callback<ComicsDetail> callback) {
//        mApi.getComicDetailByComicId(id, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                ComicsDetail detail = mGson.fromJson(response, ComicsDetail.class);
//                callback.onSuccess(detail);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onFailure(error.getCause());
//            }
//        });
//    }
//
//    @Override
//    public void loadComics(int catId, int page, final Callback<List<Comics>> callback) {
//        mApi.getComicsByCategoriesId(catId, page, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                List<Comics> list = mGson.fromJson(response, new TypeToken<List<Comics>>(){}.getType());
//                callback.onSuccess(list);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onFailure(error.getCause());
//            }
//        });
//
//    }
//
//    @Override
//    public void loadEp(int comicsId, int ep, final Callback<List<Ep>> callback) {
//        mApi.getComicEp(comicsId, ep, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                List<Ep> list = mGson.fromJson(response, new TypeToken<List<Ep>>(){}.getType());
//                callback.onSuccess(list);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                callback.onFailure(error.getCause());
//            }
//        });
//    }


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

    @Override
    public void loadCategories(final Callback<List<Categories>> callback) {
        subscribe(callback, Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = mApi.getCategoriesList();
                    subscriber.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<String, List<Categories>>() {
            @Override
            public List<Categories> call(String s) {
                List<Categories> list = mGson.fromJson(s, new TypeToken<List<Categories>>() {
                }.getType());
                return list;
            }
        }));

    }

    @Override
    public void loadComicsDetail(final int id, final Callback<ComicsDetail> callback) {
        subscribe(callback, Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = mApi.getComicDetailByComicId(id);
                    subscriber.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<String, ComicsDetail>() {
            @Override
            public ComicsDetail call(String s) {
                ComicsDetail detail = mGson.fromJson(s, ComicsDetail.class);
                return detail;
            }
        }));
    }

    @Override
    public void loadComics(final int catId, final int page, Callback<List<Comics>> callback) {
        subscribe(callback, Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = mApi.getComicsByCategoriesId(catId, page);
                    subscriber.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<String, List<Comics>>() {
            @Override
            public List<Comics> call(String s) {
                List<Comics> list = mGson.fromJson(s, new TypeToken<List<Comics>>() {
                }.getType());
                return list;
            }
        }));
    }

    @Override
    public void loadEp(final int comicsId, final int ep, final Callback<List<Ep>> callback) {
        subscribe(callback, Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = mApi.getComicEp(comicsId, ep);
                    subscriber.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(e);
                }
            }
        }).map(new Func1<String, List<Ep>>() {
            @Override
            public List<Ep> call(String s) {
                List<Ep> list = mGson.fromJson(s, new TypeToken<List<Ep>>() {
                }.getType());
                return list;
            }
        }));
    }
}
