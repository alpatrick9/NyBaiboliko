package com.patrick.developer.nybaiboliko.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.inject.Inject;
import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.models.Test;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

/**
 * Created by developer on 10/4/16.
 */

public class BaibolyFragment extends RoboFragment {

    protected  View rootView;

    protected TextView test;

    @Inject
    Test m;

    public BaibolyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.baiboly_fragment, container, false);
        test = (TextView) rootView.findViewById(R.id.test);
        test.setText(m.value);
        return rootView;
    }
}
