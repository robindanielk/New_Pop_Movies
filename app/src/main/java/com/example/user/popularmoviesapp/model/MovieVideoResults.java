package com.example.user.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 6/12/2017.
 */

public class MovieVideoResults implements Parcelable {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("site")
    @Expose
    private String site;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
    }

    public MovieVideoResults() {
    }

    protected MovieVideoResults(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
    }

    public static final Parcelable.Creator<MovieVideoResults> CREATOR = new Parcelable.Creator<MovieVideoResults>() {
        @Override
        public MovieVideoResults createFromParcel(Parcel source) {
            return new MovieVideoResults(source);
        }

        @Override
        public MovieVideoResults[] newArray(int size) {
            return new MovieVideoResults[size];
        }
    };
}
