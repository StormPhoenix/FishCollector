package com.stormphoenix.fishcollector.shared.textutils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Developer on 17-1-13.
 * Wang Cheng is a intelligent Android developer.
 */

public class DefaultTextWatcher implements TextWatcher {
    protected String text;
    protected EditText editText;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        text = s.toString().trim();
    }
}
