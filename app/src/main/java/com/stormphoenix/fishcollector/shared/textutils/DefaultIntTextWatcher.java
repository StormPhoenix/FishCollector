package com.stormphoenix.fishcollector.shared.textutils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Developer on 17-1-14.
 * Wang Cheng is a intelligent Android developer.
 */

public class DefaultIntTextWatcher implements TextWatcher {
    protected int text;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        text = Integer.parseInt(s.toString().trim());
    }
}
