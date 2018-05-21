package com.movie.search.movienightinandroid.api.moviedb.model;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Leon on 2/4/2018.
 */

public class Movie implements Comparable<Movie>, Parcelable {
    public int vote_count;
    public int id;
    public boolean video;
    public float vote_average;
    public String title;
    public float popularity;
    public String poster_path;
    public String original_language;
    public String original_title;
    public int[] genre_ids;
    public String backdrop_path;
    public boolean adult;
    public String overview;
    public String release_date;
    public String original_name;
    public String name;
    public String media_type;


    public Movie() {

    }


    public boolean hasReleaseDate() {
        return (release_date != null);

    }

    public synchronized MovieViewModel getMovieViewModel(Application application) {
        return new MovieViewModel(application, this);
    }

    public Movie(Parcel in) {
        vote_count = in.readInt();
        id = in.readInt();
        video = in.readByte() != 0;
        vote_average = in.readFloat();
        title = in.readString();
        popularity = in.readFloat();
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        genre_ids = in.createIntArray();
        backdrop_path = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        release_date = in.readString();
        original_name = in.readString();
        name = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int compareTo(@NonNull Movie other) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(vote_count);
        dest.writeInt(id);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeFloat(vote_average);
        dest.writeString(title);
        dest.writeFloat(popularity);
        dest.writeString(poster_path);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeIntArray(genre_ids);
        dest.writeString(backdrop_path);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_name);
        dest.writeString(name);
    }


    @Override
    public String toString() {
        return String.format("Title: %s\n, Original name: %s\n, Original title: %s\n, Poster path: %s\n, Release date: %s\n, Vote count: %s\n, Popularity: %s\n", title, original_name, original_title, poster_path, release_date, vote_count, popularity);
    }


}
