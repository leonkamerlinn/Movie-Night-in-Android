package com.example.leon.movienightinandroid.di;


import com.example.leon.movienightinandroid.MainActivity;
import com.example.leon.movienightinandroid.api.moviedb.dialog.SortFilterDialog;
import com.example.leon.movienightinandroid.di.module.MainActivityModule;
import com.example.leon.movienightinandroid.di.module.SortFilterDialogModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Leon on 13.2.2018..
 */

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();


    @ContributesAndroidInjector(modules = SortFilterDialogModule.class)
    abstract SortFilterDialog provideDetailFragmentFactory();
}
