package demo.chen.com.androiddemo.chapter1.messager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import demo.chen.com.androiddemo.R;
import demo.chen.com.androiddemo.base.BaseActivity;

/**
 * Created by Chen on 15/10/29.
 */
public class MessengerActivity extends BaseActivity implements View.OnClickListener {
    private TextView mContentView;
    private EditText mInputEditText;
    private Messenger mMessenger;
    private Messenger mReplyMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MessengerService.REPLY_MESSAGE:
                    addMessage("收到回复：" + msg.getData().getString("message_reply"));
                    break;
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messager);
        mContentView = (TextView) findViewById(R.id.content);
        mInputEditText = (EditText) findViewById(R.id.edit_input);
        bindService(new Intent(this, MessengerService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mMessenger = new Messenger(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mMessenger = null;
            }
        }, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                String input = mInputEditText.getText().toString();
                sendMessagerToService(input);
                mInputEditText.setText("");
                break;
        }
    }

    private void addMessage(String message) {
        String content = mContentView.getText().toString();
        content = content + "\n" + message;
        mContentView.setText(content);
    }

    private void sendMessagerToService(String message) {

        Message msg = Message.obtain();
        msg.what = MessengerService.RECEIVER_MESSAGE;
        msg.replyTo = mReplyMessenger;
        //msg.obj = message;
        Bundle bundle = new Bundle();
        bundle.putString("message",message);
        msg.setData(bundle);
        try {
            mMessenger.send(msg);
            addMessage("发送内容：" + message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
