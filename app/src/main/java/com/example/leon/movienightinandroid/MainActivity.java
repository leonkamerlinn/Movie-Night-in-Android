package com.example.leon.movienightinandroid;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.ui.sortfilter.SortFilterActivity;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.support.v7.widget.SearchViewQueryTextEvent;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;


public class MainActivity extends DaggerAppCompatActivity implements MovieRecyclerViewAdapter.MovieListener {
    public static final int REQUEST_CODE = 10;
    private SearchView mSearchView;
    private Observable<Boolean> mRadioGroupObservable;
    private GridLayoutManager mLayoutManager;
    private String mQuery = "";
    String[] checkedGenres;
    private String mSortSelectedItemExtra;
    private String mDateFromExtra;
    private String mDateToExtra;
    private boolean mMovieExtra;
    private int mVotesExtra;
    private int mResultCode;
    private float mRatingExtra;

    @Inject
    ActivityMainBinding binding;
    @Inject
    MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    @Inject
    TheMovieService.Repository movieRepository;
    @Inject
    MainViewModel viewModel;
    @Inject
    PageRepository pageRepository;

    private HashMap<String, Object> mQueryMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);
        setupMovieRecyclerView();
        movieRecyclerViewAdapter.setItemListener(this);
        mQueryMap = new HashMap<>();
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
            mQuery = query;
            movieRecyclerViewAdapter.clear();
            viewModel.setTitle(query);

            if (!mSearchView.isIconified()) {
                mSearchView.setQuery(null, false);
                mSearchView.setIconified(true);

            }

           /* if (binding.radioAll.isChecked()) {

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

            }*/

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

                if (mResultCode == Activity.RESULT_OK) {
                    intent.putExtra(SortFilterActivity.SORT_ITEM_EXTRA, mSortSelectedItemExtra);
                    intent.putExtra(SortFilterActivity.GENRE_TAGS_EXTRA, checkedGenres);
                    intent.putExtra(SortFilterActivity.DATE_FROM_EXTRA, mDateFromExtra);
                    intent.putExtra(SortFilterActivity.DATE_TO_EXTRA, mDateToExtra);
                    intent.putExtra(SortFilterActivity.MOVIE_EXTRA, mMovieExtra);
                    intent.putExtra(SortFilterActivity.VOTES_EXTRA, mVotesExtra);
                    intent.putExtra(SortFilterActivity.RATING_EXTRA, mRatingExtra);
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




                mResultCode = Activity.RESULT_OK;
                if (data.hasExtra(SortFilterActivity.SORT_ITEM_EXTRA)) {
                    mSortSelectedItemExtra = data.getStringExtra(SortFilterActivity.SORT_ITEM_EXTRA);
                }

                if (data.hasExtra(SortFilterActivity.GENRE_TAGS_EXTRA)) {
                    checkedGenres = data.getStringArrayExtra(SortFilterActivity.GENRE_TAGS_EXTRA);
                }

                if (data.hasExtra(SortFilterActivity.DATE_FROM_EXTRA)) {
                    mDateFromExtra = data.getStringExtra(SortFilterActivity.DATE_FROM_EXTRA);
                }

                if (data.hasExtra(SortFilterActivity.DATE_TO_EXTRA)) {
                    mDateToExtra = data.getStringExtra(SortFilterActivity.DATE_TO_EXTRA);
                }

                if (data.hasExtra(SortFilterActivity.MOVIE_EXTRA)) {
                    mMovieExtra = data.getBooleanExtra(SortFilterActivity.MOVIE_EXTRA, true);
                }

                if (data.hasExtra(SortFilterActivity.VOTES_EXTRA)) {
                    mVotesExtra = data.getIntExtra(SortFilterActivity.VOTES_EXTRA, 0);
                }

                if (data.hasExtra(SortFilterActivity.RATING_EXTRA)) {
                    mRatingExtra = data.getFloatExtra(SortFilterActivity.RATING_EXTRA, 0);
                }

                if (data.hasExtra(SortFilterActivity.FILTER_EXTRA)) {
                    Filter filter = data.getParcelableExtra(SortFilterActivity.FILTER_EXTRA);
                    pageRepository.filterSubject.onNext(filter);
                    System.out.println(filter.toString());
                }
                mQueryMap.put(TheMovieService.SORT_BY_QUERY, TheMovieService.SORT_BY_VOTE_COUNT_ASC);

                movieRecyclerViewAdapter.clear();
                //viewModel.getPageLiveData().clear();


              /*  viewModel.getPageLiveData().setMode(SearchFilter.FILTER);
                movieRepository.discoverMovies(viewModel.getPageLiveData().getNextPage(), mQueryMap)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(viewModel.getPageLiveData());*/

            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
      /*  Movie movie = viewModel.getPageLiveData().getMovies().get(position);
        MovieDialog movieDialog = MovieDialog.newInstance(movie);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        movieDialog.show(ft, MovieDialog.TAG);*/
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
            int visibleThreshold = 10;
            if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                pageRepository.scrollerSubject.onNext(mQuery);
            }

            /*if (!viewModel.getPageLiveData().isLoading().getValue() && totalItemCount <= lastVisibleItem + visibleThreshold) {
                //movieRecyclerViewAdapter.loadNextPage();

                viewModel.getPageLiveData().scrollerSubject.onNext(mQuery);

                switch (viewModel.getPageLiveData().getMode()) {
                    case DISCOVER_MOVIES:
                        movieRepository.discoverMovies(viewModel.getPageLiveData().getNextPage(), mQueryMap)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(viewModel.getPageLiveData());
                        break;

                    case DISCOVER_TV:
                        movieRepository.discoverTv(viewModel.getPageLiveData().getNextPage(), mQueryMap)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(viewModel.getPageLiveData());
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

                    case FILTER:
                        System.out.println("sdf");
                        movieRepository.discoverMovies(viewModel.getPageLiveData().getNextPage(), mQueryMap)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(viewModel.getPageLiveData());
                        break;
                }


            } // end if*/

        }
    }
}
