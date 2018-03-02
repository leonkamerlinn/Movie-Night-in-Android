package com.example.leon.movienightinandroid.ui.sortfilter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.application.ApplicationModule;
import com.example.leon.movienightinandroid.databinding.ActivitySortFilterBinding;
import com.example.leon.movienightinandroid.di.qualifier.ActivityContext;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module(includes = {ApplicationModule.class})
public abstract class SortFilterActivityModule {

    @Binds
    abstract Activity provideActivity(SortFilterActivity sortFilterActivity);

    @Provides
    @ActivityContext
    static Context provideContext(SortFilterActivity sortFilterActivity) {
        return sortFilterActivity;
    }

    @Provides
    static ActivitySortFilterBinding provideViewBinding(Activity activity) {
        ActivitySortFilterBinding binding = DataBindingUtil.setContentView(activity, R.layout.activity_sort_filter);
        
        return binding;
    }


}