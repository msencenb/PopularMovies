package com.mattsencenbaugh.popularmovies.interfaces;

/**
 * Created by msencenb on 8/21/17.
 */
import com.mattsencenbaugh.popularmovies.Movie;
import java.util.ArrayList;

public interface AsyncTaskDelegate {
    void onPreExecute();
    // TODO make this generic. See https://stackoverflow.com/questions/5735320/java-generics-on-an-android-asynctask and
    // https://www.javacodegeeks.com/2013/12/advanced-java-generics-retreiving-generic-type-arguments.html
    // type is probably Object/T
    void onPostExecute(ArrayList<Movie> movies);
    void onFailure(Boolean cancelled, String message);
}