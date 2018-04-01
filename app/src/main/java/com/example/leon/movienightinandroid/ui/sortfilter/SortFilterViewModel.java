package com.example.leon.movienightinandroid.ui.sortfilter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.leon.movienightinandroid.R;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Leon on 2/24/2018.
 */
@SuppressLint("StaticFieldLeak")
public class SortFilterViewModel extends ViewModel {
    private final Context mContext;
    private MutableLiveData<String> mDateFrom;
    private MutableLiveData<String> mDateTo;

    private MutableLiveData<Integer> mSortItemSelected;
    private MutableLiveData<String> mVotes;
    private MutableLiveData<Float> mRating;
    private MutableLiveData<Boolean> mIsMovie;
    private MutableLiveData<String[]> mGenres = new MutableLiveData<>();

    public SortFilterViewModel(Context context) {
        mContext = context;
        mDateFrom = new MutableLiveData<>();
        mDateTo = new MutableLiveData<>();
        mSortItemSelected = new MutableLiveData<>();
        mSortItemSelected.setValue(0);

        mVotes = new MutableLiveData<>();
        mVotes.setValue("0");

        mRating = new MutableLiveData<>();
        mIsMovie = new MutableLiveData<>();
        setMovie(true);


        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.YEAR, 2000);
        past.set(Calendar.MONTH, 1);
        past.set(Calendar.DAY_OF_MONTH, 1);


        String dateFrom = String.format("%02d%02d%02d",now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH), now.get(Calendar.YEAR));
        mDateFrom.setValue(dateFrom);

        String dateTo = String.format("%02d%02d%02d",past.get(Calendar.DAY_OF_MONTH), past.get(Calendar.MONTH), past.get(Calendar.YEAR));
        mDateTo.setValue(dateTo);
        setGenres(new String[]{});


    }

    public void setGenres(String[] genres) {
        mGenres.setValue(genres);
    }

    public LiveData<String[]> getGenres() {
        return mGenres;
    }

    public LiveData<String> dateFromLiveData() {
        return mDateFrom;
    }

    public LiveData<String> dateToLiveData() {
        return mDateTo;
    }

    public void dateFromSetText(String text) {
        mDateFrom.setValue(text);
    }

    public void dateToSetText(String text) {
        mDateTo.setValue(text);

    }


    public LiveData<String> movieSwitchLiveData() {
        return Transformations.map(mIsMovie, checked -> checked ? mContext.getString(R.string.switch_movie) : mContext.getString(R.string.switch_tv));
    }

    public void setSortItemExtra(String sortItemExtra) {
        String[] items = mContext.getResources().getStringArray(R.array.sort_items);
        int position = Arrays.asList(items).indexOf(sortItemExtra);
        mSortItemSelected.setValue(position);

    }

    public LiveData<Integer> getItemSelected() {
        return mSortItemSelected;
    }

    public void setVotesExtra(int votesExtra) {
        mVotes.setValue(String.valueOf(votesExtra));
    }

    public LiveData<String> getVotes() {
        return mVotes;
    }

    public void setRatingExtra(float ratingExtra) {

        mRating.setValue(ratingExtra);
    }

    public LiveData<Float> getRating() {
        return mRating;
    }

    public void setMovie(boolean movieSwitchChecked) {
        mIsMovie.setValue(movieSwitchChecked);
    }

    public LiveData<Boolean> isMovieChecked() {
        return mIsMovie;
    }
}
