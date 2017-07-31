package com.example.user.popularmoviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.popularmoviesapp.R;
import com.example.user.popularmoviesapp.model.Movies;
import com.example.user.popularmoviesapp.model.MoviesList;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;

/**
 * Created by user on 4/4/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>
{
    private List<Movies> movies;
    private LayoutInflater inflater;
    private Context context;

    private static final String BASE_URL ="http://image.tmdb.org/t/p/w185/";

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType)
    {
        View rootView = inflater.inflate(R.layout.grid_item,parent,false);
        final MyViewHolder holder = new MyViewHolder(rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Not been implemented",Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.originalTitle.setText(movies.get(position).getTitle());
        String posterImage = BASE_URL + movies.get(position).getPosterPath();
        Picasso.with(context)
                .load(posterImage)
                .error(R.drawable.no_movie_image)
                .placeholder(R.drawable.load)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        if(null == movies){
            return 0;
        }
        return movies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.gv_image) ImageView image;
        @BindView(R.id.gv_tv_original_title) TextView originalTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public MoviesAdapter(Context context, List<Movies> movies)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.movies = movies;
    }
}
