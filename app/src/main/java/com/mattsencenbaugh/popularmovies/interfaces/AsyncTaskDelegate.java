package com.mattsencenbaugh.popularmovies.interfaces;

/**
 * Created by msencenb on 8/21/17.
 */
import com.mattsencenbaugh.popularmovies.Movie;
import java.util.ArrayList;

public interface AsyncTaskDelegate {
    void onPreExecute();
    // TODO make this generic
    // QQQ How do I actually do that? I could make 'Movie' here an interface instead - or try to resuse 'Result' from async task (having trouble importing)?
    void onPostExecute(ArrayList<Movie> movies);
}