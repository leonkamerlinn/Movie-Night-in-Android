package com.example.leon.movienightinandroid;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leon.movienightinandroid.api.moviedb.MovieRecyclerViewAdapter;
import com.example.leon.movienightinandroid.api.moviedb.dialog.SortFilterDialog;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.di.component.DaggerMainActivityComponent;
import com.example.leon.movienightinandroid.di.component.MainActivityComponent;
import com.example.leon.movienightinandroid.di.module.ActivityModule;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    @Inject
    MovieRecyclerViewAdapter mMovieRecyclerViewAdapter;
    @Inject
    ActivityMainBinding binding;



    private MainActivityComponent activityComponent;
    private SearchView mSearchView;

    public MainActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerMainActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setSupportActionBar(binding.toolbar);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(false);
        binding.recyclerView.setAdapter(mMovieRecyclerViewAdapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                //adapter position of the first visible view.
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int visibleThreshold = 5;

                if (!mMovieRecyclerViewAdapter.isLoanding() && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    mMovieRecyclerViewAdapter.loadNextPage();
                }

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);


        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_LONG).show();
                getSupportActionBar().setTitle(query);
                mSearchView.setQuery(null, false);
                mSearchView.setIconified(true);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed

                return false;
            }
        });

        return true;
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
                SortFilterDialog.newInstance().show(getSupportFragmentManager(), SortFilterDialog.TAG);
                break;

            default:
                break;
        }
        return true;
    }
}
