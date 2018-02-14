package com.example.leon.movienightinandroid.di.module;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module(includes = {MainActivityModule.class})
public abstract class SortFilterDialogModule {



    @Provides
    static int provideNumber() {
        return 10;
    }


}
