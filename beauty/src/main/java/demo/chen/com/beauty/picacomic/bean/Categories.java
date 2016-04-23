package demo.chen.com.beauty.picacomic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chen on 16/1/5.
 */
public class Categories implements Parcelable {
    private int id;
    private String name;
    private String cover_image;
    private int all_comics;

    public int getAll_comics() {
        return all_comics;
    }

    public void setAll_comics(int all_comics) {
        this.all_comics = all_comics;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.cover_image);
        dest.writeInt(this.all_comics);
    }

    public Categories() {
    }

    protected Categories(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.cover_image = in.readString();
        this.all_comics = in.readInt();
    }

    public static final Parcelable.Creator<Categories> CREATOR = new Parcelable.Creator<Categories>() {
        public Categories createFromParcel(Parcel source) {
            return new Categories(source);
        }

        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };
}
