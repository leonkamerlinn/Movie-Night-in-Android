package com.example.leon.movienightinandroid.di.component;

import com.example.leon.movienightinandroid.MainActivity;
import com.example.leon.movienightinandroid.api.moviedb.dialog.SortFilterDialog;
import com.example.leon.movienightinandroid.di.module.ActivityModule;
import com.example.leon.movienightinandroid.di.module.ApplicationModule;
import com.example.leon.movienightinandroid.di.module.MainActivityModule;
import com.example.leon.movienightinandroid.di.module.MovieDbModule;
import com.example.leon.movienightinandroid.di.module.SortFilterDialogModule;
import com.example.leon.movienightinandroid.di.scopes.ActivityScope;
import com.example.leon.movienightinandroid.di.scopes.DialogFragmentScope;

import dagger.Component;

/**
 * Created by Leon on 2.2.2018..
 */

@DialogFragmentScope
@Component(modules = {SortFilterDialogModule.class})
public interface DialogFragmentComponent {
    void inject(SortFilterDialog sortFilterDialog);
}