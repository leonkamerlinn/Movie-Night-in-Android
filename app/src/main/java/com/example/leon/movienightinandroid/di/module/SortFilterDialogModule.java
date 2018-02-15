package com.example.leon.movienightinandroid.di.module;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;

import com.example.leon.movienightinandroid.R;
import com.example.leon.movienightinandroid.api.moviedb.dialog.SortFilterDialog;
import com.example.leon.movienightinandroid.databinding.DialogSortFilterBinding;
import com.example.leon.movienightinandroid.di.qualifier.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 2.2.2018..
 */

@Module(includes = {MainActivityModule.class})
public abstract class SortFilterDialogModule {

    @Provides
    static Context provideContext(SortFilterDialog sortFilterDialog) {
        return sortFilterDialog.getContext();
    }

    @Provides
    static DialogSortFilterBinding provideDialogSortFilterBinding(Context context) {
        DialogSortFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout. dialog_sort_filter, null, false);
        return binding;
    }
}
