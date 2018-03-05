package com.example.leon.movienightinandroid;

import android.arch.lifecycle.ViewModelProvider;

import com.example.leon.movienightinandroid.api.moviedb.TheMovieService;

import javax.inject.Inject;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final TheMovieService.Repository mMovieService;
    private final MainActivity mActivity;


    @Inject
    public MainViewModelFactory(MainActivity activity, TheMovieService.Repository movieService) {
        mMovieService = movieService;
        mActivity = activity;
    }

    @Override
    public MainViewModel create(Class modelClass) {
        return new MainViewModel(mActivity, mMovieService);
    }
}

