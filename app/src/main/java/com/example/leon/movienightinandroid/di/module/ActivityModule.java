package com.example.leon.movienightinandroid.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.leon.movienightinandroid.di.qulifier.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }



}
