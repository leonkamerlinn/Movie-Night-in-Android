package com.example.leon.movienightinandroid.api.moviedb.dialog;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.databinding.DialogSortFilterBinding;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import io.reactivex.functions.Consumer;

/**
 * Created by Leon on 8.2.2018..
 */

public class SortFilterDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DialogSortFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout. dialog_sort_filter, null, false);

        String[] sortItems = getResources().getStringArray(R.array.sort_items);
        RxAdapterView.itemSelections(binding.sortSpinner)
                .subscribe(integer -> {
                    Toast.makeText(getContext(), sortItems[integer], Toast.LENGTH_LONG).show();
                });

        return new AlertDialog.Builder(binding.getRoot().getContext())
                .setView(binding.getRoot())
                .setPositiveButton(R.string.apply, (dialog, id) -> {

                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> getDialog().cancel())
                .create();
    }


}