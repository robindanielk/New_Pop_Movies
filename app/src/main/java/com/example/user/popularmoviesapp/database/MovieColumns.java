package com.example.user.popularmoviesapp.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.PrimaryKeyConstraint;

/**
 * Created by user on 6/4/2017.
 */
/*Creating a list of columns for the MoviesDataBase */

public interface MovieColumns
{

    @DataType(DataType.Type.INTEGER)@PrimaryKey
    @AutoIncrement
    public static final String MOVIE_ID = "_id";

    @DataType(DataType.Type.TEXT)@NotNull
    public static final String MOVIE_TITLE = "title";

    @DataType(DataType.Type.TEXT)@NotNull
    public static final String MOVIE_RELEASE_DATE = "date";

    @DataType(DataType.Type.INTEGER)@NotNull
    public static final String MOVIE_DURATION = "duration";

    @DataType(DataType.Type.REAL)@NotNull
    public static final String MOVIE_RATING = "rating";

    @DataType(DataType.Type.TEXT)@NotNull
    public static final String MOVIE_POSTER_PATH = "poster";

    @DataType(DataType.Type.TEXT)@NotNull
    public static final String MOVIE_BACKDROP_PATH = "backdrop";

}
