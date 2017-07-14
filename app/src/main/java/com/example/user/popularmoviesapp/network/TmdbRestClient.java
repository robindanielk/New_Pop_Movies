package com.example.user.popularmoviesapp.network;

import com.example.user.popularmoviesapp.BuildConfig;
import com.example.user.popularmoviesapp.database.MoviesContentProvider;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 7/14/2017.
 */

public class TmdbRestClient
{

    private static String BASE_URL = "https://api.themoviedb.org/3/";

    private MoviesApi.SortMovies sortMovies;
    private MoviesApi.MovieDetails movieDetails;
    private MoviesApi.MovieDetailReviews movieDetailReviews;
    private MoviesApi.MovieDetailVideos movieDetailVideos;

    private Retrofit retrofit;

    private static TmdbRestClient instance = null;

    private TmdbRestClient()
    {
        initialiseRetrofit();
    }

    public static TmdbRestClient getInstance()
    {
        if(null == instance)
        {
            instance = new TmdbRestClient();
        }
        return instance;
    }

    public MoviesApi.SortMovies getSortMoviesResponse()
    {
        if(sortMovies == null) {
            sortMovies = retrofit.create(MoviesApi.SortMovies.class);
        }
        return sortMovies;
    }

    public MoviesApi.MovieDetails getMovieDetailsResponse()
    {
        if(movieDetails == null){
            movieDetails = retrofit.create(MoviesApi.MovieDetails.class);
        }
        return movieDetails;
    }

    public MoviesApi.MovieDetailVideos getMovieDetailVideosResponse()
    {
        if(movieDetailVideos == null)
        {
            movieDetailVideos = retrofit.create(MoviesApi.MovieDetailVideos.class);
        }
        return movieDetailVideos;
    }

    public MoviesApi.MovieDetailReviews getMovieDetailReviewsResponse()
    {
        if(movieDetailReviews == null)
        {
            movieDetailReviews = retrofit.create(MoviesApi.MovieDetailReviews.class);
        }
        return movieDetailReviews;
    }


    private void initialiseRetrofit()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request =chain.request();
                        HttpUrl url = request.url()
                                .newBuilder()
                                .addQueryParameter("api_key", BuildConfig.API_KEY)
                                .build();
                        Request.Builder builder = request.newBuilder()
                                .url(url)
                                .method(request.method(),request.body());
                        request = builder.build();
                        return chain.proceed(request);
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
