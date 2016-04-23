package demo.chen.com.beauty.ntes.view.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.xlib.util.ImageCacheUtil;

import java.util.ArrayList;
import java.util.List;

import demo.chen.com.beauty.ntes.bean.Article;
import demo.chen.com.beauty.R;
import demo.chen.com.beauty.ntes.view.ArticleDetailActivity;

/**
 * Created by Chen on 15/11/23.
 */
public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {
    private List<Article> list;


    public ArticleListAdapter() {
        super();
        list = new ArrayList<>();
    }

    public void bind(List<Article> articles) {
        if (articles != null) {
            list.clear();
            list.addAll(articles);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        list.clear();
    }

    @Override
    public ArticleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ArticleListAdapter.ViewHolder holder, int position) {
        final Article item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ArticleDetailActivity.class);
                intent.putExtra(ArticleDetailActivity.IMAGE_URL, item.getImageUrl());
                intent.putExtra(ArticleDetailActivity.URL, item.getUrl());
                view.getContext().startActivity(intent);
            }
        });
        ImageCacheUtil.loadImageUrl(holder.image, item.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView title;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.pic);
        }
    }
}
