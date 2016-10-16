package com.patrick.developer.nybaiboliko.dao;

import android.content.Context;

import com.patrick.developer.nybaiboliko.configuration.DaoManager;
import com.patrick.developer.nybaiboliko.models.HistoriqueFihirana;

import java.sql.SQLException;

/**
 * Created by patmi on 16/10/2016.
 */
public class HistoriqueFihiranaDao extends AbstractDao<HistoriqueFihirana,Long> {

    Context context;

    public HistoriqueFihiranaDao(Context context) {
        this.context = context;
        try {
            this.dao = new DaoManager(context).getHistoriqueFihiranaDao();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
