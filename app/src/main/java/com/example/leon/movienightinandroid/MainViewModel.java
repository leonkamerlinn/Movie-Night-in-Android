package com.example.leon.movienightinandroid;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.leon.movienightinandroid.api.moviedb.MovieRecyclerViewAdapter;

/**
 * Created by Leon on 2/24/2018.
 */

public class MainViewModel extends ViewModel {
    private final MutableLiveData<String> mTitle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLoading = new MutableLiveData<>();
    private final MutableLiveData<MovieRecyclerViewAdapter> mAdapter = new MutableLiveData<>();
    private final MutableLiveData<RecyclerView.LayoutManager> mLayoutManager = new MutableLiveData<>();
    public MainViewModel(Context context) {
        setTitle(context.getResources().getString(R.string.app_name));
    }

    public void setTitle(String title) {
        mTitle.setValue(title);
    }
    public void setLoading(boolean loading) {
        mLoading.setValue(loading);
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<String> getTitle() {
        return mTitle;
    }


    public void setAdapter(MovieRecyclerViewAdapter adapter) {
        mAdapter.setValue(adapter);
    }

    public LiveData<MovieRecyclerViewAdapter> getAdapter() {
        return mAdapter;
    }

    public void layoutManager(GridLayoutManager adapterLayout) {
        mLayoutManager.setValue(adapterLayout);
    }

    public LiveData<RecyclerView.LayoutManager> getLayoutManager() {
        return mLayoutManager;
    }
}
