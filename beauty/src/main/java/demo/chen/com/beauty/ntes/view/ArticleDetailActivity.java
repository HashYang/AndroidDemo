package demo.chen.com.beauty.ntes.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.xlib.common.base.BaseActivity;
import com.chen.xlib.common.base.Callback;
import com.chen.xlib.common.base.ICommonView;
import com.chen.xlib.util.FileUtils;
import com.chen.xlib.util.ImageCacheUtil;
import com.chen.xlib.util.ToolsUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import demo.chen.com.beauty.R;
import demo.chen.com.beauty.ntes.bean.ImageListModel;
import demo.chen.com.beauty.ntes.bean.ImageModel;
import demo.chen.com.beauty.ntes.presenter.ArticleDetailPresenter;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Chen on 15/11/23.
 */
public class ArticleDetailActivity extends BaseActivity implements ICommonView<ImageListModel>, View.OnClickListener {
    public static final String IMAGE_URL = "image_url";
    public static final String URL = "url";
    private String imageUrl;
    private ViewPager mViewPager;
    private String url;
    private List<ImageModel> list;
    private int index = 0;

    private SamplePagerAdapter adapter;
    private ArticleDetailPresenter mPresenter;

    private TextView tipView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tipView = (TextView) findViewById(R.id.tip);
        adapter = new SamplePagerAdapter();
        mViewPager.setAdapter(adapter);
        imageUrl = getIntent().getStringExtra(IMAGE_URL);
        url = getIntent().getStringExtra(URL);
        mPresenter = new ArticleDetailPresenter(mContext, this);
        mPresenter.loadArticeDetail(url);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tipView.setText((position + 1) + "/" + adapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showProgress() {
        showDialog("请稍等", "正在请求");
    }

    @Override
    public void hideProgress() {
        dismissDialog();
    }

    @Override
    public void showFailMsg() {
        toast("请求失败，请检查网络链接并重试");
    }

    @Override
    public void bindData(ImageListModel object) {
        adapter.setList(object.getList());
        tipView.setText((1) + "/" + adapter.getCount());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                ImageModel imageModel = adapter.getList().get(mViewPager.getCurrentItem());
                mPresenter.shareImage(imageModel);
                break;
            case R.id.download:
                ImageModel imageModel1 = adapter.getList().get(mViewPager.getCurrentItem());
                showProgress();
                mPresenter.downloadImage(imageModel1.getImg(), new Callback<File>() {
                    @Override
                    public void onSuccess(File object) {
                        dismissDialog();

                    }

                    @Override
                    public void onFailure(Throwable e) {
                        dismissDialog();
                    }
                });
                break;
        }
    }

    class SamplePagerAdapter extends PagerAdapter {
        private List<ImageModel> list;

        public SamplePagerAdapter() {
        }

        public void setList(List<ImageModel> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public List<ImageModel> getList() {
            return this.list;
        }

        @Override
        public int getCount() {
            if (list == null) {
                return 0;
            }
            return list.size();
        }


        @Override
        public View instantiateItem(ViewGroup container, int position) {
            String str = list.get(position).getImg();
            final View view;
            if (str.endsWith(".gif")) {
                view = new RelativeLayout(container.getContext());
                String cacheFile = mPresenter.getCacheFilePath(str);
                if (!FileUtils.isFileExist(cacheFile)) {
                    //Volley实现
//                    mPresenter.downloadImage(str, new Response.Listener<File>() {
//                        @Override
//                        public void onResponse(File response) {
//                            loadGifView(response, (ViewGroup) view);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    });
                    //RxJava
                    mPresenter.downloadImage(url, new Callback<File>() {
                        @Override
                        public void onSuccess(File file) {
                            loadGifView(file, (ViewGroup) view);
                        }

                        @Override
                        public void onFailure(Throwable e) {

                        }
                    });
                } else {
                    loadGifView(new File(cacheFile), (ViewGroup) view);
                }
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                view = new PhotoView(container.getContext());
                ImageCacheUtil.loadImageUrl(((ImageView) view), str);
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private void loadGifView(File file, ViewGroup viewGroup) {

            try {
                GifDrawable gifFromFile = new GifDrawable(file);
                GifImageView imageView = new GifImageView(viewGroup.getContext());
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                float height = ((float) ToolsUtil.getWidthInPx(viewGroup.getContext())) / bitmap.getWidth() * bitmap.getHeight();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height);
                params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                imageView.setLayoutParams(params);
                ((RelativeLayout) viewGroup).addView(imageView);
                imageView.setImageDrawable(gifFromFile);
                gifFromFile.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

