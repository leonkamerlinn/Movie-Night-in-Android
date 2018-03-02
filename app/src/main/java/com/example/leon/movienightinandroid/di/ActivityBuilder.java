package com.example.leon.movienightinandroid.di;


import com.example.leon.movienightinandroid.MainActivity;
import com.example.leon.movienightinandroid.api.moviedb.dialog.SortFilterDialog;
import com.example.leon.movienightinandroid.MainActivityModule;
import com.example.leon.movienightinandroid.ui.sortfilter.SortFilterActivityModule;
import com.example.leon.movienightinandroid.ui.sortfilter.SortFilterDialogModule;
import com.example.leon.movienightinandroid.ui.sortfilter.SortFilterActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Leon on 13.2.2018..
 */

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity provideMainActivity();


    @ContributesAndroidInjector(modules = SortFilterActivityModule.class)
    abstract SortFilterActivity provideSortFilterActivity();


    @ContributesAndroidInjector(modules = {SortFilterDialogModule.class})
    abstract SortFilterDialog provideSortFilterDialog();
}
