package bstu.fit.baa.goodsfinder.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import bstu.fit.baa.goodsfinder.database.DateTypeConverter;
import bstu.fit.baa.goodsfinder.database.UUIDTypeConverter;

@Entity(tableName = "GOODITEM")
public class GoodItem implements Serializable {

    @PrimaryKey
    @NonNull
    @TypeConverters(UUIDTypeConverter.class)
    private UUID id;
    private String name;
    private String description;
    private String findPlace;
    private byte[] image;
    @TypeConverters(DateTypeConverter.class)
    private Date findDate;
    private String finder;
    private String receiptPlace;
    private boolean favorite;

    public GoodItem() {}

    public GoodItem(String name, String description, String findPlace,
                    Bitmap image, Date findDate, String finder, String receiptPlace,
                    boolean favorite) {
        this.name = name;
        this.description = description;
        this.findPlace = findPlace;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        this.image = stream.toByteArray();
        this.findDate = findDate;
        this.finder = finder;
        this.receiptPlace = receiptPlace;
        this.favorite = favorite;
    }

    public GoodItem(UUID id, String name, String description, String findPlace,
                    byte[] image, Date findDate, String finder, String receiptPlace,
                    boolean favorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.findPlace = findPlace;
        this.image = image;
        this.findDate = findDate;
        this.finder = finder;
        this.receiptPlace = receiptPlace;
        this.favorite = favorite;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFindPlace() {
        return findPlace;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Bitmap getImageAsBitmap() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void setImageFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        image = stream.toByteArray();
    }

    public void setFindPlace(String findPlace) {
        this.findPlace = findPlace;
    }

    public Date getFindDate() {
        return findDate;
    }

    public String getFindDateInFormat() {
        return new SimpleDateFormat("dd.MM.").format(findDate)
                + findDate.getYear();
    }

    public void setFindDate(Date findDate) {
        this.findDate = findDate;
    }

    public String getFinder() {
        return finder;
    }

    public void setFinder(String finder) {
        this.finder = finder;
    }

    public String getReceiptPlace() {
        return receiptPlace;
    }

    public void setReceiptPlace(String receiptPlace) {
        this.receiptPlace = receiptPlace;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
