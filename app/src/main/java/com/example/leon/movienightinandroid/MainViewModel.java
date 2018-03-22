package com.example.leon.movienightinandroid;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.leon.movienightinandroid.api.moviedb.PageRepository;
import com.example.leon.movienightinandroid.api.moviedb.TheMovieService;

/**
 * Created by Leon on 2/24/2018.
 */

public class MainViewModel extends ViewModel {
    private PageRepository mPageLiveData;
    private final MutableLiveData<String> mTitle;

    public MainViewModel(Context context, TheMovieService.Repository movieService) {
        mPageLiveData = new PageRepository(movieService);
        mTitle = new MutableLiveData<>();
        setTitle(context.getResources().getString(R.string.app_name));
    }

    public void setTitle(String title) {
        mTitle.setValue(title);
    }

    public LiveData<String> getTitle() {
        return mTitle;
    }


    public PageRepository getPageLiveData() {
        return mPageLiveData;
    }




}
