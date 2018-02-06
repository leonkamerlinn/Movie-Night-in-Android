package com.example.leon.movienightinandroid;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.leon.movienightinandroid.api.moviedb.MovieRecyclerViewAdapter;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.di.component.DaggerMainActivityComponent;
import com.example.leon.movienightinandroid.di.component.MainActivityComponent;
import com.example.leon.movienightinandroid.di.module.ActivityModule;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {



    @Inject
    MovieRecyclerViewAdapter mMovieRecyclerViewAdapter;

    private MainActivityComponent activityComponent;
    private ActivityMainBinding binding;

    public MainActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerMainActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user = new User(BuildConfig.ACCESS_TOKEN_V4_AUTH, BuildConfig.API_KEY);
        binding.setUser(user);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(false);
        binding.recyclerView.setAdapter(mMovieRecyclerViewAdapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                //adapter position of the first visible view.
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int visibleThreshold = 5;

                if (!mMovieRecyclerViewAdapter.isLoanding() && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    mMovieRecyclerViewAdapter.loadNextPage();
                }

            }
        });

    }



}
