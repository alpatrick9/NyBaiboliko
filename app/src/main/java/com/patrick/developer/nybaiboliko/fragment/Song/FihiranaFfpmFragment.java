package com.patrick.developer.nybaiboliko.fragment.Song;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.dao.FihiranaDao;
import com.patrick.developer.nybaiboliko.models.Fihirana;

import java.util.ArrayList;
import java.util.List;

import static com.patrick.developer.nybaiboliko.R.id.toolbar;

/**
 * Created by developer on 10/7/16.
 */

public class FihiranaFfpmFragment extends Fragment {

    protected View rootView;

    protected TextView paroleTextView;

    protected FihiranaDao fihiranaDao;

    private InputMethodManager gestionClavier = null;

    public FihiranaFfpmFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fihirana_ffpm_fragment, container, false);

        Bundle bundle = this.getArguments();
        String id = bundle.getString("id");

        gestionClavier = (InputMethodManager)getActivity().getSystemService(
                getActivity().INPUT_METHOD_SERVICE);
        //Fermer clavier virtuel
        gestionClavier.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);

        fihiranaDao = new FihiranaDao(getActivity());

        Fihirana fihirana = fihiranaDao.findBy(id);

        setView();

        setToolbar(fihirana.getTitle());

        paroleTextView.setText(Html.fromHtml(fihirana.getDescription()));

        return rootView;
    }

    public void setView() {

        paroleTextView = (TextView) rootView.findViewById(R.id.parole);
    }

    public void setToolbar(String title) {

        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
    }
}
