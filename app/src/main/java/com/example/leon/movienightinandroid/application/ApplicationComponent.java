package com.example.leon.movienightinandroid.application;

import android.app.Application;

import com.example.leon.movienightinandroid.di.ActivityBindingModule;
import com.example.leon.movienightinandroid.ui.sortfilter.SortFilterActivityModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

/**
 * Created by Leon on 2.2.2018..
 */

@Singleton
@Component(modules = {ApplicationModule.class, ActivityBindingModule.class, AndroidSupportInjectionModule.class})
public interface ApplicationComponent extends AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(Application application);
        ApplicationComponent build();
    }
}
