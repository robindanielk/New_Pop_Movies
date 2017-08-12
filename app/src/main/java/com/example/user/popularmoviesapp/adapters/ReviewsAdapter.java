package com.example.user.popularmoviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.popularmoviesapp.R;
import com.example.user.popularmoviesapp.model.MovieReviewResults;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ridsys-001 on 12/8/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>
{
    private List<MovieReviewResults> reviewResults;
    private Context context;

    private static final String TAG = ReviewsAdapter.class.getSimpleName();

    public ReviewsAdapter(Context context,List<MovieReviewResults> reviewResults)
    {
        this.context = context;
        this.reviewResults = reviewResults;
    }


    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_layout,parent,false);
        return new ReviewsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewsViewHolder holder, int position) {
        if(null == reviewResults)
        {
            holder.reviewsEmptyTv.setText(context.getResources().getString(R.string.empty_reviews));
        }else
        {
            holder.reviewsAuthor.setText(reviewResults.get(position).getAuthor());
            holder.reviewsContent.setText(reviewResults.get(position).getContent());
        }

    }

    @Override
    public int getItemCount() {
        if(null == reviewResults)
        {
            return 0 ;
        }
        return reviewResults.size();
    }


    public class ReviewsViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.reviews_author)
        TextView reviewsAuthor;

        @BindView(R.id.reviews_content)
        TextView reviewsContent;

        @BindView(R.id.reviews_empty_tv)
        TextView reviewsEmptyTv;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
