package com.patrick.developer.nybaiboliko.fragment.find;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.tools.Tools;

/**
 * Created by developer on 10/7/16.
 */

public class FindFragment extends Fragment {

    protected View rootView;

    protected EditText findEditText;

    TabHost tabHost = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.find, container, false);

        setTabHostView();

        setView();

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

    public void setView() {
        findEditText = (EditText)rootView.findViewById(R.id.key_word);
        int widthScreen = new Tools(getActivity()).getWidthSreenSize();
        if(590 > widthScreen) {
            ViewGroup.LayoutParams layoutParams = findEditText.getLayoutParams();
            layoutParams.width = widthScreen - 100;
            findEditText.setLayoutParams(layoutParams);
        }

    }
}
