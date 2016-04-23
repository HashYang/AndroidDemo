package demo.chen.com.beauty;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.chen.xlib.common.base.BaseActivity;

import demo.chen.com.beauty.ntes.view.ArticleListFragment;
import demo.chen.com.beauty.picacomic.view.ComicCatFragment;

/**
 * Created by Chen on 15/11/23.
 */
public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switchFragment(menuItem.getItemId());
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
        switchFragment(R.id.navigation_item_ntes);
    }

    public void switchFragment(int itemId) {
        switch (itemId) {
            case R.id.navigation_item_ntes:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ArticleListFragment()).commit();
                break;
            case R.id.navigation_item_comic:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ComicCatFragment()).commit();
                break;
            case R.id.navigation_item_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AboutFragment()).commit();
                break;
        }

    }

}
