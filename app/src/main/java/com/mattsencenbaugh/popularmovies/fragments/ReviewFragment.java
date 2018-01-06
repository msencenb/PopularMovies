package com.mattsencenbaugh.popularmovies.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattsencenbaugh.popularmovies.models.Movie;
import com.mattsencenbaugh.popularmovies.R;
import com.mattsencenbaugh.popularmovies.models.Review;
import com.mattsencenbaugh.popularmovies.adapters.ReviewAdapter;
import com.mattsencenbaugh.popularmovies.databinding.ReviewsBinding;
import com.mattsencenbaugh.popularmovies.interfaces.AsyncTaskDelegate;
import com.mattsencenbaugh.popularmovies.tasks.GetMovieReviewsTask;

import java.io.Serializable;
import java.util.List;

/**
 * Created by msencenb on 12/21/17.
 */

public class ReviewFragment extends Fragment implements AsyncTaskDelegate, ReviewAdapter.ReviewAdapterOnClickHandler {
    ReviewsBinding mBinding;
    Movie mMovie;

    private ReviewAdapter mReviewAdapter;
    public static final String REVIEW_LAYOUT_STATE = "review_lm_state";
    private Parcelable mPendingLayoutState = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.reviews, container, false);
        View view = mBinding.getRoot();
        mMovie = (Movie)getArguments().getSerializable("movie");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.rvReviews.setLayoutManager(layoutManager);

        mReviewAdapter = new ReviewAdapter(this);
        mBinding.rvReviews.setAdapter(mReviewAdapter);

        //QQQ is it a good idea to be calling the network everytime here?
        // Or should the activity itself pass in a list of reviews rather than the movie?
        new GetMovieReviewsTask(this, mMovie);

        if (savedInstanceState != null) {
            mPendingLayoutState = savedInstanceState.getParcelable(REVIEW_LAYOUT_STATE);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Parcelable layoutState = mBinding.rvReviews.getLayoutManager().onSaveInstanceState();
        savedInstanceState.putParcelable(REVIEW_LAYOUT_STATE, layoutState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onPreExecute() {
        mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute(List<? extends Serializable> results) {
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        List<Review> r = (List<Review>)results;
        mReviewAdapter.setReviews(r);
        if (mPendingLayoutState != null) {
            mBinding.rvReviews.getLayoutManager().onRestoreInstanceState(mPendingLayoutState);
            mPendingLayoutState = null;
        }
        //todo show something for an empty set
    }

    @Override
    public void onFailure(Boolean cancelled, String message) {
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        //todo show error message of some kind
    }

    @Override
    public void onReviewClicked(Review review) {
        // TODO do something when the review is clicked
    }
}
