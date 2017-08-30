package com.example.user.popularmoviesapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ridsys-001 on 17/8/17.
 */

public class MoviesContentProvider extends ContentProvider {

    /*Integer Constants for matching the correct Uri*/
    private static final int CODE_MOVIE = 1024;
    private static final int CODE_MOVIE_ID = 1025;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper movieDbHelper;

    public static UriMatcher buildUriMatcher()
    {
        String content = MovieContract.CONTENT_AUTHORITY;
        UriMatcher uriMatcher =  new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(content,MovieContract.PATH_MOVIES,CODE_MOVIE);
        uriMatcher.addURI(content,MovieContract.PATH_MOVIES +"/#",CODE_MOVIE_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();
        Cursor retCursor;
        switch (sUriMatcher.match(uri))
        {
            case CODE_MOVIE:
                retCursor = db.query(MovieContract.MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                        );
                break;

            case CODE_MOVIE_ID:
                long id  = ContentUris.parseId(uri);
                retCursor = db.query(MovieContract.MoviesEntry.TABLE_NAME,
                        projection,
                        MovieContract.MoviesEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                        );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " +uri);
        }

        if(getContext() != null) {
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        switch (sUriMatcher.match(uri))
        {
            case CODE_MOVIE:
                return MovieContract.MoviesEntry.CONTENT_TYPE;
            case CODE_MOVIE_ID:
                return MovieContract.MoviesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri );
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        long id;
        Uri retUri;
        switch (sUriMatcher.match(uri))
        {
            case CODE_MOVIE:
                id = db.insert(MovieContract.MoviesEntry.TABLE_NAME,null,values);
                if(id>0){
                    retUri = MovieContract.MoviesEntry.buildMovieUri(id);
                    getContext().getContentResolver().notifyChange(uri,null);
                }else{
                    db.close();
                    throw new UnsupportedOperationException("Unable to insert rows into : " +uri);
                }
                db.close();
                break;

            default:
                db.close();
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int deletedRows;
        if(selection == null)
        {
            selection = "1";
        }
        switch (sUriMatcher.match(uri))
        {
            case CODE_MOVIE:
                deletedRows = db.delete(MovieContract.MoviesEntry.TABLE_NAME,
                        selection,
                        null);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported Deletion Operation : " +uri);
        }

        if(deletedRows > 0)
        {
            db.close();
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
