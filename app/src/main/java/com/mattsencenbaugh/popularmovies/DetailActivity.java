package com.mattsencenbaugh.popularmovies;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by msencenb on 8/24/17.
 */

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        TextView mTitle = (TextView) findViewById(R.id.tv_movie_title);
        TextView mReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        TextView mVoteAverage = (TextView) findViewById(R.id.tv_movie_vote_average);
        TextView mPlot = (TextView) findViewById(R.id.tv_movie_plot);
        ImageView mPoster = (ImageView) findViewById(R.id.detail_movie_poster);

        Intent intent = getIntent();
        if (intent.hasExtra("Movie")) {
            Movie movie = (Movie) intent.getSerializableExtra("Movie");
            mTitle.setText(movie.getTitle());
            mPlot.setText(movie.getPlot());
            Resources res = getResources();
            String movieAverage = res.getString(R.string.average_rating, movie.getVoteAverage());
            mVoteAverage.setText(movieAverage);

            Date date = movie.getReleaseDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US);
            String releaseDate = dateFormat.format(date);
            mReleaseDate.setText(releaseDate);

            Picasso.with(mPoster.getContext()).load(movie.getPosterUrl()).into(mPoster);
        }
    }
}
