package com.pustovit.tmdbclient.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.pustovit.tmdbclient.R;
import com.pustovit.tmdbclient.service.MovieDataService;
import com.pustovit.tmdbclient.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Pustovit Vladimir on 13.01.2020.
 * vovapust1989@gmail.com
 */

public class MovieRepository {

    private Application application;
    private CompositeDisposable mCompositeDisposable;
    private MutableLiveData<List<Movie>> moviesLiveData;
    private List<Movie> movies;

    public MutableLiveData<List<Movie>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public MovieRepository(Application application) {
        this.application = application;
        mCompositeDisposable = new CompositeDisposable();
        moviesLiveData = new MutableLiveData<>();

        MovieDataService movieDataService = RetrofitInstance.getService();

        Observable<MovieDBResponse> mMovieDBResponseObservable = movieDataService.getPopularMoviesWithRx(application.getApplicationContext()
                .getString(R.string.api_key));


//        mCompositeDisposable.add(
//                mMovieDBResponseObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<MovieDBResponse>() {
//                            @Override
//                            public void onNext(MovieDBResponse movieDBResponse) {
//                                movies = movieDBResponse.getMovies();
//                                Log.d(TAG, "onNext: movies.size() : " + movies.size());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                            }
//                            @Override
//                            public void onComplete() {
//                                showOnRecyclerView();
//                            }
//                        })
//     );

        /*        **Realization with flatMap operator**  */

        movies = new ArrayList<>();

        mCompositeDisposable.add(
                mMovieDBResponseObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<MovieDBResponse, Observable<Movie>>() {
                            @Override
                            public Observable<Movie> apply(MovieDBResponse movieDBResponse) throws Exception {
                                return Observable.fromArray(movieDBResponse.getMovies().toArray(new Movie[0]));
                            }
                        })
                        .filter(new Predicate<Movie>() {
                            @Override
                            public boolean test(Movie movie) throws Exception {
                                return movie.getVoteAverage() > 2;// filtering rating
                            }
                        })
                        .subscribeWith(new DisposableObserver<Movie>() {
                            @Override
                            public void onNext(Movie movie) {
                                movies.add(movie);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                moviesLiveData.postValue(movies);
                            }
                        })
        );
    }




    public void clear() {
        mCompositeDisposable.clear();
    }
}
