package com.stormphoenix.fishcollector.shared.textutils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Developer on 17-1-13.
 * Wang Cheng is a intelligent Android developer.
 */

public class DefaultFloatTextWatcher implements TextWatcher {
    protected float text;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
            text = Float.parseFloat(s.toString().trim());
    }
}
