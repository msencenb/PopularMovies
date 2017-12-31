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
