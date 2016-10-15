package com.patrick.developer.nybaiboliko.fragment.bible;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.adapter.VersetsAdapter;
import com.patrick.developer.nybaiboliko.dao.VersetDao;
import com.patrick.developer.nybaiboliko.models.Verset;
import com.patrick.developer.nybaiboliko.tools.GlobalClass;
import com.patrick.developer.nybaiboliko.tools.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/4/16.
 */

public class BibleFragment extends Fragment {

    protected  View rootView;

    protected ListView versetsListView;

    protected VersetDao versetDao;

    protected List<Verset> versets = new ArrayList<>();

    protected GlobalClass globalClass;

    Toolbar toolbar;

    public BibleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.baiboly_fragment, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();

        globalClass.setAnimation(getActivity(), rootView);

        versetDao = new VersetDao(getActivity());

        setView();

        versets = versetDao.findBy(globalClass.getBookTitle(),globalClass.getChapitre(),globalClass.getversetFirst(),globalClass.getversetLast());

        setData();

        setBackButton();

        showAll();

        return rootView;
    }

    public void setView() {
        versetsListView = (ListView) rootView.findViewById(R.id.versets_item);

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);

        String titleToolbar = globalClass.getBookTitle() + " "+ globalClass.getChapitre()+": "+globalClass.getversetFirst()+"-"+globalClass.getversetLast();

        if(globalClass.getversetLast()-globalClass.getversetFirst() == 0) {
            titleToolbar = globalClass.getBookTitle() + " "+ globalClass.getChapitre()+": "+globalClass.getversetFirst();
        }

        toolbar.setTitle(titleToolbar);
    }

    public void setBackButton() {
        FloatingActionButton backButton = (FloatingActionButton)rootView.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckVersetBibleFragment fragment = new CheckVersetBibleFragment();
                if (fragment != null) {
                    RelativeLayout maLayout = (RelativeLayout) getActivity().findViewById(R.id.contenaire);
                    maLayout.removeAllViews();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.contenaire, fragment).commit();
                }
            }
        });
    }

    public void showAll() {
        FloatingActionButton allButton = (FloatingActionButton)rootView.findViewById(R.id.all);
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                versets = versetDao.findByBookAndChap(globalClass.getBookTitle(),globalClass.getChapitre());
                toolbar.setTitle(globalClass.getBookTitle() + " "+ globalClass.getChapitre());
                setData();
            }
        });
    }

    public void setData() {
        VersetsAdapter adapter = new VersetsAdapter(getActivity(),versets);
        versetsListView.setAdapter(adapter);
    }
}