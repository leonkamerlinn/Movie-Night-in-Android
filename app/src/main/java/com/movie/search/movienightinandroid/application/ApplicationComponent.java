package com.movie.search.movienightinandroid.application;

import android.app.Application;

import com.movie.search.movienightinandroid.di.ActivityBindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

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
