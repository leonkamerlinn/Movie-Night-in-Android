package com.example.leon.movienightinandroid.api.moviedb;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.leon.movienightinandroid.MainViewModel;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.api.moviedb.model.Movie;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;
import com.example.leon.movienightinandroid.databinding.ItemMovieBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Leon on 2/4/2018.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private final MainViewModel mViewModel;

    @Inject
    public MovieRecyclerViewAdapter(MainViewModel viewModel, Activity activity) {
        viewModel.getPageLiveData().observe((LifecycleOwner) activity, this::notifyPage);
        mViewModel = viewModel;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding itemMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie, parent, false);

        return new MovieViewHolder(itemMovieBinding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        ItemMovieBinding binding = holder.binding;
        List<Movie> movies = new ArrayList<>(mViewModel.getPageLiveData().getMovies());
        Movie movie = movies.get(position);


        String title = movie.original_title;
        String name = movie.original_name;
        binding.sortTextView.setText((title == null) ? name : title);
        String imageUrl = TheMovieService.IMAGE_URL + movie.poster_path;
        Glide.with(binding.getRoot().getContext())
                .load(imageUrl)
                .into(binding.imageView);

    }

    @Override
    public int getItemCount() {
        return mViewModel.getPageLiveData().getMovies().size();
    }



    public void clear() {
        mViewModel.getPageLiveData().getMovies().clear();
        notifyDataSetChanged();
    }


    private void notifyPage(Page page) {
        notifyItemRangeInserted(mViewModel.getPageLiveData().getMovies().size() - 1, page.results.size());
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ItemMovieBinding binding;

        public MovieViewHolder(ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            binding = itemMovieBinding;
        }
    }





}
