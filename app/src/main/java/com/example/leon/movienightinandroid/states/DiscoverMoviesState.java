package com.example.leon.movienightinandroid.states;

import com.example.leon.movienightinandroid.api.moviedb.TheMovieService;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;

import java.util.HashMap;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DiscoverMoviesState implements MovieState {

    private Single<Page> mSingle;
    private final TheMovieService.Repository mMovieService;
    private final HashMap<String, Object> mQueryMap;
    private int mPage;

    public DiscoverMoviesState(TheMovieService.Repository movieService, HashMap<String, Object> queryMap) {
        mPage = 1;
        mMovieService = movieService;
        mQueryMap = queryMap;

        mSingle = movieService.discoverMovies(mPage, queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<Page> getSingle() {
        return mSingle;
    }


    @Override
    public void loadPage(int page) {
        mPage = page;
        mSingle = mMovieService.discoverMovies(page, mQueryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public int getPageNumber() {
        return mPage;
    }
}
