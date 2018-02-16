package com.example.leon.movienightinandroid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.databinding.ActivitySortFilterBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class SortFilterActivity extends DaggerAppCompatActivity {
    @Inject
    ActivitySortFilterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setSupportActionBar(binding.toolbar);
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
