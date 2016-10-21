package com.patrick.developer.nybaiboliko;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.misc.TransactionManager;
import com.patrick.developer.nybaiboliko.adapter.SlideMenuAdapter;
import com.patrick.developer.nybaiboliko.configuration.SqliteHelper;
import com.patrick.developer.nybaiboliko.dao.FihiranaDao;
import com.patrick.developer.nybaiboliko.dao.VersetDao;
import com.patrick.developer.nybaiboliko.fragment.Song.CheckFihiranaFragment;
import com.patrick.developer.nybaiboliko.fragment.bible.BibleFragment;
import com.patrick.developer.nybaiboliko.fragment.bible.CheckVersetBibleFragment;
import com.patrick.developer.nybaiboliko.fragment.find.FindFragment;
import com.patrick.developer.nybaiboliko.fragment.historique.HistoryFragment;
import com.patrick.developer.nybaiboliko.models.entity.Fihirana;
import com.patrick.developer.nybaiboliko.models.entity.Verset;
import com.patrick.developer.nybaiboliko.tools.DialogBox;
import com.patrick.developer.nybaiboliko.tools.GlobalVariable;
import com.patrick.developer.nybaiboliko.tools.JsonParser;
import com.patrick.developer.nybaiboliko.tools.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    protected Toolbar toolbar;

    private InputMethodManager gestionClavier = null;

    GlobalVariable globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gestionClavier = (InputMethodManager)getSystemService(
                INPUT_METHOD_SERVICE);

        globalVariable = (GlobalVariable)getApplicationContext();

        resetVariable();

        setView();

        setNavigation();

        getContentView();

    }

    protected void setView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    protected void setNavigation() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                gestionClavier.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView textView = (TextView)navigationView.getHeaderView(0).findViewById(R.id.copyright);
        textView.setText(Html.fromHtml(infoApp()));

    }

    protected void getContentView() {
        Fragment fragment = new CheckVersetBibleFragment();
        replaceFragment(fragment);
    }

    protected void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            RelativeLayout maLayout = (RelativeLayout) findViewById(R.id.contenaire);
            maLayout.removeAllViews();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contenaire, fragment).commit();
        }
    }

    public String infoApp() {
        String copyright = "&copy; 2016";

        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            copyright = copyright+" v"+info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return copyright;
    }

    private void resetVariable() {
        globalVariable.nbBook = 0;
        globalVariable.numTabBook = globalVariable.nbBook;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new DialogBox(this).leave();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        toolbar.getMenu().clear();

        resetVariable();

        switch (item.getItemId()) {
            case R.id.nav_bible:
                fragment = new CheckVersetBibleFragment();
                break;
            case R.id.nav_ffpm:
                fragment = new CheckFihiranaFragment();

                Bundle bundleFfpm = new Bundle();
                bundleFfpm.putString("type","ffpm");

                fragment.setArguments(bundleFfpm);
                break;
            case R.id.nav_ff:
                fragment = new CheckFihiranaFragment();

                Bundle bundleFf = new Bundle();
                bundleFf.putString("type","ff");

                fragment.setArguments(bundleFf);
                break;
            case R.id.nav_story:
                fragment = new HistoryFragment();

                Bundle bundleHistory = new Bundle();
                bundleHistory.putInt("tabHostId",0);

                fragment.setArguments(bundleHistory);
                break;
            case R.id.nav_find:
                fragment = new FindFragment();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        replaceFragment(fragment);
        return true;
    }
}
