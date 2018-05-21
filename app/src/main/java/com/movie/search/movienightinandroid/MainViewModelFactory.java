package com.movie.search.movienightinandroid;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final MainActivity mActivity;


    @Inject
    public MainViewModelFactory(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public MainViewModel create(Class modelClass) {
        return new MainViewModel(mActivity);
    }
}

