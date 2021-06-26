package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.MovieAdapter;
import models.Movie;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    //Add a tag
    public static final String TAG = "MainActivity";
    //created a movies member variable
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        //Create an adapter
        MovieAdapter movieAdapter = new MovieAdapter(this,movies);
        //Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);
        //Set a layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        //Created an instance of the AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();
        //Making a get request for the now playing  URL
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: "+ results.toString());
                    //return a list of movie objects
                    movies.addAll(Movie.fromJsonArray(results));
                    //Notify the adapter to make changes
                    movieAdapter.notifyDataSetChanged();
                    //Log statement to see if it is getting the movie id correctly
                    Log.i(TAG, "Movies: "+ movies.size());
                } catch (JSONException e) {
                    //print error statement if it does not hit the JSON
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");


            }
        });
    }
}