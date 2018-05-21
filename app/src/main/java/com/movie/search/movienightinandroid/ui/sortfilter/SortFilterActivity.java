package com.movie.search.movienightinandroid.ui.sortfilter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.movie.search.movienightinandroid.R;
import com.movie.search.movienightinandroid.api.moviedb.model.Filter;
import com.movie.search.movienightinandroid.databinding.ActivitySortFilterBinding;
import com.movie.search.movienightinandroid.utils.DateInputMask;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class SortFilterActivity extends DaggerAppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String FILTER_EXTRA = "filter_extra";
    private static final String TAG = SortFilterActivity.class.getSimpleName();

    @Inject
    ActivitySortFilterBinding binding;
    @Inject
    SortFilterViewModel viewModel;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        populateGenreCheckboxes();


        new DateInputMask(binding.editTextDateFrom, (text, selection) -> {
            binding.editTextDateFrom.setText(text);
            binding.editTextDateFrom.setSelection(selection);
        });
        new DateInputMask(binding.editTextDateTo, (text, selection) -> {
            binding.editTextDateTo.setText(text);
            binding.editTextDateTo.setSelection(selection);
        });



        binding.bottomLinearLayout.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        binding.bottomLinearLayout.setFocusableInTouchMode(true);

        RxCompoundButton.checkedChanges(binding.movieSwitch)
                .skipInitialValue()
                .subscribe(viewModel::setMovie);

        Intent intent = getIntent();
        if (intent.hasExtra(FILTER_EXTRA)) {
            Filter filter = intent.getParcelableExtra(FILTER_EXTRA);

            viewModel.setMovie(filter.isMovie());
            viewModel.setRatingExtra(filter.getRating());
            viewModel.dateToSetText(filter.getDateTo(false));
            viewModel.dateFromSetText(filter.getDateFrom(false));
            viewModel.setGenres(filter.getGenres());
            viewModel.setSortItemExtra(filter.getSortBy());
            viewModel.setVotesExtra(filter.getVotes());
        }


    }

    private void populateGenreCheckboxes() {
        String[] items = getResources().getStringArray(R.array.filter_genre_items);
        for(String item: items) {
            CheckBox checkBox  = (CheckBox) LayoutInflater.from(this).inflate(R.layout.checkbox_item, null, false);
            checkBox.setText(item);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            checkBox.setLayoutParams(params);
            binding.tagLayout.addView(checkBox);
        }
    }

    private void setupActionBar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.filter_title));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent returnIntent = new Intent();
        switch (item.getItemId()) {
            case R.id.menu_item_done:


                String selectedItem = binding.sortSpinner.getSelectedItem().toString();

                List<String> values = binding.tagLayout.getCheckedValues();
                String[] arr = values.toArray(new String[values.size()]);

                String releaseFrom = binding.editTextDateFrom.getText().toString();
                String releaseTo = binding.editTextDateTo.getText().toString();
                boolean movie = binding.movieSwitch.isChecked();
                String votes = binding.numberOfVotes.getText().toString();
                float rating = binding.ratingBar.getRating();

                Filter filter = new Filter(selectedItem, arr, releaseFrom, releaseTo, movie, Integer.valueOf(votes), (int) rating);

                returnIntent.putExtra(FILTER_EXTRA, filter);
                setResult(RESULT_OK, returnIntent);
                finish();
                break;

            default:
                setResult(RESULT_CANCELED, returnIntent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }



}


