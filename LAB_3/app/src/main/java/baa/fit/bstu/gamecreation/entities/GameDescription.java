package baa.fit.bstu.gamecreation.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class GameDescription implements Parcelable, Serializable {

    protected String name;
    protected byte[] image;
    protected String genre;
    protected Date date;
    protected String version;
    protected String review;
    protected String developer;
    protected String publisher;

    public GameDescription() {
        name = "";
        image = new byte[0];
        genre = "";
        date = new Date();
        version = "";
        review = "";
        developer = "";
        publisher = "";
    }

    public GameDescription(String name, byte[] image, String genre, Date date, String version,
                           String review, String developer, String publisher) {
        this.name = name;
        this.image = image;
        this.genre = genre;
        this.date = date;
        this.version = version;
        this.review = review;
        this.developer = developer;
        this.publisher = publisher;
    }

    protected GameDescription(Parcel in) {
        name = in.readString();
        int size = in.readInt();
        byte[] _image = new byte[size];
        in.readByteArray(_image);
        date = (Date) in.readSerializable();
        genre = in.readString();
        version = in.readString();
        review = in.readString();
        developer = in.readString();
        publisher = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String _genre) {
        this.genre = _genre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date _date) {
        this.date = _date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Bitmap getImageAsBitmap() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(image.length);
        parcel.writeByteArray(image);
        parcel.writeSerializable(date);
        parcel.writeString(genre);
        parcel.writeString(version);
        parcel.writeString(review);
        parcel.writeString(developer);
        parcel.writeString(publisher);
    }

    public static final Creator<GameDescription> CREATOR = new Creator<GameDescription>() {
        @Override
        public GameDescription createFromParcel(Parcel in) {
            return new GameDescription(in);
        }

        @Override
        public GameDescription[] newArray(int size) {
            return new GameDescription[size];
        }
    };
}
