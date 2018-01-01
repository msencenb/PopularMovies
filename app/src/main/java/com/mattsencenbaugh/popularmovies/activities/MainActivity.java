package com.mattsencenbaugh.popularmovies.activities;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.databinding.DataBindingUtil;

import com.mattsencenbaugh.popularmovies.data.MovieContract;
import com.mattsencenbaugh.popularmovies.data.MovieDbHelper;
import com.mattsencenbaugh.popularmovies.models.Movie;
import com.mattsencenbaugh.popularmovies.adapters.MovieAdapter;
import com.mattsencenbaugh.popularmovies.R;
import com.mattsencenbaugh.popularmovies.databinding.ActivityMainBinding;
import com.mattsencenbaugh.popularmovies.interfaces.AsyncTaskDelegate;
import com.mattsencenbaugh.popularmovies.tasks.GetMoviesTask;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler,
        AsyncTaskDelegate,
        LoaderManager.LoaderCallbacks<Cursor> {

    ActivityMainBinding mBinding;

    private MovieAdapter mMovieAdapter;
    private int selectedSort = R.id.top_rated;
    public static final int ID_MOVIES_LOADER = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns());
        mBinding.rvMovies.setLayoutManager(layoutManager);

        mMovieAdapter = new MovieAdapter(this, this);
        mBinding.rvMovies.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    private void loadMovieData() {
        new GetMoviesTask(this, selectedSort);
    }

    //region AsyncTaskDelegate
    @Override
    public void onPreExecute() {
        showLoadingIndicator();
    }

    @Override
    public void onPostExecute(List<? extends Serializable> results) {
        showMovieGrid();
        // QQQ how do I do a 'checked' cast here? Can't get it working with a List it seems like
        List<Movie> r = (List<Movie>)results;
        mMovieAdapter.setMovies(r);
    }

    @Override
    public void onFailure(Boolean cancelled, String message) {
        showErrorMessage();
    }
    //endregion

    //region MovieAdapter
    @Override
    public void onMovieClicked(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("Movie", (Serializable)movie);
        startActivity(intentToStartDetailActivity);
    }
    //endregion

    //region Show/Hide utilities
    private void showLoadingIndicator() {
        mBinding.tvApiErrorMessage.setVisibility(View.INVISIBLE);
        mBinding.rvMovies.setVisibility(View.INVISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showMovieGrid() {
        mBinding.tvApiErrorMessage.setVisibility(View.INVISIBLE);
        mBinding.rvMovies.setVisibility(View.VISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mBinding.tvApiErrorMessage.setVisibility(View.VISIBLE);
        mBinding.rvMovies.setVisibility(View.INVISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
    }
    //endregion

    //region Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);

        MenuItem item = menu.findItem(selectedSort);
        item.setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(!item.isChecked());

        if (id == R.id.top_rated || id == R.id.popular) {
            selectedSort = id;
            loadMovieData();
            return true;
        } else if (id == R.id.favorites) {
            selectedSort = id;
            getSupportLoaderManager().initLoader(ID_MOVIES_LOADER, null, this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        showLoadingIndicator();
        switch (id) {
            case ID_MOVIES_LOADER:
                Uri favoriteMovieQueryUri = MovieContract.MovieEntry.FAVORITE_URI;
                String sortOrder = MovieContract.MovieEntry.COLUMN_TITLE + " DESC";

                return new CursorLoader(this,
                        favoriteMovieQueryUri,
                        MovieDbHelper.ALL_MOVIE_PROJECTIONS,
                        null,
                        null,
                        sortOrder
                );

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mMovieAdapter.setCursor(cursor);
        showMovieGrid();
        // TODO smooth scroll back to the top
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.setCursor(null);
    }
    //endregion
}
