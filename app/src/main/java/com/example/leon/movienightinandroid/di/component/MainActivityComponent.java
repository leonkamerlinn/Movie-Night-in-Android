package com.example.leon.movienightinandroid.di.component;

import com.example.leon.movienightinandroid.MainActivity;
import com.example.leon.movienightinandroid.di.module.ActivityModule;
import com.example.leon.movienightinandroid.di.module.MainActivityModule;
import com.example.leon.movienightinandroid.di.module.MovieDbModule;
import com.example.leon.movienightinandroid.di.scopes.ActivityScope;

import dagger.Component;

/**
 * Created by Leon on 2.2.2018..
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MovieDbModule.class, MainActivityModule.class})
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}