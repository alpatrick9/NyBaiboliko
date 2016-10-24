package com.patrick.developer.nybaiboliko.fragment.Song;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.adapter.FihiranaAdapter;
import com.patrick.developer.nybaiboliko.dao.FihiranaDao;
import com.patrick.developer.nybaiboliko.dao.HistoryFihiranaDao;
import com.patrick.developer.nybaiboliko.models.entity.Fihirana;
import com.patrick.developer.nybaiboliko.models.entity.HistoryFihirana;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/7/16.
 */

public class CheckFihiranaFragment extends Fragment {

    protected View rootView;

    protected ListView songListView;

    protected EditText filtreEditText;

    protected List<Fihirana> songs = new ArrayList<>();

    protected FihiranaDao fihiranaDao;

    private InputMethodManager gestionClavier = null;

    private String type = "";

    public CheckFihiranaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fihirana_fragment, container, false);

        gestionClavier = (InputMethodManager)getActivity().getSystemService(
                getActivity().INPUT_METHOD_SERVICE);

        Bundle bundle = this.getArguments();
        type = bundle.getString("type");

        fihiranaDao = new FihiranaDao(getActivity());
        getRessource();

        setView();

        setToolbar();

        setFiltre();

        FihiranaAdapter adapter = new FihiranaAdapter(getActivity(), songs);
        songListView.setAdapter(adapter);
        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                gestionClavier = (InputMethodManager)getActivity().getSystemService(
                        getActivity().INPUT_METHOD_SERVICE);
                //Fermer clavier virtuel
                gestionClavier.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);

                addHistory(songs.get(i));

                Fragment fragment = new FihiranaFfpmFragment();

                Bundle bundle = new Bundle();
                bundle.putString("id",songs.get(i).getId());

                fragment.setArguments(bundle);

                if (fragment != null) {
                    RelativeLayout maLayout = (RelativeLayout) getActivity().findViewById(R.id.contenaire);
                    maLayout.removeAllViews();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.contenaire, fragment).commit();
                }
            }
        });

        return rootView;
    }

    public void getRessource() {
        switch (type) {
            case "ffpm":
                songs = fihiranaDao.findAllTitleFFPM();
                break;
            case "ff":
                songs = fihiranaDao.findAllTitleFF();
                break;
            case "ts":
                songs = fihiranaDao.findAllTitleTS();
                break;
            case "an":
                songs = fihiranaDao.findAllTitleAN();
                break;
        }
    }

    public void getRessource(Integer filtre) {
        switch (type) {
            case "ffpm":
                songs = fihiranaDao.findAllTitleFFPM(filtre);
                break;
            case "ff":
                songs = fihiranaDao.findAllTitleFF(filtre);
                break;
            case "ts":
                songs = fihiranaDao.findAllTitleTS(filtre);
                break;
            case "an":
                songs = fihiranaDao.findAllTitleAN(filtre);
                break;
        }
    }

    public void setView() {

        songListView = (ListView)rootView.findViewById(R.id.song_item);

        filtreEditText = (EditText)rootView.findViewById(R.id.fihirana_filtre);

    }

    public void setToolbar() {

        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));

        switch (type) {
            case "ffpm":
                toolbar.setTitle(getActivity().getResources().getString(R.string.ffpm_section));
                break;
            case "ff":
                toolbar.setTitle(getActivity().getResources().getString(R.string.ff_section));
                break;
            case "ts":
                toolbar.setTitle(getActivity().getResources().getString(R.string.ts_section));
                break;
            case "an":
                toolbar.setTitle(getActivity().getResources().getString(R.string.an_section));
                break;
        }
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
    }

    public void setFiltre() {
        filtreEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String stringValue = filtreEditText.getText().toString();
                if(!stringValue.isEmpty()) {
                    Integer filtre = Integer.parseInt(stringValue) ;
                    getRessource(filtre);
                    FihiranaAdapter adapter = new FihiranaAdapter(getActivity(), songs);
                    songListView.setAdapter(adapter);
                } else {
                    getRessource();
                    FihiranaAdapter adapter = new FihiranaAdapter(getActivity(), songs);
                    songListView.setAdapter(adapter);
                    //Fermer clavier virtuel
                    gestionClavier.hideSoftInputFromWindow(filtreEditText.getWindowToken(),0);
                }
            }
        });
    }

    protected void addHistory(Fihirana fihirana) {
        HistoryFihiranaDao historyFihiranaDao = new HistoryFihiranaDao(getActivity());

        HistoryFihirana historyFihirana = new HistoryFihirana(fihirana.getId(),fihirana.getTitle());
        try {
            historyFihiranaDao.create(historyFihirana);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
