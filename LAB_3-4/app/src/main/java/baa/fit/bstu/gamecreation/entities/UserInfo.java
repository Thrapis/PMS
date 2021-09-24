package baa.fit.bstu.gamecreation.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class UserInfo {
    private String email;
    private String phone;
    private Social[] socials;
    private byte[] image;

    public UserInfo(String email, String phone, Social[] socials, byte[] image) {
        this.email = email;
        this.phone = phone;
        this.socials = socials;
        this.image = image;
    }

    private UserInfo(String email, String phone, Social[] socials, Bitmap image) {
        this.email = email;
        this.phone = phone;
        this.socials = socials;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        this.image = stream.toByteArray();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Social[] getSocials() {
        return socials;
    }

    public void setSocials(Social[] socials) {
        this.socials = socials;
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
}
