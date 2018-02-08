package com.example.leon.movienightinandroid.api.moviedb.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leon.movienightinandroid.R;

/**
 * Created by Leon on 8.2.2018..
 */

public class SortFilterDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*// Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.app_name)
                .setPositiveButton(R.string.lb_onboarding_accessibility_next, (dialog, id) -> {
                    // FIRE ZE MISSILES!
                })
                .setNegativeButton(R.string.lb_onboarding_accessibility_next, (dialog, id) -> {
                    // User cancelled the dialog
                });
        // Create the AlertDialog object and return it
        return builder.create();*/



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_sort_filter, null);
/*
        Spinner spinner = rootView.findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = getResources().getStringArray(R.array.sort_items)[position];

                Toast.makeText(getContext(), value, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton(R.string.lb_onboarding_accessibility_next, (dialog, id) -> {
                    // sign in the user ...
                })
                .setNegativeButton(R.string.lb_onboarding_accessibility_next, (dialog, id) -> getDialog().cancel());
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}