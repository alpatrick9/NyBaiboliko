package com.patrick.developer.nybaiboliko;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.misc.TransactionManager;
import com.patrick.developer.nybaiboliko.adapter.SlideMenuAdapter;
import com.patrick.developer.nybaiboliko.configuration.SqliteHelper;
import com.patrick.developer.nybaiboliko.dao.FihiranaDao;
import com.patrick.developer.nybaiboliko.dao.VersetDao;
import com.patrick.developer.nybaiboliko.fragment.Song.CheckFihiranaFragment;
import com.patrick.developer.nybaiboliko.fragment.Song.FihiranaFfpmFragment;
import com.patrick.developer.nybaiboliko.fragment.bible.CheckVersetBibleFragment;
import com.patrick.developer.nybaiboliko.fragment.find.FindFragment;
import com.patrick.developer.nybaiboliko.models.Fihirana;
import com.patrick.developer.nybaiboliko.models.Verset;
import com.patrick.developer.nybaiboliko.tools.DialogBox;
import com.patrick.developer.nybaiboliko.tools.JsonParser;
import com.patrick.developer.nybaiboliko.tools.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class MainActivity extends Activity {

    protected VersetDao versetDao;

    protected FihiranaDao fihiranaDao;

    protected Toolbar toolbar;

    protected DrawerLayout menuLayout;

    protected ListView menuElementsList;

    protected ActionBarDrawerToggle menuToggle;

    protected ArrayList<String> menuListe = new ArrayList<String>();

    ProgressDialog myProgressDialog = null;

    private InputMethodManager gestionClavier = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gestionClavier = (InputMethodManager)getSystemService(
                INPUT_METHOD_SERVICE);

        setView();

        setToolbar();

        versetDao = new VersetDao(this);

        fihiranaDao = new FihiranaDao(this);

        creationSlideMenu();

        getContentView();

        try {
            initializeData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void setView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        menuLayout = (DrawerLayout)findViewById(R.id.menu_layout);

        menuElementsList = (ListView)findViewById(R.id.menu_elements);
    }

    protected void setToolbar() {
    }

    /**
     * Methoe slide menu
     */
    protected void creationSlideMenu() {

        /**
         * Initialisation des liste du slide menu
         */
        menuListe.add(this.getResources().getString(R.string.bible));
        menuListe.add(this.getResources().getString(R.string.ffpm));
        menuListe.add(this.getResources().getString(R.string.ff));
        menuListe.add(this.getResources().getString(R.string.find));

        /**
         * Creation de l'entete du slide menu
         */
        View headerView = getLayoutInflater().inflate(R.layout.entente_slide_menu, menuElementsList, false);

        menuElementsList.addHeaderView(headerView, null, false);

        TextView copyRigthTextView = (TextView) getLayoutInflater().inflate(R.layout.footer_slide_menu,null);

        copyRigthTextView.setText(Html.fromHtml(infoApp()));

        menuElementsList.addFooterView(copyRigthTextView);

        SlideMenuAdapter adapterMenu = new SlideMenuAdapter(menuListe, this);
        menuElementsList.setAdapter(adapterMenu);

        /**
         * Mise en place du slide menu
         */
        menuToggle = new ActionBarDrawerToggle(this, menuLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
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
                //Fermer clavier virtuel
                gestionClavier.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                invalidateOptionsMenu();
            }
        };

        menuLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();

        menuElementsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                menuLayout.closeDrawer(menuElementsList);
                Fragment fragment = null;
                switch (position) {
                    case 1:
                        fragment =new CheckVersetBibleFragment();
                        break;
                    case 2:
                        fragment = new CheckFihiranaFragment();

                        Bundle bundleFfpm = new Bundle();
                        bundleFfpm.putString("type","ffpm");

                        fragment.setArguments(bundleFfpm);
                        break;
                    case 3:
                        fragment = new CheckFihiranaFragment();

                        Bundle bundleFf = new Bundle();
                        bundleFf.putString("type","ff");

                        fragment.setArguments(bundleFf);
                        break;
                    case 4:
                        fragment = new FindFragment();
                        break;
                }
                replaceFragment(fragment);
            }
        });

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

    public void initializeData() throws SQLException {

        final SqliteHelper sqliteHelper = OpenHelperManager.getHelper(this, SqliteHelper.class);


            final Handler handler = new Handler();

            /*final Runnable msg = new Runnable() {
                @Override
                public void run() {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.message), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };*/

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        insertDataBase(sqliteHelper);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    myProgressDialog.dismiss();
                    /**********************************/
                    //handler.post(msg);
                }
            };

            myProgressDialog = ProgressDialog.show(MainActivity.this,"", "Miandrasa kely azafady ...", true);
            thread.start();
    }

    public void insertDataBase(SqliteHelper sqliteHelper) throws SQLException, IOException, JSONException{
        TransactionManager.callInTransaction(sqliteHelper.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {

                if (versetDao.countRow() == 0) {
                    String json = "";
                    JSONArray array = null;
                    json = new JsonParser().getJsonFile(MainActivity.this, "baiboly");
                    if (json != null) {
                        array = new JSONArray(json);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Verset verset = new Verset(new Tools(MainActivity.this).formatTitleBook(object.getString("livre")), object.getInt("chapitre"), object.getInt("verset"), object.getString("text"), object.getString("note"));
                            versetDao.create(verset);
                        }
                    }
                }

                if(fihiranaDao.countRow() == 0) {
                    String json = "";
                    JSONArray array = null;
                    json = new JsonParser().getJsonFile(MainActivity.this, "fihirana");
                    if (json != null) {
                        array = new JSONArray(json);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Fihirana fihirana = new Fihirana(object.getString("id"),object.getString("title"),object.getString("content"));
                            fihiranaDao.create(fihirana);
                        }
                    }
                }

                return null;
            }
        });
    }

    public String infoApp() {
        String copyright = "Alain Patrick Rajaonarison &copy; 2016";

        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            copyright = copyright+" v"+info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return copyright;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new DialogBox(this).leave();
        }
        return super.onKeyDown(keyCode, event);
    }

}
