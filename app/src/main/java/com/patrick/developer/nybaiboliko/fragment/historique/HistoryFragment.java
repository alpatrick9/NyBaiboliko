package com.patrick.developer.nybaiboliko.fragment.historique;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.j256.ormlite.table.TableUtils;
import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.adapter.HistoryBibleAdapter;
import com.patrick.developer.nybaiboliko.adapter.HistoryFihiranaAdapter;
import com.patrick.developer.nybaiboliko.dao.HistoryFihiranaDao;
import com.patrick.developer.nybaiboliko.dao.HistoryVersetDao;
import com.patrick.developer.nybaiboliko.fragment.Song.FihiranaFfpmFragment;
import com.patrick.developer.nybaiboliko.models.HistoryFihirana;
import com.patrick.developer.nybaiboliko.models.HistoryVerset;
import com.patrick.developer.nybaiboliko.tools.GlobalClass;
import com.patrick.developer.nybaiboliko.tools.TabAnimation;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by patmi on 16/10/2016.
 */
public class HistoryFragment extends Fragment {

    protected View rootView;

    protected ListView resultBibleView;

    protected ListView resultSongView;

    protected GlobalClass globalClass;

    TabHost tabHost = null;

    Integer currentTab;

    Integer tabHostId = 0;

    protected static float lastX;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.history, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();

        //globalClass.setAnimation(getActivity(), rootView);

        Bundle bundle = getArguments();
        tabHostId = bundle.getInt("tabHostId");

        setTabHostView();

        setView();

        try {
            initializeData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        tabHost.setCurrentTab(tabHostId);

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

        //TabAnimation.swipeTabHost(rootView,tabHost);
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
        HistoryFihiranaDao historyFihiranaDao = new HistoryFihiranaDao(getActivity());
        List<HistoryFihirana> historyFihiranas = historyFihiranaDao.findAllOrder();

        HistoryFihiranaAdapter fihiranaAdapter = new HistoryFihiranaAdapter(getActivity(), historyFihiranas);
        resultSongView.setAdapter(fihiranaAdapter);

        HistoryVersetDao historyVersetDao = new HistoryVersetDao(getActivity());
        List<HistoryVerset> historyVersets = historyVersetDao.findAllOrder();

        HistoryBibleAdapter vesetAdapter = new HistoryBibleAdapter(getActivity(), historyVersets);
        resultBibleView.setAdapter(vesetAdapter);
    }

}
