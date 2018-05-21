package com.movie.search.movienightinandroid.di;



import com.movie.search.movienightinandroid.MainActivity;
import com.movie.search.movienightinandroid.MainActivityModule;
import com.movie.search.movienightinandroid.di.scope.ActivityScoped;
import com.movie.search.movienightinandroid.ui.sortfilter.SortFilterActivity;
import com.movie.search.movienightinandroid.ui.sortfilter.SortFilterActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SortFilterActivityModule.class)
    abstract SortFilterActivity sortFilterActivity();
}
