package com.mattsencenbaugh.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by msencenb on 8/17/17.
 */

public class Movie {
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("release_date")
    private Date releaseDate;
    private String plot;

    public Movie() {}

    public Movie(String title, String posterPath, Double voteAverage, Date releaseDate, String plot) {
        this.title = title;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.plot = plot;
    }

    public String getPosterPath() {
        return "http://image.tmdb.org/t/p/w185" + posterPath;
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
