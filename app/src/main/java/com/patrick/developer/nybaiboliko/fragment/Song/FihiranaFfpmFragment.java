package com.patrick.developer.nybaiboliko.fragment.Song;

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
import com.patrick.developer.nybaiboliko.adapter.ParoleAdapter;
import com.patrick.developer.nybaiboliko.dao.FihiranaDao;
import com.patrick.developer.nybaiboliko.models.entity.Fihirana;
import com.patrick.developer.nybaiboliko.tools.GlobalVariable;
import com.patrick.developer.nybaiboliko.tools.animation.AnimationManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/7/16.
 */

public class FihiranaFfpmFragment extends Fragment {

    protected View rootView;

    protected ListView parolesListView;

    protected FihiranaDao fihiranaDao;

    protected  Fihirana fihirana;

    protected List<String> paroles = new ArrayList<>();

    protected GlobalVariable globalVariable;

    public FihiranaFfpmFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fihirana_ffpm_fragment, container, false);

        globalVariable = (GlobalVariable) getActivity().getApplicationContext();

        AnimationManager.setAnimation(getActivity(), rootView);

        Bundle bundle = this.getArguments();
        String id = bundle.getString("id");

        fihiranaDao = new FihiranaDao(getActivity());

        fihirana = fihiranaDao.findBy(id);
        setParole(fihirana);

        setView();

        ParoleAdapter adapter = new ParoleAdapter(getActivity(), paroles);
        parolesListView.setAdapter(adapter);

        setToolbar(fihirana.getTitle());

        setBackButton();

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

    public void setBackButton() {
        FloatingActionButton backButton = (FloatingActionButton)rootView.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Fragment fragment = new CheckFihiranaFragment();

                Bundle bundle = new Bundle();
                bundle.putString("type","ff");

                if(fihirana.getId().contains("FFPM")) {
                    bundle.putString("type","ffpm");
                }
                if(fihirana.getId().contains("TS")) {
                    bundle.putString("type","ts");
                }
                if(fihirana.getId().contains("AN")) {
                    bundle.putString("type","an");
                }

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
    }
}
