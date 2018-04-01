package com.example.leon.movienightinandroid.api.moviedb.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.leon.movienightinandroid.api.moviedb.TheMovieService;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<String> mTitle = new MutableLiveData<>();
    private MutableLiveData<String> mImageUrl = new MutableLiveData<>();

    public MovieViewModel(Movie movie) {
        String title = movie.original_title;
        String name = movie.original_name;
        mTitle.setValue((title == null) ? name : title);

        String path = (movie.poster_path != null) ? movie.poster_path : movie.backdrop_path;
        String imageUrl = TheMovieService.IMAGE_URL + path;
        mImageUrl.setValue(imageUrl);
    }

    public LiveData<String> getTitle() {
        return mTitle;
    }

    public LiveData<String> getImageUrl() {
        return mImageUrl;
    }
}
