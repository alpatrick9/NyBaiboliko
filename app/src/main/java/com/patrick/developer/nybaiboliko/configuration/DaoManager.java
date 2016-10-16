package com.patrick.developer.nybaiboliko.configuration;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.patrick.developer.nybaiboliko.models.Fihirana;
import com.patrick.developer.nybaiboliko.models.HistoriqueFihirana;
import com.patrick.developer.nybaiboliko.models.Verset;

import java.sql.SQLException;

/**
 * Created by developer on 10/5/16.
 */

public class DaoManager {

    SqliteHelper sqliteHelper;

    Dao<Verset, Long> versetDao;

    Dao<Fihirana, String> fihiranaDao;

    Dao<HistoriqueFihirana, Long> historiqueFihiranaDao;

    public DaoManager(Context context) {
        sqliteHelper = OpenHelperManager.getHelper(context, SqliteHelper.class);
    }

    public Dao<Verset, Long> getVersetDao() throws SQLException {
        if(versetDao == null)
            versetDao = sqliteHelper.getDao(Verset.class);
        return versetDao;
    }

    public Dao<Fihirana, String> getFihiranaDao() throws SQLException {
        if(fihiranaDao == null)
            fihiranaDao = sqliteHelper.getDao(Fihirana.class);
        return fihiranaDao;
    }

    public Dao<HistoriqueFihirana, Long> getHistoriqueFihiranaDao() throws SQLException {
        if(historiqueFihiranaDao == null)
            historiqueFihiranaDao = sqliteHelper.getDao(HistoriqueFihirana.class);
        return historiqueFihiranaDao;
    }
}
