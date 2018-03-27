package com.example.leon.movienightinandroid.api.moviedb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.api.moviedb.model.Filter;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private final Context mContext;
    private boolean isMovie;
    private FilterState mMode;
    private Page mPreviusPage;
    private Page mCurrentPage;
    private boolean isLoading;
    private int mCurretResultPage = 0;


    public PublishSubject<String> scrollerSubject = PublishSubject.create();
    public PublishSubject<Filter> filterSubject = PublishSubject.create();
    private PublishSubject<PageRepository> pageRepositorySubject = PublishSubject.create();
    private PublishSubject<Boolean> clearSubject = PublishSubject.create();



    public Observable<String> getScroller() {
        return scrollerSubject;
    }
    public Observable<Filter> getFilter() {
        return filterSubject;
    }
    public Observable<PageRepository> getObservable() {
        return pageRepositorySubject;
    }
    public Observable<Boolean> clearObservable() {
        return clearSubject;
    }



    @SuppressLint("CheckResult")
    @Inject
    public PageRepository(TheMovieService.Repository movieService, Context context) {
        mContext = context;
        mQueryMap = new HashMap<>();
        isLoading = true;
        movieService.discoverMovies(1, mQueryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

        setMode(FilterState.DISCOVER_MOVIES);


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

                    if (isMovie) {
                        movieService.discoverMovies(getNextPage(), mQueryMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this);
                    } else {
                        movieService.discoverTv(getNextPage(), mQueryMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this);
                    }

                    break;
            }
        });


        getFilter().subscribe(filter -> {
            isMovie = filter.isMovie();
            mCurretResultPage = 0;
            setMode(FilterState.FILTER);
            clearSubject.onNext(true);

            switch (filter.getSortBy()) {
                case "popularity":
                    mQueryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_POPULARITY_DESC);
                    break;

                case "release date":
                    mQueryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_RELEASE_DATE_DESC);
                    break;

                case "revenue":
                    mQueryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_REVENUE_DESC);
                    break;

                case "average votes":
                    mQueryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_VOTE_AVERAGE_DESC);
                    break;

                case "votes":
                    mQueryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_VOTE_COUNT_DESC);
                    break;

            }

            mQueryMap.put(TheMovieService.VOTE_COUNT_GTE_QUERY, filter.getVotes());

            String dateFrom = filter.getDateFrom(true);
            String dateTo = filter.getDateTo(true);
            mQueryMap.put(TheMovieService.RELEASE_DATE_GTE_QUERY, dateTo);
            mQueryMap.put(TheMovieService.RELEASE_DATE_LTE_QUERY, dateFrom);
            mQueryMap.put(TheMovieService.VOTE_AVERAGE_GTE_QUERY, filter.getRating());


            String[] genres = filter.getGenres();
            List<String> listGenres = Arrays.asList(genres);
            StringBuilder genresBuilder = new StringBuilder("");

            String[] filterGenreItems = mContext.getResources().getStringArray(R.array.filter_genre_items);
            int[] filterGenreIds = mContext.getResources().getIntArray(R.array.filter_genre_ids);


            for (int i = 1; i < filterGenreItems.length; i++) {
                String item = filterGenreItems[i];

                if (listGenres.contains(item)) {
                    int genreCode = filterGenreIds[i];
                    genresBuilder.append(String.valueOf(genreCode)+",");
                }
            }

            if (genresBuilder.length() > 0) {
                genresBuilder.replace(genresBuilder.length()-1, genresBuilder.length(), "");
                mQueryMap.put(TheMovieService.WITH_GENRES_QUERY, genresBuilder.toString());
            }






            if (filter.isMovie()) {
                movieService.discoverMovies(1, mQueryMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
            } else {
                movieService.discoverTv(1, mQueryMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this);
            }


        });
    }




    public int getNextPage() {
       return mCurretResultPage+1;
    }

    public Page getCurrentPage() {
        return mCurrentPage;
    }




    public void setMode(FilterState mode) {
        mMode = mode;
    }

    public FilterState getMode() {
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
            pageRepositorySubject.onNext(this);
            mCurretResultPage = page.page;
            System.out.println(page.toString());
        } else {
            if ( (!page.equals(mCurrentPage) && page.page > mCurrentPage.page) || mCurretResultPage == 0) {
                mPreviusPage = mCurrentPage;
                mCurrentPage = page;
                isLoading = false;
                pageRepositorySubject.onNext(this);
                mCurretResultPage = page.page;
                System.out.println(page.toString());
            }
        }




    }

    @Override
    public void onError(Throwable e) {
        System.out.println(e.getMessage());
    }
}
