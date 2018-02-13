package com.example.leon.movienightinandroid;

import com.example.leon.movienightinandroid.di.component.ApplicationComponent;
import com.example.leon.movienightinandroid.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;


/**
 * Created by Leon on 2.2.2018..
 */

public class MainApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .application(this)
                .build();

        applicationComponent.inject(this);

        return applicationComponent;
    }
}