package com.example.leon.movienightinandroid.api.moviedb;

import android.annotation.SuppressLint;

import com.example.leon.movienightinandroid.api.moviedb.model.Filter;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Leon on 2/24/2018.
 */
@Singleton
public class PageRepository implements SingleObserver<Page> {
    private final HashMap<String, Object> mQueryMap;
    private SearchFilter mMode;
    private Page mPreviusPage;
    private Page mCurrentPage;
    private boolean isLoading;

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


    @SuppressLint("CheckResult")
    @Inject
    public PageRepository(TheMovieService.Repository movieService) {
        mQueryMap = new HashMap<>();
        isLoading = true;
        movieService.discoverMovies(1, mQueryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

        setMode(SearchFilter.DISCOVER_MOVIES);


        getScroller()
                .sample(250, TimeUnit.MILLISECONDS)
                .skipWhile(s -> isLoading)
                .subscribe(s -> {
            switch (getMode()) {
                case DISCOVER_MOVIES:
                    movieService.discoverMovies(getNextPage(), mQueryMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this);
                    break;

                case DISCOVER_TV:
                    movieService.discoverTv(getNextPage(), mQueryMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this);
                    break;

                case SEARCH_MOVIES:
                    movieService.searchMovie(getNextPage(), mQueryMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this);
                    break;

                case SEARCH_TV:
                    movieService.searchTv(getNextPage(), mQueryMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this);
                    break;

                case SEARCH_ALL:
                    movieService.searchMulti(getNextPage(), mQueryMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this);
                    break;

                case FILTER:
                    movieService.discoverMovies(getNextPage(), mQueryMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this);
                    break;
            }
        });
    }




    public int getNextPage() {
       return mCurrentPage.page+1;
    }

    public Page getCurrentPage() {
        return mCurrentPage;
    }









    public void setMode(SearchFilter mode) {
        mMode = mode;
    }

    public SearchFilter getMode() {
        return mMode;
    }

    @Override
    public void onSubscribe(Disposable d) {
        isLoading = true;
    }

    @Override
    public void onSuccess(Page page) {
        if (mCurrentPage == null) {
            mCurrentPage = page;
            isLoading = false;
            System.out.println(page.toString());
            pageRepositorySubject.onNext(this);
        } else {
            if (!page.equals(mCurrentPage) && page.page > mCurrentPage.page) {
                mPreviusPage = mCurrentPage;
                mCurrentPage = page;
                isLoading = false;
                System.out.println(page.toString());
                pageRepositorySubject.onNext(this);
            }
        }




    }

    @Override
    public void onError(Throwable e) {
        System.out.println(e.getMessage());
    }
}
