package com.mattsencenbaugh.popularmovies.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.mattsencenbaugh.popularmovies.data.MovieContract;
import com.mattsencenbaugh.popularmovies.data.MovieDbHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by msencenb on 8/17/17.
 */

public class Movie implements Serializable {
    private String id;
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("release_date")
    private Date releaseDate;
    @SerializedName("overview")
    private String plot;

    public Movie() {}

    public Movie(String title, String posterPath, Double voteAverage, Date releaseDate, String plot) {
        this.title = title;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.plot = plot;
    }

    public Movie(Cursor cursor) {
        this.id = String.valueOf(cursor.getInt(MovieDbHelper.INDEX_TMDB_ID));
        this.title = cursor.getString(MovieDbHelper.INDEX_TITLE);
        this.posterPath = cursor.getString(MovieDbHelper.INDEX_POSTER_PATH);
        this.voteAverage = cursor.getDouble(MovieDbHelper.INDEX_AVERAGE_VOTE);
        this.releaseDate = new Date(cursor.getLong(MovieDbHelper.INDEX_RELEASE_DATE) * 1000);
        this.plot = cursor.getString(MovieDbHelper.INDEX_PLOT);
    }

    public String getId() {
        return id;
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

    public ContentValues getContentValues() {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_TMDB_ID, id);
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, title);
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, posterPath);
        movieValues.put(MovieContract.MovieEntry.COLUMN_AVERAGE_VOTE, voteAverage);
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDate.getTime() * 1000);
        movieValues.put(MovieContract.MovieEntry.COLUMN_PLOT, plot);
        return movieValues;
    }
}
