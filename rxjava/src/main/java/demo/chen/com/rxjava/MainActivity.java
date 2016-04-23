package demo.chen.com.rxjava;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG_FOR_LOGGER = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void method0() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Logger.d("call");
                Logger.d("Hello");
                subscriber.onNext("Hello");
                Logger.d("World");
                subscriber.onNext("World");
                Logger.d("!");
                subscriber.onNext("!");
                subscriber.onCompleted();
                Logger.d("onSubscribe call");
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Logger.d("observer onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e, "observer onError");
            }

            @Override
            public void onNext(String s) {
                Logger.d("onNext:" + s);
//                throw new RuntimeException();
            }
        };
        observable.subscribe(observer);
    }

    public void method1() {
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                Logger.d("被观察者, isMainThread: " + isMainThread());
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

                Logger.d("onCompleted, isMainThread: " + isMainThread());
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError, isMainThread: " + isMainThread());
            }

            @Override
            public void onNext(String s) {
                Logger.d("onNext" + s + ", isMainThread: " + isMainThread());
            }
        });
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                method0();
                break;
            case R.id.button2:
                method1();
                break;

        }
    }
}
