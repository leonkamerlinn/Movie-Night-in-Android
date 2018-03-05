package com.example.leon.movienightinandroid.ui.sortfilter;

import android.app.Activity;
import android.databinding.DataBindingUtil;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.databinding.ActivitySortFilterBinding;
import com.example.leon.movienightinandroid.di.scope.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module
public abstract class SortFilterActivityModule {


    @Binds
    abstract Activity provideActivity(SortFilterActivity sortFilterActivity);

    @Provides
    @ActivityScoped
    static ActivitySortFilterBinding provideViewBinding(SortFilterActivity activity) {
        ActivitySortFilterBinding binding = DataBindingUtil.setContentView(activity, R.layout.activity_sort_filter);
        return binding;
    }


}