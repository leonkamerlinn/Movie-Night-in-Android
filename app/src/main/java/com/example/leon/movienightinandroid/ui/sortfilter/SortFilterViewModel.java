package com.example.leon.movienightinandroid.ui.sortfilter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Calendar;

/**
 * Created by Leon on 2/24/2018.
 */

public class SortFilterViewModel extends ViewModel {
    private MutableLiveData<String> mDateFrom;
    private MutableLiveData<String> mDateTo;


    public SortFilterViewModel() {
        mDateFrom = new MutableLiveData<>();
        mDateTo = new MutableLiveData<>();
;

        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.YEAR, 2000);
        past.set(Calendar.MONTH, 1);
        past.set(Calendar.DAY_OF_MONTH, 1);


        String dateFrom = String.format("%02d%02d%02d",now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH), now.get(Calendar.YEAR));
        mDateFrom.setValue(dateFrom);

        String dateTo = String.format("%02d%02d%02d",past.get(Calendar.DAY_OF_MONTH), past.get(Calendar.MONTH), past.get(Calendar.YEAR));
        mDateTo.setValue(dateTo);


    }

    public LiveData<String> dateFromLiveData() {
        return mDateFrom;
    }

    public LiveData<String> dateToLiveData() {
        return mDateTo;
    }

    public void dateFromSetText(String text, int selection) {
        mDateFrom.setValue(text);

    }

    public void dateToSetValue(String text, int selection) {
        mDateTo.setValue(text);

    }
}
