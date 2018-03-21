package com.example.leon.movienightinandroid.application;

import android.app.Application;
import android.content.Context;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.api.moviedb.TheMovieService;
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
    private static TheMovieService.Repository mMovieService;
    private static OkHttpClient okk;

    @Binds
    abstract Context bindContext(Application application);

    @Provides
    static OkHttpClient provideOkHttpClient() {
        okk = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl httpUrl = original.url();

                    HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter(TheMovieService.API_KEY_QUERY, BuildConfig.API_KEY).build();

                    System.out.println(newHttpUrl.toString());

                    Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                    Request request = requestBuilder.build();




                    return chain.proceed(request);
                }).build();



        return okk;
    }

    @Provides
    static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TheMovieService.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    @Provides
    static TheMovieService.Repository provideMovieService(Retrofit retrofit) {
        if (mMovieService == null) {
            mMovieService = retrofit.create(TheMovieService.Repository.class);
        }


        return mMovieService;
    }
}