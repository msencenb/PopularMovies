package com.mattsencenbaugh.popularmovies.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mattsencenbaugh.popularmovies.data.MovieContract;
import com.mattsencenbaugh.popularmovies.data.MovieDbHelper;
import com.mattsencenbaugh.popularmovies.models.Movie;
import com.mattsencenbaugh.popularmovies.fragments.PlotFragment;
import com.mattsencenbaugh.popularmovies.R;
import com.mattsencenbaugh.popularmovies.fragments.ReviewFragment;
import com.mattsencenbaugh.popularmovies.fragments.VideoFragment;
import com.mattsencenbaugh.popularmovies.databinding.MovieDetailBinding;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by msencenb on 8/24/17.
 */

public class DetailActivity extends AppCompatActivity implements
        View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    static final int ID_MOVIE_LOADER = 43;
    static final int NUM_ITEMS = 3;
    MovieFragmentAdapter fragmentAdapter;
    ViewPager viewPager;
    Movie mMovie;
    // TODO this is not really ideal since I have to hit the db individually. Favorite should probably be on the Movie model with a custom callback
    Boolean isFavorite;

    MovieDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.movie_detail);
        mBinding = DataBindingUtil.setContentView(this, R.layout.movie_detail);

        fragmentAdapter = new MovieFragmentAdapter(getSupportFragmentManager());
        viewPager = mBinding.pager;
        viewPager.setAdapter(fragmentAdapter);

        AppCompatImageButton button = mBinding.btnFavorite;
        button.setOnClickListener(this);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = mBinding.slidingTabs;
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        if (intent.hasExtra("Movie")) {
            Movie movie = (Movie) intent.getSerializableExtra("Movie");

            setupTopViewWithMovie(movie);
            mMovie = movie;

            getSupportLoaderManager().initLoader(ID_MOVIE_LOADER, null, this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (isFavorite) {
            // delete it
            Uri deleteUri = MovieContract.MovieEntry.buildMovieUriWithId(Long.parseLong(mMovie.getId()));
            int rowsDeleted = this.getContentResolver().delete(deleteUri, null, null);
            if (rowsDeleted == 1) {
                isFavorite = false;
                updateFavoriteButton();
            }
        } else {
            ContentValues movieValues = mMovie.getContentValues();
            this.getContentResolver().insert(
                    MovieContract.MovieEntry.CONTENT_URI,
                    movieValues
            );
            // TODO the insert only returns a uri, not an inserted count. I should probably query to make sure it's inserted?
            isFavorite = true;
            updateFavoriteButton();
        }
    }

    private void updateFavoriteButton(){
        AppCompatImageButton button = mBinding.btnFavorite;
        if (isFavorite) {
            button.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            button.setImageResource(android.R.drawable.btn_star_big_off);
        }
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_MOVIE_LOADER:
                Uri isFavoriteUri = MovieContract.MovieEntry.buildMovieUriWithId(Long.parseLong(mMovie.getId()));
                return new CursorLoader(this,
                        isFavoriteUri,
                        MovieDbHelper.ALL_MOVIE_PROJECTIONS,
                        null,
                        null,
                        null
                );

            default:
                throw new RuntimeException("Loader not implemented " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.getCount() == 1) {
            isFavorite = true;
        } else {
            isFavorite = false;
        }
        updateFavoriteButton();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        AppCompatImageButton button = mBinding.btnFavorite;
        button.setVisibility(View.INVISIBLE);
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
