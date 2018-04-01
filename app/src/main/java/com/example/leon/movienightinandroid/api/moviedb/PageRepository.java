package com.example.leon.movienightinandroid.api.moviedb;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.api.moviedb.model.Filter;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;
import com.example.leon.movienightinandroid.states.DiscoverMoviesState;
import com.example.leon.movienightinandroid.states.DiscoverTvState;
import com.example.leon.movienightinandroid.states.MovieState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Leon on 2/24/2018.
 */
@Singleton
public class PageRepository implements SingleObserver<Page> {
    private Page mCurrentPage;
    private boolean isLoading;
    private int mCurrentNumberPage = 0;


    public PublishSubject<String> scrollerSubject = PublishSubject.create();
    public PublishSubject<Filter> filterSubject = PublishSubject.create();
    private PublishSubject<PageRepository> pageRepositorySubject = PublishSubject.create();
    public PublishSubject<Boolean> clearSubject = PublishSubject.create();
    private PublishSubject<Boolean> loadingSubject = PublishSubject.create();
    private MovieState mMovieState;


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
    public Observable<Boolean> getLoadObservable() {
        return loadingSubject;
    }


    public void setMovieState(MovieState movieState) {
        clear();
        loadingSubject.onNext(true);
        mMovieState = movieState;
        mMovieState.getSingle().subscribe(this);
    }

    private void clear() {
        clearSubject.onNext(true);
        mCurrentNumberPage = 0;
    }

    @SuppressLint("CheckResult")
    @Inject
    public PageRepository(TheMovieService.Repository movieService, Context context) {
        HashMap<String, Object> queryMap = new HashMap<>();
        setMovieState(new DiscoverMoviesState(movieService, queryMap));
        mMovieState.getSingle().subscribe(this);
        System.out.println(PageRepository.class.getSimpleName());

        getScroller()
                .sample(250, TimeUnit.MILLISECONDS)
                .skipWhile(s -> isLoading || !hasNextPage())
                .subscribe(s -> {
                    mMovieState.loadPage(getNextPage());
                    mMovieState.getSingle().subscribe(this);
        });



        getFilter().subscribe(filter -> {
            clear();
            switch (filter.getSortBy()) {
                case Filter.POPULARITY:
                    queryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_POPULARITY_DESC);
                    break;

                case Filter.RELEASE_DATE:
                    queryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_RELEASE_DATE_DESC);
                    break;

                case Filter.REVENUE:
                    queryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_REVENUE_DESC);
                    break;

                case Filter.AVERAGE_VOTES:
                    queryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_VOTE_AVERAGE_DESC);
                    break;

                case Filter.VOTES:
                    queryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_VOTE_COUNT_DESC);
                    break;

            }

            queryMap.put(TheMovieService.VOTE_COUNT_GTE_QUERY, filter.getVotes());
            String dateFrom = filter.getDateFrom(true);
            String dateTo = filter.getDateTo(true);
            queryMap.put(TheMovieService.RELEASE_DATE_GTE_QUERY, dateTo);
            queryMap.put(TheMovieService.RELEASE_DATE_LTE_QUERY, dateFrom);
            queryMap.put(TheMovieService.VOTE_AVERAGE_GTE_QUERY, filter.getRating());


            StringBuilder genres = getGenres(filter.getGenres(), context);

            if (genres.length() > 0) {
                genres.replace(genres.length()-1, genres.length(), "");
                queryMap.put(TheMovieService.WITH_GENRES_QUERY, genres.toString());
            }


            if (filter.isMovie()) {
                setMovieState(new DiscoverMoviesState(movieService, queryMap));
            } else {
                setMovieState(new DiscoverTvState(movieService, queryMap));
            }

            mMovieState.getSingle().subscribe(this);


        });
    }

    private StringBuilder getGenres(String[] genres, Context context) {
        List<String> listGenres = Arrays.asList(genres);
        StringBuilder genresBuilder = new StringBuilder("");

        String[] filterGenreItems = context.getResources().getStringArray(R.array.filter_genre_items);
        int[] filterGenreIds = context.getResources().getIntArray(R.array.filter_genre_ids);


        for (int i = 1; i < filterGenreItems.length; i++) {
            String item = filterGenreItems[i];

            if (listGenres.contains(item)) {
                int genreCode = filterGenreIds[i];
                genresBuilder.append(String.valueOf(genreCode)+",");
            }
        }

        return genresBuilder;
    }




    public int getNextPage() {
       return mCurrentNumberPage +1;
    }

    public Page getCurrentPage() {
        return mCurrentPage;
    }


    public boolean hasNextPage() {
        return (getCurrentPage().total_pages > getCurrentPage().page);
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
            loadingSubject.onNext(false);
            pageRepositorySubject.onNext(this);
            mCurrentNumberPage = page.page;
            System.out.println(page);
        } else {
            if ( (!page.equals(mCurrentPage) && page.page > mCurrentPage.page) || mCurrentNumberPage == 0) {
                mCurrentPage = page;
                isLoading = false;
                loadingSubject.onNext(false);
                pageRepositorySubject.onNext(this);
                mCurrentNumberPage = page.page;
                System.out.println(page);
            }
        }

    }

    @Override
    public void onError(Throwable e) {
        System.out.println(e.getMessage());
    }
}
