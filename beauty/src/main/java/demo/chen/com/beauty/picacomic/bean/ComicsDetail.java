package demo.chen.com.beauty.picacomic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chen on 16/1/5.
 */
public class ComicsDetail implements Parcelable {

    public int getEp_count() {
        return ep_count;
    }

    public void setEp_count(int ep_count) {
        this.ep_count = ep_count;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }


    private int ep_count;
    private Comic comic;

    public static class Comic implements Parcelable {
        private String name;
        private int id;
        private String author;
        private String finished;
        private int total_page;
        private String img_directory;
        private int views_count;
        private String description;
        private String updated_at;
        private String display_name;
        private String cover_image;
        private int rank;
        private int comment_count;
        private int user_id;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getCover_image() {
            return cover_image;
        }

        public void setCover_image(String cover_image) {
            this.cover_image = cover_image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
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

        public String getImg_directory() {
            return img_directory;
        }

        public void setImg_directory(String img_directory) {
            this.img_directory = img_directory;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getViews_count() {
            return views_count;
        }

        public void setViews_count(int views_count) {
            this.views_count = views_count;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeInt(this.id);
            dest.writeString(this.author);
            dest.writeString(this.finished);
            dest.writeInt(this.total_page);
            dest.writeString(this.img_directory);
            dest.writeInt(this.views_count);
            dest.writeString(this.description);
            dest.writeString(this.updated_at);
            dest.writeString(this.display_name);
            dest.writeString(this.cover_image);
            dest.writeInt(this.rank);
            dest.writeInt(this.comment_count);
            dest.writeInt(this.user_id);
        }

        public Comic() {
        }

        protected Comic(Parcel in) {
            this.name = in.readString();
            this.id = in.readInt();
            this.author = in.readString();
            this.finished = in.readString();
            this.total_page = in.readInt();
            this.img_directory = in.readString();
            this.views_count = in.readInt();
            this.description = in.readString();
            this.updated_at = in.readString();
            this.display_name = in.readString();
            this.cover_image = in.readString();
            this.rank = in.readInt();
            this.comment_count = in.readInt();
            this.user_id = in.readInt();
        }

        public static final Parcelable.Creator<Comic> CREATOR = new Parcelable.Creator<Comic>() {
            public Comic createFromParcel(Parcel source) {
                return new Comic(source);
            }

            public Comic[] newArray(int size) {
                return new Comic[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ep_count);
        dest.writeParcelable(this.comic, 0);
    }

    public ComicsDetail() {
    }

    protected ComicsDetail(Parcel in) {
        this.ep_count = in.readInt();
        this.comic = in.readParcelable(Comic.class.getClassLoader());
    }

    public static final Parcelable.Creator<ComicsDetail> CREATOR = new Parcelable.Creator<ComicsDetail>() {
        public ComicsDetail createFromParcel(Parcel source) {
            return new ComicsDetail(source);
        }

        public ComicsDetail[] newArray(int size) {
            return new ComicsDetail[size];
        }
    };
}
