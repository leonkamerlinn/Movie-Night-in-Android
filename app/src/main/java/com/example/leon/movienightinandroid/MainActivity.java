package com.example.leon.movienightinandroid;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leon.movienightinandroid.api.moviedb.MovieRecyclerViewAdapter;
import com.example.leon.movienightinandroid.api.moviedb.SearchFilter;
import com.example.leon.movienightinandroid.api.moviedb.TheMovieService;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.ui.sortfilter.SortFilterActivity;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends DaggerAppCompatActivity {
    public static final int REQUEST_CODE = 10;
    private SearchView mSearchView;
    private Observable<Boolean> mRadioGroupObservable;
    private LinearLayoutManager mLayoutManager;
    private String mQuery;
    String[] checkedGenres;

    @Inject
    ActivityMainBinding binding;
    @Inject
    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    @Inject
    TheMovieService.Repository movieRepository;
    @Inject
    MainViewModel viewModel;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);
        setupMovieRecyclerView();
    }

    private void setupMovieRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
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
            mQuery = query;
            movieRecyclerViewAdapter.clear();
            viewModel.setTitle(query);

            if (!mSearchView.isIconified()) {
                mSearchView.setQuery(null, false);
                mSearchView.setIconified(true);

            }

            if (binding.radioAll.isChecked()) {

                viewModel.getPageLiveData().setMode(SearchFilter.SEARCH_ALL);
                movieRepository.searchMulti(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(viewModel.getPageLiveData());

            } else if (binding.radioTv.isChecked()) {

                viewModel.getPageLiveData().setMode(SearchFilter.SEARCH_TV);
                movieRepository.searchTv(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(viewModel.getPageLiveData());

            } else if (binding.radioMovie.isChecked()) {

                viewModel.getPageLiveData().setMode(SearchFilter.SEARCH_MOVIES);
                movieRepository.searchMovie(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(viewModel.getPageLiveData());

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
                //SortFilterDialog.newInstance().show(getSupportFragmentManager(), SortFilterDialog.TAG);
                Intent intent = new Intent(this, SortFilterActivity.class);
                if (checkedGenres != null && checkedGenres.length > 0) {
                    intent.putExtra("values", checkedGenres);
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
                String[] result = data.getStringArrayExtra("result");
                checkedGenres = result;
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
            int visibleThreshold = 20;

            if (!viewModel.getPageLiveData().isLoading().getValue() && totalItemCount <= lastVisibleItem + visibleThreshold) {
                //movieRecyclerViewAdapter.loadNextPage();

                switch (viewModel.getPageLiveData().getFilter()) {
                    case DISCOVER_MOVIES:
                        movieRepository.discoverMovies(viewModel.getPageLiveData().getNextPage())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(viewModel.getPageLiveData());
                        break;

                    case DISCOVER_TV:
                        break;

                    case SEARCH_MOVIES:
                        movieRepository.searchMovie(mQuery)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(viewModel.getPageLiveData());
                        break;

                    case SEARCH_TV:
                        movieRepository.searchTv(mQuery)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(viewModel.getPageLiveData());
                        break;

                    case SEARCH_ALL:
                        movieRepository.searchMulti(mQuery)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(viewModel.getPageLiveData());
                        break;
                }


            }

        }
    }
}
