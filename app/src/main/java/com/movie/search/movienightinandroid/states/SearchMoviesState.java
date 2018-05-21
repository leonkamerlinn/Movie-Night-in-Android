package com.movie.search.movienightinandroid.states;

import com.movie.search.movienightinandroid.api.moviedb.TheMovieService;
import com.movie.search.movienightinandroid.api.moviedb.model.Page;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchMoviesState implements MovieState {

    private Single<Page> mSingle;
    private final TheMovieService.Repository mMovieService;
    private final String mQuery;
    private int mPage;

    public SearchMoviesState(TheMovieService.Repository movieService, String query) {
        mPage = 1;
        mMovieService = movieService;
        mQuery = query;

        mSingle = movieService.searchMovie(mPage, query)
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
        mSingle = mMovieService.searchMovie(page, mQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public int getPageNumber() {
        return mPage;
    }
}
