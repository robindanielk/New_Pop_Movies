package com.example.user.popularmoviesapp.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.user.popularmoviesapp.adapters.FavoritesAdapter;
import com.example.user.popularmoviesapp.database.MovieContract;

/**
 * Created by ridsys-001 on 4/9/17.
 */

public class FavoritesCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private FavoritesAdapter favoritesAdapter;

    private static final String[] MOVIE_COLUMNS_PROJECTION
            = {
            MovieContract.MoviesEntry.COLUMN_MOVIE_ID,
            MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE,
            MovieContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE,
            MovieContract.MoviesEntry.COLUMN_MOVIE_USER_RATING,
            MovieContract.MoviesEntry.COLUMN_MOVIE_BACKDROP,
            MovieContract.MoviesEntry.COLUMN_MOVIE_POSTER
    };

    public FavoritesCursorLoader(Context context,FavoritesAdapter favoritesAdapter)
    {
        this.context =context;
        this.favoritesAdapter = favoritesAdapter;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(context,
                MovieContract.MoviesEntry.CONTENT_URI,
                MOVIE_COLUMNS_PROJECTION,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        favoritesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        favoritesAdapter.swapCursor(null);
    }
}
