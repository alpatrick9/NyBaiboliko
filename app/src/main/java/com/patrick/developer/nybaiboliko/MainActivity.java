package com.patrick.developer.nybaiboliko;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.patrick.developer.nybaiboliko.adapter.SlideMenuAdapter;
import com.patrick.developer.nybaiboliko.fragment.BaibolyFragment;

import java.util.ArrayList;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.menu_layout)
    private DrawerLayout menuLayout;

    @InjectView(R.id.menu_elements)
    private ListView menuElementsList;

    private ActionBarDrawerToggle menuToggle;
    //element du Slider Menu
    private ArrayList<String> menuListe = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        creationSlideMenu();

        getContentView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Methoe slide menu
     */
    protected void creationSlideMenu(){

        /**
         * Initialisation des liste du slide menu
         */
        menuListe.add(this.getResources().getString(R.string.bible));
        menuListe.add(this.getResources().getString(R.string.ffpm));
        menuListe.add(this.getResources().getString(R.string.ff));

        /**
         * Creation de l'entete du slide menu
         */
        View headerView = getLayoutInflater().inflate(
                R.layout.entente_slide_menu, menuElementsList, false);
        menuElementsList.addHeaderView(headerView,null,false);

        SlideMenuAdapter adapterMenu = new SlideMenuAdapter(menuListe, this);
        menuElementsList.setAdapter(adapterMenu);

        /**
         * Mise en place du slide menu
         */
        menuToggle = new ActionBarDrawerToggle(this,menuLayout,toolbar, R.string.drawer_open,R.string.drawer_close) {
            /**
             * action à la fermeture
             * @param view
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            /**
             * Action à l'ouverture
             * @param drawerView
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        menuLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();

        //listner pour les elements du slider menu
        /*menuElementsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        replaceFragment(new AccueilFragment());
                        break;
                    case 2:
                        if(globalClass.getSection().equals("accueil")) replaceFragment(new RechercheFragment());
                        else if(globalClass.getSection().equals("prospects")) replaceFragment(new RechercheProspects());
                        else replaceFragment(new RechercherParSectionFragment());
                        break;
                    case 3:
                        replaceFragment(new ParametreFragment());
                        break;
                    case 4:
                        new BoiteDeDialogue(BaseActivity.this).createDialogueApropos(getResources().getString(R.string.apropos),getResources().getString(R.string.apropos_message,versionInfo));
                        break;
                    case 5:
                        new BoiteDeDialogue(BaseActivity.this).boiteDialogeLogOut();
                        break;
                }

                menuLayout.closeDrawer(menuElementsList);
            }
        });*/

    }

    protected void getContentView() {
        Fragment fragment = new BaibolyFragment();
        replaceFragment(fragment);
    }

    protected void replaceFragment(Fragment fragment){
        if (fragment != null) {
            RelativeLayout maLayout = (RelativeLayout) findViewById(R.id.contenaire);
            maLayout.removeAllViews();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenaire, fragment).commit();
        }
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new BoiteDeDialogue(this).boiteDialogeQuitte();
        }
        return super.onKeyDown(keyCode, event);
    }*/

}
