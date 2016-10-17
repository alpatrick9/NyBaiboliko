package com.patrick.developer.nybaiboliko.configuration;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.patrick.developer.nybaiboliko.models.Fihirana;
import com.patrick.developer.nybaiboliko.models.HistoryFihirana;
import com.patrick.developer.nybaiboliko.models.HistoryVerset;
import com.patrick.developer.nybaiboliko.models.Verset;

import java.sql.SQLException;

/**
 * Created by developer on 10/5/16.
 */

public class DaoManager {

    SqliteHelper sqliteHelper;

    Dao<Verset, Long> versetDao;

    Dao<Fihirana, String> fihiranaDao;

    Dao<HistoryFihirana, Long> historyFihiranaDao;

    Dao<HistoryVerset, Long> historyVersetDao;



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

    public Dao<HistoryFihirana, Long> getHistoryFihiranaDao() throws SQLException {
        if(historyFihiranaDao == null)
            historyFihiranaDao = sqliteHelper.getDao(HistoryFihirana.class);
        return historyFihiranaDao;
    }

    public Dao<HistoryVerset, Long> getHistoryVersetDao() throws SQLException {
        if(historyVersetDao == null)
            historyVersetDao = sqliteHelper.getDao(HistoryVerset.class);
        return historyVersetDao;
    }
}
