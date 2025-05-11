package com.tfg.vitalfit.utils;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.core.util.Consumer;

public abstract class SimpleTextWatcher implements TextWatcher {
    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override public void afterTextChanged(Editable s) {}
}
