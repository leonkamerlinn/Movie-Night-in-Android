package com.example.leon.movienightinandroid.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.api.moviedb.UrlContracts;
import com.example.leon.movienightinandroid.di.qulifier.ApplicationContext;
import com.example.leon.movienightinandroid.di.qulifier.DatabaseInfo;

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

@Module(includes = ApplicationModule.class)
public class InfoModule {
    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return "demo-dagger.db";
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return 2;
    }

    @Provides
    SharedPreferences provideSharedPrefs(@ApplicationContext Context context) {
        return context.getSharedPreferences("demo-prefs", Context.MODE_PRIVATE);
    }

 /*   @Provides
    @ApplicationContext
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
                .build();

        UrlContracts.TheMovieService movieService = retrofit.create(UrlContracts.TheMovieService.class);
        return movieService;
    }*/
}

