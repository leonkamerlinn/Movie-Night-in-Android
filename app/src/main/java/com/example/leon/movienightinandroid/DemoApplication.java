package com.example.leon.movienightinandroid;

import android.app.Application;
import android.content.Context;

import com.example.leon.movienightinandroid.di.component.ApplicationComponent;
import com.example.leon.movienightinandroid.di.component.DaggerApplicationComponent;
import com.example.leon.movienightinandroid.di.module.ApplicationModule;


/**
 * Created by Leon on 2.2.2018..
 */

public class DemoApplication extends Application {

    protected ApplicationComponent applicationComponent;



    public static DemoApplication get(Context context) {
        return (DemoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))

                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}