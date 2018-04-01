package com.example.leon.movienightinandroid.dialog;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.api.moviedb.TheMovieService;
import com.example.leon.movienightinandroid.api.moviedb.model.Movie;
import com.example.leon.movienightinandroid.databinding.DialogMovieBinding;

public class MovieDialog extends DialogFragment {
    public static final String TAG = MovieDialog.class.getSimpleName();

    public static final String MOVIE_KEY = "movie_key";


    public MovieDialog() {

    }

    public static MovieDialog newInstance(Movie movie) {
        MovieDialog dialog = new MovieDialog();

        Bundle args = new Bundle();
        args.putParcelable(MOVIE_KEY, movie);
        dialog.setArguments(args);
        return dialog;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        Movie movie = bundle.getParcelable(MOVIE_KEY);
        DialogMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_movie, null, false);
        binding.setLifecycleOwner(getActivity());
        binding.setModel(movie.getMovieViewModel(getActivity().getApplication()));


       /* binding.title.setText(movie.title);

        String imageUrl = TheMovieService.IMAGE_URL + movie.poster_path;
        Glide.with(getContext())
                .load(imageUrl)
                .into(binding.imageView);

        binding.votesCount.setText(String.valueOf(movie.vote_count));
        binding.ratingBar.setRating(movie.vote_average);
        binding.date.setText(movie.release_date);
        binding.description.setText(movie.overview);*/

        return new AlertDialog.Builder(binding.getRoot().getContext())
                .setView(binding.getRoot())
                .create();
    }
}
