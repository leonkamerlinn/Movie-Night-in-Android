package com.example.leon.movienightinandroid;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leon.movienightinandroid.api.moviedb.MovieRecyclerViewAdapter;
import com.example.leon.movienightinandroid.api.moviedb.UrlContracts;
import com.example.leon.movienightinandroid.api.moviedb.dialog.TimePickerFragment;
import com.example.leon.movienightinandroid.api.moviedb.model.Page;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.ui.SortFilterActivity;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.leon.movienightinandroid.api.moviedb.MovieRecyclerViewAdapter.Mode.DISCOVER_MOVIES;


public class MainActivity extends DaggerAppCompatActivity {
    public static final int REQUEST_CODE = 10;
    private SearchView mSearchView;
    private Observable<Boolean> mRadioGroupObservable;
    private LinearLayoutManager mLayoutManager;
    private String mQuery;

    @Inject
    ActivityMainBinding binding;
    @Inject
    MovieRecyclerViewAdapter mMovieRecyclerViewAdapter;
    @Inject
    UrlContracts.TheMovieService movieService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(binding.toolbar);

        Observable<Boolean> radioAllObservable = RxCompoundButton.checkedChanges(binding.radioAll)
                .filter(aBoolean -> aBoolean);
        Observable<Boolean> radioMovieObservable = RxCompoundButton.checkedChanges(binding.radioMovie)
                .filter(aBoolean -> aBoolean);
        Observable<Boolean> radioTvObservable = RxCompoundButton.checkedChanges(binding.radioTv)
                .filter(aBoolean -> aBoolean);

        mRadioGroupObservable = Observable.merge(radioAllObservable, radioMovieObservable, radioTvObservable);


        mLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setHasFixedSize(false);
        binding.recyclerView.setAdapter(mMovieRecyclerViewAdapter);
        binding.recyclerView.addOnScrollListener(new RecyclerViewScroller());


        Observable<Page> one = movieService.discoverMovies(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Page> two = movieService.discoverMovies(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Page> three = movieService.discoverMovies(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        Observable.concat(one, two, three).subscribe(mMovieRecyclerViewAdapter);


        mMovieRecyclerViewAdapter.setMode(DISCOVER_MOVIES);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);


        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        Observable<String> stringObservable = RxSearchView.queryTextChangeEvents(mSearchView)
                .filter(SearchViewQueryTextEvent::isSubmitted)
                .map(searchViewQueryTextEvent -> searchViewQueryTextEvent.queryText().toString());



        Observable.combineLatest(stringObservable, mRadioGroupObservable, (query, aBoolean) -> {
            mQuery = query;
            Toast.makeText(MainActivity.this, query,Toast.LENGTH_SHORT).show();
            mMovieRecyclerViewAdapter.clear();
            getSupportActionBar().setTitle(query);

            if (!mSearchView.isIconified()) {
                mSearchView.setQuery(null, false);
                mSearchView.setIconified(true);

            }

            if (binding.radioAll.isChecked()) {

                mMovieRecyclerViewAdapter.setMode(MovieRecyclerViewAdapter.Mode.SEARCH_ALL);
                movieService.searchMulti(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mMovieRecyclerViewAdapter);

            } else if (binding.radioTv.isChecked()) {

                mMovieRecyclerViewAdapter.setMode(MovieRecyclerViewAdapter.Mode.SEARCH_TV);
                movieService.searchTv(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mMovieRecyclerViewAdapter);

            } else if (binding.radioMovie.isChecked()) {

                mMovieRecyclerViewAdapter.setMode(MovieRecyclerViewAdapter.Mode.SEARCH_MOVIES);
                movieService.searchMovie(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mMovieRecyclerViewAdapter);

            }



            return query;
        }).subscribe();

        return true;
    }

    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        /*DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");*/

        Calendar now = Calendar.getInstance();
       /* DatePickerDialog dpd = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {

                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.show(getFragmentManager(), "Datepickerdialog");*/
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!mSearchView.isIconified()) {
            mSearchView.setQuery(null, false);
            mSearchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sort:
                //SortFilterDialog.newInstance().show(getSupportFragmentManager(), SortFilterDialog.TAG);
                Intent intent = new Intent(this, SortFilterActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no
                Toast.makeText(this, "cancel", Toast.LENGTH_LONG).show();
            }
        }
    }



    private class RecyclerViewScroller extends RecyclerView.OnScrollListener {
        public RecyclerViewScroller() {
            super();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);



            int totalItemCount = mLayoutManager.getItemCount();
            //adapter position of the first visible view.
            int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            int visibleThreshold = 5;

            if (!mMovieRecyclerViewAdapter.isLoanding() && totalItemCount <= lastVisibleItem + visibleThreshold) {
                //mMovieRecyclerViewAdapter.loadNextPage();

                switch (mMovieRecyclerViewAdapter.getMode()) {
                    case DISCOVER_MOVIES:
                        movieService.discoverMovies(mMovieRecyclerViewAdapter.getNextPage())
                            .skipWhile(page -> mMovieRecyclerViewAdapter.isLastPage())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(mMovieRecyclerViewAdapter);
                        break;

                    case DISCOVER_TV:
                        break;

                    case SEARCH_MOVIES:
                        movieService.searchMovie(mQuery)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(mMovieRecyclerViewAdapter);
                        break;

                    case SEARCH_TV:
                        movieService.searchTv(mQuery)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(mMovieRecyclerViewAdapter);
                        break;

                    case SEARCH_ALL:
                        movieService.searchMulti(mQuery)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(mMovieRecyclerViewAdapter);
                        break;
                }


            }

        }
    }
}
