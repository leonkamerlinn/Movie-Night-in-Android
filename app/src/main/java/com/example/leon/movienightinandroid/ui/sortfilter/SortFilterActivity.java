package com.example.leon.movienightinandroid.ui.sortfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.databinding.ActivitySortFilterBinding;
import com.example.leon.movienightinandroid.utils.DateInputMask;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class SortFilterActivity extends DaggerAppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String SORT_ITEM_EXTRA = "sort_item_extra";
    public static final String GENRE_TAGS_EXTRA = "genre_tags_extra";
    public static final String DATE_FROM_EXTRA = "date_from_extra";
    public static final String DATE_TO_EXTRA = "date_to_extra";
    public static final String MOVIE_EXTRA = "movie_extra";
    public static final String VOTES_EXTRA = "votes_extra";
    public static final String RATING_EXTRA = "rating_extra";

    @Inject
    ActivitySortFilterBinding binding;
    @Inject
    SortFilterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        populateGenreCheckboxes();

        Intent intent = getIntent();

        if (intent.hasExtra(SORT_ITEM_EXTRA)) {
            viewModel.setSortItemExtra(intent.getStringExtra(SORT_ITEM_EXTRA));
        }

        if (intent.hasExtra(GENRE_TAGS_EXTRA)) {
            binding.tagLayout.setCheckedGenres(intent.getStringArrayExtra(GENRE_TAGS_EXTRA));
        }

        if (intent.hasExtra(DATE_FROM_EXTRA)) {
            viewModel.dateFromSetText(intent.getStringExtra(DATE_FROM_EXTRA));
        }

        if (intent.hasExtra(DATE_TO_EXTRA)) {
            viewModel.dateToSetText(intent.getStringExtra(DATE_TO_EXTRA));
        }

        if (intent.hasExtra(MOVIE_EXTRA)) {
            viewModel.setMovieSwitchText(intent.getBooleanExtra(MOVIE_EXTRA, false));
        }

        if (intent.hasExtra(VOTES_EXTRA)) {
            viewModel.setVotesExtra(intent.getIntExtra(VOTES_EXTRA, 0));
        }

        if (intent.hasExtra(RATING_EXTRA)) {
            viewModel.setRatingExtra(intent.getFloatExtra(RATING_EXTRA, 0));
        }


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

        RxCompoundButton.checkedChanges(binding.movieSwitch).subscribe(viewModel::setMovieSwitchText);


    }

    private void populateGenreCheckboxes() {
        String[] items = getResources().getStringArray(R.array.filter_gener_items);
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


    private void datePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
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
                returnIntent.putExtra(SORT_ITEM_EXTRA, selectedItem);

                List<String> values = binding.tagLayout.getCheckedValues();
                String[] arr = values.toArray(new String[values.size()]);
                returnIntent.putExtra(GENRE_TAGS_EXTRA, arr);

                String releaseFrom = binding.editTextDateFrom.getText().toString();
                returnIntent.putExtra(DATE_FROM_EXTRA, releaseFrom);

                String releaseTo = binding.editTextDateTo.getText().toString();
                returnIntent.putExtra(DATE_TO_EXTRA, releaseTo);

                boolean movie = binding.movieSwitch.isChecked();
                returnIntent.putExtra(MOVIE_EXTRA, movie);

                String votes = binding.numberOfVotes.getText().toString();
                returnIntent.putExtra(VOTES_EXTRA, Integer.valueOf(votes));

                float rating = binding.ratingBar.getRating();
                returnIntent.putExtra(RATING_EXTRA, rating);
                System.out.println(rating);

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


