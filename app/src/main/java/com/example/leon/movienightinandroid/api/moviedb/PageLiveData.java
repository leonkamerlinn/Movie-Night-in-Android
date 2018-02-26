package com.example.leon.movienightinandroid.api.moviedb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;

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

    private final MutableLiveData<String> message;
    private MutableLiveData<Boolean> mLoading;

    private Mode mMode;
    private Page mLastPage;
    private final List<Movie> mMovies;

    public enum Mode {
        SEARCH_ALL,
        SEARCH_MOVIES,
        SEARCH_TV,
        DISCOVER_MOVIES,
        DISCOVER_TV
    }


    public PageLiveData(UrlContracts.TheMovieService movieService) {
        mMovies = new ArrayList<>();
        mLoading = new MutableLiveData<>();
        message = new MutableLiveData<>();
        message.setValue("Hello");
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


        setMode(Mode.DISCOVER_MOVIES);
    }


    public LiveData<String> getMessage() {
        return message;
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


    public void setMode(Mode mode) {
        mMode = mode;
    }

    public Mode getMode() {
        return mMode;
    }


    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public LiveData<Boolean> getLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading.setValue(loading);
    }
}
