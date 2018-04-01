package com.example.leon.movienightinandroid.ui.sortfilter;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

public class SortFilterViewModelFactory implements ViewModelProvider.Factory {

    private final SortFilterActivity mActivity;


    @Inject
    public SortFilterViewModelFactory(SortFilterActivity activity) {
        mActivity = activity;
    }

    @Override
    public SortFilterViewModel create(Class modelClass) {
        return new SortFilterViewModel(mActivity);
    }
}

