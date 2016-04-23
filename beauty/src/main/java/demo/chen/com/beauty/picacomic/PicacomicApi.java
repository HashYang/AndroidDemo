package demo.chen.com.beauty.picacomic;

import com.chen.xlib.common.net.HttpClientManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * Created by Chen on 16/1/5.
 */
public class PicacomicApi {

    private final static String PICACOMIC_SERVICE = "http://picaman.picacomic.com/api/";
    private final static String PICACOMIC_GETCATEGORIES_LIST = PICACOMIC_SERVICE + "categories";
    private OkHttpClient client;

    public PicacomicApi() {
        client = HttpClientManager.getInstance().getOkHttpClient();
    }

//    /**
//     * 获取分类列表
//     * http://picaman.picacomic.com/api/categories
//     *
//     * @param listener
//     * @param errorListener
//     */
//    public void getCategoriesList(Response.Listener<String> listener, Response.ErrorListener errorListener) {
//        Request request = new StringRequest(Request.Method.GET, PICACOMIC_SERVICE + "categories", listener, errorListener);
//        NetworkClient.addRequest(request);
//    }
//
//    /**
//     * 获取分类下漫画列表
//     * http://picaman.picacomic.com/api/categories/8/page/1/comics
//     *
//     * @param categoriesId
//     * @param page
//     * @param listener
//     * @param errorListener
//     */
//    public void getComicsByCategoriesId(int categoriesId, int page, Response.Listener<String> listener, Response.ErrorListener errorListener) {
//        Request request = new StringRequest(Request.Method.GET, PICACOMIC_SERVICE + "categories/" + categoriesId + "/page/" + page + "/comics", listener, errorListener);
//        NetworkClient.addRequest(request);
//    }
//
//    /**
//     * 获取漫画详情
//     *
//     * @param comicId
//     * @param listener
//     * @param errorListener
//     */
//    public void getComicDetailByComicId(int comicId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
//        Request request = new StringRequest(Request.Method.GET, PICACOMIC_SERVICE + "comics/" + comicId, listener, errorListener);
//        NetworkClient.addRequest(request);
//    }
//
//    /**
//     * 获取漫画
//     * @param comicId
//     * @param epId
//     * @param listener
//     * @param errorListener
//     */
//    public void getComicEp(int comicId, int epId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
//        Request request = new StringRequest(Request.Method.GET, PICACOMIC_SERVICE + "comics/" + comicId + "/ep/" + epId, listener, errorListener);
//        NetworkClient.addRequest(request);
//    }


    public String getCategoriesList() throws IOException {
        Request request = new Request.Builder()
                .url(PICACOMIC_GETCATEGORIES_LIST)
                .get().build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    public String getComicsByCategoriesId(int categoriesId, int page) throws IOException {
        Request request = new Request.Builder().url(PICACOMIC_SERVICE + "categories/" + categoriesId + "/page/" + page + "/comics").get().build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    public String getComicDetailByComicId(int comicId) throws IOException {
        Request request = new Request.Builder().url(PICACOMIC_SERVICE + "comics/" + comicId).get().build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    public String getComicEp(int comicId, int epId) throws IOException {
        Request request = new Request.Builder().url(PICACOMIC_SERVICE + "comics/" + comicId + "/ep/" + epId).get().build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

}
