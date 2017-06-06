package com.example.user.popularmoviesapp.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by user on 6/6/2017.
 */

@Database(version = MovieDatabase.VERSION)
public final class MovieDatabase
{
    private MovieDatabase(){}

    public static final int VERSION = 1;

    @Table(MovieColumns.class)
    public static final String MOVIES = "movies";
}
