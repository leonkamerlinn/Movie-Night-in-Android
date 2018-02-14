package com.example.leon.movienightinandroid.api.moviedb.dialog;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.databinding.DialogSortFilterBinding;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import dagger.android.support.DaggerAppCompatDialogFragment;

/**
 * Created by Leon on 8.2.2018..
 */


public class SortFilterDialog extends DaggerAppCompatDialogFragment {
    public static final String TAG = SortFilterDialog.class.getSimpleName();



    public SortFilterDialog() {
    }

    public static SortFilterDialog newInstance() {
        return new SortFilterDialog();
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DialogSortFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout. dialog_sort_filter, null, false);




        String[] sortItems = getResources().getStringArray(R.array.sort_items);
        RxAdapterView.itemSelections(binding.sortSpinner)
                .subscribe(integer -> {

                });



        return new AlertDialog.Builder(binding.getRoot().getContext())
                .setView(binding.getRoot())
                .setPositiveButton(R.string.apply, (dialog, id) -> {

                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> getDialog().cancel())
                .create();

    }






}