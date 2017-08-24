package com.mattsencenbaugh.popularmovies.tasks;


import android.os.AsyncTask;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mattsencenbaugh.popularmovies.Movie;
import com.mattsencenbaugh.popularmovies.R;
import com.mattsencenbaugh.popularmovies.interfaces.AsyncTaskDelegate;
import com.mattsencenbaugh.popularmovies.utilities.NetworkUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Date;
import java.util.Locale;

/**
 * Created by msencenb on 8/21/17.
 */

public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private final AsyncTaskDelegate mTaskDelegate;
    private final int sort;

    public FetchMoviesTask(AsyncTaskDelegate delegate, int sort) {
        this.mTaskDelegate = delegate;
        this.sort = sort;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mTaskDelegate.onPreExecute();
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        URL moviesUrl = null;

        if (this.sort == R.id.top_rated) {
            moviesUrl = NetworkUtilities.buildTopRatedUrl();
        } else if (this.sort == R.id.popular) {
            moviesUrl = NetworkUtilities.buildPopularUrl();
        } else {
            // Bail - we aren't sure what url to request;
            return null;
        }

        try {
            String jsonMovieResponse = NetworkUtilities.getResponseFromHttpUrl(moviesUrl);
            return parseMovieResponse(jsonMovieResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        mTaskDelegate.onPostExecute(movies);
    }

    private ArrayList<Movie> parseMovieResponse(String movieJsonString) throws JSONException {

        final String RESULTS_KEY = "results";

        // QQQ How can I make this similar to swift's Codable implementation? I like the typesafety that creates
        final String POSTER_KEY = "poster_path";
        final String TITLE_KEY = "title";
        final String RELEASE_KEY = "release_date";
        final String VOTE_AVERAGE_KEY = "vote_average";
        final String PLOT_KEY = "overview";

        JSONObject movieJson = new JSONObject(movieJsonString);
        JSONArray movieArray = movieJson.getJSONArray(RESULTS_KEY);

        ArrayList<Movie> movies = new ArrayList<>(movieArray.length());

        for (int i = 0; i < movieArray.length(); i++) {
            /* Get the JSON object representing the movie */
            JSONObject movieDetails = movieArray.getJSONObject(i);

            String title = movieDetails.getString(TITLE_KEY);
            Double voteAverage = movieDetails.getDouble(VOTE_AVERAGE_KEY);
            String plot = movieDetails.getString(PLOT_KEY);
            Date date = getDateFromJsonString(movieDetails.getString(RELEASE_KEY));
            String posterPath = getFullPosterPathFromJsonString(movieDetails.getString(POSTER_KEY));

            Movie movie = new Movie(title, posterPath, voteAverage, date, plot);
            movies.add(movie);
        }

        return movies;
    }

    private String getFullPosterPathFromJsonString(String posterPath) {
        final String POSTER_BASE_PATH = "http://image.tmdb.org/t/p/w185";
        return POSTER_BASE_PATH + posterPath;
    }

    private Date getDateFromJsonString(String jsonDate) {
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = null;
        try {
            date = sourceFormat.parse(jsonDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
