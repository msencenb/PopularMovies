package com.mattsencenbaugh.popularmovies.utilities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by msencenb on 12/13/17.
 */

public class Envelope<T> {
    int page;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("total_pages")
    int totalPages;
    T results;

    public T getResults() {
        return results;
    }
}
