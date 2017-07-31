package com.example.user.popularmoviesapp.network;

import com.example.user.popularmoviesapp.model.MovieReviews;
import com.example.user.popularmoviesapp.model.MovieVideos;
import com.example.user.popularmoviesapp.model.Movies;
import com.example.user.popularmoviesapp.model.MoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by user on 7/13/2017.
 */

/*This is the service class for the network calls*/

public class MoviesApi
{
    public interface PopularMovies
    {
        @GET("movie/popular")
        Call<MoviesList> getPopularMovies();
    }

    public interface TopRatedMovies
    {
        @GET("movie/top_rated")
        Call<MoviesList> getTopRatedMovies();
    }

    public interface MovieDetails
    {
        @GET("movie/{movie_id}")
        Call<Movies> getMovieDetails(@Path("movie_id") int id);
    }

    public interface MovieDetailVideos
    {
        @GET("movie/{movie_id}/videos")
        Call<MovieVideos> getMovieVideos(@Path("movie_id") int id);
    }

    public interface MovieDetailReviews
    {
        @GET("movie/{movie_id}/reviews")
        Call<MovieReviews> getMovieReviews(@Path("movie_id") int id);
    }
}
