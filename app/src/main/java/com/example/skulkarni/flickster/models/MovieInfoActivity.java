package com.example.skulkarni.flickster.models;

import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.skulkarni.flickster.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

/**
 * Created by skulkarni on 8/5/16.
 */
public class MovieInfoActivity extends YouTubeBaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        String movieName = getIntent().getStringExtra("movie_name");
        String overview = getIntent().getStringExtra("overview");
        int movieID = getIntent().getIntExtra("movieID", 0);

        String url = "https://api.themoviedb.org/3/movie/" + movieID + "/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Remember to CLEAR OUT old items before appending in the new ones
                JSONArray trailerResults = null;

                try {
                    trailerResults = response.getJSONArray("youtube");

                    JSONObject trailer;
                    trailer = trailerResults.getJSONObject(0);
                    final String source = trailer.getString("source");

                    YouTubePlayerView youTubePlayerView =
                            (YouTubePlayerView) findViewById(R.id.player);

                    youTubePlayerView.initialize("AIzaSyBo_eMEDhEzrNQ_7MfFCECAwbNbOv4ggqE",
                            new YouTubePlayer.OnInitializedListener() {
                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                    YouTubePlayer youTubePlayer, boolean b) {

                                    // do any work here to cue video, play video, etc.
                                    youTubePlayer.cueVideo(source);
                                }
                                @Override
                                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                    YouTubeInitializationResult youTubeInitializationResult) {

                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }


        });

        Float rating = getIntent().getFloatExtra("rating", 0) / 10f * 5f;

        RatingBar rb = (RatingBar) findViewById(R.id.rating_bar);
        TextView movieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        TextView movieOverview = (TextView) findViewById(R.id.tvMovieOverview);

        rb.setRating(rating);
        movieTitle.setText(movieName);
        movieOverview.setText(overview);


    }

}
