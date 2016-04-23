package demo.chen.com.beauty.picacomic.view.adapter;

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

import demo.chen.com.beauty.R;
import demo.chen.com.beauty.picacomic.bean.Comics;
import demo.chen.com.beauty.picacomic.view.ComicDetailActivity;

/**
 * Created by Chen on 15/11/23.
 */
public class ComicsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<Comics> list;
    private boolean mShowFooter = true;

    public ComicsListAdapter() {
        super();
        list = new ArrayList<>();
    }

    public void add(List<Comics> comicses) {
        if (comicses != null) {
            list.addAll(comicses);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        list.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_list_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final Comics item = list.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.title.setText(item.getName());
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            ImageCacheUtil.loadImageUrl(viewHolder.image, item.getCover_image());
            ((ViewHolder) holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ComicDetailActivity.class);
                    intent.putExtra(ComicDetailActivity.COMIC, item.getId());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

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

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (list == null) {
            return begin;
        }
        return list.size() + begin;
    }

    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }
}
