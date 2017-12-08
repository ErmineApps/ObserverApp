package kondratkov.ermineapps.observerapp.model;

import com.google.gson.annotations.Expose;

import java.io.File;

/**
 * Created by kondratkov on 26.11.2017.
 */

public class User {
    //@SerializedName("Users")
    @Expose
    private int Id;
    @Expose
    private String Name;
    @Expose
    private String Password;
    @Expose
    private String Email;
    @Expose
    private String CityName;
    @Expose
    private String Token="";
    @Expose
    private int Photo;

    private City Cities;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public City getCities() {
        return Cities;
    }

    public void setCities(City cities) {
        Cities = cities;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getPhoto() {
        return Photo;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }
}
