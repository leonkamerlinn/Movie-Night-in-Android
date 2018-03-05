package com.example.leon.movienightinandroid.ui.sortfilter;

import android.content.Intent;
import android.os.Bundle;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class SortFilterActivity extends DaggerAppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String GENRE_TAGS_EXTRA = "genre_tags_extra";

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
        if (intent.hasExtra(GENRE_TAGS_EXTRA)) {
            binding.tagLayout.setCheckedGenres(intent.getStringArrayExtra(GENRE_TAGS_EXTRA));
        }


        new DateInputMask(binding.editTextDateFrom, (text, selection) -> viewModel.dateFromSetText(text, selection));
        new DateInputMask(binding.editTextDateTo, (text, selection) -> viewModel.dateToSetValue(text, selection));
        binding.bottomLinearLayout.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        binding.bottomLinearLayout.setFocusableInTouchMode(true);



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
                List<String> values = binding.tagLayout.getCheckedValues();
                String[] arr = values.toArray(new String[values.size()]);
                returnIntent.putExtra(GENRE_TAGS_EXTRA, arr);
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


