package com.mattsencenbaugh.popularmovies;

import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattsencenbaugh.popularmovies.databinding.MovieDetailBinding;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by msencenb on 8/24/17.
 */

public class DetailActivity extends AppCompatActivity {
    MovieDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        mBinding = DataBindingUtil.setContentView(this, R.layout.movie_detail);

        Intent intent = getIntent();
        if (intent.hasExtra("Movie")) {
            Movie movie = (Movie) intent.getSerializableExtra("Movie");
            mBinding.tvMovieTitle.setText(movie.getTitle());
            mBinding.tvMoviePlot.setText(movie.getPlot());
            Resources res = getResources();
            String movieAverage = res.getString(R.string.average_rating, movie.getVoteAverage());
            mBinding.tvMovieVoteAverage.setText(movieAverage);

            Date date = movie.getReleaseDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US);
            String releaseDate = dateFormat.format(date);
            mBinding.tvMovieReleaseDate.setText(releaseDate);

            ImageView moviePoster = mBinding.detailMoviePoster;
            Picasso.with(moviePoster.getContext()).load(movie.getPosterPath()).into(moviePoster);
        }
    }
}
