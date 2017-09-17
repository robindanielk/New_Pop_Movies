package com.example.user.popularmoviesapp.adapters;import android.content.ActivityNotFoundException;import android.content.Context;import android.content.Intent;import android.net.Uri;import android.support.v7.widget.CardView;import android.support.v7.widget.RecyclerView;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.ImageView;import android.widget.TextView;import com.example.user.popularmoviesapp.Helper.Constants;import com.example.user.popularmoviesapp.R;import com.example.user.popularmoviesapp.model.MovieVideoResults;import com.squareup.picasso.Picasso;import java.util.List;import butterknife.BindView;import butterknife.ButterKnife;/** * Created by USER on 8/2/2017. */public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {    private List<MovieVideoResults> movieVideos;    private Context context;    private LayoutInflater inflater;    private final static String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";    public TrailersAdapter(List<MovieVideoResults> movieVideos, Context context) {        this.context = context;        this.inflater = LayoutInflater.from(context);        this.movieVideos = movieVideos;    }    @Override    public TrailersAdapter.TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {        View rootView = inflater.inflate(R.layout.rv_movie_trailers_thumbnail                , parent, false);        TrailersViewHolder holder = new TrailersViewHolder(rootView);        return holder;    }    @Override    public void onBindViewHolder(TrailersAdapter.TrailersViewHolder holder, int position) {        if (movieVideos.get(position).getName() != null) {            holder.trailersText.setText(movieVideos.get(position).getName());        } else {            holder.trailersText.setText(context.getResources()                    .getString(R.string.default_trailer_name));        }        Picasso.with(context)                .load(String.format(YOUTUBE_THUMBNAIL_URL, movieVideos.get(position).getKey()))                .error(R.drawable.no_movie_image)                .into(holder.trailerThumbnail);    }    @Override    public int getItemCount() {        return movieVideos.size();    }    public class TrailersViewHolder extends RecyclerView.ViewHolder {        @BindView(R.id.cardview_trailers)        CardView trailersCard;        @BindView(R.id.iv_trailer_thumbnail)        ImageView trailerThumbnail;        @BindView(R.id.tv_trailers_text)        TextView trailersText;        public TrailersViewHolder(View itemView) {            super(itemView);            ButterKnife.bind(this, itemView);            itemView.setOnClickListener(new View.OnClickListener() {                @Override                public void onClick(View v) {                    watchMoviesOnClick(movieVideos.get(getAdapterPosition()).getKey());                }            });        }    }    /*Selecting the proper Activity for opening the Trailers*/    private void watchMoviesOnClick(String id) {        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_APP + id));        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_URL_BROWSER + id));        try {            context.startActivity(appIntent);        } catch (ActivityNotFoundException e) {            context.startActivity(webIntent);        }    }    public void swapVideos(Context context, List<MovieVideoResults> movieVideos) {        this.context = context;        this.movieVideos = movieVideos;        notifyDataSetChanged();    }}