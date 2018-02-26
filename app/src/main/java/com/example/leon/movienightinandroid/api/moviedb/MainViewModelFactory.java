package com.example.leon.movienightinandroid.api.moviedb;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.example.leon.movienightinandroid.MainViewModel;
import com.example.leon.movienightinandroid.di.qualifier.ApplicationContext;

import javax.inject.Inject;

/**
 * Created by Leon on 2/24/2018.
 */

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final UrlContracts.TheMovieService mMovieService;
    private final Context mContext;


    @Inject
    public MainViewModelFactory(@ApplicationContext Context context, UrlContracts.TheMovieService movieService) {
        mMovieService = movieService;
        mContext = context;
    }

    @Override
    public MainViewModel create(Class modelClass) {
        return new MainViewModel(mContext, mMovieService);
    }
}
