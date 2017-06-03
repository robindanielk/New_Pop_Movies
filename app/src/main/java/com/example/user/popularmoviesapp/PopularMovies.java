package com.example.user.popularmoviesapp;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 3/30/2017.
 */

public class PopularMovies implements Parcelable
{

    private String mOriginalTitle;
    private String mMovieImage;
    private String mOverview;
    private double mVoteAverage;
    private String mReleaseDate;
    private String backDropImageUrl;

    public PopularMovies(String mOriginalTitle,String mMovieImage,String mOverview,
                         double mVoteAverage,String mReleaseDate,String backDropImageUrl)
    {
        this.mOriginalTitle = mOriginalTitle;
        this.mMovieImage = mMovieImage;
        this.mOverview = mOverview;
        this.mReleaseDate = mReleaseDate;
        this.mVoteAverage = mVoteAverage;
        this.backDropImageUrl = backDropImageUrl;
    }

    /*Method to  get the Original Title  * @return = String  */

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    /*Method to  get the MovieImage * @return = Image  */

    public String getMovieImage() {
        return mMovieImage;
    }

    /*Method to get the Overview * @return = String */

    public String getOverview() {
        return mOverview;
    }

    /*Method to  get the VoteAverage * @return = double  */

    public double getVoteAverage() {
        return mVoteAverage;
    }

    /*Method to  get the Release Date   * @return = String  */

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getBackDropImageUrl() { return backDropImageUrl;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(mOriginalTitle);
        dest.writeString(mMovieImage);
        dest.writeString(mOverview);
        dest.writeDouble(mVoteAverage);
        dest.writeString(mReleaseDate);
        dest.writeString(backDropImageUrl);

    }

    public static final Parcelable.Creator<PopularMovies> CREATOR =
            new Creator<PopularMovies>() {
                @Override
                public PopularMovies createFromParcel(Parcel source) {
                    return new PopularMovies(source);
                }

                @Override
                public PopularMovies[] newArray(int size) {
                    return new PopularMovies[size];
                }
            };

            private PopularMovies(Parcel in)
            {
                mOriginalTitle = in.readString();
                mMovieImage = in.readString();
                mOverview = in.readString();
                mVoteAverage = in.readDouble();
                mReleaseDate = in.readString();
                backDropImageUrl = in.readString();
            }
}
