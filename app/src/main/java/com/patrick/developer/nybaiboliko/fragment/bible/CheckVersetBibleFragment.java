package com.patrick.developer.nybaiboliko.fragment.bible;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.tools.bible.BookManager;

/**
 * Created by developer on 10/5/16.
 */

public class CheckVersetBibleFragment extends Fragment {

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
