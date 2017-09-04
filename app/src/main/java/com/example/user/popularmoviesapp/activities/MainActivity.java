package com.example.user.popularmoviesapp.activities;import android.content.Context;import android.content.Intent;import android.content.SharedPreferences;import android.content.res.Configuration;import android.net.ConnectivityManager;import android.net.NetworkInfo;import android.os.Bundle;import android.preference.PreferenceManager;import android.support.annotation.NonNull;import android.support.v7.app.AppCompatActivity;import android.support.v7.widget.DefaultItemAnimator;import android.support.v7.widget.GridLayoutManager;import android.support.v7.widget.RecyclerView;import android.support.v7.widget.Toolbar;import android.util.Log;import android.view.Menu;import android.view.MenuItem;import android.view.View;import android.widget.ProgressBar;import android.widget.TextView;import com.example.user.popularmoviesapp.Helper.Constants;import com.example.user.popularmoviesapp.R;import com.example.user.popularmoviesapp.adapters.FavoritesAdapter;import com.example.user.popularmoviesapp.adapters.MoviesAdapter;import com.example.user.popularmoviesapp.model.Movies;import com.example.user.popularmoviesapp.model.MoviesList;import com.example.user.popularmoviesapp.network.TmdbRestClient;import com.facebook.stetho.Stetho;import java.util.ArrayList;import java.util.List;import butterknife.BindView;import butterknife.ButterKnife;import retrofit2.Call;import retrofit2.Callback;import retrofit2.Response;/*This class displays the complete list of movies based on the sort order*/public class MainActivity extends AppCompatActivity implements        SharedPreferences.OnSharedPreferenceChangeListener {    @BindView(R.id.recyclerview)    RecyclerView recyclerView;    @BindView(R.id.tv_empty_tv)    TextView mEmptyView;    @BindView(R.id.gv_pb_indicator)    ProgressBar mLoaderIndicator;    private Boolean isConnected;    private MoviesAdapter mMoviesAdapter;    private List<Movies> movies;    private static final String TAG = MainActivity.class.getSimpleName();    private GridLayoutManager mLayoutManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        Stetho.initializeWithDefaults(this);        setContentView(R.layout.activity_main);        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);        setSupportActionBar(toolbar);        ButterKnife.bind(this);        if(getApplicationContext().getResources().getConfiguration().orientation                == Configuration.ORIENTATION_PORTRAIT){        mLayoutManager = new GridLayoutManager(this,2);        }else{            mLayoutManager = new GridLayoutManager(this,3);        }        recyclerView.setLayoutManager(mLayoutManager);        movies = new ArrayList<Movies>();        mMoviesAdapter = new MoviesAdapter(this, movies);        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();        checkSharedPreferences();    }    /*Method to get the sort_order from the Shared Preferences */    private void checkSharedPreferences() {        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);        sharedPreferences.registerOnSharedPreferenceChangeListener(this);        String sortOrder = sharedPreferences.getString(this.getString(R.string.pref_list_key),                this.getString(R.string.pref_list_value_popular));        if (sortOrder.equals(this.getString(R.string.pref_list_value_popular))) {            Log.d(TAG, "Sorting By Popular");            loadMoviesPopular();        } else if (sortOrder.equals(this.getString(R.string.pref_list_value_top_rated))) {            Log.d(TAG, "Sorting By Top Rated");            loadTopRatedMovies();        } else if(sortOrder.equals(this.getString(R.string.pref_list_value_favourites)))            Log.d(TAG, "Sorting By Favourites");              loadFavoriteMovies();        }    /*Network call for the Popular Movies List*/    private void loadMoviesPopular() {        if (isConnected) {            final Call<MoviesList> moviesCall = TmdbRestClient.getInstance()                    .getPopularMoviesResponse()                    .getPopularMovies();            mLoaderIndicator.setVisibility(View.VISIBLE);            moviesCall.enqueue(new Callback<MoviesList>() {                @Override                public void onResponse(@NonNull Call<MoviesList> call, @NonNull Response<MoviesList> response) {                    mLoaderIndicator.setVisibility(View.GONE);                    List<Movies> movies = response.body().getResults();                    mMoviesAdapter.swapMovies(getApplicationContext(), movies);                    recyclerView.setAdapter(mMoviesAdapter);                    recyclerView.smoothScrollToPosition(0);                    recyclerView.setItemAnimator(new DefaultItemAnimator());                }                @Override                public void onFailure(@NonNull Call<MoviesList> call, @NonNull Throwable t) {                    mLoaderIndicator.setVisibility(View.GONE);                    Log.e(TAG, t.getMessage());                    mEmptyView.setText(getResources().getString(R.string.error_in_callback));                }            });        } else {            mEmptyView.setText(getResources().getString(R.string.no_internet_connection));        }    }    /*Network call for the TopRated Movies List*/    private void loadTopRatedMovies() {        if (isConnected) {            final Call<MoviesList> moviesCall = TmdbRestClient.getInstance()                    .getTopRatedMoviesResponse()                    .getTopRatedMovies();            mLoaderIndicator.setVisibility(View.VISIBLE);            moviesCall.enqueue(new Callback<MoviesList>() {                @Override                public void onResponse(@NonNull Call<MoviesList> call, @NonNull Response<MoviesList> response) {                    mLoaderIndicator.setVisibility(View.GONE);                    List<Movies> movies = response.body().getResults();                    mMoviesAdapter.swapMovies(getApplicationContext(), movies);                    recyclerView.setAdapter(mMoviesAdapter);                    recyclerView.smoothScrollToPosition(0);                    recyclerView.setItemAnimator(new DefaultItemAnimator());                }                @Override                public void onFailure(@NonNull Call<MoviesList> call, @NonNull Throwable t) {                    mLoaderIndicator.setVisibility(View.GONE);                    Log.e(TAG, t.getMessage());                    mEmptyView.setText(getResources().getString(R.string.error_in_callback));                }            });        } else {            /*When internet connection fails*/            mEmptyView.setText(getResources().getString(R.string.no_internet_connection));        }    }    @Override    public boolean onCreateOptionsMenu(Menu menu) {        getMenuInflater().inflate(R.menu.main, menu);        return true;    }    @Override    public boolean onOptionsItemSelected(MenuItem item) {        int id = item.getItemId();        if (id == R.id.menu_action_settings) {            Intent startSettingsIntent = new Intent(this, SettingsActivity.class);            startActivity(startSettingsIntent);            return true;        }        return super.onOptionsItemSelected(item);    }    @Override    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {        Log.d(TAG, "SharedPreferences has been changed");        checkSharedPreferences();    }    @Override    protected void onDestroy() {        super.onDestroy();        PreferenceManager.getDefaultSharedPreferences(this)                .unregisterOnSharedPreferenceChangeListener(this);    }    private void loadFavoriteMovies()    {        FavoritesAdapter favoritesAdapter = new FavoritesAdapter();        recyclerView.setAdapter(favoritesAdapter);        getSupportLoaderManager().initLoader(Constants.MOVIE_LOADER_ID,null,new FavoritesCursorLoader(                this,favoritesAdapter));    }}