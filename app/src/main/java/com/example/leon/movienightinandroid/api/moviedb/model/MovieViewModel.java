package com.example.leon.movienightinandroid.api.moviedb.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.leon.movienightinandroid.api.moviedb.TheMovieService;

public class MovieViewModel extends AndroidViewModel {

    private MutableLiveData<String> mTitle = new MutableLiveData<>();
    private MutableLiveData<String> mImageUrl = new MutableLiveData<>();
    private MutableLiveData<String> mType = new MutableLiveData<>();
    private MutableLiveData<Boolean> mShowReleaseDate = new MutableLiveData<>();
    private MutableLiveData<String> mVotes = new MutableLiveData<>();
    private MutableLiveData<Float> mAverageVote = new MutableLiveData<>();
    private MutableLiveData<String> mReleaseDate = new MutableLiveData<>();
    private MutableLiveData<String> mDescription = new MutableLiveData<>();

    public MovieViewModel(Application application, Movie movie) {
        super(application);
        String title = movie.original_title;
        String name = movie.original_name;
        mTitle.setValue((title == null) ? name : title);

        String path = (movie.poster_path != null) ? movie.poster_path : movie.backdrop_path;
        String imageUrl = TheMovieService.IMAGE_URL + path;
        mImageUrl.setValue(imageUrl);



        mShowReleaseDate.setValue(movie.hasReleaseDate());
        mVotes.setValue(String.valueOf(movie.vote_count));
        mAverageVote.setValue(movie.vote_average);
        mReleaseDate.setValue(movie.release_date);
        mDescription.setValue(movie.overview);
    }

    public LiveData<String> getTitle() {
        return mTitle;
    }

    public LiveData<String> getImageUrl() {
        return mImageUrl;
    }

    public LiveData<String> getType() {
        return mType;
    }

    public LiveData<Boolean> showReleaseDate() {
        return mShowReleaseDate;
    }

    public LiveData<String> getVotes() {
        return mVotes;
    }

    public LiveData<Float> getAverageVote() {
        return mAverageVote;
    }

    public LiveData<String> getReleaseDate() {
        return mReleaseDate;
    }

    public LiveData<String> getDescription() {
        return mDescription;
    }
}
