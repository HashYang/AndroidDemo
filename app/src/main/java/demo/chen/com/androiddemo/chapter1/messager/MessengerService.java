package demo.chen.com.androiddemo.chapter1.messager;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MessengerService extends Service {
    public static final int RECEIVER_MESSAGE = 1;
    public static final int REPLY_MESSAGE = 2;

    public MessengerService() {
    }

    private final Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case RECEIVER_MESSAGE:
                    try {
                        Message replyMsg  = Message.obtain();
                        String message = msg.getData().getString("message");
                        Bundle bundle = new Bundle();
                        bundle.putString("message_reply",message);
                        replyMsg.setData(bundle);
                        replyMsg.what = REPLY_MESSAGE;
                        msg.replyTo.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);

                    break;
            }
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
