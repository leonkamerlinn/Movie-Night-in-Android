package com.example.leon.movienightinandroid;

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
import com.example.leon.movienightinandroid.api.moviedb.dialog.TimePickerFragment;
import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.ui.SortFilterActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity {

    public static final int REQUEST_CODE = 10;
    private SearchView mSearchView;

    @Inject
    ActivityMainBinding binding;

    @Inject
    MovieRecyclerViewAdapter mMovieRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(binding.toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(false);
        //showDatePickerDialog();

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
        menuInflater.inflate(R.menu.home_menu, menu);


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

    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        /*DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");*/

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {

                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.show(getFragmentManager(), "Datepickerdialog");
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
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no
                Toast.makeText(this, "cancel", Toast.LENGTH_LONG).show();
            }
        }
    }
}
