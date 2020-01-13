package com.pustovit.tmdbclient.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;


import com.pustovit.tmdbclient.R;
import com.pustovit.tmdbclient.ViewModel.MainActivityViewModel;
import com.pustovit.tmdbclient.adapter.MovieAdapter;
import com.pustovit.tmdbclient.model.Movie;

import java.util.List;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "myTag";

    private List<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MainActivityViewModel mainActivityViewModel;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvMovies);


        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        getPopularMovies();


        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getPopularMovies();
            }
        });
        getSupportActionBar().setTitle("TMDB Popular movies today ");




    }

    //RxJava here
    private void getPopularMovies() {
        mainActivityViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> moviesList) {
                movies = moviesList;
                showOnRecyclerView();
            }
        });

    }


    private void showOnRecyclerView() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setItemViewCacheSize(20);
        movieAdapter = new MovieAdapter(this, movies);
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();


        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityViewModel.clear();
    }
}
