package com.example.user.popularmoviesapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.user.popularmoviesapp.database.MovieContract.*;

/**
 * Created by ridsys-001 on 16/8/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "favorites.db";

    public static final int DATABASE_VERSION = 2;

    public MovieDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE =
                "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +
                        MoviesEntry._ID  + " INTEGER PRIMARY KEY, " +
                        MoviesEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                        MoviesEntry.COLUMN_MOVIE_TITLE + " TEXT, "  +
                        MoviesEntry.COLUMN_MOVIE_DURATION + " TEXT NOT, " +
                        MoviesEntry.COLUMN_MOVIE_USER_RATING + " REAL, " +
                        MoviesEntry.COLUMN_MOVIE_BACKDROP + " TEXT, " +
                        MoviesEntry.COLUMN_MOVIE_POSTER + " TEXT,"  +
                        MoviesEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT " + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        onCreate(db);

    }
}
