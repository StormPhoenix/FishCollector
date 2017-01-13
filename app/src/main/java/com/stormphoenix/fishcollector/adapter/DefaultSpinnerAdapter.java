package com.stormphoenix.fishcollector.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Developer on 17-1-13.
 * Wang Cheng is a intelligent Android developer.
 */

public class DefaultSpinnerAdapter extends ArrayAdapter {
    public DefaultSpinnerAdapter(Context context, int resource) {
        super(context, resource);
    }

    public DefaultSpinnerAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public DefaultSpinnerAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public DefaultSpinnerAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public DefaultSpinnerAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public DefaultSpinnerAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
