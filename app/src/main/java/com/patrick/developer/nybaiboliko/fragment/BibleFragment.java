package com.patrick.developer.nybaiboliko.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.inject.Inject;
import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.adapter.VersetsAdapter;
import com.patrick.developer.nybaiboliko.dao.VersetDao;
import com.patrick.developer.nybaiboliko.models.Verset;
import com.patrick.developer.nybaiboliko.tools.GlobalClass;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.provided.RoboFragment;

/**
 * Created by developer on 10/4/16.
 */

public class BibleFragment extends RoboFragment {

    protected  View rootView;

    protected ListView versetsListView;

    protected VersetDao versetDao;

    protected List<Verset> versets = new ArrayList<>();

    protected GlobalClass globalClass;
    public BibleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.baiboly_fragment, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        versetDao = new VersetDao(getActivity());
        setView();
        versets = versetDao.findBy(globalClass.getBookTitle(),globalClass.getChapitre(),globalClass.getversetFirst(),globalClass.getversetLast());
        VersetsAdapter adapter = new VersetsAdapter(getActivity(),versets);
        versetsListView.setAdapter(adapter);
        return rootView;
    }

    public void setView() {
        versetsListView = (ListView) rootView.findViewById(R.id.versets_item);
    }
}