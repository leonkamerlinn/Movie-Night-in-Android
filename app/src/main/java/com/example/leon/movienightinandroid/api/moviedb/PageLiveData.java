package com.example.leon.movienightinandroid.api.moviedb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.leon.movienightinandroid.api.moviedb.model.Filter;
import com.example.leon.movienightinandroid.api.moviedb.model.Movie;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Leon on 2/24/2018.
 */

public class PageLiveData extends LiveData<Page> implements Observer<Page> {
    private final MutableLiveData<Boolean> mLoading;
    private SearchFilter mMode;
    private Page mLastPage;
    private final List<Movie> mMovies;
    private int currNumPage = 0;


    public PublishSubject<String> scrollerSubject = PublishSubject.create();
    public PublishSubject<Filter> filterSubject = PublishSubject.create();


    public Observable<String> getScroller() {
        return scrollerSubject;
    }
    public Observable<Filter> getFilter() {
        return filterSubject;
    }

    public PageLiveData(TheMovieService.Repository movieService) {

        mMovies = new ArrayList<>();
        mLoading = new MutableLiveData<>();
        setLoading(true);
        Map<String, Object> objectMap = new HashMap<>();
        Observable<Page> one = movieService.discoverMovies(1, objectMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Page> two = movieService.discoverMovies(2, objectMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Page> three = movieService.discoverMovies(3, objectMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        Observable.concat(one, two, three).subscribe(this);


        setMode(SearchFilter.DISCOVER_MOVIES);
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void clear() {
        currNumPage = 0;
        mMovies.clear();

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
        currNumPage++;
        return currNumPage;
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

    public SearchFilter getMode() {
        return mMode;
    }

    public LiveData<Boolean> isLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading.setValue(loading);
    }
}
