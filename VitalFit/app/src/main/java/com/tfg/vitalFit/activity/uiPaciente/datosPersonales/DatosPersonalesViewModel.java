package com.tfg.vitalfit.activity.uiPaciente.datosPersonales;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DatosPersonalesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DatosPersonalesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}