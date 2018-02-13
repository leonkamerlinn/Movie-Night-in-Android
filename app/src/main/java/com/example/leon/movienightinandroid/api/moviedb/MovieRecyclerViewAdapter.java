package com.example.leon.movienightinandroid.api.moviedb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leon.movienightinandroid.BuildConfig;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.api.moviedb.model.Movie;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leon on 2/4/2018.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {
    private final HashSet<Movie> mMovies;
    private final UrlContracts.TheMovieService mMovieService;
    private boolean isLoanding = true;
    private int mCurrentPage = 1;



    public MovieRecyclerViewAdapter(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl httpUrl = original.url();

                    HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter(UrlContracts.API_KEY_QUERY, BuildConfig.API_KEY).build();
                    Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlContracts.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        UrlContracts.TheMovieService movieService = retrofit.create(UrlContracts.TheMovieService.class);


        mMovies = new HashSet<>();
        mMovieService = movieService;

        loadPage(mCurrentPage);
    }

    public void loadPage(int page) {
        isLoanding = true;

        mMovieService.listMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Page>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Page page) {
                        addPage(page);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        isLoanding = false;
                    }
                });
    }

    public boolean isLoanding() {
        return isLoanding;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);


        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        List<Movie> movies = new ArrayList<>(mMovies);
        holder.textView.setText(movies.get(position).original_title);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void loadNextPage() {
        mCurrentPage++;
        loadPage(mCurrentPage);
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.sortTextView);
        }
    }

    public void addPage(Page page) {

        for (Movie movie: page.results) {
            if (mMovies.add(movie)) {
                notifyItemInserted(mMovies.size() - 1);
            }
        }

    }

    public interface Progress {
        void onDone();
        void onLoanding();
    }
}
