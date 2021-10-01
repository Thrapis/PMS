package bstu.fit.baa.goodsfinder.entitie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class GoodItem implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private String findPlace;
    private byte[] image;
    private Date findDate;
    private String finder;
    private String receiptPlace;

    public GoodItem() {}

    public GoodItem(String name, String description, String findPlace,
                    Bitmap image, Date findDate, String finder, String receiptPlace) {
        this.name = name;
        this.description = description;
        this.findPlace = findPlace;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        this.image = stream.toByteArray();
        this.findDate = findDate;
        this.finder = finder;
        this.receiptPlace = receiptPlace;
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
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
}
