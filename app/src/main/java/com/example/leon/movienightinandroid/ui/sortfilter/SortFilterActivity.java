package com.example.leon.movienightinandroid.ui.sortfilter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.databinding.ActivitySortFilterBinding;

import java.util.List;

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

        String[] items = getResources().getStringArray(R.array.filter_gener_items);
        for(String item: items) {
            CheckBox checkBox  = (CheckBox) LayoutInflater.from(this).inflate(R.layout.checkbox_item, null, false);
            checkBox.setText(item);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            checkBox.setLayoutParams(params);
            binding.tagLayout.addView(checkBox);

        }
        Intent intent = getIntent();
        if (intent.hasExtra("values")) {
            binding.tagLayout.setCheckedGenres(intent.getStringArrayExtra("values"));
        }



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
                returnIntent.putExtra("result", arr);
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
