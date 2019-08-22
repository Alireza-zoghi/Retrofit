package com.alirezazoghi.retrofit.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Actors {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("fname")
    @Expose
    private String fname;

    @SerializedName("lname")
    @Expose
    private String lname;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("age")
    @Expose
    private Integer age;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("status")
    @Expose
    private String status;

    public String getFname() {
        return fname;
    }

    public String getImage() {
        return image;
    }

    public String getLname() {
        return lname;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

}
