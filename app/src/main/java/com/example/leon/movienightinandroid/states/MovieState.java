package com.example.leon.movienightinandroid.states;

import com.example.leon.movienightinandroid.api.moviedb.model.Page;

import io.reactivex.Single;

public interface MovieState {
    Single<Page> getSingle();
    void loadPage(int page);
    int getPageNumber();
}
