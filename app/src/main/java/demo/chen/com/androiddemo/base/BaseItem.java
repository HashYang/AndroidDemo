package demo.chen.com.androiddemo.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chen on 15/11/9.
 */
public class BaseItem implements Parcelable {
    private String name;
    private Class itemClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getItemClass() {
        return itemClass;
    }

    public void setItemClass(Class itemClass) {
        this.itemClass = itemClass;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeSerializable(this.itemClass);
    }

    public BaseItem() {
    }

    protected BaseItem(Parcel in) {
        this.name = in.readString();
        this.itemClass = (Class) in.readSerializable();
    }

    public static final Parcelable.Creator<BaseItem> CREATOR = new Parcelable.Creator<BaseItem>() {
        public BaseItem createFromParcel(Parcel source) {
            return new BaseItem(source);
        }

        public BaseItem[] newArray(int size) {
            return new BaseItem[size];
        }
    };
}
