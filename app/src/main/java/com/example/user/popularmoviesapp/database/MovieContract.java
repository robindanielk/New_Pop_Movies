package com.example.user.popularmoviesapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ridsys-001 on 16/8/17.
 */

public class MovieContract {


    /*Name for the entire content provider*/
    public static final String CONTENT_AUTHORITY = "com.example.user.popularmoviesapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /*Path that needs to be appended to the Base Uri*/
    public static final String PATH_MOVIES = "movies";


    public static final class MoviesEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" +CONTENT_URI + "/" + PATH_MOVIES;

        /*Table Name and Column Names for the Movies Table*/
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_USER_RATING = "userrating";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";
        public static final String COLUMN_MOVIE_BACKDROP = "backdrop";
        public static final String COLUMN_MOVIE_POSTER = "poster";

    }
}
