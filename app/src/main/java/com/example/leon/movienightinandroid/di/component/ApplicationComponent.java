package com.example.leon.movienightinandroid.di.component;

import android.content.Context;

import com.example.leon.movienightinandroid.DemoApplication;
import com.example.leon.movienightinandroid.api.moviedb.UrlContracts;
import com.example.leon.movienightinandroid.di.module.ApplicationModule;
import com.example.leon.movienightinandroid.di.qulifier.ApplicationContext;
import com.example.leon.movienightinandroid.di.scopes.ApplicationScope;

import dagger.Component;

/**
 * Created by Leon on 2.2.2018..
 */

@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(DemoApplication demoApplication);

    @ApplicationContext
    Context getContext();

    UrlContracts.TheMovieService getService();

}