package com.example.leon.movienightinandroid.di.module;

import android.content.Context;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.api.moviedb.MovieRecyclerViewAdapter;
import com.example.leon.movienightinandroid.api.moviedb.UrlContracts;
import com.example.leon.movienightinandroid.di.qulifier.ApplicationContext;
import com.example.leon.movienightinandroid.di.qulifier.TheMovieDb;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leon on 2.2.2018..
 */

@Module(includes = {ApplicationModule.class})
public class MovieDbModule {

    @Provides
    UrlContracts.TheMovieService provideMovieService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl httpUrl = original.url();

                    HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter(UrlContracts.API_KEY_QUERY, BuildConfig.API_KEY).build();
                    Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlContracts.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        UrlContracts.TheMovieService movieService = retrofit.create(UrlContracts.TheMovieService.class);
        return movieService;
    }



}