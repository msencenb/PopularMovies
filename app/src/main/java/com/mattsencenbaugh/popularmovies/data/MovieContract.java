package com.mattsencenbaugh.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by msencenb on 12/31/17.
 */

// Although this is currently only used for favorites, it could be extended for full offline access
public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.mattsencenbaugh.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITES = "favorites";
    public static final String PATH_MOVIE = "movies";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final Uri FAVORITE_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .appendPath(PATH_FAVORITES)
                .build();

        public static Uri buildMovieUriWithId(long id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_TMDB_ID = "tmdb_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_AVERAGE_VOTE = "average_vote";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_PLOT = "plot";
    }
}
