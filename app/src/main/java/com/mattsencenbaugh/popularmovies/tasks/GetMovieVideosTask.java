package com.mattsencenbaugh.popularmovies.tasks;

import android.util.Log;

import com.mattsencenbaugh.popularmovies.models.Movie;
import com.mattsencenbaugh.popularmovies.models.Video;
import com.mattsencenbaugh.popularmovies.interfaces.AsyncTaskDelegate;
import com.mattsencenbaugh.popularmovies.interfaces.TMDBService;
import com.mattsencenbaugh.popularmovies.utilities.Envelope;
import com.mattsencenbaugh.popularmovies.utilities.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by msencenb on 12/27/17.
 */

public class GetMovieVideosTask implements Callback<Envelope<List<Video>>> {
    private final AsyncTaskDelegate mTaskDelegate;

    public GetMovieVideosTask(AsyncTaskDelegate delegate, Movie movie) {
        mTaskDelegate = delegate;
        mTaskDelegate.onPreExecute();

        TMDBService service = ServiceGenerator.createService(TMDBService.class);
        Call<Envelope<List<Video>>> call = service.getMovieVideos(movie.getId());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Envelope<List<Video>>> call, Response<Envelope<List<Video>>> response) {
        if (response.isSuccessful()) {
            Envelope<List<Video>> envelope = response.body();
            List<Video> videoList = envelope.getResults();
            mTaskDelegate.onPostExecute(videoList);
        } else {
            System.out.println(response.errorBody());
            mTaskDelegate.onFailure(false,"failed");
        }
    }

    //todo DRY this up, all of these tasks share the same failure code - add a superclass
    @Override
    public void onFailure(Call<Envelope<List<Video>>> call, Throwable t) {
        t.printStackTrace();
        if (call.isCanceled()) {
            Log.e(TAG, "request was cancelled");
            mTaskDelegate.onFailure(true, "cancelled");
        } else {
            Log.e(TAG, "Other larger issue");
            mTaskDelegate.onFailure(false, "failed onfailure");
        }
    }
}
