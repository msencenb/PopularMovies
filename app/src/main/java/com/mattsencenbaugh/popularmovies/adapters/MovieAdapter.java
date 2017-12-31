package com.mattsencenbaugh.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mattsencenbaugh.popularmovies.models.Movie;
import com.mattsencenbaugh.popularmovies.R;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by msencenb on 8/17/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    // This adapter is either driven by a list of movies (retrieved by the api)
    // or by a database cursor
    private List<Movie> movies;
    private Cursor mCursor;
    private final Context mContext;

    final private MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onMovieClicked(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mContext = context;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mImageView;

        MovieAdapterViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        void updateForMovie(Movie movie) {
            Picasso.with(mImageView.getContext()).load(movie.getPosterPath()).into(mImageView);

            //TODO consider adding placeholder and error in with picasso
            /*Picasso.with(mImageView.getContext())
                    .load(movie.getPosterUrl())
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder_error)
                    .into(mImageView);*/
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
        Movie movie;
        if (movies != null) {
            movie = movies.get(position);
        } else {
            mCursor.moveToPosition(position);
            movie = new Movie(mCursor);
        }
        holder.updateForMovie(movie);
    }

    @Override
    public int getItemCount() {
        if (movies == null && mCursor == null) return 0;

        if (movies != null) {
            return movies.size();
        } else {
            return mCursor.getCount();
        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        this.mCursor = null;
        notifyDataSetChanged();
    }

    public void setCursor(Cursor newCursor) {
        if (newCursor != null) {
            this.mCursor = newCursor;
            this.movies = null;
        }
        notifyDataSetChanged();
    }
}
