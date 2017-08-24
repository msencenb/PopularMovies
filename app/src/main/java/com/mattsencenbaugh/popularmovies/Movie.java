package com.mattsencenbaugh.popularmovies;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by msencenb on 8/17/17.
 */

public class Movie implements Serializable {
    private final String title;
    private final String posterUrl;
    private final Double voteAverage;
    private final Date releaseDate;
    private final String plot;

    public Movie(String title, String posterUrl, Double voteAverage, Date releaseDate, String plot) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.plot = plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getPlot() {
        return plot;
    }
}
