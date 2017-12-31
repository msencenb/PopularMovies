package com.mattsencenbaugh.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattsencenbaugh.popularmovies.R;
import com.mattsencenbaugh.popularmovies.models.Review;

import java.util.List;

/**
 * Created by msencenb on 12/22/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private List<Review> reviews;

    final private ReviewAdapterOnClickHandler mClickHandler;

    public interface ReviewAdapterOnClickHandler {
        void onReviewClicked(Review review);
    }

    public ReviewAdapter(ReviewAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.updateForReview(review);
    }

    @Override
    public int getItemCount() {
        if (reviews == null) return 0;

        return reviews.size();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mContentTextView;
        final TextView mAuthorTextView;

        public ReviewAdapterViewHolder(View view) {
            super(view);
            mAuthorTextView = view.findViewById(R.id.tv_review_author);
            mContentTextView = view.findViewById(R.id.tv_review_content);
            view.setOnClickListener(this);
        }

        public void updateForReview(Review review) {
            mAuthorTextView.setText(review.getAuthor());
            mContentTextView.setText(review.getContent());
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            Review review = reviews.get(index);
            mClickHandler.onReviewClicked(review);
        }
    }
}
