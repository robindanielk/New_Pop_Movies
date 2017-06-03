package com.example.user.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 4/4/2017.
 */

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.MyViewHolder>
{
    private List<PopularMovies> popularMoviesList;
    private LayoutInflater inflater;
    private Context context;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType)
    {
        View rootView = inflater.inflate(R.layout.grid_item,parent,false);
        final MyViewHolder holder = new MyViewHolder(rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("moviesPosition",popularMoviesList.get(position));
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        PopularMovies pmovies = popularMoviesList.get(position);

        Picasso.with(context).load(pmovies.getMovieImage()).error(R.drawable.no_movie_image).
                into(holder.image);



        holder.originalTitle.setText(pmovies.getOriginalTitle());

    }

    @Override
    public int getItemCount() {
        if(null == popularMoviesList){
            return 0;
        }
        return popularMoviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image;
        public TextView originalTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            originalTitle = (TextView) itemView.findViewById(R.id.gv_tv_original_title);
            image = (ImageView) itemView.findViewById(R.id.gv_image);
        }
    }

    public GridViewAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setMovieList(List<PopularMovies> updatedPopularMoviesList)
    {
        this.popularMoviesList = new ArrayList<>();
        this.popularMoviesList.addAll(updatedPopularMoviesList);
        notifyDataSetChanged();
    }


}
