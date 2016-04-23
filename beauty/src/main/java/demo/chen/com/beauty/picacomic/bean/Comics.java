package demo.chen.com.beauty.picacomic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chen on 16/1/5.
 */
public class Comics implements Parcelable {
    private int id;
    private String name;
    private String author;
    private String finished;
    private int total_page;
    private int cats;
    private String cover_image;
    private String rank;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCats() {
        return cats;
    }

    public void setCats(int cats) {
        this.cats = cats;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeString(this.finished);
        dest.writeInt(this.total_page);
        dest.writeInt(this.cats);
        dest.writeString(this.cover_image);
        dest.writeString(this.rank);
    }

    public Comics() {
    }

    protected Comics(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.author = in.readString();
        this.finished = in.readString();
        this.total_page = in.readInt();
        this.cats = in.readInt();
        this.cover_image = in.readString();
        this.rank = in.readString();
    }

    public static final Parcelable.Creator<Comics> CREATOR = new Parcelable.Creator<Comics>() {
        public Comics createFromParcel(Parcel source) {
            return new Comics(source);
        }

        public Comics[] newArray(int size) {
            return new Comics[size];
        }
    };
}
