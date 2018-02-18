package com.example.leon.movienightinandroid.api.moviedb;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.api.moviedb.model.Movie;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;
import com.example.leon.movienightinandroid.databinding.ItemMovieBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Leon on 2/4/2018.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> implements Observer<Page> {
    private Mode mMode;
    private Page mLastPage;

    public enum Mode {
        SEARCH_ALL,
        SEARCH_MOVIES,
        SEARCH_TV,
        DISCOVER_MOVIES,
        DISCOVER_TV
    }


    private final List<Movie> mMovies;
    private final UrlContracts.TheMovieService mMovieService;
    private boolean isLoanding = true;

    @Inject
    public MovieRecyclerViewAdapter(UrlContracts.TheMovieService movieService){
        mMovies = new ArrayList<>();
        mMovieService = movieService;
    }

    public void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);


        ItemMovieBinding itemMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie, parent, false);

        return new MovieViewHolder(itemMovieBinding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        ItemMovieBinding binding = holder.binding;

        List<Movie> movies = new ArrayList<>(mMovies);
        String title = movies.get(position).original_title;
        String name = movies.get(position).original_name;
        binding.sortTextView.setText((title == null) ? name : title);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }



    @Override
    public void onSubscribe(Disposable d) {
        isLoanding = true;
    }

    @Override
    public void onNext(Page page) {
        System.out.println(page.results.size());
        for (Movie movie: page.results) {
            System.out.println(movie.original_title);
            System.out.println(movie.id);
        }
        addPage(page);
    }

    @Override
    public void onError(Throwable e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void onComplete() {
        isLoanding = false;
    }



    public boolean isLastPage() {
        return (mLastPage.total_pages == mLastPage.page);
    }

    public int getNextPage() {
        return mLastPage.page + 1;
    }

    public Page getLastPage() {
        return mLastPage;
    }


    public boolean isLoanding() {
        return isLoanding;
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ItemMovieBinding binding;

        public MovieViewHolder(ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            binding = itemMovieBinding;
        }
    }

    private void addPage(Page page) {
        mLastPage = page;
        mMovies.addAll(page.results);
        notifyDataSetChanged();
       // notifyItemRangeInserted(mMovies.size() - 1, page.results.size());

    }

    public void setMode(Mode mode) {
        mMode = mode;
    }

    public Mode getMode() {
        return mMode;
    }

}
