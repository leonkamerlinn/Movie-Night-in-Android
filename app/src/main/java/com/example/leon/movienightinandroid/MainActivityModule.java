package com.example.leon.movienightinandroid;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;

import com.example.leon.movienightinandroid.api.moviedb.MainViewModelFactory;
import com.example.leon.movienightinandroid.application.ApplicationModule;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.di.qualifier.ActivityContext;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module(includes = {ApplicationModule.class})
public abstract class MainActivityModule {

    @Binds
    abstract Activity provideActivity(MainActivity mainActivity);

    @Provides
    @ActivityContext
    static Context provideContext(MainActivity mainActivity) {
        return mainActivity;
    }

    @Provides
    static ActivityMainBinding provideViewBinding(Activity activity, MainViewModel mainViewModel) {
        ActivityMainBinding binding = DataBindingUtil.setContentView(activity, R.layout.activity_main);
        binding.setLifecycleOwner((LifecycleOwner) activity);
        binding.setModel(mainViewModel);
        return binding;
    }

    @Provides
    static MainViewModel provideMainViewModel(MainActivity activity, MainViewModelFactory mainViewModelFactory) {
        return ViewModelProviders.of(activity, mainViewModelFactory).get(MainViewModel.class);

    }


}