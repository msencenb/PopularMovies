package com.mattsencenbaugh.popularmovies.tasks;

import android.util.Log;

import com.mattsencenbaugh.popularmovies.models.Movie;
import com.mattsencenbaugh.popularmovies.R;
import com.mattsencenbaugh.popularmovies.utilities.Envelope;
import com.mattsencenbaugh.popularmovies.utilities.ServiceGenerator;
import com.mattsencenbaugh.popularmovies.interfaces.AsyncTaskDelegate;
import com.mattsencenbaugh.popularmovies.interfaces.TMDBService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by msencenb on 12/12/17.
 */

public class GetMoviesTask implements Callback<Envelope<List<Movie>>> {
    private final AsyncTaskDelegate mTaskDelegate;

    public GetMoviesTask(AsyncTaskDelegate delegate, int sort) {
        this.mTaskDelegate = delegate;
        mTaskDelegate.onPreExecute();

        TMDBService service = ServiceGenerator.createService(TMDBService.class);

        if (sort == R.id.top_rated) {
            Call<Envelope<List<Movie>>> call = service.getTopMovies();
            call.enqueue(this);
        } else {
            Call<Envelope<List<Movie>>> call = service.getPopularMovies();
            call.enqueue(this);
        }
    }


    @Override
    public void onResponse(Call<Envelope<List<Movie>>> call, Response<Envelope<List<Movie>>> response) {
        if(response.isSuccessful()) {
            Envelope<List<Movie>> envelope = response.body();
            List<Movie> movieList = envelope.getResults();
            mTaskDelegate.onPostExecute(movieList);
        } else {
            System.out.println(response.errorBody());
            mTaskDelegate.onFailure(false,"failed");
        }
    }

    @Override
    public void onFailure(Call<Envelope<List<Movie>>> call, Throwable t) {
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
