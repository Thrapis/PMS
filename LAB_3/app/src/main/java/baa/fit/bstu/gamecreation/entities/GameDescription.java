package baa.fit.bstu.gamecreation.entities;

import android.graphics.Bitmap;

import java.util.Date;

public class GameDescription {

    private String _name;
    private Bitmap _image;
    private String _genre;
    private Date _date;
    private String _version;
    private String _review;
    private String _developer;
    private String _publisher;

    public GameDescription() {
        _name = "";
        _genre = "";
        _date = new Date();
    }

    public GameDescription(String name, Bitmap image, String genre, Date date, String version) {
        _name = name;
        _image = image;
        _genre = genre;
        _date = date;
        _version = version;
    }


    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getGenre() {
        return _genre;
    }

    public void setGenre(String _genre) {
        this._genre = _genre;
    }

    public Date getDate() {
        return _date;
    }

    public void setDate(Date _date) {
        this._date = _date;
    }

    public Bitmap getImage() {
        return _image;
    }

    public void setImage(Bitmap _image) {
        this._image = _image;
    }

    public String getVersion() {
        return _version;
    }

    public void setVersion(String _version) {
        this._version = _version;
    }
}
