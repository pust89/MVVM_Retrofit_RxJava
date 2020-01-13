package com.pustovit.tmdbclient.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pustovit.tmdbclient.model.Movie;
import com.pustovit.tmdbclient.model.MovieRepository;

import java.util.List;

/**
 * Created by Pustovit Vladimir on 13.01.2020.
 * vovapust1989@gmail.com
 */

public class MainActivityViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getMovies(){
        return movieRepository.getMoviesLiveData();
    }

    public void clear(){
        movieRepository.clear();
    }
}
