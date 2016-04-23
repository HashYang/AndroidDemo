// IAidlInterface.aidl
package demo.chen.com.androiddemo.chapter1.aidl;

// Declare any non-default types here with import statements
import demo.chen.com.androiddemo.chapter1.aidl.ICountChangeCallback;

interface IAidlInterface {
    int getCount();
    void setCount(int count);
    void registerCallback(ICountChangeCallback callback);
    void unRegisterCallback(ICountChangeCallback callback);
}
