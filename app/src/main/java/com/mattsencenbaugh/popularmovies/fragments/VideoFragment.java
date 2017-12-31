package com.mattsencenbaugh.popularmovies.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattsencenbaugh.popularmovies.models.Movie;
import com.mattsencenbaugh.popularmovies.R;
import com.mattsencenbaugh.popularmovies.models.Video;
import com.mattsencenbaugh.popularmovies.adapters.VideoAdapter;
import com.mattsencenbaugh.popularmovies.databinding.VideosBinding;
import com.mattsencenbaugh.popularmovies.interfaces.AsyncTaskDelegate;
import com.mattsencenbaugh.popularmovies.tasks.GetMovieVideosTask;

import java.io.Serializable;
import java.util.List;

/**
 * Created by msencenb on 12/27/17.
 */

public class VideoFragment extends Fragment implements AsyncTaskDelegate, VideoAdapter.VideoAdapterOnClickHandler {
    VideosBinding mBinding;
    Movie mMovie;

    private VideoAdapter mVideoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.videos, container, false);
        View view = mBinding.getRoot();
        mMovie = (Movie)getArguments().getSerializable("movie");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.rvVideos.setLayoutManager(layoutManager);

        mVideoAdapter = new VideoAdapter(this);
        mBinding.rvVideos.setAdapter(mVideoAdapter);

        new GetMovieVideosTask(this, mMovie);

        return view;
    }

    @Override
    public void onPreExecute() {
        mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute(List<? extends Serializable> results) {
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        List<Video> v = (List<Video>)results;
        mVideoAdapter.setVideos(v);
    }

    @Override
    public void onFailure(Boolean cancelled, String message) {
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        //todo show an error message
    }

    @Override
    public void onVideoClicked(Video video) {
        openWebPage(video.getYoutubeLink());
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
