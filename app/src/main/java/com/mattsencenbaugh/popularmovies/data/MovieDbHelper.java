package com.mattsencenbaugh.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by msencenb on 12/31/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "popular_movies.db";
    public static final int DATABASE_VERSION = 1;

    public static final String[] ALL_MOVIE_PROJECTIONS = {
            MovieContract.MovieEntry.COLUMN_TMDB_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_AVERAGE_VOTE,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_PLOT
    };

    // The indexes representing the projection above
    public static final int INDEX_TMDB_ID = 0;
    public static final int INDEX_TITLE = 1;
    public static final int INDEX_POSTER_PATH = 2;
    public static final int INDEX_AVERAGE_VOTE = 3;
    public static final int INDEX_RELEASE_DATE = 4;
    public static final int INDEX_PLOT = 5;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME
                + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.MovieEntry.COLUMN_TMDB_ID + " INTEGER NOT NULL," +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_AVERAGE_VOTE + " REAL NOT NULL," +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL," +
                MovieContract.MovieEntry.COLUMN_PLOT + " TEXT NOT NULL," +
                " UNIQUE (" + MovieContract.MovieEntry.COLUMN_TMDB_ID + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
