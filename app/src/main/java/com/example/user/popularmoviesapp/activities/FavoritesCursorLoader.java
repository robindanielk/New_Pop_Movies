package com.example.user.popularmoviesapp.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.user.popularmoviesapp.Helper.Constants;
import com.example.user.popularmoviesapp.adapters.FavoritesAdapter;
import com.example.user.popularmoviesapp.database.MovieContract;

/**
 * Created by ridsys-001 on 4/9/17.
 */

public class FavoritesCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private FavoritesAdapter favoritesAdapter;

    public FavoritesCursorLoader(Context context, FavoritesAdapter favoritesAdapter) {
        this.context = context;
        this.favoritesAdapter = favoritesAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case Constants.MOVIE_LOADER_ID:

                String[] MOVIE_COLUMNS_PROJECTION
                        = {
                        MovieContract.MoviesEntry.COLUMN_MOVIE_ID,
                        MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE,
                        MovieContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE,
                        MovieContract.MoviesEntry.COLUMN_MOVIE_USER_RATING,
                        MovieContract.MoviesEntry.COLUMN_MOVIE_BACKDROP,
                        MovieContract.MoviesEntry.COLUMN_MOVIE_POSTER
                };

                return new CursorLoader(context,
                        MovieContract.MoviesEntry.CONTENT_URI,
                        MOVIE_COLUMNS_PROJECTION,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("loader failed " + id);

        }
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
