package com.alirezazoghi.retrofit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actors implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

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
    private String age;

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

    public int getId() {
        return id;
    }

    public String getAge() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.fname);
        dest.writeString(this.lname);
        dest.writeString(this.image);
        dest.writeString(this.age);
        dest.writeString(this.username);
        dest.writeString(this.status);
    }

    public Actors() {
    }

    protected Actors(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fname = in.readString();
        this.lname = in.readString();
        this.image = in.readString();
        this.age = in.readString();
        this.username = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<Actors> CREATOR = new Parcelable.Creator<Actors>() {
        @Override
        public Actors createFromParcel(Parcel source) {
            return new Actors(source);
        }

        @Override
        public Actors[] newArray(int size) {
            return new Actors[size];
        }
    };
}
