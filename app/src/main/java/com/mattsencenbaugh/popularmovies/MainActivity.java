package com.mattsencenbaugh.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mattsencenbaugh.popularmovies.interfaces.AsyncTaskDelegate;
import com.mattsencenbaugh.popularmovies.tasks.GetMoviesTask;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, AsyncTaskDelegate {
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private TextView mApiErrorMessage;

    private ProgressBar mLoadingIndicator;

    private int selectedSort = R.id.top_rated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mApiErrorMessage = (TextView) findViewById(R.id.tv_api_error_message);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
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
    public void onPostExecute(ArrayList<Movie> movies) {
        showMovieGrid();
        mMovieAdapter.setMovies(movies);
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
        mApiErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showMovieGrid() {
        mApiErrorMessage.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mApiErrorMessage.setVisibility(View.VISIBLE);
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
        if (id == R.id.top_rated || id == R.id.popular) {
            item.setChecked(!item.isChecked());
            selectedSort = id;
            loadMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion
}
