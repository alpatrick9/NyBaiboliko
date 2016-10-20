package com.patrick.developer.nybaiboliko.configuration;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.patrick.developer.nybaiboliko.dao.HistoryFihiranaDao;
import com.patrick.developer.nybaiboliko.dao.HistoryVersetDao;
import com.patrick.developer.nybaiboliko.models.entity.Fihirana;
import com.patrick.developer.nybaiboliko.models.entity.HistoryFihirana;
import com.patrick.developer.nybaiboliko.models.entity.HistoryVerset;
import com.patrick.developer.nybaiboliko.models.entity.Verset;

import java.sql.SQLException;

/**
 * Created by developer on 10/5/16.
 */

public class SqliteHelper extends OrmLiteSqliteOpenHelper {
    protected static final String DATABASE_NAME = "bible";
    protected static final int DATABASE_VERSION = 1;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            /**
             * creates the Verset database table
             */
            TableUtils.createTable(connectionSource, Verset.class);
            TableUtils.createTable(connectionSource, Fihirana.class);
            TableUtils.createTable(connectionSource, HistoryFihirana.class);
            TableUtils.createTable(connectionSource, HistoryVerset.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            /**
             * Recreates the database when onUpgrade is called by the framework
             */
            TableUtils.dropTable(connectionSource, Verset.class, false);
            TableUtils.dropTable(connectionSource, Fihirana.class, false);
            TableUtils.dropTable(connectionSource, HistoryFihirana.class, false);
            TableUtils.dropTable(connectionSource, HistoryVerset.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTableIfNotExist(Context context) throws SQLException {

        HistoryFihiranaDao historyFihiranaDao = new HistoryFihiranaDao(context);

        HistoryVersetDao historyVersetDao = new HistoryVersetDao(context);

        if(!historyFihiranaDao.isTableExist())
            TableUtils.createTable(connectionSource, HistoryFihirana.class);

        if(!historyVersetDao.isTableExist())
            TableUtils.createTable(connectionSource, HistoryVerset.class);
    }
}