package com.example.user.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 6/12/2017.
 */

public class MovieVideos implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieVideoResults> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieVideoResults> getResults() {
        return results;
    }

    public void setResults(List<MovieVideoResults> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeTypedList(this.results);
    }

    public MovieVideos() {
    }

    protected MovieVideos(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(MovieVideoResults.CREATOR);
    }

    public static final Parcelable.Creator<MovieVideos> CREATOR = new Parcelable.Creator<MovieVideos>() {
        @Override
        public MovieVideos createFromParcel(Parcel source) {
            return new MovieVideos(source);
        }

        @Override
        public MovieVideos[] newArray(int size) {
            return new MovieVideos[size];
        }
    };
}

