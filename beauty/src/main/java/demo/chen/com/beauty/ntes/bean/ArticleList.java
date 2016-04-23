package demo.chen.com.beauty.ntes.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Chen on 15/11/30.
 */
public class ArticleList {
    @SerializedName("data")
    private ArrayList<Article> list;

    public ArrayList<Article> getList() {
        return list;
    }

    public void setList(ArrayList<Article> list) {
        this.list = list;
    }
}
