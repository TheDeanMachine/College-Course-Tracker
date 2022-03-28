package com.VFeskin.collegecoursetracker.Utility;

import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.textfield.TextInputLayout;


/**
 * This class is a custom TextWatcher, used to implement a single method.
 * onTextChanged, clears any setError on text field.
 */
public class CustomTextWatcher implements TextWatcher {

    TextInputLayout textInputLayout;

    public CustomTextWatcher(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textInputLayout.setError(null);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
