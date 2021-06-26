package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.internal.e;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.net.URL;

import models.Movie;
import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    public static final String TRAILER_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US";

    public static final String TAG = "MovieTrailerActivity";
    String videoId;
Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);
        //Returns the intent that started the activity
        //getStringExtra() -the name of the desired item
        String movieID = getIntent().getStringExtra("movieID");
        String TRAILERAPI = String.format(TRAILER_URL, movieID);
        // temporary test video id -- TODO replace with movie trailer video id
        // Create instance of Async client
        AsyncHttpClient client = new AsyncHttpClient();
        // Make HTTP request to get API data
        client.get(TRAILERAPI, new JsonHttpResponseHandler() {
            @Override
            // onSuccess, get data into a JSONObject
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
              try {
                      // Get results into JSONArray
                      JSONArray results = jsonObject.getJSONArray("results");
                      // Get index 0 of array as a JSONObject
                      String moviestring = results.getString(0);
                      // Parse string value 'key' from object
                      JSONObject movieObject = new JSONObject(moviestring);
                      videoId = movieObject.getString("key");
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }


              }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });

        Log.d(TAG, "onCreate: ");
        YouTubePlayerView playerView = findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.YOUTUBE_API_KEY), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // Cue video player with ID
                youTubePlayer.cueVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }


        });


    }
}