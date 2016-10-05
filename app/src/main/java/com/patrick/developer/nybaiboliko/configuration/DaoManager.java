package com.patrick.developer.nybaiboliko.configuration;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.patrick.developer.nybaiboliko.models.Verset;

import java.sql.SQLException;

/**
 * Created by developer on 10/5/16.
 */

public class DaoManager {

    SqliteHelper sqliteHelper;

    Dao<Verset, Long> versetDao;

    public DaoManager(Context context) {
        sqliteHelper = OpenHelperManager.getHelper(context, SqliteHelper.class);
    }

    public Dao<Verset, Long> getVersetDao() throws SQLException {
        if(versetDao == null)
            versetDao = sqliteHelper.getDao(Verset.class);
        return versetDao;
    }
}
