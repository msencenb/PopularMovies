package com.mattsencenbaugh.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;
import com.mattsencenbaugh.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by msencenb on 8/21/17.
 */

public final class NetworkUtilities {
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
    private static final String TAG = "MoviesNetworkRequest";

    private static final String MOVIES_INDEX_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String MOVIES_POPULAR = "popular";
    private static final String MOVIES_TOP_RATED = "top_rated";

    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY = BuildConfig.API_KEY;

    public static URL buildPopularUrl() {
        Uri builtUri = buildUrlWithSort(MOVIES_POPULAR);
        return initializeUrl(builtUri);
    }

    public static URL buildTopRatedUrl() {
        Uri builtUri = buildUrlWithSort(MOVIES_TOP_RATED);
        return initializeUrl(builtUri);
    }

    private static URL initializeUrl(Uri uri) {
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);
        return url;
    }

    private static Uri buildUrlWithSort(String sort) {
        return Uri.parse(MOVIES_INDEX_BASE_URL).buildUpon()
                .appendPath(sort)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
    }
}