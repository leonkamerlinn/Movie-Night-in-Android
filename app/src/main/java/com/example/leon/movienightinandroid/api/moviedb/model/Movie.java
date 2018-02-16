package com.example.leon.movienightinandroid.api.moviedb.model;

import android.support.annotation.NonNull;

/**
 * Created by Leon on 2/4/2018.
 */

public class Movie implements Comparable<Movie> {
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

    @Override
    public int compareTo(@NonNull Movie other) {
        return 0;
    }
}
