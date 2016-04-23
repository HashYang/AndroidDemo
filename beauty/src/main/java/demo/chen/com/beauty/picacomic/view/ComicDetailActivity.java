package demo.chen.com.beauty.picacomic.view;

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
import com.chen.xlib.common.base.ICommonView;
import com.chen.xlib.util.FileUtils;
import com.chen.xlib.util.ImageCacheUtil;
import com.chen.xlib.util.ToolsUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import demo.chen.com.beauty.R;
import demo.chen.com.beauty.ntes.bean.ImageModel;
import demo.chen.com.beauty.picacomic.bean.Ep;
import demo.chen.com.beauty.picacomic.presenter.ComicDetailPresenter;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Chen on 15/11/23.
 */
public class ComicDetailActivity extends BaseActivity implements ICommonView<List<Ep>> {
    public static final String COMIC = "comic";
    private int comicId;
    private ViewPager mViewPager;
    private String url;
    private List<ImageModel> list;
    private int index = 0;

    private SamplePagerAdapter adapter;
    private ComicDetailPresenter mPresenter;
    private TextView tipView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        tipView = (TextView) findViewById(R.id.tip);
        adapter = new SamplePagerAdapter();
        mViewPager.setAdapter(adapter);
        comicId = getIntent().getIntExtra(COMIC, 1);
        mPresenter = new ComicDetailPresenter(this);
        mPresenter.loadEp(comicId, 1);
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
    public void bindData(List<Ep> object) {
        adapter.setList(object);
    }

    static class SamplePagerAdapter extends PagerAdapter {
        private List<Ep> list;

        public SamplePagerAdapter() {
        }

        public void setList(List<Ep> list) {
            this.list = list;
            notifyDataSetChanged();
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
            String str = list.get(position).getUrl();
            final View view;
            if (str.endsWith(".gif")) {
                String filename = str.substring(str.lastIndexOf("/") + 1);
                view = new RelativeLayout(container.getContext());

                //下载gif
                String cacheFile = ImageCacheUtil.getCacheDir() + filename;
                if (!FileUtils.isFileExist(cacheFile)) {
//                    NetworkClient.addRequest(new FileDownloadRequest(Request.Method.GET, str, cacheFile, new Response.Listener<File>() {
//                        @Override
//                        public void onResponse(File response) {
//                            loadGifView(response, (ViewGroup) view);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    }));
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

