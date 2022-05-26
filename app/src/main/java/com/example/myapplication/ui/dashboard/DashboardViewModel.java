package com.example.myapplication.ui.dashboard;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.view.View;
import android.content.Intent;
public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Please enter the Date");
    }

    public LiveData<String> getText() {
        return mText;
    }



}