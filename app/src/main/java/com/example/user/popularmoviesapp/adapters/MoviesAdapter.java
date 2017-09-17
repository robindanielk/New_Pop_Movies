package com.example.user.popularmoviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.popularmoviesapp.Helper.Constants;
import com.example.user.popularmoviesapp.R;
import com.example.user.popularmoviesapp.activities.DetailsActivity;
import com.example.user.popularmoviesapp.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 4/4/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private List<Movies> moviesList;
    private Context context;

    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private static final String BASE_URL = "http://image.tmdb.org/t/p/w185/";

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);

        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.originalTitle.setText(moviesList.get(position).getTitle());
        String posterImage = BASE_URL + moviesList.get(position).getPosterPath();
        Picasso.with(context)
                .load(posterImage)
                .error(R.drawable.no_movie_image)
                .placeholder(R.drawable.load)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (null == moviesList) {
            return 0;
        }
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gv_image)
        ImageView image;
        @BindView(R.id.gv_tv_original_title)
        TextView originalTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        Movies selectedMoviesPos = moviesList.get(pos);
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra(Constants.MOVIE_INTENT_ID, selectedMoviesPos);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Log.d(TAG, "Selected Movie Position " + selectedMoviesPos);
                    }
                }
            });
        }
    }

    public MoviesAdapter(Context context, List<Movies> movies) {
        this.context = context;
        this.moviesList = movies;
    }

    public void swapMovies(Context context, List<Movies> movies) {
        this.context = context;
        this.moviesList = movies;
        notifyDataSetChanged();
    }
}
