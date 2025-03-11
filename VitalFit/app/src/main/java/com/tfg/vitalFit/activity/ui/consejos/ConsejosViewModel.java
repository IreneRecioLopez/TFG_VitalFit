package com.tfg.vitalfit.activity.ui.consejos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConsejosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ConsejosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}