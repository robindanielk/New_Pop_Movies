package com.example.user.popularmoviesapp.database;

import android.net.Uri;
import android.util.StringBuilderPrinter;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by user on 6/6/2017.
 */

@ContentProvider(authority = MoviesContentProvider.AUTHORITY,
database = MovieDatabase.class)

public final class MoviesContentProvider
{
    public static final String AUTHORITY =
            "com.example.user.popularmoviesapp.database.MoviesContentProvider";

    static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" +AUTHORITY);

    interface Path
    {
        String MOVIES = "movies";
    }

    private static Uri buildUri(String ...paths)
    {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for(String path:paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = MovieDatabase.MOVIES)
    public static class Movies
    {
        /* whole database table*/
        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor.dir/movie",
                defaultSort = MovieColumns.MOVIE_ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.MOVIES);

        /*single columns*/
        @InexactContentUri(
                name = "MOVIE_ID",
                path = Path.MOVIES + "/#",
                type = "vnd.android.cursor.item/movie",
                whereColumn = MovieColumns.MOVIE_ID,
                pathSegment = 1)
        public static Uri withId(long id)
        {
            return buildUri(Path.MOVIES, String.valueOf(id));
        }
    }


}
