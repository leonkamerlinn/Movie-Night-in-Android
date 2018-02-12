package com.example.leon.movienightinandroid.di.module;

import android.app.Activity;
import android.databinding.DataBindingUtil;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.User;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module
public class SortFilterDialogModule {


    @Provides
    String provideMessage() {
        return SortFilterDialogModule.class.getSimpleName();
    }





}