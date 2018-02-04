package com.example.leon.movienightinandroid.di.module;

import android.app.Application;
import android.content.Context;

import com.example.leon.movienightinandroid.di.qulifier.ApplicationContext;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Leon on 2.2.2018..
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }
}