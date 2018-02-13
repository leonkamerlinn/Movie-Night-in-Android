package com.example.leon.movienightinandroid.di.module;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module
public abstract class SortFilterDialogModule {
    @Provides
    static int provideNumber() {
        return 10;
    }

    @Provides
    static String provideTitle() {
        return SortFilterDialogModule.class.getSimpleName();
    }
}
