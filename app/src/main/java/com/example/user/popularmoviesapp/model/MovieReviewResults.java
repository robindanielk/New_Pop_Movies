package com.example.user.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 6/12/2017.
 */

public class MovieReviewResults implements Parcelable {
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
    }

    public MovieReviewResults() {
    }

    protected MovieReviewResults(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<MovieReviewResults> CREATOR = new Parcelable.Creator<MovieReviewResults>() {
        @Override
        public MovieReviewResults createFromParcel(Parcel source) {
            return new MovieReviewResults(source);
        }

        @Override
        public MovieReviewResults[] newArray(int size) {
            return new MovieReviewResults[size];
        }
    };
}
