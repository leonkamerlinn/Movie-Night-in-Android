package com.example.leon.movienightinandroid.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Leon on 2.2.2018..
 */

@Module
public abstract class ApplicationModule {
    @Binds
    abstract Context provideContext(Application application);




}