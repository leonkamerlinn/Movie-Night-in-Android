package com.movie.search.movienightinandroid.states;

import com.movie.search.movienightinandroid.api.moviedb.model.Page;

import io.reactivex.Single;

public interface MovieState {
    Single<Page> getSingle();
    void loadPage(int page);
    int getPageNumber();
}
