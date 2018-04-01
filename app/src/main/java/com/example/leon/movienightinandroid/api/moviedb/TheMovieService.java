package com.example.leon.movienightinandroid.api.moviedb;

import android.arch.lifecycle.LiveData;

import com.example.leon.movienightinandroid.api.moviedb.model.Genre;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.internal.operators.single.SingleToObservable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Leon on 2/4/2018.
 */

public class TheMovieService {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w185_and_h278_bestv2";

    //queries
    public static final String API_KEY_QUERY = "api_key";
    public static final String PAGE_QUERY = "page";
    public static final String SEARCH_QUERY = "query";
    public static final String SORT_BY_QUERY = "sort_by";
    public static final String WITH_GENRES_QUERY = "with_genres";


    //filter
    public static final String VOTE_AVERAGE_GTE_QUERY = "vote_average.gte";
    public static final String VOTE_AVERAGE_LTE_QUERY = "vote_average.lte";

    public static final String VOTE_COUNT_GTE_QUERY = "vote_count.gte";
    public static final String VOTE_COUNT_LTE_QUERY = "vote_count.lte";

    public static final String RELEASE_DATE_GTE_QUERY = "release_date.gte";
    public static final String RELEASE_DATE_LTE_QUERY = "release_date.lte";


    //sort
    public static final String SORT_BY_POPULARITY_ASC = "popularity.asc";
    public static final String SORT_BY_POPULARITY_DESC = "popularity.desc";

    public static final String SORT_BY_RELEASE_DATE_ASC = "release_date.asc";
    public static final String SORT_BY_RELEASE_DATE_DESC = "release_date.desc";

    public static final String SORT_BY_REVENUE_ASC = "revenue.asc";
    public static final String SORT_BY_REVENUE_DESC = "revenue.desc";

    public static final String SORT_BY_VOTE_AVERAGE_ASC = "vote_average.asc";
    public static final String SORT_BY_VOTE_AVERAGE_DESC = "vote_average.desc";

    public static final String SORT_BY_VOTE_COUNT_ASC = "vote_count.asc";
    public static final String SORT_BY_VOTE_COUNT_DESC = "vote_count.desc";

    //url paths
    public static final String DISCOVER_MOVIE_PATH = "discover/movie";
    public static final String DISCOVER_TV_PATH = "discover/tv";
    public static final String GENERE_MOVIE_PATH = "genre/movie";
    public static final String SEARCH_MOVIE_PATH = "search/movie";
    public static final String SEARCH_TV_PATH = "search/tv";
    public static final String SEARCH_MULTI_PATH = "search/multi";

    public interface Repository {
        @GET(TheMovieService.DISCOVER_MOVIE_PATH)
        Single<Page> discoverMovies(
                @Query(TheMovieService.PAGE_QUERY) int page,
                @QueryMap Map<String, Object> map
        );

        @GET(DISCOVER_MOVIE_PATH)
        Observable<Page> discoverMovies(@Query(TheMovieService.PAGE_QUERY) int page);


        @GET(TheMovieService.DISCOVER_TV_PATH)
        Single<Page> discoverTv(
                @Query(TheMovieService.PAGE_QUERY) int page,
                @QueryMap Map<String, Object> map
        );

        @GET(TheMovieService.DISCOVER_TV_PATH)
        Observable<Page> discoverTv(@Query(TheMovieService.PAGE_QUERY) int page);

        @GET(TheMovieService.GENERE_MOVIE_PATH)
        Observable<List<Genre>> genres();

        @GET(TheMovieService.SEARCH_MOVIE_PATH)
        Single<Page> searchMovie(@Query(TheMovieService.SEARCH_QUERY) String query);

        @GET(TheMovieService.SEARCH_MOVIE_PATH)
        Single<Page> searchMovie(
                @Query(TheMovieService.PAGE_QUERY) int page,
                @Query(TheMovieService.SEARCH_QUERY) String query
        );

        @GET(TheMovieService.SEARCH_TV_PATH)
        Single<Page> searchTv(@Query(TheMovieService.SEARCH_QUERY) String query);

        @GET(TheMovieService.SEARCH_TV_PATH)
        Single<Page> searchTv(
                @Query(TheMovieService.PAGE_QUERY) int page,
                @Query(TheMovieService.SEARCH_QUERY) String query
        );

        @GET(TheMovieService.SEARCH_MULTI_PATH)
        Single<Page> searchMulti(@Query(TheMovieService.SEARCH_QUERY) String query);

        @GET(TheMovieService.SEARCH_MULTI_PATH)
        Single<Page> searchMulti(
                @Query(TheMovieService.PAGE_QUERY) int page,
                @Query(TheMovieService.SEARCH_QUERY) String query
        );
    }

    /*
    * https://api.themoviedb.org/3/discover/movie?api_key=96ad78922f07ad458d6ed2c9bf625b87&with_genres=18,28
    *
    * https://api.themoviedb.org/3/genre/movie/list?api_key=96ad78922f07ad458d6ed2c9bf625b87&language=en-US
    *
    *
    *   Allow users to choose:
    *
    *   rating threshold
        minimum # of ratings
        release date range


        Allow users to filter based on genre

        Users should be able to specify if they want to see movies, TV shows, or both in their search results.

        Allow users to choose how results are sorted. Users should be able to sort by popularity, release date, revenue, average vote, and number of votes.

    *
    * */
}
