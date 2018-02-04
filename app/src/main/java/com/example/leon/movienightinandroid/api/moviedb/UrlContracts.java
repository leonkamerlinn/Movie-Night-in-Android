package com.example.leon.movienightinandroid.api.moviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Leon on 2/4/2018.
 */

public class UrlContracts {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY_QUERY = "api_key";
    public static final String PAGE_QUERY = "page";
    public static final String DISCOVER_MOVIES_PATH = "discover/movie";

    public interface TheMovieService {
        @GET(UrlContracts.DISCOVER_MOVIES_PATH)
        Call<Page> listMovies(
                @Query(UrlContracts.PAGE_QUERY) int page
        );
    }
}
