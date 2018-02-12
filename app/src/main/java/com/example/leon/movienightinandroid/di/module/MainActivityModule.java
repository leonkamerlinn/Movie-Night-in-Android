package com.example.leon.movienightinandroid.di.module;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.User;
import com.example.leon.movienightinandroid.api.moviedb.UrlContracts;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;

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

@Module(includes = {ActivityModule.class})
public class MainActivityModule {


    @Provides
    ActivityMainBinding provideDataBinding(Activity activity) {
        ActivityMainBinding binding = DataBindingUtil.setContentView(activity, R.layout.activity_main);
        User user = new User(BuildConfig.ACCESS_TOKEN_V4_AUTH, BuildConfig.API_KEY);
        binding.setUser(user);

        return binding;
    }





}