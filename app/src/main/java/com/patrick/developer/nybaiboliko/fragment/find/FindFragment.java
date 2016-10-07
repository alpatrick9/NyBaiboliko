package com.patrick.developer.nybaiboliko.fragment.find;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.patrick.developer.nybaiboliko.R;

/**
 * Created by developer on 10/7/16.
 */

public class FindFragment extends Fragment {
    protected View rootView;

    TabHost tabHost = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.find, container, false);

        setTabHostView();

        return rootView;
    }

    public void setTabHostView() {
        tabHost = (TabHost)rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("tab_result_bible");
        spec.setContent(R.id.result_bible);
        spec.setIndicator("Baiboly");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab_result_song");
        spec.setContent(R.id.result_son);
        spec.setIndicator("Fihirana");
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}
