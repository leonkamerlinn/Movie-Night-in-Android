package com.example.leon.movienightinandroid.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leon.movienightinandroid.MainActivity;
import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.api.moviedb.dialog.TimePickerFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class SortFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_filter);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                returnIntent.putExtra("result","message");
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
}
