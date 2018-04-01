package com.example.leon.movienightinandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.leon.movienightinandroid.api.moviedb.MovieRecyclerViewAdapter;
import com.example.leon.movienightinandroid.api.moviedb.PageRepository;
import com.example.leon.movienightinandroid.api.moviedb.TheMovieService;
import com.example.leon.movienightinandroid.api.moviedb.model.Filter;
import com.example.leon.movienightinandroid.api.moviedb.model.Movie;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.dialog.MovieDialog;
import com.example.leon.movienightinandroid.states.SearchAllState;
import com.example.leon.movienightinandroid.states.SearchMoviesState;
import com.example.leon.movienightinandroid.states.SearchTvState;
import com.example.leon.movienightinandroid.ui.sortfilter.SortFilterActivity;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;


public class MainActivity extends DaggerAppCompatActivity implements MovieRecyclerViewAdapter.MovieListener {
    public static final int REQUEST_CODE = 10;
    public static final String KEY_LIST_STATE = "list_state";
    private SearchView mSearchView;
    private Observable<Boolean> mRadioGroupObservable;
    private GridLayoutManager mLayoutManager;
    private Filter mFilter;

    @Inject
    ActivityMainBinding binding;
    @Inject
    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    @Inject
    TheMovieService.Repository movieService;
    @Inject
    MainViewModel viewModel;
    @Inject
    PageRepository pageRepository;


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);

        setupMovieRecyclerView();
        movieRecyclerViewAdapter.setItemListener(this);
        pageRepository.getLoadObservable().subscribe(viewModel::setLoading);

        if (savedInstanceState != null) {
            pageRepository.restore();
        }
    }


    private void setupMovieRecyclerView() {
        mLayoutManager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setHasFixedSize(false);
        binding.recyclerView.setAdapter(movieRecyclerViewAdapter);
        binding.recyclerView.addOnScrollListener(new RecyclerViewScroller());
    }

    private Observable<Boolean> getRadioGroupObservable() {
        if (mRadioGroupObservable == null) {
            Observable<Boolean> radioAllObservable = RxCompoundButton.checkedChanges(binding.radioAll)
                    .filter(aBoolean -> aBoolean);
            Observable<Boolean> radioMovieObservable = RxCompoundButton.checkedChanges(binding.radioMovie)
                    .filter(aBoolean -> aBoolean);
            Observable<Boolean> radioTvObservable = RxCompoundButton.checkedChanges(binding.radioTv)
                    .filter(aBoolean -> aBoolean);
            mRadioGroupObservable = Observable.merge(radioAllObservable, radioMovieObservable, radioTvObservable);
        }
        return mRadioGroupObservable;
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

        getCombinedSearchAndRadioObservable().subscribe();

        return true;
    }

    private Observable<String> getCombinedSearchAndRadioObservable() {
        return Observable.combineLatest(getSearchStringObservable(), getRadioGroupObservable(), (query, aBoolean) -> {
            viewModel.setTitle(query);

            if (!mSearchView.isIconified()) {
                mSearchView.setQuery(null, false);
                mSearchView.setIconified(true);
            }

            if (binding.radioAll.isChecked()) {
                pageRepository.setMovieState(new SearchAllState(movieService, query));
            } else if (binding.radioTv.isChecked()) {
                pageRepository.setMovieState(new SearchTvState(movieService, query));
            } else if (binding.radioMovie.isChecked()) {
                pageRepository.setMovieState(new SearchMoviesState(movieService, query));

            }

            return query;
        });
    }

    private Observable<String> getSearchStringObservable() {
        return RxSearchView.queryTextChangeEvents(mSearchView)
                    .filter(SearchViewQueryTextEvent::isSubmitted)
                    .map(searchViewQueryTextEvent -> searchViewQueryTextEvent.queryText().toString());
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
                Intent intent = new Intent(this, SortFilterActivity.class);
                if (mFilter != null) {
                    intent.putExtra(SortFilterActivity.FILTER_EXTRA, mFilter);
                }
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

                if (data.hasExtra(SortFilterActivity.FILTER_EXTRA)) {
                    mFilter = data.getParcelableExtra(SortFilterActivity.FILTER_EXTRA);
                    pageRepository.filterSubject.onNext(mFilter);
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Movie movie = movieRecyclerViewAdapter.getMovies().get(position);
        MovieDialog movieDialog = MovieDialog.newInstance(movie);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        movieDialog.show(ft, MovieDialog.TAG);
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
            int visibleThreshold = 30;
            if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                pageRepository.scrollerSubject.onNext("");
            }

        }
    }
}
