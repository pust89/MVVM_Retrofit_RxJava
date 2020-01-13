package com.pustovit.tmdbclient.service;

import com.pustovit.tmdbclient.model.MovieDBResponse;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pustovit Vladimir on 07.01.2020.
 * vovapust1989@gmail.com
 */

public interface MovieDataService {

    /*        **Realization only with Retrofit**  */
//    @GET("movie/popular")
//    Call<MovieDBResponse> getPopularMovies(@Query("api_key")String apiKey);

    @GET("movie/popular")
    Observable<MovieDBResponse> getPopularMoviesWithRx(@Query("api_key")String apiKey);

}
