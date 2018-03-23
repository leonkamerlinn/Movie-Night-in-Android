package com.example.leon.movienightinandroid.api.moviedb;

import com.example.leon.movienightinandroid.api.moviedb.model.Filter;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;

import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Leon on 2/24/2018.
 */

public class PageRepository implements SingleObserver<Page> {
    private SearchFilter mMode;
    private Page mLastPage;



    public PublishSubject<String> scrollerSubject = PublishSubject.create();
    public PublishSubject<Filter> filterSubject = PublishSubject.create();
    private PublishSubject<PageRepository> pageRepositorySubject = PublishSubject.create();


    public Observable<String> getScroller() {
        return scrollerSubject;
    }
    public Observable<Filter> getFilter() {
        return filterSubject;
    }

    public Observable<PageRepository> getObservable() {
        return pageRepositorySubject;
    }


    @Inject
    public PageRepository(TheMovieService.Repository movieService) {

        Map<String, Object> objectMap = new HashMap<>();
        movieService.discoverMovies(1, objectMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);



        setMode(SearchFilter.DISCOVER_MOVIES);
    }






    public Page getLastPage() {
        return mLastPage;
    }

    public boolean isLastPage() {
        return (getLastPage().total_pages == mLastPage.page);
    }







    public void setMode(SearchFilter mode) {
        mMode = mode;
    }

    public SearchFilter getMode() {
        return mMode;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onSuccess(Page page) {
        mLastPage = page;
        pageRepositorySubject.onNext(this);
        System.out.println(page);
    }

    @Override
    public void onError(Throwable e) {

    }
}
