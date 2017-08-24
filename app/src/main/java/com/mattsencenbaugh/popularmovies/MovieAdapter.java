package com.mattsencenbaugh.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by msencenb on 8/17/17.
 */

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private ArrayList<Movie> movies;

    final private MovieAdapterOnClickHandler mClickHandler;

    interface MovieAdapterOnClickHandler {
        void onMovieClicked(Movie movie);
    }

    MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        public void updateForMovie(Movie movie) {
            Picasso.with(mImageView.getContext()).load(movie.getPosterUrl()).into(mImageView);
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            Movie movie = movies.get(index);
            mClickHandler.onMovieClicked(movie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForPosterItem = R.layout.movie_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForPosterItem, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.updateForMovie(movie);
    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;

        return movies.size();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
