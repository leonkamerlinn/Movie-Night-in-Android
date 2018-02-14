package com.example.leon.movienightinandroid.di.module;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.MainActivity;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.User;
import com.example.leon.movienightinandroid.api.moviedb.dialog.SortFilterDialog;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.databinding.DialogSortFilterBinding;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module(includes = {ApplicationModule.class})
public abstract class MainActivityModule {

    @Binds
    abstract Activity provideActivity(MainActivity mainActivity);

/*    @Binds
    abstract Activity provideActivity(SortFilterDialog sortFilterDialog);*/


    @Provides
    static ActivityMainBinding provideViewBinding(Activity activity) {
        ActivityMainBinding binding = DataBindingUtil.setContentView(activity, R.layout.activity_main);
        User user = new User(BuildConfig.ACCESS_TOKEN_V4_AUTH, BuildConfig.API_KEY);
        binding.setUser(user);

        return binding;
    }

    @Provides
    static Toast provideToast(Activity activity) {
        return Toast.makeText(activity, String.valueOf(10), Toast.LENGTH_LONG);
    }



}