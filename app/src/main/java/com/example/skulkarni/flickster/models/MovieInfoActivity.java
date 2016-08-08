package com.example.skulkarni.flickster.models;

import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.skulkarni.flickster.R;

import org.w3c.dom.Text;

/**
 * Created by skulkarni on 8/5/16.
 */
public class MovieInfoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        String movieName = getIntent().getStringExtra("movie_name");
        String overview = getIntent().getStringExtra("overview");
        Float rating = getIntent().getFloatExtra("rating", 0) / 10f * 5f;

        RatingBar rb = (RatingBar) findViewById(R.id.rating_bar);
        TextView movieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        TextView movieOverview = (TextView) findViewById(R.id.tvMovieOverview);

        rb.setRating(rating);
        movieTitle.setText(movieName);
        movieOverview.setText(overview);
    }

}
