package com.example.leon.movienightinandroid.api.moviedb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.leon.movienightinandroid.api.moviedb.model.Movie;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Leon on 2/24/2018.
 */

public class PageLiveData extends LiveData<Page> implements Observer<Page> {
    private final MutableLiveData<Boolean> mLoading;
    private SearchFilter mMode;
    private Page mLastPage;
    private final List<Movie> mMovies;




    public PageLiveData(TheMovieService.Repository movieService) {
        mMovies = new ArrayList<>();
        mLoading = new MutableLiveData<>();
        setLoading(true);

        Observable<Page> one = movieService.discoverMovies(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Page> two = movieService.discoverMovies(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Page> three = movieService.discoverMovies(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        Observable.concat(one, two, three).subscribe(this);


        setMode(SearchFilter.DISCOVER_MOVIES);
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    private void addPage(Page page) {
        mLastPage = page;
        mMovies.addAll(page.results);
    }
    public Page getLastPage() {
        return mLastPage;
    }

    public boolean isLastPage() {
        return (getLastPage().total_pages == mLastPage.page);
    }

    public int getNextPage() {
        return mLastPage.page + 1;
    }


    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Page page) {
        addPage(page);
        setValue(page);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
        setLoading(false);
    }


    public void setMode(SearchFilter mode) {
        mMode = mode;
    }

    public SearchFilter getFilter() {
        return mMode;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading.setValue(loading);
    }
}