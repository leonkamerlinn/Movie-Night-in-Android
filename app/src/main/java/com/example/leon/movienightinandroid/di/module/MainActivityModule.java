package com.example.leon.movienightinandroid.di.module;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.Toast;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.MainActivity;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.User;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module(includes = {ApplicationModule.class})
public abstract class MainActivityModule {

    @Binds
    abstract Activity provideContext(MainActivity mainActivity);

    @Provides
    static String provideMainPresenter() {
        return MainActivityModule.class.getSimpleName();
    }

    @Provides
    static ActivityMainBinding provideViewBinding(Activity activity) {
        ActivityMainBinding binding = DataBindingUtil.setContentView(activity, R.layout.activity_main);
        User user = new User(BuildConfig.ACCESS_TOKEN_V4_AUTH, BuildConfig.API_KEY);
        binding.setUser(user);

        return binding;
    }

    @Provides
    static Toast provideToast(Activity context, String msg) {
        return Toast.makeText(context, msg, Toast.LENGTH_LONG);
    }
}