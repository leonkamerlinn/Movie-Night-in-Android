package com.example.leon.movienightinandroid.di;



import com.example.leon.movienightinandroid.MainActivity;
import com.example.leon.movienightinandroid.MainActivityModule;
import com.example.leon.movienightinandroid.di.scope.ActivityScoped;
import com.example.leon.movienightinandroid.ui.sortfilter.SortFilterActivity;
import com.example.leon.movienightinandroid.ui.sortfilter.SortFilterActivityModule;

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
