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
import com.patrick.developer.nybaiboliko.adapter.ParoleAdapter;
import com.patrick.developer.nybaiboliko.dao.FihiranaDao;
import com.patrick.developer.nybaiboliko.models.Fihirana;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.patrick.developer.nybaiboliko.R.id.toolbar;

/**
 * Created by developer on 10/7/16.
 */

public class FihiranaFfpmFragment extends Fragment {

    protected View rootView;

    protected ListView parolesListView;

    protected FihiranaDao fihiranaDao;

    private InputMethodManager gestionClavier = null;

    protected List<String> paroles = new ArrayList<>();

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
        setParole(fihirana);

        setView();

        ParoleAdapter adapter = new ParoleAdapter(getActivity(), paroles);
        parolesListView.setAdapter(adapter);

        setToolbar(fihirana.getTitle());

        return rootView;
    }

    public void setView() {

        parolesListView = (ListView) rootView.findViewById(R.id.paroles);
    }

    public void setToolbar(String title) {
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
    }

    public void setParole(Fihirana fihirana) {
        try {
            JSONArray array = new JSONArray(fihirana.getDescription());
            for(int i = 0; i < array.length(); i++) {
                paroles.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
