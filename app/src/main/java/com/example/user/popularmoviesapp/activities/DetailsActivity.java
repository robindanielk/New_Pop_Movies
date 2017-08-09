package com.example.user.popularmoviesapp.activities;import android.os.Bundle;import android.support.annotation.NonNull;import android.support.design.widget.CollapsingToolbarLayout;import android.support.v7.app.AppCompatActivity;import android.support.v7.widget.AppCompatRatingBar;import android.support.v7.widget.DefaultItemAnimator;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.support.v7.widget.Toolbar;import android.util.Log;import android.widget.ImageView;import android.widget.TextView;import com.example.user.popularmoviesapp.Helper.Constants;import com.example.user.popularmoviesapp.R;import com.example.user.popularmoviesapp.adapters.TrailersAdapter;import com.example.user.popularmoviesapp.model.MovieReviewResults;import com.example.user.popularmoviesapp.model.MovieReviews;import com.example.user.popularmoviesapp.model.MovieVideoResults;import com.example.user.popularmoviesapp.model.MovieVideos;import com.example.user.popularmoviesapp.model.Movies;import com.example.user.popularmoviesapp.network.TmdbRestClient;import com.ms.square.android.expandabletextview.ExpandableTextView;import com.squareup.picasso.Picasso;import java.util.List;import butterknife.BindView;import butterknife.ButterKnife;import retrofit2.Call;import retrofit2.Callback;import retrofit2.Response;public class DetailsActivity extends AppCompatActivity {    private final String BACKDROP_IMAGE_URL = "http://image.tmdb.org/t/p/w500";    private final String POSTER_IMAGE_URL = "http://image.tmdb.org/t/p/w185";    @BindView(R.id.details_collapsing_toolbar)    CollapsingToolbarLayout collapsingToolbarLayout;    @BindView(R.id.details_ct_back_drop)    ImageView backDropImage;    @BindView(R.id.details_cv_movie_image)    ImageView thumbnailImage;    @BindView(R.id.details_tv_title)    TextView detailsTitle;    @BindView(R.id.details_tv_release_date)    TextView detailsReleaseDate;    @BindView(R.id.details_tv_duration)    TextView detailsDuration;    @BindView(R.id.details_tv_ratings)    TextView detailsRating;    @BindView(R.id.details_tv_overview)    TextView detailsOverview;    @BindView(R.id.details_rv_videos)    RecyclerView trailersRecyclerView;    @BindView(R.id.details_rating_bar)    AppCompatRatingBar ratingBar;    @BindView(R.id.expandable_reviews_tv)    ExpandableTextView expandableTVReviews;    @BindView(R.id.expandable_tv_content)    TextView expandableTvContent;    private int movie_id;    Movies movie;    private static final String TAG = TrailersAdapter.class.getSimpleName();    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.details_activity);        ButterKnife.bind(this);        Toolbar toolbar  = (Toolbar) findViewById(R.id.details_toolbar);        setSupportActionBar(toolbar);        getSupportActionBar().setDisplayHomeAsUpEnabled(true);        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,                LinearLayoutManager.HORIZONTAL,                false);        trailersRecyclerView.setLayoutManager(linearLayoutManager);        if(getIntent() != null)        {            if(getIntent().hasExtra(Constants.MOVIE_INTENT_ID))            {                    movie = getIntent().getParcelableExtra(Constants.MOVIE_INTENT_ID);                    movie_id = movie.getId();            }        }        loadMovieDetails();        loadMovieReviews();        loadMoviesTrailers();    }    private void loadMovieDetails()    {        Call<Movies> moviesCall = TmdbRestClient.getInstance()                .getMovieDetailsResponse()                .getMovieDetails(movie_id);        moviesCall.enqueue(new Callback<Movies>() {            @Override            public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {                collapsingToolbarLayout.setTitle(movie.getTitle());                Picasso.with(getApplicationContext())                        .load(BACKDROP_IMAGE_URL + movie.getBackdropPath())                        .error(R.drawable.no_movie_image)                        .placeholder(R.drawable.load)                        .into(backDropImage);                Picasso.with(getApplicationContext())                        .load(POSTER_IMAGE_URL + movie.getPosterPath())                        .error(R.drawable.no_movie_image)                        .placeholder(R.drawable.load)                        .into(thumbnailImage);                detailsTitle.setText(movie.getOriginalTitle());                detailsReleaseDate.setText(movie.getReleaseDate());                detailsDuration.setText(movie.getRuntime());                detailsRating.setText(String.valueOf(movie.getVoteAverage()));                detailsOverview.setText(movie.getOverview());            }            @Override            public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {                Log.e(TAG,t.getMessage());            }        });    }    private void loadMoviesTrailers()    {        Call<MovieVideos> videosCall = TmdbRestClient.getInstance()                .getMovieDetailVideosResponse()                .getMovieVideos(movie_id);        videosCall.enqueue(new Callback<MovieVideos>() {            @Override            public void onResponse(@NonNull Call<MovieVideos> call, @NonNull Response<MovieVideos> response) {                List<MovieVideoResults> trailers = response.body().getResults();                trailersRecyclerView.setAdapter(new TrailersAdapter(trailers,getApplicationContext()));                trailersRecyclerView.smoothScrollToPosition(0);                trailersRecyclerView.setItemAnimator(new DefaultItemAnimator());            }            @Override            public void onFailure(Call<MovieVideos> call, Throwable t) {                Log.e(TAG,t.getMessage());            }        });    }    private void loadMovieReviews()    {       Call<MovieReviews> reviewsCall = TmdbRestClient.getInstance()               .getMovieDetailReviewsResponse()               .getMovieReviews(movie_id);        reviewsCall.enqueue(new Callback<MovieReviews>() {            @Override            public void onResponse(@NonNull Call<MovieReviews> call, @NonNull Response<MovieReviews> response) {                List<MovieReviewResults> reviews = response.body().getResults();                expandableTVReviews.setText(reviews.get(0).getAuthor());                expandableTvContent.setText(reviews.get(0).getContent());            }            @Override            public void onFailure(Call<MovieReviews> call, Throwable t) {                Log.d(TAG,t.getMessage());            }        });    }}