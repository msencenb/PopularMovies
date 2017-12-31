package com.mattsencenbaugh.popularmovies.interfaces;

/**
 * Created by msencenb on 8/21/17.
 */

import java.io.Serializable;
import java.util.List;

public interface AsyncTaskDelegate {
    void onPreExecute();
    void onPostExecute(List<? extends Serializable> results);
    // TODO make this return an error object rather than this simple message
    void onFailure(Boolean cancelled, String message);
}