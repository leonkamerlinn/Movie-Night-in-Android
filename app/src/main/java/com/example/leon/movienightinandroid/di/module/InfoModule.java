package com.example.leon.movienightinandroid.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.leon.movienightinandroid.di.qulifier.ApplicationContext;
import com.example.leon.movienightinandroid.di.qulifier.DatabaseInfo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module(includes = ApplicationModule.class)
public class InfoModule {
    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return "demo-dagger.db";
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return 2;
    }

    @Provides
    SharedPreferences provideSharedPrefs(@ApplicationContext Context context) {
        return context.getSharedPreferences("demo-prefs", Context.MODE_PRIVATE);
    }

}