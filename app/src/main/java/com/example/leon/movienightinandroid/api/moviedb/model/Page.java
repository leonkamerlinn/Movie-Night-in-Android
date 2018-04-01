package com.example.leon.movienightinandroid.api.moviedb.model;

import java.util.List;

/**
 * Created by Leon on 2/3/2018.
 */

public class Page {
    public int page;
    public int total_results;
    public int total_pages;
    public List<Movie> results;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Page) {
            Page other = (Page)obj;
            return (page == other.page);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Page: %s, Total pages %s", page, total_pages);
    }
}
