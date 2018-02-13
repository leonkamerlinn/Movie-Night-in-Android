package com.example.leon.movienightinandroid.di.component;

import android.app.Application;

import com.example.leon.movienightinandroid.MainApplication;
import com.example.leon.movienightinandroid.di.ActivityBuilder;
import com.example.leon.movienightinandroid.di.module.ApplicationModule;

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
@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityBuilder.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(MainApplication mainApplication);

    @Override
    void inject(DaggerApplication daggerApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
}
