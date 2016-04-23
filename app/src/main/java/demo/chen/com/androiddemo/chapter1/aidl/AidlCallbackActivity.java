package demo.chen.com.androiddemo.chapter1.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.widget.Toolbar;
import android.view.View;

import demo.chen.com.androiddemo.R;
import demo.chen.com.androiddemo.base.BaseActivity;

/**
 * Created by Chen on 15/10/29.
 */
public class AidlCallbackActivity extends BaseActivity implements View.OnClickListener {

    private IAidlInterface mAidlInterface;
    private ICountChangeCallback mCountChangeCallback = new ICountChangeCallback.Stub() {

        @Override
        public void notifyCount(int count) throws RemoteException {
            toast("count change to " + count);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidlcallback);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initService();
    }

    private void initService() {
        bindService(new Intent(this, AidlCallbackService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mAidlInterface = IAidlInterface.Stub.asInterface(iBinder);
                try {
                    iBinder.linkToDeath(new IBinder.DeathRecipient() {
                        @Override
                        public void binderDied() {
                            toast("service is crash");
                        }
                    }, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    mAidlInterface.registerCallback(mCountChangeCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                toast("service disconnect");
                mAidlInterface = null;
            }
        }, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_count:
                try {
                    mAidlInterface.setCount(10);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.service_crash:
                try {
                    mAidlInterface.setCount(-1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
