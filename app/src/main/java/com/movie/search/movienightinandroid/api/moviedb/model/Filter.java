package com.movie.search.movienightinandroid.api.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Filter implements Parcelable {
    public static final String POPULARITY = "popularity";
    public static final String RELEASE_DATE = "release date";
    public static final String REVENUE = "revenue";
    public static final String AVERAGE_VOTES = "average votes";
    public static final String VOTES = "votes";




    private String mSortBy;
    private String[] mGenres;
    private String mDateFrom;
    private String mDateTo;
    private boolean mIsMovie;
    private int mVotes;
    private int mRating;

    public Filter(String sortBy, String[] genres, String dateFrom, String dateTo, boolean isMovie, int votes, int rating) {
        mSortBy = sortBy;
        mGenres = genres;
        mDateFrom = dateFrom;
        mDateTo = dateTo;
        mIsMovie = isMovie;
        mVotes = votes;
        mRating = rating;
    }

    protected Filter(Parcel in) {
        mSortBy = in.readString();
        mGenres = in.createStringArray();
        mDateFrom = in.readString();
        mDateTo = in.readString();
        mIsMovie = in.readByte() != 0;
        mVotes = in.readInt();
        mRating = in.readInt();
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };

    public String getSortBy() {
        return mSortBy;
    }

    public void setSortBy(String sortBy) {
        mSortBy = sortBy;
    }

    public String[] getGenres() {
        return mGenres;
    }

    public void setGenres(String[] genres) {
        mGenres = genres;
    }

    public String getDateFrom(boolean format) {
        DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = originalFormat.parse(mDateFrom);
        } catch (ParseException e) {
        }
        String formattedDate = targetFormat.format(date);

        return format ? formattedDate : mDateFrom;
    }

    public void setDateFrom(String dateFrom) {
        mDateFrom = dateFrom;
    }

    public String getDateTo(boolean format) {
        DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = originalFormat.parse(mDateTo);
        } catch (ParseException e) {
        }
        String formattedDate = targetFormat.format(date);

        return format ? formattedDate : mDateTo;
    }

    public void setDateTo(String dateTo) {
        mDateTo = dateTo;
    }

    public boolean isMovie() {
        return mIsMovie;
    }

    public void setMovie(boolean movie) {
        mIsMovie = movie;
    }

    public int getVotes() {
        return mVotes;
    }

    public void setVotes(int votes) {
        mVotes = votes;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSortBy);
        dest.writeStringArray(mGenres);
        dest.writeString(mDateFrom);
        dest.writeString(mDateTo);
        dest.writeByte((byte) (mIsMovie ? 1 : 0));
        dest.writeInt(mVotes);
        dest.writeInt(mRating);
    }

    @Override
    public String toString() {
        return String.format("%s, %s", getDateFrom(true), getDateTo(true));
    }
}
