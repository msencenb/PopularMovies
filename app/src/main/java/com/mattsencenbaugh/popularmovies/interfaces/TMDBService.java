package com.mattsencenbaugh.popularmovies.interfaces;

import com.mattsencenbaugh.popularmovies.BuildConfig;
import com.mattsencenbaugh.popularmovies.Movie;
import com.mattsencenbaugh.popularmovies.Review;
import com.mattsencenbaugh.popularmovies.utilities.Envelope;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by msencenb on 12/12/17.
 */
// Example: http://api.themoviedb.org/3/movie/popular?api_key=343d06e2af355cbcbe6ac0844906e933
public interface TMDBService {
    @GET("3/movie/top_rated")
    Call<Envelope<List<Movie>>> getTopMovies();

    @GET("3/movie/popular")
    Call<Envelope<List<Movie>>> getPopularMovies();

    @GET("3/movie/{movie_id}/reviews")
    Call<Envelope<List<Review>>> getMovieReviews(@Path("movie_id") String movieId);
}
