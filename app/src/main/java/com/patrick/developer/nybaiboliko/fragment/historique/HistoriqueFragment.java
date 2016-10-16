package com.patrick.developer.nybaiboliko.fragment.historique;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.adapter.FindFihiranaAdapter;
import com.patrick.developer.nybaiboliko.adapter.FindVesetAdapter;
import com.patrick.developer.nybaiboliko.dao.FihiranaDao;
import com.patrick.developer.nybaiboliko.dao.HistoriqueFihiranaDao;
import com.patrick.developer.nybaiboliko.dao.VersetDao;
import com.patrick.developer.nybaiboliko.fragment.Song.FihiranaFfpmFragment;
import com.patrick.developer.nybaiboliko.fragment.bible.BibleFragment;
import com.patrick.developer.nybaiboliko.models.Fihirana;
import com.patrick.developer.nybaiboliko.models.HistoriqueFihirana;
import com.patrick.developer.nybaiboliko.models.Verset;
import com.patrick.developer.nybaiboliko.tools.GlobalClass;
import com.patrick.developer.nybaiboliko.tools.TabAnimation;
import com.patrick.developer.nybaiboliko.tools.Tools;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by patmi on 16/10/2016.
 */
public class HistoriqueFragment extends Fragment {

    protected View rootView;

    protected ListView resultBibleView;

    protected ListView resultSongView;

    protected GlobalClass globalClass;

    TabHost tabHost = null;

    Integer currentTab;

    public HistoriqueFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.historique, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();

        globalClass.setAnimation(getActivity(), rootView);

        setTabHostView();

        setView();

        try {
            initializeData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addlistener();

        return rootView;
    }

    public void setTabHostView() {
        tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("tab_result_bible");
        spec.setContent(R.id.story_bible);
        spec.setIndicator("Baiboly");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab_result_song");
        spec.setContent(R.id.story_song);
        spec.setIndicator("Fihirana");
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

        currentTab = tabHost.getCurrentTab();

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                View currentView = tabHost.getCurrentView();
                if (tabHost.getCurrentTab() > currentTab)
                {
                    currentView.setAnimation(TabAnimation.inFromRightAnimation() );
                }
                else
                {
                    currentView.setAnimation( TabAnimation.outToRightAnimation() );
                }

                currentTab = tabHost.getCurrentTab();
            }
        });
    }

    public void setView() {

        resultBibleView = (ListView) rootView.findViewById(R.id.result_bible_view);

        resultSongView = (ListView) rootView.findViewById(R.id.result_song_view);

        ListView menuElementsList = (ListView) getActivity().findViewById(R.id.menu_elements);
        menuElementsList.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));

        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.story_title);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
    }

    public void initializeData() throws SQLException {
        HistoriqueFihiranaDao historiqueFihiranaDao = new HistoriqueFihiranaDao(getActivity());
        System.out.println("nombre historique "+historiqueFihiranaDao.countRow());
        /*FindFihiranaAdapter fihiranaAdapter = new FindFihiranaAdapter(getActivity(), globalClass.getResultFindFihirana());
        resultSongView.setAdapter(fihiranaAdapter);

        /*FindVesetAdapter vesetAdapter = new FindVesetAdapter(getActivity(), globalClass.getResultFindVerset());
        resultBibleView.setAdapter(vesetAdapter);*/
    }

    public void addlistener() {
        resultSongView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Fragment fragment = new FihiranaFfpmFragment();

                Bundle bundle = new Bundle();
                bundle.putString("id",globalClass.getResultFindFihirana().get(i).getId());

                fragment.setArguments(bundle);
                changeFragment(fragment);

            }
        });

        resultBibleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /*Verset verset = globalClass.getResultFindVerset().get(i);

                globalClass.setBookTitle(verset.getBook());
                globalClass.setChapitre(verset.getChapitreNumber());
                globalClass.setversetFirst(verset.getVersetNumber());
                globalClass.setversetLast(verset.getVersetNumber());

                Fragment fragment = new BibleFragment();
                changeFragment(fragment);*/
            }
        });
    }

    public void changeFragment(Fragment fragment) {
        if (fragment != null) {
            RelativeLayout maLayout = (RelativeLayout) getActivity().findViewById(R.id.contenaire);
            maLayout.removeAllViews();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenaire, fragment).commit();
        }
    }
}
