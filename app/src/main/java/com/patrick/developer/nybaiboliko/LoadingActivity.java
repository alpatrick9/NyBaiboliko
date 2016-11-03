package com.patrick.developer.nybaiboliko;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.misc.TransactionManager;
import com.patrick.developer.nybaiboliko.configuration.SqliteHelper;
import com.patrick.developer.nybaiboliko.dao.FihiranaDao;
import com.patrick.developer.nybaiboliko.dao.VersetDao;
import com.patrick.developer.nybaiboliko.models.entity.Fihirana;
import com.patrick.developer.nybaiboliko.models.entity.Verset;
import com.patrick.developer.nybaiboliko.tools.JsonParser;
import com.patrick.developer.nybaiboliko.tools.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * Created by developer on 10/21/16.
 */

public class LoadingActivity extends Activity {

    protected VersetDao versetDao;

    protected FihiranaDao fihiranaDao;

    ProgressDialog myProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        RelativeLayout view = (RelativeLayout) findViewById(R.id.loading);

        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.start_anim);
        view.setAnimation(scaleUp);

        versetDao = new VersetDao(this);

        fihiranaDao = new FihiranaDao(this);

        try {
            initializeData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initializeData() throws SQLException {

        final SqliteHelper sqliteHelper = OpenHelperManager.getHelper(this, SqliteHelper.class);

        sqliteHelper.createTableIfNotExist(this);

        final Handler handler = new Handler();

        final Runnable run = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                finish();
            }
        };

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
                handler.postDelayed(run, 1100);
            }
        };

        myProgressDialog = ProgressDialog.show(LoadingActivity.this, "", "Miandrasa kely azafady ...", true);
        thread.start();
    }

    public void insertDataBase(SqliteHelper sqliteHelper) throws SQLException, IOException, JSONException {
        TransactionManager.callInTransaction(sqliteHelper.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {

                if (versetDao.countRow() == 0) {
                    String json = "";
                    JSONArray array = null;
                    json = new JsonParser().getJsonFile(LoadingActivity.this, "baiboly");
                    if (json != null) {
                        array = new JSONArray(json);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Verset verset = new Verset(object.getString("livre"), object.getInt("chapitre"), object.getInt("verset"), object.getString("text"), object.getString("note"));
                            versetDao.create(verset);
                        }
                    }
                }

                if (fihiranaDao.countRow() == 0) {
                    String json = "";
                    JSONArray array = null;
                    json = new JsonParser().getJsonFile(LoadingActivity.this, "fihirana");
                    if (json != null) {
                        array = new JSONArray(json);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Fihirana fihirana = new Fihirana(object.getString("id"), object.getString("title"), object.getString("content"));
                            fihiranaDao.create(fihirana);
                        }
                    }
                }

                return null;
            }
        });
    }
}
