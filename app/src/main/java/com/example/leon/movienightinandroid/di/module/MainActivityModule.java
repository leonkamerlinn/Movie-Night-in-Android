package com.example.leon.movienightinandroid.di.module;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.MainActivity;
import com.example.leon.movienightinandroid.MainViewModel;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.User;
import com.example.leon.movienightinandroid.api.moviedb.MainViewModelFactory;
import com.example.leon.movienightinandroid.api.moviedb.dialog.SortFilterDialog;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.databinding.DialogSortFilterBinding;
import com.example.leon.movienightinandroid.di.qualifier.ActivityContext;

import javax.inject.Singleton;

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