package com.pustovit.tmdbclient.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pustovit Vladimir on 07.01.2020.
 * vovapust1989@gmail.com
 */

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static MovieDataService getService(){

        // If we are not adding a custom timeouts like this,
        // retrofit will always use the default timeouts.
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();


        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJAVA
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(MovieDataService.class);
    }
}

/*        **Realization only with Retrofit**

        if(retrofit == null){
                retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
                }
*/