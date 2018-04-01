package com.example.leon.movienightinandroid.api.moviedb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.api.moviedb.model.Movie;
import com.example.leon.movienightinandroid.api.moviedb.model.MovieViewModel;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;
import com.example.leon.movienightinandroid.databinding.ItemMovieBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Leon on 2/4/2018.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>{

    private final Activity mActivity;
    private MovieListener mMovieListener;
    private List<Movie> mMovies;


    @SuppressLint("CheckResult")
    @Inject
    public MovieRecyclerViewAdapter(Activity activity, PageRepository pageRepository) {
        mActivity = activity;
        mMovies = new ArrayList<>();
        pageRepository.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repository -> {
                    mMovies.addAll(repository.getCurrentPage().results);
                    notifyPage(pageRepository.getCurrentPage());
                });

        pageRepository.clearObservable()
                .subscribe(aBoolean -> {
                    mMovies.clear();
                    notifyDataSetChanged();
                });
    }





    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding itemMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie, parent, false);

        return new MovieViewHolder(itemMovieBinding, this);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        ItemMovieBinding binding = holder.binding;
        Movie movie = getMovies().get(position);
        binding.setLifecycleOwner((LifecycleOwner) mActivity);

        binding.setModel(movie.getMovieViewModel(mActivity.getApplication()));
    }



    @Override
    public int getItemCount() {
        return getMovies().size();
    }


    private void notifyPage(Page page) {
        notifyItemRangeInserted(getMovies().size() - 1, page.results.size());
    }

    public void setItemListener(MovieListener movieListener) {
        mMovieListener = movieListener;
    }

    public List<Movie> getMovies() {
        return Stream.of(mMovies)
                .filter(movie -> (movie.backdrop_path != null || movie.poster_path != null))
                .collect(Collectors.toList());
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ItemMovieBinding binding;
        private final MovieRecyclerViewAdapter adapter;


        public MovieViewHolder(ItemMovieBinding itemMovieBinding, MovieRecyclerViewAdapter movieRecyclerViewAdapter) {
            super(itemMovieBinding.getRoot());
            binding = itemMovieBinding;
            binding.getRoot().setOnClickListener(this);
            adapter = movieRecyclerViewAdapter;
        }

        @Override
        public void onClick(View v) {
            if (adapter.mMovieListener != null) {
                adapter.mMovieListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface MovieListener {
        void onItemClick(View view, int position);

    }

}


