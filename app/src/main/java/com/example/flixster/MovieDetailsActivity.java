package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import org.parceler.Parcels;

import models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {
    //the movie to display
    Movie movie;

    RatingBar rbVoteAverage;
    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    ImageView imagePoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        //find the title, overview, vote_average for finding view by ID
        //finds the view by the specific ID requested.
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        imagePoster = (ImageView) findViewById(R.id.imagePoster);
        Toast.makeText(MovieDetailsActivity.this, "You are viewing the details of the movie", Toast.LENGTH_SHORT).show();

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);
        Glide.with(this)
                //loading the image for each movie
                .load(movie.getPosterPaths())
                //transforming the image into a circle
                .transform(new CircleCrop())
                //paste the image on the sc
                .into(imagePoster);
        imagePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent for the new activity
                Intent intent = new Intent(MovieDetailsActivity.this,MovieTrailerActivity.class);
                intent.putExtra("movieID", movie.getMovieID());
                // show the activity
                startActivity(intent);
            }
        });

    }

}
