package demo.chen.com.lucymoney;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by Chen on 16/2/6.
 */
public class RedPacket extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int event = accessibilityEvent.getEventType();
        switch (event) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = accessibilityEvent.getText();
                for (CharSequence str : texts
                        ) {
                    if (str.toString().contains("[微信红包]")) {
                        if (accessibilityEvent.getParcelableData() != null
                                && accessibilityEvent.getParcelableData() instanceof Notification) {
                            Notification notification = (Notification) accessibilityEvent
                                    .getParcelableData();
                            PendingIntent pendingIntent = notification.contentIntent;
                            try {
                                pendingIntent.send();
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = accessibilityEvent.getClassName().toString();
                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
                    getPacket();// 领取红包
                } else if (className
                        .equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
                    openPacket();// 打开红包
                }
                break;
        }
    }

    @SuppressLint("NewApi")
    private void openPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b43");
            for (AccessibilityNodeInfo n : list) {
                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }

    }

    @SuppressLint("NewApi")
    private void getPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> nodeInfos = rootNode
                    .findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b_");
            for (AccessibilityNodeInfo nodeInfo : nodeInfos) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }

        }
    }

    @Override
    public void onInterrupt() {

    }
}
