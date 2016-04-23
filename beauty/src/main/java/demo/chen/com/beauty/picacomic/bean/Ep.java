package demo.chen.com.beauty.picacomic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chen on 16/1/5.
 */
public class Ep implements Parcelable {
    private String url;
    private int width;
    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    public Ep() {
    }

    protected Ep(Parcel in) {
        this.url = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<Ep> CREATOR = new Parcelable.Creator<Ep>() {
        public Ep createFromParcel(Parcel source) {
            return new Ep(source);
        }

        public Ep[] newArray(int size) {
            return new Ep[size];
        }
    };
}
