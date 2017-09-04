package com.example.user.popularmoviesapp.adapters;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.popularmoviesapp.Helper.Constants;
import com.example.user.popularmoviesapp.R;
import com.example.user.popularmoviesapp.activities.DetailsActivity;
import com.example.user.popularmoviesapp.database.MovieContract;
import com.example.user.popularmoviesapp.model.Movies;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ridsys-001 on 4/9/17.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>
{
    private Cursor favCursor;

    private static final String TAG = FavoritesAdapter.class.getSimpleName();
    private static final String BASE_URL ="http://image.tmdb.org/t/p/w185/";

    public FavoritesAdapter(){}

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item,parent,false);
        return new FavoritesViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        favCursor.moveToPosition(position);
        String posterPath = BASE_URL + favCursor.getString(favCursor.getColumnIndex(MovieContract
                .MoviesEntry.COLUMN_MOVIE_POSTER));
        Picasso.with(holder.imageView.getContext())
                .load(posterPath)
                .error(R.drawable.no_movie_image)
                .placeholder(R.drawable.load)
                .into(holder.imageView);
        holder.originalTitle.setText(favCursor.getString(favCursor.getColumnIndex(MovieContract
                .MoviesEntry.COLUMN_MOVIE_TITLE)));
    }

    @Override
    public int getItemCount() {
        if(favCursor == null){
        return 0;
        }
        return favCursor.getCount();
    }






     public void swapCursor(Cursor favCursor)
     {

         this.favCursor = favCursor;
         notifyDataSetChanged();
     }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gv_image)
        ImageView imageView;

        @BindView(R.id.gv_tv_original_title)
        TextView originalTitle;

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newIntent = new Intent(v.getContext(), DetailsActivity.class);
                    favCursor.moveToPosition(getAdapterPosition());
                    Movies currentMovie = getCurrentMovies();
                    newIntent.putExtra(Constants.MOVIE_INTENT_ID,currentMovie);
                    v.getContext().startActivity(newIntent);
                }
            });
        }
    }

    private Movies getCurrentMovies()
    {
        Movies moviesList = new Movies();
        moviesList.setId(favCursor.getInt(favCursor.getColumnIndex(MovieContract.MoviesEntry
                .COLUMN_MOVIE_ID)));
        moviesList.setTitle(favCursor.getString(favCursor.getColumnIndex(MovieContract.
                MoviesEntry.COLUMN_MOVIE_TITLE)));
        moviesList.setReleaseDate(favCursor.getString(favCursor.getColumnIndex(MovieContract
                .MoviesEntry.COLUMN_MOVIE_RELEASE_DATE)));
        moviesList.setVoteAverage(favCursor.getDouble(favCursor.getColumnIndex(MovieContract
                .MoviesEntry.COLUMN_MOVIE_USER_RATING)));
        moviesList.setBackdropPath(favCursor.getString(favCursor.getColumnIndex(MovieContract
                .MoviesEntry.COLUMN_MOVIE_BACKDROP)));
        moviesList.setPosterPath(favCursor.getString(favCursor.getColumnIndex(MovieContract
                .MoviesEntry.COLUMN_MOVIE_POSTER)));
        return moviesList;
    }

}
