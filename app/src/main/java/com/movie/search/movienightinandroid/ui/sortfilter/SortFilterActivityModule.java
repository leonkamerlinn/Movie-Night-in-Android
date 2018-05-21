package com.movie.search.movienightinandroid.ui.sortfilter;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;

import com.movie.search.movienightinandroid.R;
import com.movie.search.movienightinandroid.databinding.ActivitySortFilterBinding;
import com.movie.search.movienightinandroid.di.scope.ActivityScoped;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module
@Singleton
public abstract class SortFilterActivityModule {


    @Binds
    abstract Activity provideActivity(SortFilterActivity sortFilterActivity);

    @Provides
    @ActivityScoped
    static ActivitySortFilterBinding provideViewBinding(SortFilterActivity activity, SortFilterViewModel sortFilterViewModel) {
        ActivitySortFilterBinding binding = DataBindingUtil.setContentView(activity, R.layout.activity_sort_filter);
        binding.setLifecycleOwner(activity);
        binding.setModel(sortFilterViewModel);
        return binding;
    }

    @Provides
    @ActivityScoped
    static SortFilterViewModel provideSortFilterViewModel(SortFilterActivity activity, SortFilterViewModelFactory sortFilterViewModelFactory) {

        return ViewModelProviders.of(activity, sortFilterViewModelFactory).get(SortFilterViewModel.class);

    }


}