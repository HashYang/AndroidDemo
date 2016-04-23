package demo.chen.com.beauty.ntes;


import com.chen.xlib.common.base.BaseApi;
import com.chen.xlib.common.net.HttpClientManager;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * Created by Chen on 15/11/30.
 */
public class NtesApi extends BaseApi {
    public static final String NEST_API_ARTICLE_LIST = "http://play.163.com/special/00314VCP/sp_2015pic_all.js";

    public NtesApi() {
        client = HttpClientManager.getInstance().getOkHttpClient();
    }

//    public void getArticleList(Response.Listener<String> listener, Response.ErrorListener errorListener) {
//        Request request = new StringRequest(Request.Method.GET, NtesApi.NEST_API_ARTICLE_LIST, listener, errorListener);
//        NetworkClient.addRequest(request);
//    }
//
//    public void getImageList(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
//        Request request = new StringRequest(Request.Method.GET, url, listener, errorListener);
//        NetworkClient.addRequest(request);
//    }


    public String getArticleList() throws IOException {

        Request request = new Request.Builder()
                .url(NEST_API_ARTICLE_LIST)
                .get().build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

    public String getImageList(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get().build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        return result;
    }

}
