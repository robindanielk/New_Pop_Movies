package com.example.user.popularmoviesapp;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mEmptyView;
    private ProgressBar mLoaderIndicator;
    private Boolean isConnected;
    private GridViewAdapter gridViewAdapter;

    private Resources res;

    private ArrayList<PopularMovies> popularMoviesArrayList = new ArrayList<>();
    private static final String POPULAR_MOVIES_KEY = "key";


    private static final String MOVIE_BASE_URI = "https://api.themoviedb.org/3/discover/movie";
    private String API_KEY ;
    private String DEFAULT_SORT_BY ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null)
        {
            popularMoviesArrayList = savedInstanceState.getParcelableArrayList(POPULAR_MOVIES_KEY);

        }

        res = getResources();
        API_KEY = res.getString(R.string.movie_api);
        DEFAULT_SORT_BY = res.getString(R.string.sort_by_revenue_default);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        mLoaderIndicator = (ProgressBar) findViewById(R.id.gv_pb_indicator);
        mEmptyView  = (TextView) findViewById(R.id.tv_empty_tv);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        gridViewAdapter = new GridViewAdapter(this);
        recyclerView.setAdapter(gridViewAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        sortByOptions(DEFAULT_SORT_BY);
    }

    private void sortByOptions(final String sortBy)
    {
        if(isConnected) {
            new AsyncTask<String, Void, List<PopularMovies>>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    mLoaderIndicator.setVisibility(View.VISIBLE);

                }

                @Override
                protected List<PopularMovies> doInBackground(String... params) {
                    if (params.length == 0) {
                        Log.v(TAG,"Input Url is invalid");
                        return null;
                    }
                    Uri baseUri = Uri.parse(MOVIE_BASE_URI).buildUpon()
                            .appendQueryParameter("api_key", API_KEY)
                            .appendQueryParameter("language", "en-US")
                            .appendQueryParameter("sort_by", sortBy)
                            .appendQueryParameter("include_adult", "false")
                            .appendQueryParameter("page", "1")
                            .build();
                    return QueryUtils.extractPopularMovies(baseUri.toString());
                }

                @Override
                protected void onPostExecute(List<PopularMovies> popularMovies) {
                    mLoaderIndicator.setVisibility(View.GONE);
                    if (popularMovies != null)
                    {
                        setArrayValues(popularMovies);
                    } else {
                        mEmptyView.setText(res.getString(R.string.no_current_movies));
                    }
                }
            }.execute(sortBy);
        }else
        {
            mEmptyView.setText(res.getString(R.string.no_internet_connection));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_popular:
                popularMoviesArrayList.clear();
                sortByOptions(res.getString(R.string.sort_by_popularity));
                return true;
            case R.id.menu_top_rated:
                popularMoviesArrayList.clear();
                sortByOptions(res.getString(R.string.sort_by_vote_average));
                return true;
            case R.id.menu_highest_revenue:
                popularMoviesArrayList.clear();
                sortByOptions(res.getString(R.string.sort_by_revenue_default));
                return true;
            default:
                Log.e(TAG,"Error in sortByString method call");
                return super.onOptionsItemSelected(item);
        }
    }

    private void setArrayValues(List<PopularMovies> popularMovies)
    {
        for(PopularMovies pMovies:popularMovies)
        {
            popularMoviesArrayList.add(pMovies);
        }
        gridViewAdapter.setMovieList(popularMoviesArrayList);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(POPULAR_MOVIES_KEY,popularMoviesArrayList);
    }
}
