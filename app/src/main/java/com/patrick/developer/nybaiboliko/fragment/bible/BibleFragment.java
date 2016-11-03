package com.patrick.developer.nybaiboliko.fragment.bible;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.patrick.developer.nybaiboliko.MainActivity;
import com.patrick.developer.nybaiboliko.R;
import com.patrick.developer.nybaiboliko.adapter.FindVesetAdapter;
import com.patrick.developer.nybaiboliko.adapter.VersetsAdapter;
import com.patrick.developer.nybaiboliko.dao.VersetDao;
import com.patrick.developer.nybaiboliko.fragment.Song.CheckFihiranaFragment;
import com.patrick.developer.nybaiboliko.models.ebean.BookRef;
import com.patrick.developer.nybaiboliko.models.entity.Verset;
import com.patrick.developer.nybaiboliko.tools.GlobalVariable;
import com.patrick.developer.nybaiboliko.tools.Tools;
import com.patrick.developer.nybaiboliko.tools.animation.AnimationManager;
import com.patrick.developer.nybaiboliko.tools.animation.TabAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 10/4/16.
 */

public class BibleFragment extends Fragment {

    protected  View rootView;

    protected ListView versetsListView;

    protected ListView versetsListView2;

    protected ListView versetsListView3;

    protected VersetDao versetDao;

    protected List<Verset> versets = new ArrayList<>();

    protected List<Verset> versets2 = new ArrayList<>();

    protected List<Verset> versets3 = new ArrayList<>();

    protected GlobalVariable globalVariable;

    Toolbar toolbar;

    TabHost tabHost = null;

    Integer currentTab;

    CallbackManager callbackManager;

    public BibleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.baiboly_fragment, container, false);

        globalVariable = (GlobalVariable) getActivity().getApplicationContext();

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        AppEventsLogger.activateApp(getActivity());

        callbackManager = CallbackManager.Factory.create();

        AnimationManager.setAnimation(getActivity(), rootView);

        versetDao = new VersetDao(getActivity());

        setView();

        setTabHostView();

        setToolbarTitle();

        versets = versetDao.findBy(globalVariable.bookRef.bookTitle, globalVariable.bookRef.chapitre, globalVariable.bookRef.versetStart, globalVariable.bookRef.versetLast);

        switch (globalVariable.nbBook) {
            case 1:
                versets2 = versetDao.findBy(globalVariable.bookRef1.bookTitle, globalVariable.bookRef1.chapitre, globalVariable.bookRef1.versetStart, globalVariable.bookRef1.versetLast);
                break;
            case 2:
                versets2 = versetDao.findBy(globalVariable.bookRef1.bookTitle, globalVariable.bookRef1.chapitre, globalVariable.bookRef1.versetStart, globalVariable.bookRef1.versetLast);
                versets3 = versetDao.findBy(globalVariable.bookRef2.bookTitle, globalVariable.bookRef2.chapitre, globalVariable.bookRef2.versetStart, globalVariable.bookRef2.versetLast);
                break;
        }

        setData();

        setListener();

        inflateMenuToolBar();

        return rootView;
    }

    public void setView() {

        tabHost = (TabHost)rootView.findViewById(R.id.tabHost);

        versetsListView = (ListView) rootView.findViewById(R.id.versets_item);

        versetsListView2 = (ListView) rootView.findViewById(R.id.versets_item_2);

        versetsListView3 = (ListView) rootView.findViewById(R.id.versets_item_3);

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
    }

    public void setTabHostView() {
        tabHost.setup();
        Tools tools = new Tools(getActivity());

        String indicatorTab1 = tools.formatTitleBookToView(globalVariable.bookRef.bookTitle) + " "+ globalVariable.bookRef.chapitre+": "+ globalVariable.bookRef.versetStart+"-"+ globalVariable.bookRef.versetLast;
        TabHost.TabSpec spec = tabHost.newTabSpec("first_tab");
        spec.setContent(R.id.versets_item);
        spec.setIndicator(indicatorTab1);
        tabHost.addTab(spec);

        switch (globalVariable.nbBook) {
            case 1:
                String indicatorTab2 = tools.formatTitleBookToView(globalVariable.bookRef1.bookTitle) + " "+ globalVariable.bookRef1.chapitre+": "+ globalVariable.bookRef1.versetStart+"-"+ globalVariable.bookRef1.versetLast;
                spec = tabHost.newTabSpec("second_tab");
                spec.setContent(R.id.versets_item_2);
                spec.setIndicator(indicatorTab2);
                tabHost.addTab(spec);
                break;
            case 2:
                indicatorTab2 = tools.formatTitleBookToView(globalVariable.bookRef1.bookTitle) + " "+ globalVariable.bookRef1.chapitre+": "+ globalVariable.bookRef1.versetStart+"-"+ globalVariable.bookRef1.versetLast;
                spec = tabHost.newTabSpec("second_tab");
                spec.setContent(R.id.versets_item_2);
                spec.setIndicator(indicatorTab2);
                tabHost.addTab(spec);

                String indicatorTab3 = tools.formatTitleBookToView(globalVariable.bookRef2.bookTitle) + " "+ globalVariable.bookRef2.chapitre+": "+ globalVariable.bookRef2.versetStart+"-"+ globalVariable.bookRef2.versetLast;
                spec = tabHost.newTabSpec("third_tab");
                spec.setContent(R.id.versets_item_3);
                spec.setIndicator(indicatorTab3);
                tabHost.addTab(spec);
                break;
        }

        tabHost.setCurrentTab(globalVariable.numTabBook);

        currentTab = tabHost.getCurrentTab();

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                View currentView = tabHost.getCurrentView();

                setToolbarTitle();

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

    private void setToolbarTitle() {
        toolbar.setTitle(getTitle());
    }

    public void setData() {
        VersetsAdapter adapter = new VersetsAdapter(getActivity(),versets);
        versetsListView.setAdapter(adapter);

        VersetsAdapter adapter2 = new VersetsAdapter(getActivity(), versets2);
        versetsListView2.setAdapter(adapter2);

        VersetsAdapter adapter3 = new VersetsAdapter(getActivity(), versets3);
        versetsListView3.setAdapter(adapter3);
    }

    private void setListener() {

        setBackButton();

        showAll();

    }

    public void setBackButton() {

        FloatingActionButton backButton = (FloatingActionButton)rootView.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toolbar.getMenu().clear();

                CheckVersetBibleFragment fragment = new CheckVersetBibleFragment();

                globalVariable.numTabBook = tabHost.getCurrentTab();

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
                String titleToolbar = "";
                switch (tabHost.getCurrentTab()) {
                    case 0:
                        versets = versetDao.findByBookAndChap(globalVariable.bookRef.bookTitle, globalVariable.bookRef.chapitre);
                        titleToolbar = globalVariable.bookRef.bookTitle + " "+ globalVariable.bookRef.chapitre;
                        break;
                    case 1:
                        versets2 = versetDao.findByBookAndChap(globalVariable.bookRef1.bookTitle, globalVariable.bookRef1.chapitre);
                        titleToolbar = globalVariable.bookRef1.bookTitle + " "+ globalVariable.bookRef1.chapitre;
                        break;
                    case 2:
                        versets3 = versetDao.findByBookAndChap(globalVariable.bookRef2.bookTitle, globalVariable.bookRef2.chapitre);
                        titleToolbar = globalVariable.bookRef2.bookTitle + " "+ globalVariable.bookRef2.chapitre;
                        break;
                }

                TextView title = (TextView) tabHost.getTabWidget().getChildTabViewAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
                title.setText(titleToolbar);

                toolbar.setTitle(titleToolbar);
                setData();
            }
        });
    }

    private void inflateMenuToolBar() {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.open_other:
                        if(globalVariable.nbBook == 2) {
                            Toast.makeText(getActivity(),"Telo ihany ny isan\'ny boky afaka sokafana",Toast.LENGTH_LONG).show();
                        } else {
                            globalVariable.nbBook++;
                            globalVariable.numTabBook = globalVariable.nbBook;
                            CheckVersetBibleFragment fragment =new CheckVersetBibleFragment();
                            if (fragment != null) {
                                RelativeLayout maLayout = (RelativeLayout) getActivity().findViewById(R.id.contenaire);
                                maLayout.removeAllViews();
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.contenaire, fragment).commit();
                            }
                        }
                        break;
                    case R.id.share_fb:
                        if(new Tools(getActivity()).isConnected()) {

                            ShareDialog shareDialog = new ShareDialog(getActivity());

                            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                                @Override
                                public void onSuccess(Sharer.Result result) {
                                }

                                @Override
                                public void onCancel() {

                                }

                                @Override
                                public void onError(FacebookException error) {
                                }
                            });

                            if(ShareDialog.canShow(ShareLinkContent.class)) {
                                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                        .setContentUrl(Uri.parse(getUrl()))
                                        .setContentTitle(getTitle())
                                        .setContentDescription(toStringVerset())
                                        .setImageUrl(Uri.parse("http://nybaiboliko.esy.es/web/images/nybaiboliko.png"))
                                        .build();
                                shareDialog.show(linkContent);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Tsy mandeha ny internet-nao azafady!",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.share_other:
                        if(new Tools(getActivity()).isConnected()) {
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.setType("text/html");
                            shareIntent.putExtra(Intent.EXTRA_EMAIL,"");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Andala-tsoratra masina avy amin'ny Ny Baiboliko");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<h2>"+getTitle()+"</h2> "+toStringVerset()));
                            startActivity(Intent.createChooser(shareIntent, getTitle()));
                        } else {
                            Toast.makeText(getActivity(),"Tsy mandeha ny internet-nao azafady!",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private String getUrl() {
        String url = "http://nybaiboliko.esy.es/web/"+getParamUrl();
        return url;
    }

    private String getParamUrl() {
        String title = "";
        switch (tabHost.getCurrentTab()) {
            case 0:
                title = globalVariable.bookRef.bookTitle + "/"+ globalVariable.bookRef.chapitre+"/"+ globalVariable.bookRef.versetStart+"/"+ globalVariable.bookRef.versetLast;
                break;
            case 1:
                title = globalVariable.bookRef1.bookTitle + "/"+ globalVariable.bookRef1.chapitre+"/"+ globalVariable.bookRef1.versetStart+"/"+ globalVariable.bookRef1.versetLast;
                break;
            case 2:
                title = globalVariable.bookRef2.bookTitle + "/"+ globalVariable.bookRef2.chapitre+"/"+ globalVariable.bookRef2.versetStart+"/"+ globalVariable.bookRef2.versetLast;
                break;
        }
        return title;
    }
    private String getTitle() {
        String titleToolbar = "";
        switch (tabHost.getCurrentTab()) {
            case 0:
                titleToolbar = globalVariable.bookRef.bookTitle + " "+ globalVariable.bookRef.chapitre+": "+ globalVariable.bookRef.versetStart+"-"+ globalVariable.bookRef.versetLast;

                if(globalVariable.bookRef.versetLast- globalVariable.bookRef.versetStart == 0) {
                    titleToolbar = globalVariable.bookRef.bookTitle + " "+ globalVariable.bookRef.chapitre+": "+ globalVariable.bookRef.versetStart;
                }
                break;
            case 1:
                titleToolbar = globalVariable.bookRef1.bookTitle + " "+ globalVariable.bookRef1.chapitre+": "+ globalVariable.bookRef1.versetStart+"-"+ globalVariable.bookRef1.versetLast;

                if(globalVariable.bookRef1.versetLast- globalVariable.bookRef1.versetStart == 0) {
                    titleToolbar = globalVariable.bookRef1.bookTitle + " "+ globalVariable.bookRef1.chapitre+": "+ globalVariable.bookRef1.versetStart;
                }
                break;
            case 2:
                titleToolbar = globalVariable.bookRef2.bookTitle + " "+ globalVariable.bookRef2.chapitre+": "+ globalVariable.bookRef2.versetStart+"-"+ globalVariable.bookRef2.versetLast;

                if(globalVariable.bookRef2.versetLast- globalVariable.bookRef2.versetStart == 0) {
                    titleToolbar = globalVariable.bookRef2.bookTitle + " "+ globalVariable.bookRef2.chapitre+": "+ globalVariable.bookRef2.versetStart;
                }
                break;
        }
        return titleToolbar;
    }

    private String toStringVerset() {
        String buffer = "";
        switch (tabHost.getCurrentTab()) {
            case 0:
                buffer = toString(versets);
                break;
            case 1:
                buffer = toString(versets2);
                break;
            case 2:
                buffer = toString(versets3);
                break;
        }
        return buffer;
    }

    private String toString(List<Verset> liste){
        String result = "";
        for (Verset v: liste) {
            result = result + v.getVersetNumber() + ". "+ v.getVersetText()+" ";
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}