package com.movie.search.movienightinandroid;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;

import com.movie.search.movienightinandroid.databinding.ActivityMainBinding;
import com.movie.search.movienightinandroid.di.scope.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module
public abstract class MainActivityModule {

    @Binds
    abstract Activity provideActivity(MainActivity mainActivity);



    @Provides
    @ActivityScoped
    static ActivityMainBinding provideViewBinding(MainActivity activity, MainViewModel viewModel) {
        ActivityMainBinding binding = DataBindingUtil.setContentView(activity, R.layout.activity_main);
        binding.setLifecycleOwner(activity);
        binding.setModel(viewModel);

        //viewModel.getPageLiveData().isLoading().observe(activity, binding::setLoading);
        return binding;
    }

    @Provides
    @ActivityScoped
    static MainViewModel provideMainViewModel(MainActivity activity, MainViewModelFactory mainViewModelFactory) {
        return ViewModelProviders.of(activity, mainViewModelFactory).get(MainViewModel.class);

    }


}