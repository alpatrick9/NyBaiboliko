package com.patrick.developer.nybaiboliko.fragment.find;

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
import com.patrick.developer.nybaiboliko.dao.VersetDao;
import com.patrick.developer.nybaiboliko.fragment.Song.FihiranaFfpmFragment;
import com.patrick.developer.nybaiboliko.fragment.bible.BibleFragment;
import com.patrick.developer.nybaiboliko.models.entity.Verset;
import com.patrick.developer.nybaiboliko.tools.GlobalVariable;
import com.patrick.developer.nybaiboliko.tools.Tools;
import com.patrick.developer.nybaiboliko.tools.animation.AnimationManager;
import com.patrick.developer.nybaiboliko.tools.animation.TabAnimation;

/**
 * Created by developer on 10/7/16.
 */

public class FindFragment extends Fragment {

    protected View rootView;

    protected EditText findEditText;

    protected ImageButton goFindButton;

    protected ListView resultBibleView;

    protected ListView resultSongView;

    protected GlobalVariable globalVariable;

    protected String keyWord;

    TabHost tabHost = null;

    Integer currentTab;

    ProgressDialog myProgressDialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.find, container, false);

        globalVariable = (GlobalVariable) getActivity().getApplicationContext();

        AnimationManager.setAnimation(getActivity(), rootView);

        setTabHostView();

        setView();

        initializeData();

        findData();

        addlistener();

        return rootView;
    }

    public void setTabHostView() {
        tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("tab_result_bible");
        spec.setContent(R.id.result_bible);
        spec.setIndicator("Baiboly");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab_result_song");
        spec.setContent(R.id.result_son);
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
        findEditText = (EditText) rootView.findViewById(R.id.key_word);
        int widthScreen = new Tools(getActivity()).getWidthSreenSize();
        if (750 > widthScreen) {
            ViewGroup.LayoutParams layoutParams = findEditText.getLayoutParams();
            layoutParams.width = widthScreen - 150;
            findEditText.setLayoutParams(layoutParams);
        }

        goFindButton = (ImageButton) rootView.findViewById(R.id.find);

        resultBibleView = (ListView) rootView.findViewById(R.id.result_bible_view);

        resultSongView = (ListView) rootView.findViewById(R.id.result_song_view);

        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.find_title);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
    }

    public void findData() {
        goFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager gestionClavier = (InputMethodManager) getActivity().getSystemService(
                        getActivity().INPUT_METHOD_SERVICE);
                //Fermer clavier virtuel
                gestionClavier.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                keyWord = findEditText.getText().toString();

                final Handler handler = new Handler();

                final Runnable initialize = new Runnable() {
                    @Override
                    public void run() {
                        initializeData();
                    }
                };

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (!keyWord.isEmpty()) {

                            globalVariable.keyWord = keyWord;

                            VersetDao versetDao = new VersetDao(getActivity());
                            globalVariable.resultFindVerset = versetDao.findByKeyWord(keyWord.replace("'","''"));

                            FihiranaDao fihiranaDao = new FihiranaDao(getActivity());
                            globalVariable.resultFindFihirana = fihiranaDao.findByKeyWord(keyWord.replace("'","''"));
                        }
                        myProgressDialog.dismiss();
                        /**********************************/
                        handler.post(initialize);
                    }
                };

                myProgressDialog = ProgressDialog.show(getActivity(), "", "Miandrasa kely azafady ...", true);
                thread.start();

            }
        });
    }

    public void initializeData() {
        FindFihiranaAdapter fihiranaAdapter = new FindFihiranaAdapter(getActivity(), globalVariable.resultFindFihirana);
        resultSongView.setAdapter(fihiranaAdapter);

        FindVesetAdapter vesetAdapter = new FindVesetAdapter(getActivity(), globalVariable.resultFindVerset);
        resultBibleView.setAdapter(vesetAdapter);
    }

    public void addlistener() {
        resultSongView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Fragment fragment = new FihiranaFfpmFragment();

                Bundle bundle = new Bundle();
                bundle.putString("id", globalVariable.resultFindFihirana.get(i).getId());

                fragment.setArguments(bundle);
                changeFragment(fragment);

            }
        });

        resultBibleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Verset verset = globalVariable.resultFindVerset.get(i);

                globalVariable.bookRef.bookTitle = verset.getBook();
                globalVariable.bookRef.chapitre = verset.getChapitreNumber();
                globalVariable.bookRef.versetStart = verset.getVersetNumber();
                globalVariable.bookRef.versetLast = verset.getVersetNumber();

                Fragment fragment = new BibleFragment();
                changeFragment(fragment);
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
