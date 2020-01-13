package com.pustovit.tmdbclient.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pustovit.tmdbclient.R;
import com.pustovit.tmdbclient.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {
    private static final String TAG = "myTag";
    private Movie movie;
    private ImageView movieImage;
    private TextView movieTitle, movieSynopsis, movieRating, movieReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        movieImage = findViewById(R.id.ivMovieLarge);
        movieImage = findViewById(R.id.ivMovieLarge);
        movieTitle = findViewById(R.id.tvMovieTitle);
        movieSynopsis = findViewById(R.id.tvPlotsynopsis);
        movieRating = findViewById(R.id.tvMovieRating);
        movieReleaseDate = findViewById(R.id.tvReleaseDate);


        Intent intent = getIntent();

        if (intent.hasExtra(Movie.class.getSimpleName())) {
            movie = intent.getParcelableExtra(Movie.class.getSimpleName());

            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath())
                    .error(R.drawable.ic_placeholder_24dp)
                    .into(movieImage);
        }
        getSupportActionBar().setTitle(movie.getTitle());

        movieTitle.setText(movie.getTitle());
        movieSynopsis.setText(movie.getOverview());
        movieRating.setText(Double.toString(movie.getVoteAverage()));
        movieReleaseDate.setText(movie.getReleaseDate());
    }

}
