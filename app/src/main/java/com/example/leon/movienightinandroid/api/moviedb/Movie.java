package com.example.leon.movienightinandroid.api.moviedb;

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
    public String original_title;

    @Override
    public int compareTo(@NonNull Movie other) {
        return 0;
    }
}
