package com.mattsencenbaugh.popularmovies;

import android.support.design.widget.TabLayout;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mattsencenbaugh.popularmovies.databinding.MovieDetailBinding;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by msencenb on 8/24/17.
 */

public class DetailActivity extends AppCompatActivity {
    static final int NUM_ITEMS = 3;
    MovieFragmentAdapter fragmentAdapter;
    ViewPager viewPager;
    Movie mMovie;

    MovieDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        mBinding = DataBindingUtil.setContentView(this, R.layout.movie_detail);

        fragmentAdapter = new MovieFragmentAdapter(getSupportFragmentManager());
        viewPager = mBinding.pager;
        viewPager.setAdapter(fragmentAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = mBinding.slidingTabs;
        tabLayout.setupWithViewPager(viewPager);


        Intent intent = getIntent();
        if (intent.hasExtra("Movie")) {
            Movie movie = (Movie) intent.getSerializableExtra("Movie");

            setupTopViewWithMovie(movie);
            mMovie = movie;
        }
    }

    private void setupTopViewWithMovie(Movie movie) {
        mBinding.tvMovieTitle.setText(movie.getTitle());
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

    public class MovieFragmentAdapter extends FragmentPagerAdapter {
        public MovieFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();

            switch (position) {
                case 0:
                    bundle.putString("plot", mMovie.getPlot());
                    PlotFragment pFragment = new PlotFragment();
                    pFragment.setArguments(bundle);
                    return pFragment;
                case 1:
                    bundle.putSerializable("movie", mMovie);
                    ReviewFragment rFragment = new ReviewFragment();
                    rFragment.setArguments(bundle);
                    return rFragment;
                case 2:
                    bundle.putSerializable("movie", mMovie);
                    VideoFragment vFragment = new VideoFragment();
                    vFragment.setArguments(bundle);
                    return vFragment;
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Plot";
                case 1:
                    return "Reviews";
                case 2:
                    return "Videos";
            }
            return "";
        }
    }
}
