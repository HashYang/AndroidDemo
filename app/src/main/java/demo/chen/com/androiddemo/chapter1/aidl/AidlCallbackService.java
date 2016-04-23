package demo.chen.com.androiddemo.chapter1.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Chen on 15/10/29.
 */
public class AidlCallbackService extends Service {
    private int mCount;
    private RemoteCallbackList<ICountChangeCallback> mCallbacks = new RemoteCallbackList<ICountChangeCallback>();
    private IAidlInterface.Stub mBinder = new IAidlInterface.Stub() {

        @Override
        public int getCount() throws RemoteException {
            return mCount;
        }

        @Override
        public void setCount(int count) throws RemoteException {
            if (count < 0) {
                stopSelf();
            }
            mCount = count;
            notifyCount(count);
        }

        @Override
        public void registerCallback(ICountChangeCallback callback) throws RemoteException {
            if (callback != null) {
                mCallbacks.register(callback);
            }
        }

        @Override
        public void unRegisterCallback(ICountChangeCallback callback) throws RemoteException {
            if (callback != null) {
                mCallbacks.unregister(callback);
            }
        }
    };

    private void notifyCount(int count) {
        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).notifyCount(count);
            } catch (RemoteException e) {
            }
        }
        mCallbacks.finishBroadcast();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
