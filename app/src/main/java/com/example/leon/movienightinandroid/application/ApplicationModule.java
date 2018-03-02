package com.example.leon.movienightinandroid.application;

import android.app.Application;
import android.content.Context;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.api.moviedb.UrlContracts;
import com.example.leon.movienightinandroid.di.qualifier.ApplicationContext;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leon on 2.2.2018..
 */

@Module
public abstract class ApplicationModule {
    @Binds
    @ApplicationContext
    abstract Context provideContext(Application application);

    @Provides
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl httpUrl = original.url();

                    HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter(UrlContracts.API_KEY_QUERY, BuildConfig.API_KEY).build();
                    Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                }).build();
    }

    @Provides
    static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(UrlContracts.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    static UrlContracts.TheMovieService provideMovieService(Retrofit retrofit) {
        return retrofit.create(UrlContracts.TheMovieService.class);
    }
}