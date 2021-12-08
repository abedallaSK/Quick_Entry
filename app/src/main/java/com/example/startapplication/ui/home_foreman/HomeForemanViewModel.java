package com.example.startapplication.ui.home_foreman;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeForemanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeForemanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home213 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}