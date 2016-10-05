package com.patrick.developer.nybaiboliko.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.inject.Inject;
import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.tools.BookManager;
import com.patrick.developer.nybaiboliko.tools.JsonParser;

import java.util.ArrayList;

import roboguice.fragment.provided.RoboFragment;

/**
 * Created by developer on 10/5/16.
 */

public class CheckVersetBibleFragment extends RoboFragment {

    protected View rootView;

    TabHost tabHost =null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.check_verset_bible_fragment, container, false);

        setTabHostView();

        new BookManager(getActivity(),rootView,tabHost).creationBoutonLivre();

        return rootView;
    }

    public void setTabHostView() {
        tabHost = (TabHost)rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("tab_livres");
        spec.setContent(R.id.livres);
        spec.setIndicator("Boky");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab_chapitres");
        spec.setContent(R.id.chapitres);
        spec.setIndicator("Toko");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab_versets");
        spec.setContent(R.id.versets);
        spec.setIndicator("Andininy");
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}