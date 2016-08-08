package com.example.skulkarni.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skulkarni.flickster.R;
import com.example.skulkarni.flickster.models.Movie;
import com.example.skulkarni.flickster.models.MovieInfoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skulkarni on 8/1/16.
 */
public class MovieArrayAdapter extends RecyclerView.Adapter<MovieArrayAdapter.MovieViewHolder>  {

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView overview;
        private ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tvTitle);
            overview = (TextView) itemView.findViewById(R.id.tvOverview);
            poster = (ImageView) itemView.findViewById(R.id.ivMovieImage);
        }

        public void update(Movie movie, Context context) {
            title.setText(movie.getOriginalTitle());
            overview.setText(movie.getOverview());
            String posterPath;

            int orientation = context.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                posterPath = movie.getPosterPath();
            } else {
                posterPath = movie.getBackdropPath();
            }

            Picasso.with(context).load(posterPath)
                    .placeholder(R.drawable.placeholder)
                    .into(poster);

        }
    }
    private List<Movie> mMovies;
    private Context mContext;

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        mMovies = movies;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        // Return a new holder instance
        return new MovieViewHolder(movieView);
    }

    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Movie movie = mMovies.get(position);
        preFetchImages(position, 2);
        viewHolder.update(movie, getContext());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MovieInfoActivity.class);
                i.putExtra("movie_name", movie.getOriginalTitle());
                i.putExtra("rating", movie.getRating());
                i.putExtra("overview", movie.getOverview());
                getContext().startActivity(i);
            }
        });
    }

    public void preFetchImages(int position, int imgsToPreFetch) {
        int length = mMovies.size();
        for (int i = 1; i < imgsToPreFetch+1; i++) {
            Movie nextMovie = mMovies.get(Math.min(position+i, length-1));
            Movie prevMovie = mMovies.get(Math.max(position-i, 0));
            String nextPosterPath;
            String prevPosterPath;

            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                nextPosterPath = nextMovie.getPosterPath();
                prevPosterPath = prevMovie.getPosterPath();
            } else {
                nextPosterPath = nextMovie.getBackdropPath();
                prevPosterPath = prevMovie.getBackdropPath();
            }

            Picasso.with(getContext()).load(nextPosterPath).fetch();
            Picasso.with(getContext()).load(prevPosterPath).fetch();
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void update(List<Movie> newMovies) {
        mMovies = (ArrayList) newMovies;
    }
}
